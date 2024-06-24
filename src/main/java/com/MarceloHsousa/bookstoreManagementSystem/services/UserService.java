package com.MarceloHsousa.bookstoreManagementSystem.services;

import com.MarceloHsousa.bookstoreManagementSystem.entities.User;
import com.MarceloHsousa.bookstoreManagementSystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public User insert(User user){
        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public User findById(long id){

        Optional<User> obj = userRepository.findById(id);
        return obj.orElseThrow((()-> new RuntimeException()));
    }

    @Transactional(readOnly = true)
    public List<User> findAll(){
        return userRepository.findAll();
    }

    @Transactional
    public User updatePassword(String currentPassword, String newPassword, String confirmNewPassword, long id){

        if (!newPassword.equals(confirmNewPassword)){
            throw new RuntimeException("deu ruim ");
        }

        User user = findById(id);

        if (!currentPassword.equals(user.getPassword())){
            throw  new RuntimeException("fudeo");
        }

        user.setPassword(newPassword);
        return user;

    }
}
