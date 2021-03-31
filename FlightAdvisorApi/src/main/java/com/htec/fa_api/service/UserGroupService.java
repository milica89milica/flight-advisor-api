package com.htec.fa_api.service;


import com.htec.fa_api.exception.HttpException;
import com.htec.fa_api.model.User;
import com.htec.fa_api.model.UserGroup;
import com.htec.fa_api.repository.UserGroupRepository;
import com.htec.fa_api.repository.UserRepository;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@CacheConfig(cacheNames = {"userGroups"})
@Service
public class UserGroupService {
    private final UserGroupRepository userGroupRepository;
    private final MessageSource messageSource;
    private final UserRepository userRepository;

    public UserGroupService(UserGroupRepository userGroupRepository, MessageSource messageSource, UserRepository userRepository) {
        this.userGroupRepository = userGroupRepository;
        this.messageSource = messageSource;
        this.userRepository = userRepository;
    }

    @Cacheable
    public List<UserGroup> getAll() {
        return userGroupRepository.getAllByActive((byte) 1);
    }

    @Transactional(rollbackFor = Exception.class)
    public UserGroup insert(UserGroup userGroup) throws HttpException { //todo
        if (userGroupRepository.existsByNameAndActive(userGroup.getName(), (byte) 1)) {
            throw new HttpException(messageSource.getMessage("notUnique.name", null, null), HttpStatus.BAD_REQUEST);
        }
        return userGroupRepository.save(userGroup);
    }

    @CachePut(key = "#object.id")
    @Transactional(rollbackFor = Exception.class)
    public UserGroup update(UserGroup object) throws HttpException {
        Optional<UserGroup> userGroup = userGroupRepository.findById(object.getId());
        if (!userGroup.isPresent()) {
            throw new HttpException(messageSource.getMessage("notExists.userGroup", null, null), HttpStatus.NOT_FOUND);
        }
        return userGroupRepository.save(object); //check created, updated
    }

    @CacheEvict(key = "#id")
    @Transactional(rollbackFor = Exception.class)
    public UserGroup delete(Integer id) throws HttpException {
        Optional<UserGroup> userGroup = userGroupRepository.findById(id);
        if (!userGroup.isPresent()) {
            throw new HttpException(messageSource.getMessage("notExists.userGroup", null, null), HttpStatus.NOT_FOUND);
        }
        List<User> users = userRepository.getAllByUserGroupIdAndActive(userGroup.get().getId(),(byte)1);
        if (!users.isEmpty()) {
            throw new HttpException(messageSource.getMessage("cantDelete.relatedToUser", null, null), HttpStatus.NOT_ACCEPTABLE);
        }
        userGroup.get().setActive((byte) 0);
        return userGroupRepository.save(userGroup.get());
    }

    @Cacheable(key = "#id")
    public Optional<UserGroup> findById(Integer id) {
        return userGroupRepository.findById(id);
    }


}
