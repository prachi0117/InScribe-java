package com.programming.springblog.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.programming.springblog.dto.UserDto;
import com.programming.springblog.exception.ResourceNotFoundException;
import com.programming.springblog.model.User;
import com.programming.springblog.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
    private  UserRepository userRepository;

    
    @Override
    public UserDto createUser(UserDto userDto) {
        User user = dtoToUser(userDto);
        User savedUser = userRepository.save(user);
        return userToDto(savedUser);
    }

    @Override
    public UserDto updateUser(UserDto userDto, Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));

        user.setUserName(userDto.getUsername());
        User updatedUser = userRepository.save(user);
        return userToDto(updatedUser);
    }

    @Override
    public UserDto getUserById(Integer userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        return this.userToDto(user);
    }
    

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(this::userToDto).collect(Collectors.toList());
    }

    @Override
    public void deleteUser(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
        userRepository.delete(user);
    }

    @Override
    public UserDto registerNewUser(UserDto userDto) {
        User user = new User();
        user.setUserName(userDto.getUsername());
        User newUser = userRepository.save(user);
        return userToDto(newUser);
    }

    private User dtoToUser(UserDto userDto) {
        User user = new User();
        user.setUserName(userDto.getUsername());
        return user;
    }

    public UserDto userToDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());  // Ensure this line correctly sets the id
        userDto.setUsername(user.getUserName());
        // set other fields
        return userDto;
    }
    
	

	@Override
	public String getUserNameById(Integer userId) {
		
			User user = userRepository.findById(userId)
					.orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
			return user.getUserName();
		
	}
}
