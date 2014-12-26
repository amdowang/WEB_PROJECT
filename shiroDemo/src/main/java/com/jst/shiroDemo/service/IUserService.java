package com.jst.shiroDemo.service;

import com.jst.common.service.IBaseService;
import com.jst.shiroDemo.model.Department;
import com.jst.shiroDemo.model.User;

public interface IUserService extends IBaseService<User>{
	public static final String HASH_ALGORITHM = "SHA-1";
	public static final int HASH_INTERATIONS = 1024;
	public User findUserByLoginName(String loginName);
}
