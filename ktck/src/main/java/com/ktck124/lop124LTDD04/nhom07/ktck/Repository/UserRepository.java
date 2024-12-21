package com.ktck124.lop124LTDD04.nhom07.ktck.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ktck124.lop124LTDD04.nhom07.ktck.modal.User;

public interface UserRepository  extends JpaRepository<User, Long>{
	User findByEmail(String email);
}
