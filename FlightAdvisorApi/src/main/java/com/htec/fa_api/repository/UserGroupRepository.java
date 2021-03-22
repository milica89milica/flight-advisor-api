package com.htec.fa_api.repository;


import com.htec.fa_api.model.UserGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserGroupRepository extends JpaRepository<UserGroup, Integer> {
    List<UserGroup> getAllByActive(Byte active);
}
