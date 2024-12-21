package com.ktck124.lop124LTDD04.nhom07.ktck.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ktck124.lop124LTDD04.nhom07.ktck.Repository.UserRepository;
import com.ktck124.lop124LTDD04.nhom07.ktck.modal.User;


@Service
public class UserSerivce {
	@Autowired
	private UserRepository userRepository;
	public User findByUserId(Long userId) throws Exception {
		Optional<User> optionUser = userRepository.findById(userId);
		if(optionUser.isEmpty()) {
			throw new Exception("User not found");
		}
		return optionUser.get();
	}
}
