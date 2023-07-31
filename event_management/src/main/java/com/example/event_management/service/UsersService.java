package com.example.event_management.service;

import com.example.event_management.exception.DuplicateUsernameException;
import com.example.event_management.exception.UnAuthorisedException;
import com.example.event_management.model.ForgetPassword;
import com.example.event_management.model.ResetPasswordRequest;
import com.example.event_management.repository.UsersRepository;
import com.example.event_management.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UsersService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private UserEventService userEventService;

    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    boolean isPasswordCorrect = false;

    public List<Users> findAll() {
        return usersRepository.findAll();
    }

    public List<Users>  findUsersWithPagination(int pageNumber, int limit, String sortBy) {
        Sort sort = Sort.by(Sort.Direction.ASC, sortBy);

        Page<Users> page = usersRepository.findAll(PageRequest.of(pageNumber, limit, sort));

        return page.getContent();
    }

    public Users getById(String id) {

        Users user = usersRepository.findById(id).orElseThrow(() -> new UnAuthorisedException("User not found"));

        return user;
    }

    public Users findByUsername(String username) {
        Users user = usersRepository.findByUsername(username);

        if(Objects.isNull(user)){
            return null;
        }

        return user;
    }

    public Users save(Users users){
        if(Objects.nonNull(findByUsername(users.getUsername()))){
            throw new DuplicateUsernameException("'userName' is not available. It is already used by another user");
        }

        users.setId(UUID.randomUUID().toString());

        users = usersRepository.save(users);

        return users;
    }

    public void deleteById(String id){
        usersRepository.deleteById(id);

        userEventService.deleteByUserId(id);
    }


    public Users update(Users users) {
        Users updateUser = usersRepository.findById(users.getId()).orElseThrow(() -> new UnAuthorisedException("User Id not found"));

        updateUser.setFullName(users.getFullName());
        updateUser.setMobileNumber(users.getMobileNumber());

        return usersRepository.save(updateUser);
    }

    public Users resetPassword(ResetPasswordRequest resetPasswordRequest){
        Users user = usersRepository.findById(resetPasswordRequest.getUserId()).
                                    orElseThrow(() -> new UnAuthorisedException("User Id not found"));

        isPasswordCorrect = encoder.matches(resetPasswordRequest.getOldPassword(), user.getPassword());

        if(isPasswordCorrect){
            user.setPassword(resetPasswordRequest.getNewPassword());
        }else {
            throw new UnAuthorisedException("Old Password is not matching");
        }

        return usersRepository.save(user);
    }

    public Users authenticateUserUsingEmail(ForgetPassword forgetPassword) {

        Users users = usersRepository.findByEmail(forgetPassword.getEmail());

        if(Objects.isNull(users)) {
            throw new UnAuthorisedException("Email not exists");
        }

        isPasswordCorrect = forgetPassword.getNewPassword().equals(forgetPassword.getConfirmPassword());

        if(isPasswordCorrect){
            users.setPassword(forgetPassword.getNewPassword());
            usersRepository.save(users);
        }else{
            throw new UnAuthorisedException("'newPassword' and 'confirmPassword' are not matching");
        }

        return users;
    }

}
