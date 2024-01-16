package com.jsp.ums.repoistory;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jsp.ums.entity.User;

public interface UserRepoistory extends JpaRepository<User, Integer> {

}
