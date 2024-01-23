package com.jsp.ums.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.jsp.ums.entity.User;
import com.jsp.ums.repoistory.UserRepoistory;

public class CustomUserDetailService implements UserDetailsService {

	@Autowired
	private UserRepoistory userRepoistory;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User user = userRepoistory.findByUserName(username).orElseThrow(()->new UsernameNotFoundException("User Not Found"));
		 return new CustomUserDetail(user);
		
	}
	
}
