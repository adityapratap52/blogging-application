package com.blogging.servicesimpl;

import com.blogging.entities.Category;
import com.blogging.entities.Post;
import com.blogging.entities.User;
import com.blogging.exceptions.ResourceNotFoundException;
import com.blogging.payloads.PostDto;
import com.blogging.payloads.PostResponse;
import com.blogging.repositories.CategoryRepo;
import com.blogging.repositories.PostRepo;
import com.blogging.repositories.UserRepo;
import com.blogging.services.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CategoryRepo categoryRepo;


    @Override
    public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {

        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "UserId", userId));
        Category category = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "CategoryId", categoryId));

        Post post = this.modelMapper.map(postDto, Post.class);
        post.setImageName("default.png");
        post.setAddedDate(new Date());
        post.setUpdatedDate(new Date());
        post.setUser(user);
        post.setCategory(category);

        Post newPost = this.postRepo.save(post);

        return this.modelMapper.map(newPost, PostDto.class);
    }

    @Override
    public PostDto updatePost(PostDto postDto) {

        Post post = this.postRepo.findById(postDto.getPostId()).orElseThrow(() -> new ResourceNotFoundException("Post", "PostId", postDto.getPostId()));

        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        if (postDto.getImageName() != null && !Objects.equals(postDto.getImageName(), "")) {
            post.setImageName(postDto.getImageName());
        }
        postDto.setUpdatedDate(new Date());

        Post updatedPost = this.postRepo.save(post);
        return this.modelMapper.map(updatedPost, PostDto.class);
    }

    @Override
    public void deletePost(Integer postId) {

        Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "PostId", postId));

        this.postRepo.delete(post);
    }

    @Override
    public PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {

        Sort sort = (sortDir.equalsIgnoreCase("asc")) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        Page<Post> pagePost = this.postRepo.findAll(pageable);
        List<Post> posts = pagePost.getContent();

        List<PostDto> postDtos = posts.stream().map((post) -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());

        // Set the pagination
        PostResponse postResponse = this.returnResponse(postDtos, pagePost);

        return postResponse;
    }

    @Override
    public PostDto getPostById(Integer postId) {

        Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "PostId", postId));

        return this.modelMapper.map(post, PostDto.class);
    }

    @Override
    public PostResponse getPostsByCategory(Integer categoryId, Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {

        Sort sort = (sortDir.equalsIgnoreCase("asc")) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        Category cat = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "CategoryId", categoryId));

        Page<Post> pagePost = this.postRepo.findByCategory(cat, pageable);
        List<Post> posts = pagePost.getContent();

        List<PostDto> postDtos = posts.stream().map((post) -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());

        // Set the pagination
        PostResponse postResponse = this.returnResponse(postDtos, pagePost);

        return postResponse;
    }

    @Override
    public PostResponse getPostsByUser(Integer userId, Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {

        Sort sort = (sortDir.equalsIgnoreCase("asc")) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "UserId", userId));

        Page<Post> pagePost = this.postRepo.findByUser(user, pageable);
        List<Post> posts = pagePost.getContent();

        List<PostDto> postDtos = posts.stream().map((post) -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());

        // Set the pagination
        PostResponse postResponse = this.returnResponse(postDtos, pagePost);

        return postResponse;
    }

    @Override
    public List<PostDto> searchPosts(String keyword) {

//        List<Post> posts = this.postRepo.findByTitleContaining("%"+keyword+"%");
        List<Post> posts = this.postRepo.findByTitleContaining(keyword);

        List<PostDto> postDtos = posts.stream().map((post) -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());

        return postDtos;
    }

    public PostResponse returnResponse(List<PostDto> postDtos, Page<Post> pagePost) {
        PostResponse postResponse = new PostResponse();
        postResponse.setContent(postDtos);
        postResponse.setPageNumber(pagePost.getNumber());
        postResponse.setPageSize(pagePost.getSize());
        postResponse.setTotalElements(pagePost.getTotalElements());
        postResponse.setTotalPages(pagePost.getTotalPages());
        postResponse.setLastPage(pagePost.isLast());

        return postResponse;
    }
}
