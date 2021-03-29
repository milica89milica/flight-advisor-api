package com.htec.fa_api.service;


import com.htec.fa_api.exception.HttpException;
import com.htec.fa_api.model.User;
import com.htec.fa_api.model.UserGroup;
import com.htec.fa_api.repository.UserRepository;
import com.htec.fa_api.util.MailHelper;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;

@CacheConfig(cacheNames={"users"})
@Service
public class UserService {
    private final UserRepository userRepository;
    private final MessageSource messageSource;
    private final UserGroupService userGroupService;

    private final MailHelper sender;

    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, MessageSource messageSource, UserGroupService userGroupService, MailHelper sender) {
        this.userRepository = userRepository;
        this.messageSource = messageSource;
        this.userGroupService = userGroupService;
        this.sender = sender;
        this.passwordEncoder = new BCryptPasswordEncoder(10, new SecureRandom());
    }

    @Cacheable
    public List<User> getAll() {
        return userRepository.getAllByActive((byte) 1);
    }
    @Cacheable(key = "#userGroupId")
    public List<User> getAllByUserGroup(Integer userGroupId) {
        return userRepository.getAllByUserGroupIdAndActive(userGroupId, (byte) 1);
    }

    @Cacheable(key = "#username")
    public User getByUsername(String username) {
        return userRepository.getByUsernameAndActive(username, (byte) 1);
    }

    @Transactional(rollbackFor = Exception.class)
    public User insert(User object) throws HttpException {
        User user = userRepository.getByUsernameAndActive(object.getUsername(), (byte) 1);
        if (user != null) {
            throw new HttpException(messageSource.getMessage("notUnique.username", null, null), HttpStatus.BAD_REQUEST);
        }
        Optional<UserGroup> userGroup = userGroupService.findById(object.getUserGroup().getId());
        if (!userGroup.isPresent()) {
            throw new HttpException(messageSource.getMessage("notExists.userGroup", null, null), HttpStatus.BAD_REQUEST);
        }
        //send mail
        String autoGeneratedPasswd = RandomStringUtils.randomAlphanumeric(8); //performance?
        object.setPassword(passwordEncoder.encode(autoGeneratedPasswd));

        sender.sendEmail(object.getEmail(), "{mail.subject.addUser}", "Your password:".concat(autoGeneratedPasswd));
        return userRepository.save(object);
    }

    @CachePut(key = "#object.id")
    @Transactional(rollbackFor = Exception.class)
    public User update(User object) throws HttpException {
        Optional<User> user = userRepository.findById(object.getId());
        if (!user.isPresent()) {
            throw new HttpException(messageSource.getMessage("notExists.user", null, null), HttpStatus.NOT_FOUND);
        }
        return userRepository.save(object); //check created, updated
    }

    @CacheEvict(key = "#id")
    @Transactional(rollbackFor = Exception.class)
    public User delete(Integer id) throws HttpException {
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()) {
            throw new HttpException(messageSource.getMessage("notExists.user", null, null), HttpStatus.NOT_FOUND);
        }
        user.get().setActive((byte) 0);
        return userRepository.save(user.get());
    }

    public Optional<User> findById(Integer id) {
        return userRepository.findById(id);
    }

    @CachePut(key = "#SecurityContextHolder.getContext().getAuthentication().getId()") //todo check!!!
    @Transactional(rollbackFor = Exception.class)
    public User updatePassword(String newPassword){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = this.getByUsername(username);
        user.setPassword(newPassword);
        return userRepository.save(user);
    }

}
