package com.htec.fa_api.repository;


import com.htec.fa_api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    List<User> getAllByActive(Byte active);
    List<User> getAllByUserGroupIdAndActive(Integer userGroupId, Byte active);
    User getByUsernameAndActive(String username, Byte active);
}
