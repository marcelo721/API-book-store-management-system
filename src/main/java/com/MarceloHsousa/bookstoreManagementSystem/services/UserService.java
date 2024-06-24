package com.MarceloHsousa.bookstoreManagementSystem.services;

import com.MarceloHsousa.bookstoreManagementSystem.entities.User;
import com.MarceloHsousa.bookstoreManagementSystem.repository.UserRepository;
import com.MarceloHsousa.bookstoreManagementSystem.services.exceptions.EmailUniqueViolationException;
import com.MarceloHsousa.bookstoreManagementSystem.services.exceptions.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
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

        try {
            return userRepository.save(user);

        }catch (DataIntegrityViolationException e){
            throw new EmailUniqueViolationException(String.format("Email {%s} already registered ", user.getEmail()));
        }
    }

    @Transactional(readOnly = true)
    public User findById(long id){

        Optional<User> obj = userRepository.findById(id);
        return obj.orElseThrow((()-> new EntityNotFoundException("User Not Found")));
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
