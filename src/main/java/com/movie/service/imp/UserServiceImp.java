package com.movie.service.imp;

import com.movie.entity.User;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.movie.mapper.UserMapper;
import com.movie.service.IUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImp implements IUserService{

	@Resource
	private UserMapper usermapper;
	
	@Transactional(propagation=Propagation.REQUIRED,readOnly=true)
	public User login(String user_name, String user_pwd) {
		List<User> userList = usermapper.findUserByName(user_name);
		for(User user : userList) {
			if(user.getUser_pwd().equals(user_pwd)) {
				return user;
			}
		}
		return null;
	}
	
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public Integer updateUserInfo(User user) {
		return this.usermapper.updateUser(user);
	}
	
	@Transactional(propagation=Propagation.REQUIRED,readOnly=true)
	public User findUserById(long user_id) {
		return this.usermapper.findUserById(user_id);
	}
	
	@Transactional(propagation=Propagation.REQUIRED,readOnly=true)
	public List<User> findUserByName(String name) {
		return this.usermapper.findUserByName(name);
	}
	
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public Integer addUser(User user) {
		return this.usermapper.addUser(user);
	}

	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public Integer deleteUser(long user_id) {
		return this.usermapper.deleteUser(user_id);
	}
	
	@Transactional(propagation=Propagation.REQUIRED,readOnly=true)
	public PageInfo<User> findAllUserBySplitPage(Integer page, Integer limit, String keyword) {
		PageHelper.startPage(page, limit);
		List<User> list = new ArrayList<User>();
		if(keyword != null && !keyword.trim().equals("")) {
			list = this.usermapper.findUserLikeName(keyword);
		}else {
			list = this.usermapper.findAllUser();
		}
		return new PageInfo<User>(list);
	}

	@Transactional(propagation=Propagation.REQUIRED,readOnly=true)
	public List<User> findAllUserInfos() {
		return this.usermapper.findAllUser();
	}

	@Transactional(propagation=Propagation.REQUIRED,readOnly=true)
	public List<User> findUserLikeName(String name) {
		return this.usermapper.findUserLikeName(name);
	}
}
