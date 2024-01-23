package com.jsp.ums.serviceImpl;

import java.util.ArrayList;
import java.util.Optional;

import org.hibernate.mapping.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.jsp.ums.entity.User;
import com.jsp.ums.exception.UserNotFoundById;
import com.jsp.ums.repoistory.UserRepoistory;
import com.jsp.ums.requestdto.UserRequest;
import com.jsp.ums.responsedto.UserResponse;
import com.jsp.ums.service.UserService;
import com.jsp.ums.utility.ResponsesStrcture;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private PasswordEncoder encoder;
	
	@Autowired
	private UserRepoistory userRepoistory;
	
	@Autowired
	private ResponsesStrcture<UserResponse> str;
	
	public User mapToUser(UserRequest userRequest)
	{
		return User.builder()
				.userName(userRequest.getUserName())
				.email(userRequest.getEmail())
				.password(encoder.encode(userRequest.getPassword()))
				.build();
	}
	
	public UserResponse mapToUserResponse(User user)
	{
		return UserResponse.builder()
				.userId(user.getUserId())
				.userName(user.getUserName())
				.email(user.getEmail())
				.build();
	}
	
	@Override
	public ResponseEntity<ResponsesStrcture<UserResponse>> saveUser(UserRequest userRequest) {
	
		User user = mapToUser(userRequest);
		  User user2 = userRepoistory.save(user);
		  UserResponse userResponse = mapToUserResponse(user2);
		  
		 str.setStatuscode(HttpStatus.CREATED.value());
		 str.setMessage("User Data Saved Sucessfully");
		 str.setData(userResponse);
		 return  new ResponseEntity<ResponsesStrcture<UserResponse>>(str,HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<ResponsesStrcture<UserResponse>> updateUserById(UserRequest userRequest, int userId) {
		  
		User olduser = userRepoistory.findById(userId).orElseThrow(()->new UserNotFoundById("User Not Found"));
		
		 User user = mapToUser(userRequest);
		 user.setUserId(olduser.getUserId());
		 User save = userRepoistory.save(user);
		 
		 UserResponse UserResponse = mapToUserResponse(user);
		
		str.setStatuscode(HttpStatus.OK.value());
		str.setMessage("User Updated Sucessfully");
		str.setData(UserResponse);
		
		return new ResponseEntity<ResponsesStrcture<UserResponse>>(str,HttpStatus.OK);
	}

	
	@Override
	public ResponseEntity<ResponsesStrcture<UserResponse>> findUserByid(int userId) {
		User user=userRepoistory.findById(userId).orElseThrow(()->new UserNotFoundById("User Data Not Found!"));
		
		str.setStatuscode(HttpStatus.FOUND.value());
		str.setMessage("User Data Found");
		str.setData(mapToUserResponse(user));
		
		return new ResponseEntity<ResponsesStrcture<UserResponse>>(str,HttpStatus.FOUND);
		
			
			
	}

	@Override
	public ResponseEntity<ResponsesStrcture<UserResponse>> deletUserByid(int userId) {
		
		User user = userRepoistory.findById(userId).orElseThrow(()->new UserNotFoundById("User Not Found"));
		userRepoistory.delete(user);
		
		UserResponse userResponse = mapToUserResponse(user);
		
		str.setStatuscode(HttpStatus.OK.value());
		str.setMessage("User Deleted Sucessfully");
		str.setData(userResponse);
		
		return new ResponseEntity<ResponsesStrcture<UserResponse>>(str,HttpStatus.OK);
	}

	@Override
	public ResponseEntity<ResponsesStrcture<java.util.List<UserResponse>>> findAllUsers() {
		
		java.util.List<User> userlist = userRepoistory.findAll();
		
		if(!userlist.isEmpty()) {
		
			java.util.List<UserResponse> list=new ArrayList<>();
			
			for(User user:userlist) {
				UserResponse userResponse = mapToUserResponse(user);
				list.add(userResponse);
			}
			ResponsesStrcture<java.util.List<UserResponse>> strcture=new ResponsesStrcture<>();
			
			strcture.setStatuscode(HttpStatus.FOUND.value());
			strcture.setMessage("Users data Found");
			strcture.setData(list);
			
			return new ResponseEntity<ResponsesStrcture<java.util.List<UserResponse>>>(strcture,HttpStatus.FOUND);
			
		}
		
		throw new UserNotFoundById("The Requested User Not Found");
	}

	
	
	

}
