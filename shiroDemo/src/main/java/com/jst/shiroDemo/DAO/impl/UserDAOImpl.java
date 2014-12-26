package com.jst.shiroDemo.DAO.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.jst.common.DAO.PropertyFilter;
import com.jst.common.DAO.impl.BaseDAOImpl;
import com.jst.common.model.Page;
import com.jst.shiroDemo.DAO.IUserDAO;
import com.jst.shiroDemo.model.User;

@Repository
public class UserDAOImpl extends BaseDAOImpl<User> implements IUserDAO {

	public User findUserByLoginName(String loginName){
		Query query = getCurrentSession().createQuery("from User as  model where model.userCode = ?");
		query.setParameter(0, loginName);
		List list = query.list();
		if(list!=null && !list.isEmpty()){
			return (User)list.get(0);
		}
		return null;
	}
	/*public void testPro(String name){
		getCurrentSession().doReturningWork(new RetunDoWorkingss(name));
	}
	class RetunDoWorkingss implements ReturningWork<User>{
		private String name;
		RetunDoWorkingss(String name){
			this.name=name;
		}
		@Override
		public User execute(Connection arg0) throws SQLException {
			System.out.println(name);
			return null;
		}
		
	}*/
}
