package com.tjport.gjy.sys.service;

import com.tjport.gjy.sys.vo.UserVo;

public interface IUserService {

	public void save(UserVo vo) throws Exception;
	public void update(UserVo vo) throws Exception;
}
