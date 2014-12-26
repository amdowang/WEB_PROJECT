package com.jst.shiroDemo.DAO;

import com.jst.common.DAO.IBaseDAO;
import com.jst.shiroDemo.model.User;

public interface IUserDAO extends IBaseDAO<User>{
	public User findUserByLoginName(String loginName);
}
