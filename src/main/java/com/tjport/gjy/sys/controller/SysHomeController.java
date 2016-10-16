package com.tjport.gjy.sys.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.tjport.common.model.TreeGridResult;
import com.tjport.common.model.TreeNodeAttributes;
import com.tjport.common.spring.BaseController;
import com.tjport.gjy.security.ShiroPrincipal;
import com.tjport.gjy.sys.dao.IResourceDao;
import com.tjport.gjy.sys.model.Resource;
import com.tjport.gjy.sys.model.User;

@Controller
@RequestMapping(SysHomeController.BASE)
public class SysHomeController extends BaseController {
	final static String BASE = "sys";

	@Autowired
	private IResourceDao resourceDao;

	@RequestMapping(value = "sysHome/{pid}")
	public String sysHome(@PathVariable String pid, Map<String, Object> map) {

		// return "success"; //跳转到success页面
		Subject subject = SecurityUtils.getSubject();
		ShiroPrincipal principal = (ShiroPrincipal) subject.getPrincipal();
		List<Resource> authResources = principal.getResources();

		List<Resource> resources = resourceDao.getSubResources(pid);
		List<TreeGridResult> menu = new ArrayList<TreeGridResult>();
		for (Resource resource : resources) {

			for (Resource authResource : authResources) {

				if (authResource.equals(resource)) {
					TreeGridResult node = new TreeGridResult();
					node.setId(resource.getId());
					node.setText(resource.getName());
					node.setIconCls(resource.getIcon());
					node.setAttributes(new TreeNodeAttributes(resource.getUrl()));
					node.setState("open");
					menu.add(node);
					break;
				}

			}
		}

		User user = principal.getUser();
		map.put("menu", JSON.toJSONString(menu));
		map.put("userName", user.getName());
		map.put("topName", user.getOrg().getName());

		return BASE + "/sysHome";
	}

	@ResponseBody
	@RequestMapping(value="getSysHomeSubMenu/{pid}")
	public List<TreeGridResult> getSysHomeSubMenu(@PathVariable String pid) {

		Subject subject = SecurityUtils.getSubject();
		ShiroPrincipal principal = (ShiroPrincipal) subject.getPrincipal();
		List<Resource> authResources = principal.getResources();

		List<Resource> resources = resourceDao.getSubResources(pid);
		List<TreeGridResult> menu = new ArrayList<TreeGridResult>();
		for (Resource resource : resources) {

			for (Resource authResource : authResources) {

				if (authResource.equals(resource)) {
					TreeGridResult node = new TreeGridResult();
					node.setId(resource.getId());
					node.setText(resource.getName());
					node.setIconCls(resource.getIcon());
					node.setAttributes(new TreeNodeAttributes(resource.getUrl()));
					node.setState("open");
					menu.add(node);
					break;
				}

			}
		}

		return menu;
	}

}
