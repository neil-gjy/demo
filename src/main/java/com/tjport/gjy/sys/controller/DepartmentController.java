package com.tjport.gjy.sys.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.tjport.common.model.ComboboxResult;
import com.tjport.common.model.DatagridResult;
import com.tjport.common.model.Result;
import com.tjport.common.query.Page;
import com.tjport.common.query.QueryFilter;
import com.tjport.gjy.sys.dao.IDepartmentDao;
import com.tjport.gjy.sys.model.Department;



@Controller  
@RequestMapping(DepartmentController.BASE + "/" + DepartmentController.PATH)  
public class DepartmentController {

    final static String BASE = "sys"; 
    final static String PATH = "dept"; 
	
	@Autowired
	private IDepartmentDao departmentDao;
	
	
    @RequestMapping("departmentList")  
    public String index() {  
         return BASE + "/" + PATH + "/departmentList";
    } 
    
    @RequestMapping(value="/departmentDialog/{type}", method=RequestMethod.GET)  
    public String departmentDialog(@PathVariable String type, Map<String,Object> map) {  
    	 map.put("type", type);  
         //return "success"; //跳转到success页面   
         return BASE + "/" + PATH +  "/departmentDialog";
    } 
    
    @ResponseBody
   	@RequestMapping("loadDepartmentList")
    public DatagridResult<Department> loadDepartmentList(int page, int rows, String sort, String order, String customSearch)
    {
    	Page<Department> DepartmentPage = new Page<Department>(page, rows);
    	DepartmentPage.setOrderBy(sort);
    	DepartmentPage.setOrder(order);
    	DepartmentPage.setAutoCount(true);
      
    	
        if (StringUtils.isNoneBlank(customSearch))
        {
        	QueryFilter filter = JSON.parseObject(customSearch, QueryFilter.class);
        	
        	DepartmentPage = departmentDao.findPage(DepartmentPage, filter);
        }
        else
        {
        	DepartmentPage = departmentDao.getAll(DepartmentPage);        	
        }

        List<Department> list = new ArrayList<Department>();
        for(Department Department : DepartmentPage.getResult()){
        	list.add(Department);
        }
        DatagridResult<Department> datagrid = new DatagridResult<Department>();
        datagrid.setRows(list);
        datagrid.setTotal(DepartmentPage.getTotalCount());

        return datagrid;
    }
    
   /* @ResponseBody
   	@RequestMapping("loadAllDepartments")
    public DatagridResult<Department> loadAllResources(String sort, String order)
    {
        List<Department> list = departmentDao.findAll(sort, order);
       
        DatagridResult<Department> datagrid = new DatagridResult<Department>();
        datagrid.setRows(list);
        datagrid.setTotal(list.size());

        return datagrid;
    }*/
    
    @ResponseBody
   	@RequestMapping("loadAllDepartments")
    public DatagridResult<Department> loadAllDepartments(String sort, String order, String deptName)
    {
        List<Department> list = new ArrayList<Department>();
        
        if (StringUtils.isNoneBlank(deptName))
        {	
        	list = departmentDao.find(sort, order, "name like ?", deptName);
        }
        else
        {
        	list = departmentDao.findAll(sort, order);        	
        }
       
        DatagridResult<Department> datagrid = new DatagridResult<Department>();
        datagrid.setRows(list);
        datagrid.setTotal(list.size());

        return datagrid;
    }
    
    @ResponseBody
	@RequestMapping("DepartmentAdd")
	public Result add(Department DepartmentVo) {
    	Result mReturn = null;
		try {
			departmentDao.save(DepartmentVo);
			mReturn = Result.successResult().setMsg("添加成功");
		} catch (Exception e) {
			//logger.error("添加失败", e);
			mReturn = Result.errorResult().setMsg(e.getMessage());
		}
		return mReturn;
	}
    
    @ResponseBody
	@RequestMapping("DepartmentEdit")
	public Result edit(Department DepartmentVo) {
    	Result mReturn = null;
		try {
			departmentDao.update(DepartmentVo);
			mReturn = Result.successResult().setMsg("编辑成功");
		} catch (Exception e) {
			//logger.error("添加失败", e);
			mReturn = Result.errorResult().setMsg(e.getMessage());
		}
		return mReturn;
	}
    
    @ResponseBody
	@RequestMapping("DepartmentDel")
	public Result del(String id) {
    	Result mReturn = new Result();
		try {

			departmentDao.delete(id);
			mReturn = Result.successResult().setMsg("删除成功");
		} catch (Exception e) {
			//logger.error("添加失败", e);
			mReturn = Result.errorResult().setMsg(e.getMessage());
		}
		return mReturn;
	}
    
    
    @ResponseBody
	@RequestMapping("loadDepartmentCombo")
    public List<ComboboxResult> loadDepartmentCombo(){
    	List<Department> list = departmentDao.findAll();
    	List<ComboboxResult> result = new ArrayList<ComboboxResult>();
    	
    	for(Department l : list){
    		ComboboxResult comboboxResult = new ComboboxResult();
    		comboboxResult.setId(l.getId());
    		comboboxResult.setText(l.getName());
    		result.add(comboboxResult);
    	}
    	
    	return result;
    }
}
