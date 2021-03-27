package com.htec.fa_api.service;


import com.htec.fa_api.exception.HttpException;
import com.htec.fa_api.model.User;
import com.htec.fa_api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final MessageSource messageSource;

    @Autowired
    PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, MessageSource messageSource) {
        this.userRepository = userRepository;
        this.messageSource = messageSource;
    }

    public List<User> getAll() {
        return userRepository.getAllByActive((byte) 1);
    }

    public List<User> getAllByUserGroup(Integer userGroupId) { //todo provjeriti unique usera
        return userRepository.getAllByUserGroupIdAndActive(userGroupId, (byte) 1);
    }

    public User getByUsername(String username) {
        return userRepository.getByUsernameAndActive(username, (byte) 1);
    }

    @Transactional(rollbackFor = Exception.class)
    public User insert(User object) throws HttpException {
        User user = userRepository.getByUsernameAndActive(object.getUsername(),(byte)1);
        if(user!=null){
            throw new HttpException(messageSource.getMessage("notUnique.username",null,null), HttpStatus.BAD_REQUEST);
        }
        object.setPassword(passwordEncoder.encode(object.getPassword())); //todo
        return userRepository.save(object);
    }

    @Transactional(rollbackFor = Exception.class)
    public User update(User object) throws HttpException {
        Optional<User> user = userRepository.findById(object.getId());
        if(!user.isPresent()){
            throw new HttpException(messageSource.getMessage("notExists.user", null, null), HttpStatus.NOT_FOUND);
        }
        return userRepository.save(object); //check created, updated
    }

    @Transactional(rollbackFor = Exception.class)
    public void delete(Integer id) throws HttpException {
        Optional<User> user = userRepository.findById(id);
        user.get().setActive((byte)0);
        userRepository.save(user.get());
    }

    public Optional<User> findById(Integer id){
        return userRepository.findById(id);
    }

}
