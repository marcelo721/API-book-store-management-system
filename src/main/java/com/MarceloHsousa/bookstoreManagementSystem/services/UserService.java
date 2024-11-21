package com.MarceloHsousa.bookstoreManagementSystem.services;

import com.MarceloHsousa.bookstoreManagementSystem.entities.User;
import com.MarceloHsousa.bookstoreManagementSystem.entities.enums.StatusAccount;
import com.MarceloHsousa.bookstoreManagementSystem.repositories.UserRepository;
import com.MarceloHsousa.bookstoreManagementSystem.services.exceptions.*;
import com.MarceloHsousa.bookstoreManagementSystem.util.BookStoreUtils;
import com.MarceloHsousa.bookstoreManagementSystem.web.dto.userDto.UserUpdateDto;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    @Transactional
    public User insert(User user) throws MessagingException, UnsupportedEncodingException {
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));

            String randomCode = BookStoreUtils.generateRandomString(64);
            user.setVerificationCode(randomCode);
            user.setStatusAccount(StatusAccount.DISABLED);
            emailService.sendVerifyEmail(user);

            return userRepository.save(user);

        }catch (DataIntegrityViolationException e){
            throw new EmailUniqueViolationException(String.format("Email {%s} already registered ", user.getEmail()));
        }
    }

    @Transactional(readOnly = true)
    public User findById(long id){

        Optional<User> obj = userRepository.findById(id);
        return userRepository.findById(id)
                .map(user -> {
                    if (user.getStatusAccount() == StatusAccount.DISABLED) {
                        throw new UserAccountNotEnabledException("Account not enabled");
                    }
                    return user;
                })
                .orElseThrow(() -> new EntityNotFoundException("User Not Found"));
    }

    @Transactional(readOnly = true)
    public Page<User> findAll(PageRequest request){
        return userRepository.findAll(request);
    }

    @Transactional
    public User updatePassword(String currentPassword, String newPassword, String confirmNewPassword, long id){

        if (!newPassword.equals(confirmNewPassword)){
            throw new PasswordInvalidException("the confirmation passwords are different");
        }

        User user = findById(id);

        if (!passwordEncoder.matches(currentPassword, user.getPassword())){
            throw  new PasswordInvalidException("The password is wrong");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
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
    public User updateName(Long idUser, UserUpdateDto userUpdate){
        User user = findById(idUser);

        if (!passwordEncoder.matches(userUpdate.getPassword(), user.getPassword()))
            throw new PasswordInvalidException("password is wrong!");

        user.setName(userUpdate.getName());
        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public User findByEmail(String email){

        return userRepository.findByEmail(email).orElseThrow(
                () -> new EntityNotFoundException("User Not Found")
        );
    }

    @Transactional()
    public StatusAccount verify(String code) {
        User user = userRepository.findByVerificationCode(code);

        if (user == null || user.getStatusAccount().equals(StatusAccount.ENABLED)) {
            return StatusAccount.ALREADY_ENABLED;

        } else{
            user.setVerificationCode(null);
            user.setStatusAccount(StatusAccount.ENABLED);
            userRepository.save(user);
            return StatusAccount.ENABLED ;
        }
    }
}
