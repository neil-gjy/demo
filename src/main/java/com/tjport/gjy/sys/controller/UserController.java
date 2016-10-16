package com.tjport.gjy.sys.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.tjport.common.model.DatagridResult;
import com.tjport.common.model.Result;
import com.tjport.common.query.Page;
import com.tjport.common.query.QueryFilter;
import com.tjport.common.query.Rule;
import com.tjport.gjy.security.ShiroPrincipal;
import com.tjport.gjy.sys.dao.IUserDao;
import com.tjport.gjy.sys.model.User;
import com.tjport.gjy.sys.service.IUserService;
import com.tjport.gjy.sys.vo.UserVo;



@Controller  
@RequestMapping(UserController.BASE + "/" + UserController.PATH)  
public class UserController {  
    final static String BASE = "sys"; 
    final static String PATH = "user";
	
	@Autowired
	private IUserDao userDao;
	
	@Autowired
	private IUserService userService;
	
	
    @RequestMapping("userList")  
    public String index() {  
         //return "success"; //跳转到success页面   
         return BASE + "/" + PATH + "/userList";
    } 
    
    @RequestMapping(value="userDialog/{type}")  
    public String userDialog(@PathVariable String type, Map<String,Object> map) {  
    	 map.put("type", type);  
         //return "success"; //跳转到success页面   
         return BASE + "/" + PATH + "/userDialog";
    } 
    
  
    @RequestMapping(value="userSelectionDialog")  
    public String orgSelectionDialog() {  

         return BASE + "/" + PATH + "/userSelectionDialog";
    } 
    
    @ResponseBody
   	@RequestMapping("loadUserList")
    public DatagridResult<UserVo> loadUserList(int page, int rows, String sort, String order, String customSearch) throws Exception
    {
    	//获取用户信息
    	ShiroPrincipal principal = (ShiroPrincipal) SecurityUtils.getSubject().getPrincipal(); 	
    	User currentUser = principal.getUser();
    	
    	// 分页参数
    	Page<User> userPage = new Page<User>(page, rows);
    	userPage.setOrderBy(sort);      // 排序字段
    	userPage.setOrder(order);		// 升序降序
    	userPage.setAutoCount(true);    // 计算总数
      
    	
        if (StringUtils.isNoneBlank(customSearch))
        {
        	QueryFilter filter = JSON.parseObject(customSearch, QueryFilter.class);

        	if (currentUser.getIsAdmin()) {
        		userPage = userDao.findPage(userPage, filter);
        	} else if(currentUser.getOrg() != null){
        		
	        	Rule[] rules = filter.getRules();
	        	Rule rule = new Rule("org.id","eq", currentUser.getOrg().getId());
	        	rules = Arrays.copyOf(rules, rules.length+1);
	        	rules[rules.length-1] = rule;
	        	filter.setRules(rules);
	        	
	        	userPage = userDao.findPage(userPage, filter);
        	}
        	
        	
        }
        else
        {
        	
        	
        	if (currentUser.getIsAdmin()) {
        		userPage = userDao.getAll(userPage);
        	} else if(currentUser.getOrg() != null){
        		
        		QueryFilter filter = new QueryFilter();
	        	
        		filter.setGroupOp("and");
	        	
	        	Rule rule = new Rule("org.id","eq", currentUser.getOrg().getId());
	        	Rule[] rules = new Rule[1];
	        	rules[0] = rule;
	        	filter.setRules(rules);
	        	
	        	userPage = userDao.findPage(userPage, filter);
        	}
        }
 
        // 查询结果转换为页面返回 结果
        List<UserVo> list = new ArrayList<UserVo>();
        for(User user : userPage.getResult()){
        	list.add(new UserVo(user));
        }
        
        DatagridResult<UserVo> datagrid = new DatagridResult<UserVo>();
        datagrid.setRows(list);
        datagrid.setTotal(userPage.getTotalCount());

        return datagrid;
    }
    
    
    @ResponseBody
	@RequestMapping("userAdd")
	public Result add(UserVo userVo) {
    	
    	
    	userVo.setCreateTime(new Date(System.currentTimeMillis()));
    	
    	Result mReturn = null;
		try {

			userService.save(userVo);
			
			mReturn = Result.successResult().setMsg("添加成功");
		} catch (Exception e) {
			//logger.error("添加失败", e);
			mReturn = Result.errorResult().setMsg(e.getMessage());
		}
		return mReturn;
	}
    
    @ResponseBody
	@RequestMapping("userEdit")
	public Result edit(UserVo userVo) {

    	userVo.setUpdateTime(new Date(System.currentTimeMillis()));
    	
    	Result mReturn = null;
		try {

			userService.update(userVo);
			
			mReturn = Result.successResult().setMsg("编辑成功");
		} catch (Exception e) {
			//logger.error("添加失败", e);
			mReturn = Result.errorResult().setMsg(e.getMessage());
		}
		return mReturn;
	}
    
    @ResponseBody
	@RequestMapping("userDel")
	public Result del(String id) {
    	Result mReturn = new Result();
		try {

			userDao.delete(id);
			mReturn = Result.successResult().setMsg("删除成功");
		} catch (Exception e) {
			//logger.error("添加失败", e);
			mReturn = Result.errorResult().setMsg(e.getMessage());
		}
		return mReturn;
	}
    
    
  
}  