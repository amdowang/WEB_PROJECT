/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.jst.common.service.impl;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.jst.shiroDemo.model.User;
import com.jst.shiroDemo.service.IUserService;

public class ShiroDbRealm extends AuthorizingRealm {
	protected IUserService userService;

	/**
	 * 认证回调函数,登录时调用.
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken authcToken) throws AuthenticationException {
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		User user = userService.findUserByLoginName(token.getUsername());

		if (user != null) {
			return new SimpleAuthenticationInfo(user.getUserCode(),
					user.getPassword(), getName());
		}
		// if (user != null) {
		// byte[] salt = Encodes.decodeHex(user.getSalt());
		// return new SimpleAuthenticationInfo(user.getUserCode(),
		// user.getPassword(), ByteSource.Util.bytes(salt), getName());
		// }
		return null;
	}

	/**
	 * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用.
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		String userCode = (String) principals.getPrimaryPrincipal();
		// User user = userService.findUserByLoginName(shiroUser.loginName);
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		//info.addRole("user");
		//admin用户权限最大，其它只能查看，如果是正式环境需要调用数据库
		if (userCode.equals("admin")) {
			info.addStringPermission("user:add");
			info.addStringPermission("user:edit");
			info.addStringPermission("user:update");
			info.addStringPermission("user:delete");
			info.addStringPermission("user:select");
		} else {
			info.addStringPermission("user:select");
		}
		// info.addRoles(user.getRoleList());
		return info;
	}

	@Autowired
	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

}
