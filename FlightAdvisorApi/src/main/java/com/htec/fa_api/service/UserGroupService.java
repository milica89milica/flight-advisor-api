package com.htec.fa_api.service;


import com.htec.fa_api.model.UserGroup;
import com.htec.fa_api.repository.UserGroupRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserGroupService {

    private final UserGroupRepository userGroupRepository;

    public UserGroupService(UserGroupRepository userGroupRepository) {
        this.userGroupRepository = userGroupRepository;
    }

    public List<UserGroup> getAll(){
        return userGroupRepository.getAllByActive((byte) 1);
    }
}
