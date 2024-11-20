package com.MarceloHsousa.bookstoreManagementSystem.JunitTest.repositories;

import com.MarceloHsousa.bookstoreManagementSystem.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@RequiredArgsConstructor
public class UserRepositoryTests {

    private UserRepository userRepository;

    @Test
    public void deleteUserShouldWhenIdExists(){
    }
}
