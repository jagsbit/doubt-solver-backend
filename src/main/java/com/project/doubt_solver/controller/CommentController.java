package com.project.doubt_solver.controller;


import com.project.doubt_solver.payloads.CommentDto;
import com.project.doubt_solver.service.impl.CommentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private CommentServiceImpl commentService;

    @PostMapping("user/{userId}/doubt/{doubtId}")
    public ResponseEntity<CommentDto> saveComment(@RequestBody CommentDto commentDto, @PathVariable int userId,
                                               @PathVariable int doubtId){
        CommentDto savedComment=commentService.createComment(commentDto,userId,doubtId);
        return new ResponseEntity<>(savedComment, HttpStatus.CREATED);

    }

    @PutMapping("/upvote/{commentId}/user/{userId}")
    public ResponseEntity<String> upvote(@PathVariable int commentId,@PathVariable int userId){
        String response=commentService.upVote(commentId,userId);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
    @DeleteMapping("/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable int commentId){
        String response=commentService.deleteComment(commentId);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<CommentDto>> getCommentsByUser(@PathVariable int userId){
        List<CommentDto> commentDtoList=commentService.getCommentsByUser(userId);
        return  new ResponseEntity<>(commentDtoList,HttpStatus.OK);
    }
}
