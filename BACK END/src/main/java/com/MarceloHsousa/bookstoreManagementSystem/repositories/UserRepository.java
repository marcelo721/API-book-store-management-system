package com.MarceloHsousa.bookstoreManagementSystem.repositories;

import com.MarceloHsousa.bookstoreManagementSystem.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    User findByVerificationCode(String code);
}
