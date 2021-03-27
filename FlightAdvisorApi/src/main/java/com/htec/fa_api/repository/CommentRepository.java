package com.htec.fa_api.repository;


import com.htec.fa_api.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

    List<Comment> getAllByActive(Byte active);

    List<Comment> getAllByCityIdAndActive(Integer cityId, byte active);

}
