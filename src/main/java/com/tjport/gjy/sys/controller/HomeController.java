package com.tjport.gjy.sys.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tjport.common.model.Result;
import com.tjport.common.model.TreeGridResult;
import com.tjport.common.model.TreeNodeAttributes;
import com.tjport.common.spring.BaseController;
import com.tjport.gjy.security.ShiroPrincipal;
import com.tjport.gjy.sys.dao.IResourceDao;
import com.tjport.gjy.sys.dao.IUserDao;
import com.tjport.gjy.sys.model.Resource;
import com.tjport.gjy.sys.model.User;


@Controller
@RequestMapping
public class HomeController extends BaseController {
	final static String BASE = "/";

	@Autowired
	private IResourceDao resourceDao;
	
	@Autowired
	private IUserDao userDao;
	
	@RequestMapping("home")
	public String index(Map<String,Object> map) {
		// return "success"; //跳转到success页面
		Subject subject = SecurityUtils.getSubject();
		ShiroPrincipal principal = (ShiroPrincipal) subject.getPrincipal();
		
		User user = principal.getUser();
		
		map.put("userName", user.getName());
		map.put("topName", user.getOrg().getName());
		
		return "home";
	}
	
	@RequestMapping("unauthorized")
	public String unauthorized() {
		
		return "unauthorized";
	}

	
	@ResponseBody
   	@RequestMapping("getHomeMenu")
    public List<TreeGridResult> getHomeMenu()
    {
		Subject subject = SecurityUtils.getSubject();
		
		ShiroPrincipal principal = (ShiroPrincipal) subject.getPrincipal();
		
		List<Resource> resources = principal.getResources();
		
		List<TreeGridResult> menu = new ArrayList<TreeGridResult>();
		
		for(Resource resource : resources){
			
			if (resource.getLevel().equals("1")){
				TreeGridResult node = new TreeGridResult();
				node.setId(resource.getId());
				node.setText(resource.getName());
				node.setIconCls(resource.getIcon());
				node.setAttributes(new TreeNodeAttributes(resource.getUrl()));
				node.setState("open");
				
				menu.add(node);
			}
			
		}
		
        return menu;
    }
	
	//努纳修改
	@ResponseBody
	@RequestMapping("homePassModify")
	public Result passModify(String oldPass, String newPass, String confirmPass){
		Result mReturn = null;
		
		//获取用户信息
    	ShiroPrincipal principal = (ShiroPrincipal) SecurityUtils.getSubject().getPrincipal(); 	
    	User user = principal.getUser();
    	
    	try{
    		userDao.passModify(user.getId(), oldPass, newPass, confirmPass);
    		mReturn = Result.successResult().setMsg("密码修改成功！");
    	}
    	catch(Exception e){
    		mReturn = Result.successResult().setMsg(e.getMessage());
    	}
		
		
		return mReturn;
	}
	
}
