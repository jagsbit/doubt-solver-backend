package com.project.doubt_solver.payloads;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class CommentDto {
    private int commentId;
    private String content;
    private Set<Integer> upvotes=new HashSet<>();
    private LocalDateTime createdAt;

    public CommentDto(String content) {
        this.content = content;
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public CommentDto() {
    }

    public Set<Integer> getUpvotes() {
        return upvotes;
    }

    public void setUpvotes(Set<Integer> upvotes) {
        this.upvotes = upvotes;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }



    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
