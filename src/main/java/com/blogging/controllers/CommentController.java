package com.blogging.controllers;

import com.blogging.payloads.ApiResponse;
import com.blogging.payloads.CommentDto;
import com.blogging.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/user/{userId}/post/{postId}/addComment")
    public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto, @PathVariable Integer userId, @PathVariable Integer postId) {

        CommentDto createComment = this.commentService.createComment(commentDto, postId, userId);

        return new ResponseEntity<>(createComment, HttpStatus.CREATED);
    }

    @DeleteMapping("/deleteComment/{commentId}")
    public ResponseEntity<ApiResponse> deleteComment(@PathVariable Integer commentId) {

        this.commentService.deleteComment(commentId);

        return new ResponseEntity<>(new ApiResponse("Comment deleted Successfully", true), HttpStatus.OK);
    }
}
