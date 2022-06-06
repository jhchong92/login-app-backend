package com.jh.loginappbackend.dao;

import com.jh.loginappbackend.model.AppUser;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<AppUser, UUID> {
  AppUser findByEmail(String email);
}
