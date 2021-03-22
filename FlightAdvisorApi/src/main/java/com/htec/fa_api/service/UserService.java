package com.htec.fa_api.service;


import com.htec.fa_api.model.User;
import com.htec.fa_api.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAll(){ 
        return userRepository.getAllByActive((byte)1);
    }

    public List<User> getAllByUserGroup(Integer userGroupId){
        return userRepository.getAllByUserGroupIdAndActive(userGroupId, (byte)1);
    }

}
