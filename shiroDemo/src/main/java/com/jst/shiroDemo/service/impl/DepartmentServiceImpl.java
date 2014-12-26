package com.jst.shiroDemo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jst.common.service.impl.BaseServiceImpl;
import com.jst.shiroDemo.DAO.IDepartmentDAO;
import com.jst.shiroDemo.model.Department;
import com.jst.shiroDemo.service.IDepartmentService;

@Service
public class DepartmentServiceImpl extends BaseServiceImpl<Department> implements  IDepartmentService{
	@Autowired
	public void setBaseDAO(IDepartmentDAO dao){
		super.dao = dao;
	}
}
 