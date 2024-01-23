package com.jsp.ums.repoistory;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jsp.ums.entity.User;

public interface UserRepoistory extends JpaRepository<User, Integer> {

	Optional<User> findByUserName(String userName);
}
