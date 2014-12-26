package com.jst.shiroDemo.web;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.jst.common.DAO.PropertyFilter;
import com.jst.common.DAO.PropertyFilter.MatchType;
import com.jst.common.json.EuiGrid;
import com.jst.common.json.Row;
import com.jst.common.model.JstFetch;
import com.jst.common.model.JstFetch.JstFetchModel;
import com.jst.common.model.Page;
import com.jst.common.web.BaseAction;
import com.jst.shiroDemo.model.User;
import com.jst.shiroDemo.service.IUserService;

public class UserAction extends BaseAction<User> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	public void setbaseService(IUserService baseService) {
		this.service = baseService;
	}
	public void list() throws Exception {
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		if (getModel() != null) {
			// PropertyFilter pf = new PropertyFilter("EQS_dictCode", typeCode);
			if(StringUtils.isNotBlank(getModel().getUserCode())){
				PropertyFilter pf = new PropertyFilter("userCode", MatchType.LIKE, getModel().getUserCode());
				filters.add(pf);
			}
			if(StringUtils.isNotBlank(getModel().getUserName())){
				PropertyFilter pf = new PropertyFilter("userName", MatchType.LIKE, getModel().getUserName());
				filters.add(pf);
			}
//			PropertyFilter pf = new PropertyFilter("dept.id", MatchType.BETWEEN, Arrays.asList(new Long(1),new Long(4)));
//			filters.add(pf);
		}
		JstFetch fetch = new JstFetch("dept", JstFetchModel.JOIN);
		Page<User> inpage = new Page<User>();
		setPages(inpage);
		service.getPage(inpage, filters,fetch);
		EuiGrid eg = new EuiGrid();
		eg.setRowCount(inpage.getTotalCount());
		List<User> userList = inpage.getResult();
		for (User tmpUser:userList) {
			Row row = eg.createRowInstance();
			row.addCell("id", tmpUser.getId());
			row.addCell("userCode", tmpUser.getUserCode());
			row.addCell("userName", tmpUser.getUserName());
			row.addCell("deptName", tmpUser.getDept()!=null?tmpUser.getDept().getDeptName():"");
			row.addCell("state", tmpUser.getState()==1?"有效":"无效");
			eg.addRow(row);
		}
		writerJson(eg.toString());
	}
	@Override
	public String getPremissionModelCode() {
		return "user";
	}

}
