package com.movie.mapper;

import com.movie.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface UserMapper {
	User findUserById(long user_id);
	Integer addUser(User user);
	Integer deleteUser(long user_id);
	Integer updateUser(User user);
	List<User> findAllUser();
	List<User> findUserByName(String name);
	List<User> findUserLikeName(String name);
}
