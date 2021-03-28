package com.htec.fa_api.repository;


import com.htec.fa_api.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer>  {

    List<Comment> getAllByActiveOrderByUpdatedDesc(Byte active);

    List<Comment> getAllByCityIdAndActive(Integer cityId, byte active);


}
