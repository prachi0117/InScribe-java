package com.programming.springblog.service;

import java.util.List;

import com.programming.springblog.dto.UserDto;

public interface UserService {

	UserDto registerNewUser(UserDto user);
	
	
	UserDto createUser(UserDto user);

	UserDto updateUser(UserDto user, Integer userId);

	UserDto getUserById(Integer userId);

	List<UserDto> getAllUsers();

	void deleteUser(Integer userId);

    String getUserNameById(Integer userId);

}