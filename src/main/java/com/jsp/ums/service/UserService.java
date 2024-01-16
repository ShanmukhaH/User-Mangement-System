package com.jsp.ums.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.jsp.ums.requestdto.UserRequest;
import com.jsp.ums.responsedto.UserResponse;
import com.jsp.ums.utility.ResponsesStrcture;

public interface UserService {

	ResponseEntity<ResponsesStrcture<UserResponse>> saveUser(UserRequest userRequest);
	ResponseEntity<ResponsesStrcture<UserResponse>> updateUserById(UserRequest userRequest, int userId);
	ResponseEntity<ResponsesStrcture<UserResponse>> findUserByid(int userId);
	ResponseEntity<ResponsesStrcture<UserResponse>> deletUserByid(int userId);
	ResponseEntity<ResponsesStrcture<List<UserResponse>>> findAllUsers();

	

}
