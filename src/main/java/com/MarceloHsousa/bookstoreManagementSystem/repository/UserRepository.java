package com.MarceloHsousa.bookstoreManagementSystem.repository;

import com.MarceloHsousa.bookstoreManagementSystem.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
