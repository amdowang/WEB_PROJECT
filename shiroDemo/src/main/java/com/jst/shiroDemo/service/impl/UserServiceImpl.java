package com.jst.shiroDemo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jst.common.service.impl.BaseServiceImpl;
import com.jst.common.util.Digests;
import com.jst.common.util.Encodes;
import com.jst.shiroDemo.DAO.IUserDAO;
import com.jst.shiroDemo.model.User;
import com.jst.shiroDemo.service.IUserService;
import com.jst.shiroDemo.util.EncryptUtils;

@Service
public class UserServiceImpl extends BaseServiceImpl<User> implements IUserService {
	private static final int SALT_SIZE = 8;
	@Autowired
	public void setBaseDAO(IUserDAO dao){
		super.dao = dao;
	}
	
	public User findUserByLoginName(String loginName){
		return ((IUserDAO)dao).findUserByLoginName(loginName);
	}
	
	@Override
	public void add(User t) {
		entryptPassword(t);
		super.add(t);
	}
	
	@Override
	public void update(User t) {
		entryptPassword(t);
		super.update(t);
	}

	/**
	 * 设定安全的密码，生成随机的salt并经过1024次 sha-1 hash
	 */
	private void entryptPassword(User user) {
//		byte[] salt = Digests.generateSalt(SALT_SIZE);
//		user.setSalt(Encodes.encodeHex(salt));
//		
//		byte[] hashPassword = Digests.sha1(user.getPassword().getBytes(), salt, HASH_INTERATIONS);
//		user.setPassword(Encodes.encodeHex(hashPassword));
		user.setPassword(EncryptUtils.encryptMD5(user.getPassword()));
	}
}
