package com.project.doubt_solver.service;

import com.project.doubt_solver.payloads.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto createComment(CommentDto commentDto,int userId,int doubtId);
    String deleteComment(int commentId);
    String upVote(int commentId,int userId);
    List<CommentDto> getCommentsByUser(int userId);
}
