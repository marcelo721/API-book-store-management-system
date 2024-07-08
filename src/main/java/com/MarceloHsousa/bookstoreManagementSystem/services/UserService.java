package com.MarceloHsousa.bookstoreManagementSystem.services;

import com.MarceloHsousa.bookstoreManagementSystem.entities.User;
import com.MarceloHsousa.bookstoreManagementSystem.repository.UserRepository;
import com.MarceloHsousa.bookstoreManagementSystem.services.exceptions.EmailUniqueViolationException;
import com.MarceloHsousa.bookstoreManagementSystem.services.exceptions.EntityNotFoundException;
import com.MarceloHsousa.bookstoreManagementSystem.services.exceptions.IntegrityViolationException;
import com.MarceloHsousa.bookstoreManagementSystem.services.exceptions.PasswordInvalidException;
import com.MarceloHsousa.bookstoreManagementSystem.web.dto.userDto.UserUpdateDto;
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
            throw new PasswordInvalidException("the confirmation passwords are different");
        }

        User user = findById(id);

        if (!currentPassword.equals(user.getPassword())){
            throw  new PasswordInvalidException("The password is wrong");
        }

        user.setPassword(newPassword);
        return user;
    }

    @Transactional
    public void delete(Long id){
        try {
            User user = findById(id);
            userRepository.deleteById(user.getId());

        }catch (DataIntegrityViolationException e){
            throw  new IntegrityViolationException("Error !" + e.getMessage());
        }
    }

    @Transactional
    public User updateEmail(Long idUser, UserUpdateDto userUpdate){
        return null;
    }

    @Transactional
    public User updateName(Long idUser, UserUpdateDto userUpdate){
        return null;
    }

    private void UpdateData(User user, UserUpdateDto userUpdate){
    }
}
