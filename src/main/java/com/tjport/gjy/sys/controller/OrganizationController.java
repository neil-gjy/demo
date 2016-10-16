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
import com.tjport.common.model.ComboboxResult;
import com.tjport.common.model.DatagridResult;
import com.tjport.common.model.Result;
import com.tjport.common.query.Page;
import com.tjport.common.query.QueryFilter;
import com.tjport.gjy.sys.dao.IOrganizationDao;
import com.tjport.gjy.sys.model.Organization;



@Controller  
@RequestMapping(OrganizationController.BASE + "/" + OrganizationController.PATH)  
public class OrganizationController {  
    final static String BASE = "sys"; 
    final static String PATH = "organization";
	
	@Autowired
	private IOrganizationDao orgnizationDao;
	
	
    @RequestMapping("organizationList")  
    public String index() {  
         //return "success"; //跳转到success页面   
         return BASE + "/" + PATH + "/organizationList";
    } 
    
    @RequestMapping(value="organizationDialog/{type}")  
    public String userDialog(@PathVariable String type, Map<String,Object> map) {  
    	 map.put("type", type);  
         //return "success"; //跳转到success页面   
         return BASE + "/" + PATH + "/organizationDialog";
    } 
    
    @RequestMapping(value="orgSelectionDialog")  
    public String orgSelectionDialog() {  

         return BASE + "/" + PATH + "/orgSelectionDialog";
    } 
    
    @ResponseBody
   	@RequestMapping("loadOrganizationList")
    public DatagridResult<Organization> loadOrganizationList(int page, int rows, String sort, String order, String customSearch)
    {
    	Page<Organization> OrganizationPage = new Page<Organization>(page, rows);
    	OrganizationPage.setOrderBy(sort);
    	OrganizationPage.setOrder(order);
    	OrganizationPage.setAutoCount(true);
      
    	
        if (StringUtils.isNoneBlank(customSearch))
        {
            
        	QueryFilter filter = JSON.parseObject(customSearch, QueryFilter.class);

        	
        	OrganizationPage = orgnizationDao.findPage(OrganizationPage, filter);
        }
        else
        {
        	OrganizationPage = orgnizationDao.getAll(OrganizationPage);        	
        }

        List<Organization> list = new ArrayList<Organization>();
        for(Organization orgnization : OrganizationPage.getResult()){
        	list.add(orgnization);
        }
        DatagridResult<Organization> datagrid = new DatagridResult<Organization>();
        datagrid.setRows(list);
        datagrid.setTotal(OrganizationPage.getTotalCount());

        return datagrid;
    }
    
    @ResponseBody
	@RequestMapping("organizationAdd")
	public Result add(Organization orgnization) {
    	System.out.println("organizationAdd");
    	
    	Result mReturn = null;
		try {
			orgnizationDao.save(orgnization);
			mReturn = Result.successResult().setMsg("添加成功");
		} catch (Exception e) {
			//logger.error("添加失败", e);
			mReturn = Result.errorResult().setMsg(e.getMessage());
		}
		return mReturn;
	}
    
    @ResponseBody
	@RequestMapping("organizationEdit")
	public Result edit(Organization orgnization) {
    	Result mReturn = null;
		try {

			//Organization orgnization = new Organization();
			//BeanUtils.copyProperties(orgnizationTO, orgnization);

			orgnizationDao.update(orgnization);
			mReturn = Result.successResult().setMsg("编辑成功");
		} catch (Exception e) {
			//logger.error("添加失败", e);
			mReturn = Result.errorResult().setMsg(e.getMessage());
		}
		return mReturn;
	}
    
    @ResponseBody
	@RequestMapping("organizationDel")
	public Result del(String id) {
    	Result mReturn = new Result();
		try {

			orgnizationDao.delete(id);
			mReturn = Result.successResult().setMsg("删除成功");
		} catch (Exception e) {
			//logger.error("添加失败", e);
			mReturn = Result.errorResult().setMsg(e.getMessage());
		}
		return mReturn;
	}
    
    @ResponseBody
	@RequestMapping("loadOrganizationCombo")
    public List<ComboboxResult> loadOrganizationCombo(){
    	List<Organization> list = orgnizationDao.findAll();
    	List<ComboboxResult> result = new ArrayList<ComboboxResult>();
    	
    	for(Organization l : list){
    		ComboboxResult comboboxResult = new ComboboxResult();
    		comboboxResult.setId(l.getId());
    		comboboxResult.setText(l.getName());
    		result.add(comboboxResult);
    	}
    	
    	return result;
    }
    
    
  
}  