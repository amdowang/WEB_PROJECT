package com.jst.common.web;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.jst.common.json.EuiGrid;
import com.jst.common.json.WriterUtil;
import com.jst.common.model.Page;
import com.jst.common.service.IBaseService;
import com.opensymphony.xwork2.ActionSupport;

public abstract class BaseAction<T> extends ActionSupport implements
		ServletRequestAware, ServletResponseAware {
	private final Log log = LogFactory.getLog(BaseAction.class);
	private int page = 0;
	private int rows = 0;
	private String sort = null;
	private String order = null;
	private static final long serialVersionUID = 1L;
	protected HttpServletRequest request;
	protected HttpServletResponse response;
	// 返回类型
	protected final String LIST = "list";
	protected final String EDIT = "edit";
	protected final String ADD = "add";
	protected T model;
	protected Long id;
	protected String ids;
	protected Class<T> modelClass;
	protected IBaseService<T> service; // 业务逻辑
	protected Subject currentUser;
	protected Session session;

	// public abstract IBaseService<T> getBaseService();
	protected void setPages(Page<T> tmppage) {
		tmppage.setPageNo(page);
		tmppage.setPageSize(rows);
		tmppage.setOrder(order);
		tmppage.setOrderBy(sort);
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public BaseAction() {
		ParameterizedType pt = (ParameterizedType) this.getClass()
				.getGenericSuperclass();
		modelClass = (Class) pt.getActualTypeArguments()[0];
		currentUser = SecurityUtils.getSubject();
		this.session = currentUser.getSession();
	}

	/**
	 * 继承BaseAction的action需要先设置这个方法，使其获得当前action的业务服务
	 * 
	 * @param service
	 */
	public void setService(IBaseService<T> baseService) {
		this.service = baseService;
	}

	/**
	 * 生成T实体类
	 * 
	 * @return T
	 */
	public T getModel() {
		try {
			if (model == null) {
				model = modelClass.newInstance();
			}
			return model;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void setServletResponse(HttpServletResponse arg0) {
		this.response = arg0;
	}

	@Override
	public void setServletRequest(HttpServletRequest arg0) {
		this.request = arg0;
	}

	public String toList() throws Exception {
		return LIST;
	}

	public String toAdd() throws Exception {
		if (currentUser.isPermitted(getPremissionModelCode() + ":add")) {
			return ADD;
		}
		WriterUtil.writerJson(response, "无权访问" + getPremissionModelCode()
				+ ":add");
		return null;
	}

	public String toEdit() throws Exception {
		try {
			model = (T) service.Query(id);
		} catch (Exception e) {
			log.error(e, e);
			WriterUtil.writerJson(response, EuiGrid.toErrorMsg(e.getMessage()));
		}
		return EDIT;
	}

	public String add() throws Exception {
		currentUser.isPermitted(getPremissionModelCode() + ":add");
		try {
			service.add(getModel());
			WriterUtil.writerJson(response, EuiGrid.toSuccessMsg("添加成功"));
		} catch (Exception e) {
			log.error(e, e);
			WriterUtil.writerJson(response, EuiGrid.toErrorMsg(e.getMessage()));
		}
		return null;
	}

	public String edit() throws Exception {
		currentUser.isPermitted(getPremissionModelCode() + ":edit");
		try {
			service.update(getModel());
			WriterUtil.writerJson(response, EuiGrid.toSuccessMsg("修改成功"));
		} catch (Exception e) {
			log.error(e, e);
			WriterUtil.writerJson(response, EuiGrid.toErrorMsg(e.getMessage()));
		}
		return null;
	}

	public String delete() throws Exception {
		currentUser.isPermitted(getPremissionModelCode() + ":delete");
		try {
			service.delete(id);
			writerJson(EuiGrid.toSuccessMsg("删除成功!"));
		} catch (Exception e) {
			log.error(e, e);
			writerJson(EuiGrid.toErrorMsg(e.getMessage()));
		}
		return null;
	}

	public String deletes() throws Exception {
		currentUser.isPermitted(getPremissionModelCode() + ":delete");
		try {
			String[] sIdArray = ids.split(",");
			Long[] lIdArary = new Long[sIdArray.length];
			for (int i = 0; i < sIdArray.length; i++) {
				lIdArary[i] = Long.parseLong(sIdArray[i]);
			}
			service.delete(lIdArary);
			writerJson(EuiGrid.toSuccessMsg("删除成功!"));
		} catch (Exception e) {
			log.error(e, e);
			writerJson(EuiGrid.toErrorMsg(e.getMessage()));
		}
		return null;
	}

	public void list() throws Exception {
		currentUser.isPermitted(getPremissionModelCode() + ":select");
	}

	protected void writerJson(String msg) throws IOException {
		WriterUtil.writerJson(response, msg);
	}

	public abstract String getPremissionModelCode();
}
