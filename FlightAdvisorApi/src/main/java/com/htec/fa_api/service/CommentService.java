package com.htec.fa_api.service;

import com.htec.fa_api.exception.HttpException;
import com.htec.fa_api.model.City;
import com.htec.fa_api.model.Comment;
import com.htec.fa_api.model.User;
import com.htec.fa_api.repository.CommentRepository;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final CityService cityService;
    private final MessageSource messageSource;
    private final UserService userService;

    public CommentService(CommentRepository commentRepository, CityService cityService, MessageSource messageSource, UserService userService) {
        this.commentRepository = commentRepository;
        this.cityService = cityService;
        this.messageSource = messageSource;
        this.userService = userService;
    }

    public List<Comment> getAll() {
        return commentRepository.getAllByActiveOrderByUpdatedDesc((byte) 1);
    }

    @Transactional(rollbackFor = Exception.class) //after insert comment are available on /user also
    public Comment insert(Comment object) throws HttpException {
        Optional<City> city = cityService.findById(object.getCity().getId());
        if (!city.isPresent()) {
            throw new HttpException(messageSource.getMessage("notExists.city", null, null), HttpStatus.NOT_FOUND);
        }
        Optional<User> user = userService.findById(object.getUser().getId());
        if (!user.isPresent()) {
            throw new HttpException(messageSource.getMessage("notExists.user", null, null), HttpStatus.NOT_FOUND);
        }
        return commentRepository.save(object);
    }

    public Optional<Comment> findById(Integer id) {
        return commentRepository.findById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public Comment update(Comment object) {
        Optional<Comment> comment = commentRepository.findById(object.getId());
        comment.get().setDetails(object.getDetails());
        comment.get().setTitle(object.getTitle());
        return commentRepository.save(comment.get());
    }

    @Transactional(rollbackFor = Exception.class)
    public Comment partialUpdate(Comment object) throws HttpException {
        Optional<Comment> comment = commentRepository.findById(object.getId());
        if (!comment.isPresent()) {
            throw new HttpException(messageSource.getMessage("notExists.comment", null, null), HttpStatus.NOT_FOUND);
        }
        comment.get().setDetails(object.getDetails());
        comment.get().setTitle(object.getTitle());
        return commentRepository.save(comment.get());
    }

    @Transactional(rollbackFor = Exception.class)
    public Comment delete(Integer id) throws HttpException {
        Optional<Comment> comment = commentRepository.findById(id);
        if (!comment.isPresent()) {
            throw new HttpException(messageSource.getMessage("notExists.comment", null, null), HttpStatus.NOT_FOUND);
        }
        commentRepository.deleteById(id); //there is no need for soft delete
        return comment.get();
    }

    public List<Comment> getAllLatest(Integer nLatest){
        List<Comment> comments = commentRepository.getAllByActiveOrderByUpdatedDesc((byte)1);
        return comments.stream().limit(nLatest).collect(Collectors.toList());
    }


}
