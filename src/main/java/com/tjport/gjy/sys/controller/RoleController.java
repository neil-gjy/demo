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
import com.tjport.gjy.sys.dao.IRoleDao;
import com.tjport.gjy.sys.model.Role;
import com.tjport.gjy.sys.vo.RoleVo;


@Controller  
@RequestMapping(RoleController.BASE + "/" + RoleController.PATH)  
public class RoleController {  
    final static String BASE = "sys"; 
    final static String PATH = "role";
	
	@Autowired
	private IRoleDao roleDao;
	
	
    @RequestMapping("roleList")  
    public String index() {  
         //return "success"; //跳转到success页面   
         return BASE + "/" + PATH + "/roleList";
    } 
    
    @RequestMapping(value="roleDialog/{type}")  
    public String roleDialog(@PathVariable String type, Map<String,Object> map) {  
    	 map.put("type", type);  
         //return "success"; //跳转到success页面   
         return BASE + "/" + PATH + "/roleDialog";
    } 
    
  
    @RequestMapping(value="resourcesDialog")  
    public String resourcesDialog() {  
    	
     
         return BASE + "/" + PATH + "/resourcesDialog";
    } 
    
    @ResponseBody
   	@RequestMapping("loadRoleList")
    public DatagridResult<RoleVo> loadRoleList(int page, int rows, String sort, String order, String customSearch)
    {
    	// 分页参数
    	Page<Role> rolePage = new Page<Role>(page, rows);
    	rolePage.setOrderBy(sort);      // 排序字段
    	rolePage.setOrder(order);		// 升序降序
    	rolePage.setAutoCount(true);    // 计算总数
      
    	
        if (StringUtils.isNoneBlank(customSearch))
        {
        	// Json查询参数转换为查询过滤器
        	QueryFilter filter = JSON.parseObject(customSearch, QueryFilter.class);
        	
        	rolePage = roleDao.findPage(rolePage, filter);
        }
        else
        {
        	rolePage = roleDao.getAll(rolePage);        	
        }
 
        // 查询结果转换为页面返回 结果
        List<RoleVo> list = new ArrayList<RoleVo>();
        for(Role role : rolePage.getResult()){
        	list.add(new RoleVo(role));
        }
        
        DatagridResult<RoleVo> datagrid = new DatagridResult<RoleVo>();
        datagrid.setRows(list);
        datagrid.setTotal(rolePage.getTotalCount());

        return datagrid;
    }
    
    // jqGrid测试
    @ResponseBody
   	@RequestMapping("loadRoleListJq")
    public DatagridResult<RoleVo> loadRoleListJq(int page, int rows, String sidx, String sord, String customSearch)
    {
    	// 分页参数
    	Page<Role> rolePage = new Page<Role>(page, rows);
    	rolePage.setOrderBy(sidx);      // 排序字段
    	rolePage.setOrder(sord);		// 升序降序
    	rolePage.setAutoCount(true);    // 计算总数
      
    	
        if (StringUtils.isNoneBlank(customSearch))
        {
        	// Json查询参数转换为查询过滤器
        	QueryFilter filter = JSON.parseObject(customSearch, QueryFilter.class);
        	
        	rolePage = roleDao.findPage(rolePage, filter);
        }
        else
        {
        	rolePage = roleDao.getAll(rolePage);        	
        }
 
        // 查询结果转换为页面返回 结果
        List<RoleVo> list = new ArrayList<RoleVo>();
        for(Role role : rolePage.getResult()){
        	list.add(new RoleVo(role));
        }
        
        DatagridResult<RoleVo> datagrid = new DatagridResult<RoleVo>();
        datagrid.setRows(list);
        datagrid.setTotal(rolePage.getTotalCount());

        return datagrid;
    }
    
    @ResponseBody
	@RequestMapping("roleAdd")
	public Result add(Role RoleVo) {
    	Result mReturn = null;
		try {

			roleDao.save(RoleVo);
			
			mReturn = Result.successResult().setMsg("添加成功");
		} catch (Exception e) {
			//logger.error("添加失败", e);
			mReturn = Result.errorResult().setMsg(e.getMessage());
		}
		return mReturn;
	}
    
    @ResponseBody
	@RequestMapping("roleEdit")
	public Result edit(Role RoleVo) {
    	Result mReturn = null;
		try {

			roleDao.update(RoleVo);
			
			mReturn = Result.successResult().setMsg("编辑成功");
		} catch (Exception e) {
			//logger.error("添加失败", e);
			mReturn = Result.errorResult().setMsg(e.getMessage());
		}
		return mReturn;
	}
    
    @ResponseBody
	@RequestMapping("roleDel")
	public Result del(String id) {
    	Result mReturn = new Result();
		try {

			roleDao.delete(id);
			mReturn = Result.successResult().setMsg("删除成功");
		} catch (Exception e) {
			//logger.error("添加失败", e);
			mReturn = Result.errorResult().setMsg(e.getMessage());
		}
		return mReturn;
	}
    
    
    @ResponseBody
   	@RequestMapping("loadRoleCombo")
       public List<ComboboxResult> loadRoleCombo(){
       	List<Role> list = roleDao.findAll();
       	List<ComboboxResult> result = new ArrayList<ComboboxResult>();
       	
       	for(Role item : list){
       		ComboboxResult comboboxResult = new ComboboxResult();
       		comboboxResult.setId(item.getId());
       		comboboxResult.setText(item.getName());

       		result.add(comboboxResult);
       	}
       	
       	return result;
       }
}  