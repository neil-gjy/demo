package com.tjport.gjy.sys.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
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
import com.tjport.gjy.sys.dao.IResourceDao;
import com.tjport.gjy.sys.dao.IRoleDao;
import com.tjport.gjy.sys.model.Resource;
import com.tjport.gjy.sys.service.IRoleService;



@Controller  
@RequestMapping(ResourceController.BASE + "/" + ResourceController.PATH)  
public class ResourceController {  
    final static String BASE = "sys"; 
    final static String PATH = "resource";
	
	@Autowired
	private IResourceDao resourceDao;
	
	@Autowired
	private IRoleDao roleDao;
	
	@Autowired
	private IRoleService roleService;
	
    @RequestMapping("resourceList")  
    public String index() {  
    	System.out.println("index");
         //return "success"; //跳转到success页面   
         return BASE + "/" + PATH + "/resourceList";
    } 
    
    @RequestMapping(value="resourceDialog/{type}")  
    public String userDialog(@PathVariable String type, Map<String,Object> map) {  
    	 map.put("type", type);  
         //return "success"; //跳转到success页面   
         return BASE + "/" + PATH + "/resourceDialog";
    } 
    
    @ResponseBody
   	@RequestMapping("loadResourceList")
    public DatagridResult<Resource> loadResourceList(int page, int rows, String sort, String order, String customSearch)
    {
    	System.out.println("loadResourceList");
    	Page<Resource> ResourcePage = new Page<Resource>(page, rows);
    	ResourcePage.setOrderBy(sort);
    	ResourcePage.setOrder(order);
    	ResourcePage.setAutoCount(true);
      
    	
        if (StringUtils.isNoneBlank(customSearch))
        {
            
        	QueryFilter filter = JSON.parseObject(customSearch, QueryFilter.class);

        	
        	ResourcePage = resourceDao.findPage(ResourcePage, filter);
        }
        else
        {
        	ResourcePage = resourceDao.getAll(ResourcePage);        	
        }

        List<Resource> list = new ArrayList<Resource>();
        for(Resource resource : ResourcePage.getResult()){
        	list.add(resource);
        }
        DatagridResult<Resource> datagrid = new DatagridResult<Resource>();
        datagrid.setRows(list);
        datagrid.setTotal(ResourcePage.getTotalCount());

        return datagrid;
    }
    
    @ResponseBody
	@RequestMapping("resouceAdd")
	public Result add(Resource resource) {    	
    	Result mReturn = null;
		try {
			resourceDao.save(resource);
			System.out.println("resourceid"+resource.getId());
			mReturn = Result.successResult().setMsg("添加成功");
		} catch (Exception e) {
			//logger.error("添加失败", e);
			mReturn = Result.errorResult().setMsg(e.getMessage());
		}
		return mReturn;
	}
    
    @ResponseBody
	@RequestMapping("resourceEdit")
	public Result edit(Resource resource) {
    	Result mReturn = null;
		try {

			resourceDao.update(resource);
			mReturn = Result.successResult().setMsg("编辑成功");
		} catch (Exception e) {
			//logger.error("添加失败", e);
			mReturn = Result.errorResult().setMsg(e.getMessage());
		}
		return mReturn;
	}
    
    @ResponseBody
	@RequestMapping("resourceDel")
	public Result del(String id) {
    	Result mReturn = new Result();
		try {

			resourceDao.delete(id);
			mReturn = Result.successResult().setMsg("删除成功");
		} catch (Exception e) {
			//logger.error("添加失败", e);
			mReturn = Result.errorResult().setMsg(e.getMessage());
		}
		return mReturn;
	}
    
    @ResponseBody
   	@RequestMapping("loadAllResources")
    public DatagridResult<Resource> loadAllResources(String sort, String order)
    {
        List<Resource> list = resourceDao.findAll(sort, order);
       
        DatagridResult<Resource> datagrid = new DatagridResult<Resource>();
        datagrid.setRows(list);
        datagrid.setTotal(list.size());

        return datagrid;
    }
    
    @ResponseBody
   	@RequestMapping("getResourcesByRole")
    public Result getResourcesByRole(String id)
    {
        List<Resource> list = roleDao.getResourcesByRole(id);
       
        Result result = Result.successResult();
        result.setObj(list);
        
        return result;
    }
    
    @ResponseBody
   	@RequestMapping("grantResources")
   	public Result grantResources(String roleId, String[] resourceIds) {
       	Result mReturn = null;
   		try {
   			
   			roleService.grantResources(roleId, resourceIds);
   			
   			mReturn = Result.successResult().setMsg("保存成功！");
   		} catch (Exception e) {
   			
   			mReturn = Result.errorResult().setMsg(e.getMessage());
   		}
   		return mReturn;
   	}
}  