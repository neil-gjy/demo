package com.tjport.gjy.sys.dao.impl;

import java.util.ArrayList;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.tjport.common.hibernate.BaseDaoImpl;
import com.tjport.common.utils.DesUtils;
import com.tjport.gjy.sys.dao.IUserDao;
import com.tjport.gjy.sys.model.User;

@Repository("userDao")
public class UserDaoImpl extends BaseDaoImpl <User, String> implements IUserDao {

	@Transactional(readOnly=true)
	@Override
	public User findUserByUsername(String username) {
		// TODO Auto-generated method stub
		
		ArrayList<String> params = new ArrayList<String>();
		params.add(username);
		
		Query query = this.createQuery("from User o where o.username = ?", params.toArray());
		
		User user = (User)query.uniqueResult();
		
		return user;
	}
	
	@Transactional
    public boolean passModify(String userID, String oldPass, String newPass, String confirmPass) throws Exception
    {
        if (userID.equals("")&&userID != null) throw new Exception("用户名不能为空");

        if (oldPass.equals("")&&oldPass != null) throw new Exception("未输入当前密码");

        if (newPass.equals("")&&newPass != null) throw new Exception("请输入新密码");

        if (confirmPass.equals("")&&confirmPass != null) throw new Exception("请输入确认密码");

        User entity = this.get(userID);
        if (entity == null)
        {
            throw new Exception("用户不存在");
        }
        else
        {
            if (entity.getPassword().equals(DesUtils.encrypt(oldPass)))
            {
                if (newPass.equals(confirmPass))
                {
                	entity.setPassword(DesUtils.encrypt(newPass));
                    this.update(entity);
                }
                else
                {
                    throw new Exception("两次输入密码不一致");
                }
            }
            else
            {
                throw new Exception("当前密码错误");
            }
        }
        return true;
    }
	
}
