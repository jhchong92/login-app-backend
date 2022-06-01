package com.jh.loginappbackend.dao;

import com.jh.loginappbackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
