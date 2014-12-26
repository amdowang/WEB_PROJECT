package com.jst.shiroDemo.web;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.jst.common.DAO.PropertyFilter;
import com.jst.common.DAO.PropertyFilter.MatchType;
import com.jst.common.json.EuiGrid;
import com.jst.common.json.Row;
import com.jst.common.model.Page;
import com.jst.common.web.BaseAction;
import com.jst.shiroDemo.model.Department;
import com.jst.shiroDemo.model.User;
import com.jst.shiroDemo.service.IDepartmentService;
import com.jst.shiroDemo.service.IUserService;

public class DepartmentAction extends BaseAction<Department> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	public void setbaseService(IDepartmentService service) {
		this.service = service;
	}
	public void list() throws Exception {
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
//		if (getModel() != null) {
//			// PropertyFilter pf = new PropertyFilter("EQS_dictCode", typeCode);
//			if(StringUtils.isNotBlank(getModel().getUserCode())){
//				PropertyFilter pf = new PropertyFilter("userCode", MatchType.LIKE, getModel().getUserCode());
//				filters.add(pf);
//			}
//			if(StringUtils.isNotBlank(getModel().getUserName())){
//				PropertyFilter pf = new PropertyFilter("userName", MatchType.LIKE, getModel().getUserName());
//				filters.add(pf);
//			}
//		}
		Page<Department> inpage = new Page<Department>();
//		setPage(inpage);
//		service.getPage(inpage, filters);
//		EuiGrid eg = new EuiGrid();
//		eg.setRowCount(inpage.getTotalCount());
//		List<User> userList = inpage.getResult();
//		for (User tmpUser:userList) {
//			Row row = eg.createRowInstance();
//			row.addCell("id", tmpUser.getId());
//			row.addCell("userCode", tmpUser.getUserCode());
//			row.addCell("userName", tmpUser.getUserName());
//			row.addCell("deptName", "开发一部");
//			row.addCell("state", tmpUser.getState()==1?"有效":"无效");
//			eg.addRow(row);
//		}
//		writerJson(eg.toString());
	}
	@Override
	public String getPremissionModelCode() {
		// TODO Auto-generated method stub
		return null;
	}

}
