package com.blogging.controllers;

import com.blogging.entities.Post;
import com.blogging.payloads.ApiResponse;
import com.blogging.payloads.PostDto;
import com.blogging.payloads.PostResponse;
import com.blogging.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;


    @PostMapping("/user/{userId}/category/{categoryId}/addPost")
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto,
                                              @PathVariable Integer userId,
                                              @PathVariable Integer categoryId) {

        PostDto createdPost = this.postService.createPost(postDto, userId, categoryId);

        return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
    }

    @PutMapping("/updatePost/{postId}")
    public ResponseEntity<PostDto> updatedPost(@RequestBody PostDto postDto,
                                               @PathVariable Integer postId) {

        PostDto updatedPost = this.postService.updatePost(postDto, postId);

        return new ResponseEntity<>(updatedPost, HttpStatus.OK);
    }

    @GetMapping("/category/{categoryId}/allPostsByCatId")
    public ResponseEntity<PostResponse> getPostsByCategory(@PathVariable Integer categoryId,
                                                            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
                                                            @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize,
                                                           @RequestParam(value = "sortBy", defaultValue = "postId", required = false) String sortBy,
                                                           @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir) {

        PostResponse posts = this.postService.getPostsByCategory(categoryId, pageNumber, pageSize, sortBy, sortDir);

        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}/allPostsByUserId")
    public ResponseEntity<PostResponse> getPostsByUser(@PathVariable Integer userId,
                                                        @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
                                                        @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize,
                                                       @RequestParam(value = "sortBy", defaultValue = "postId", required = false) String sortBy,
                                                       @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir) {

        PostResponse posts = this.postService.getPostsByUser(userId, pageNumber, pageSize, sortBy, sortDir);

        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @DeleteMapping("/deletePost/{postId}")
    public ResponseEntity<ApiResponse> deletePostById(@PathVariable Integer postId) {

        this.postService.deletePost(postId);

        return new ResponseEntity<>(new ApiResponse("Post is Successfully deleted", true), HttpStatus.OK);
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId) {

        return ResponseEntity.ok(this.postService.getPostById(postId));
    }

    @GetMapping("/allPosts")
    public ResponseEntity<PostResponse> getAllPosts(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = "postId", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir) {

        return ResponseEntity.ok(this.postService.getAllPost(pageNumber, pageSize, sortBy, sortDir));
    }
}
