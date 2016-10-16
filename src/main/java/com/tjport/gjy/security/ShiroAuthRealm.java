package com.tjport.gjy.security;


import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import com.tjport.common.utils.DesUtils;
import com.tjport.gjy.sys.dao.IUserDao;
import com.tjport.gjy.sys.model.Resource;
import com.tjport.gjy.sys.model.User;
import com.tjport.gjy.sys.service.IRoleService;



public class ShiroAuthRealm extends AuthorizingRealm {
	private static final Log logger = LogFactory.getLog(ShiroAuthRealm.class);

	@Autowired
	private IUserDao userDao;
	
	@Autowired
	private IRoleService roleService;

	/**
	 * 构造函数，设置安全的初始化信息
	 */
	public ShiroAuthRealm() {
		super();
		setAuthenticationTokenClass(UsernamePasswordToken.class);
	}

	/**
	 * 获取当前认证实体的授权信息（授权包括：角色、权限）
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		// 获取当前登录的用户名
		ShiroPrincipal subject = (ShiroPrincipal) super.getAvailablePrincipal(principals);
		String username = subject.getUsername();
		String userId = subject.getId();
		
		logger.info("用户【" + username + "】授权开始......");
		
		try {
			if (!subject.isAuthorized()) {
				if(subject.getUser().getIsAdmin()){
					userId  = null; 
				}
				// 根据用户名称，获取该用户所有的权限列表
				List<Resource> resources = roleService.getResourcesByUserId(userId);
				List<String> rolelist = roleService.getRolesName(userId);
				subject.setResources(resources);
				subject.setRoles(rolelist);
				subject.setAuthorized(true);
				
				logger.info("用户【" + username + "】授权初始化成功......");
				logger.info("用户【" + username + "】 角色列表为：" + subject.getRoles());
				logger.info("用户【" + username + "】 权限列表为：" + subject.getAuthorities());
			} else {
				logger.info("用户【" + username + "】已授权......");
			}
		} catch (RuntimeException e) {
			throw new AuthorizationException("用户【" + username + "】授权失败");
		}
		
		
		// 给当前用户设置权限
		info.addStringPermissions(subject.getStringPermissions());
		info.addRoles(subject.getRoles());
		
		
		return info;
	}

	/**
	 * 根据认证方式（如表单）获取用户名称、密码
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		UsernamePasswordToken upToken = (UsernamePasswordToken) token;
		String username = upToken.getUsername();
		String password = new String(upToken.getPassword());
		if (username == null) {
			logger.warn("用户名不能为空");
			throw new AccountException("用户名不能为空");
		}
		
		User user = null;
		try {
			user = userDao.findUserByUsername(username);
		} catch (Exception ex) {
			logger.error("获取用户失败\n" + ex.getMessage(),ex);
		}
		if (user == null) {
			logger.warn("用户不存在");
			throw new UnknownAccountException("用户不存在");
		}
		
		
		try {
			
			if (!DesUtils.encrypt(password).equals(user.getPassword())) {
				logger.warn("用户名密码错误 ");
				throw new UnknownAccountException("用户名密码错误");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/*if (!password.equals(user.getPassword())) {
			logger.warn("用户名密码错误 ");
			throw new UnknownAccountException("用户名密码错误");
		}*/
		
		ShiroPrincipal subject = new ShiroPrincipal(user);

		String uid = user.getId();
	
		if(user.getIsAdmin()){ 
			uid  = null; 
		}
		
		List<Resource> resources = roleService.getResourcesByUserId(uid);
		List<String> rolelist = roleService.getRolesName(uid);

		subject.setResources(resources);
		subject.setRoles(rolelist);
		subject.setAuthorized(true);

		logger.info("用户【" + username + "】登录成功");

	
		return new SimpleAuthenticationInfo(subject, user.getPassword(), getName());
	}

	@PostConstruct
	public void initCredentialsMatcher() {
		setCredentialsMatcher(new CustomCredentialsMatcher());
	}

	/**
	 * 将一些数据放到ShiroSession中,以便于其它地方使用
	 * 
	 * @see 比如Controller,使用时直接用HttpSession.getAttribute(key)就可以取到
	 */
	public void setSession(Object key, Object value) {
		Subject currentUser = SecurityUtils.getSubject();
		if (null != currentUser) {
			Session session = currentUser.getSession();
			System.out.println("Session默认超时时间为[" + session.getTimeout() + "]毫秒");
			if (null != session) {
				session.setAttribute(key, value);
			}
		}
	}

	class CustomCredentialsMatcher extends SimpleCredentialsMatcher {
		@Override
		public boolean doCredentialsMatch(AuthenticationToken authcToken, AuthenticationInfo info) {
			UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
			Object tokenCredentials = null;
			try {
				tokenCredentials = encrypt(String.valueOf(token.getPassword()));
				
				//tokenCredentials = token.getPassword();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			Object accountCredentials = getCredentials(info);
			// 将密码加密与系统加密后的密码校验，内容一致就返回true,不一致就返回false
			return equals(tokenCredentials, accountCredentials);
		}

		// 将传进来密码加密方法
		private String encrypt(String data) throws Exception {
					
			//return data;
			return DesUtils.encrypt(data);
		}
	}
}
