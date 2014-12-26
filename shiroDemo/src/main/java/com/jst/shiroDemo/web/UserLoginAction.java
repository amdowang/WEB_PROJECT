package com.jst.shiroDemo.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;

import com.jst.common.web.BaseAction;
import com.jst.shiroDemo.model.User;
import com.jst.shiroDemo.util.EncryptUtils;

public class UserLoginAction extends BaseAction<User> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Log log = LogFactory.getLog(UserLoginAction.class);
	private User user;
	private String verifyCode;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getVerifyCode() {
		return verifyCode;
	}

	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}

	public String login() throws Exception {
		// SecurityUtils.getSubject().login(new
		// UsernamePasswordToken(user.getUserCode(), user.getPassword()));
		try {
			// 刷新主页面，如果用户已登陆，不需要重新登陆
			if (session.getAttribute("verifyCode") == null
					&& currentUser.isAuthenticated()) {
				log.debug("userLogin_login: 用户已登录，无需重新验证");
				return SUCCESS;
			}

			// 判断验证码
			if (verifyCode == null || !verifyCode.equals((String) session.getAttribute("verifyCode"))) {
				addActionMessage("验证码错误");
				log.debug("验证码错误");
				return LOGIN;
			}
			// 清除验证码
			session.removeAttribute("verifyCode");
			// 判断用户名密码
//			UsernamePasswordToken token = new UsernamePasswordToken(user.getUserCode(), user.getPassword());
			//md5加密
			 UsernamePasswordToken token = new UsernamePasswordToken(
			 user.getUserCode(),EncryptUtils.encryptMD5(user.getPassword()));
			 //token.setRememberMe(true);
			try {
				currentUser.login(token);
				// ((DefaultWebSecurityManager)SecurityUtils.getSecurityManager()).getSessionManager().getSession(key)
				currentUser.getSession().setAttribute("USERNAME", user.getUserCode());
			} catch (AuthenticationException e) {
				log.info(e, e);
				addActionMessage("用户名或密码错误");
				return LOGIN;
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error(e, e);
		}
		return LOGIN;
	}

	public String logout() throws Exception {
		currentUser.logout();
		return LOGIN;
	}

	@Override
	public String getPremissionModelCode() {
		return null;
	}
}
