package com.ktck124.lop124LTDD04.nhom07.ktck.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.ktck124.lop124LTDD04.nhom07.ktck.Repository.UserRepository;
import com.ktck124.lop124LTDD04.nhom07.ktck.Response.DeleteResponse;
import com.ktck124.lop124LTDD04.nhom07.ktck.modal.User;
import com.ktck124.lop124LTDD04.nhom07.ktck.service.UserSerivce;




@RestController
public class UserController {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UserSerivce userSerivce;
	@GetMapping("/api/user")
	ResponseEntity<List<User>> getUser(){
		return ResponseEntity.status(HttpStatus.OK).body(userRepository.findAll());
	}
	@DeleteMapping("/api/user/{userId}")
	ResponseEntity<DeleteResponse> deleteUser(@PathVariable("userId") Long userId ) throws Exception{
		
		User user = userSerivce.findByUserId(userId);
		
		userRepository.delete(user);
		DeleteResponse deleteResponse = new DeleteResponse();
		deleteResponse.setMessage("Delete User success");
		return ResponseEntity.status(HttpStatus.OK).body(deleteResponse);
	}
	
	@GetMapping("/api/user/{userId}")
	ResponseEntity<User> getUserById(@PathVariable("userId") Long userId) throws Exception{
		User user = userSerivce.findByUserId(userId);
		
		return ResponseEntity.status(HttpStatus.OK).body(user);
	}
	@PatchMapping("/api/user")
	ResponseEntity<User> updateUser(@RequestBody User user) throws Exception{
		
		User updateUser = userRepository.findByEmail(user.getEmail());
		
		updateUser.setEmail(user.getEmail());
		updateUser.setFullName(user.getFullName());
		updateUser.setPhoneNumber(user.getPhoneNumber());
		
			updateUser.setPassword(user.getPassword());	
		
		return ResponseEntity.status(HttpStatus.OK).body(userRepository.save(updateUser));
	}
}
