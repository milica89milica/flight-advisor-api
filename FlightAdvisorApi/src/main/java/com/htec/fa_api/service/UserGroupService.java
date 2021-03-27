package com.htec.fa_api.service;


import com.htec.fa_api.exception.HttpException;
import com.htec.fa_api.model.UserGroup;
import com.htec.fa_api.repository.UserGroupRepository;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserGroupService {
    private final UserGroupRepository userGroupRepository;
    private final MessageSource messageSource;

    public UserGroupService(UserGroupRepository userGroupRepository, MessageSource messageSource) {
        this.userGroupRepository = userGroupRepository;
        this.messageSource = messageSource;
    }

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

    @Transactional(rollbackFor = Exception.class)
    public UserGroup update(UserGroup object) throws HttpException {
        Optional<UserGroup> userGroup = userGroupRepository.findById(object.getId());
        if (!userGroup.isPresent()) {
            throw new HttpException(messageSource.getMessage("notExists.userGroup", null, null), HttpStatus.NOT_FOUND);
        }
        return userGroupRepository.save(object); //check created, updated
    }

    @Transactional(rollbackFor = Exception.class)
    public UserGroup delete(Integer id) throws HttpException {
        Optional<UserGroup> userGroup = userGroupRepository.findById(id);
        if(!userGroup.isPresent()){
            throw new HttpException(messageSource.getMessage("notExists.userGroup", null, null), HttpStatus.NOT_FOUND);
        }
        userGroup.get().setActive((byte)0);
        return userGroupRepository.save(userGroup.get());
    }

    public Optional<UserGroup> findById(Integer id) {
        return userGroupRepository.findById(id);
    }


}
