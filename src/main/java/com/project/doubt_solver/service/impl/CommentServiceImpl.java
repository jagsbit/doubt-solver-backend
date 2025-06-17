package com.project.doubt_solver.service.impl;

import com.project.doubt_solver.exceptions.ResourceNotFoundException;
import com.project.doubt_solver.model.Comment;
import com.project.doubt_solver.model.Doubt;
import com.project.doubt_solver.model.Users;
import com.project.doubt_solver.payloads.CommentDto;
import com.project.doubt_solver.repository.CommentRepository;
import com.project.doubt_solver.repository.DoubtRepository;
import com.project.doubt_solver.repository.UserRepository;
import com.project.doubt_solver.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepo;
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private DoubtRepository doubtRepo;

    @Autowired
    private AIServiceImpl aiService;

    @Autowired
    private ModelMapper modelMapper;
    @Override
    public CommentDto createComment(CommentDto commentDto, int userId, int doubtId) {
        Users user=userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","user_id",userId));
        Doubt doubt=doubtRepo.findById(doubtId).orElseThrow(()->new ResourceNotFoundException("Doubt","doubt_id",doubtId));
        boolean response=aiService.checkComment(doubt.getContent(),commentDto.getContent());
        if(!response) return new CommentDto("Your Comment is not related to question");
        Comment comment=modelMapper.map(commentDto,Comment.class);
        comment.setUser(user);
        comment.setDoubt(doubt);
        Comment savedComment=commentRepo.save(comment);
        return modelMapper.map(savedComment,CommentDto.class);
    }

    @Override
    public String deleteComment(int commentId) {

        Comment comment=commentRepo.findById(commentId)
                .orElseThrow(()->new ResourceNotFoundException("Comment","commentId",commentId));

        commentRepo.delete(comment);
        return "comment deleted successfully";
    }

    @Override
    public String upVote(int commentId, int userId) {
        Comment comment=commentRepo.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment","commentId",commentId));
        Users user=userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","userId",userId));
        comment.getUpvotes().add(userId);
        commentRepo.save(comment);
        return "Upvoted";
    }

    @Override
    public List<CommentDto> getCommentsByUser(int userId) {
        Users user=userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","userId",userId));
        List<Comment> commentList=commentRepo.findByUser(user);
        List<CommentDto> commentDtoList=commentList.stream().map((comment -> modelMapper.map(comment,CommentDto.class))).toList();
        return commentDtoList;
    }


}
