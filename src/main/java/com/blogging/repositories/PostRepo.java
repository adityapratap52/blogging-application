package com.blogging.repositories;

import com.blogging.entities.Category;
import com.blogging.entities.Post;
import com.blogging.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepo extends JpaRepository<Post, Integer> {

    Page<Post> findByUser(User user, Pageable pageable);
    Page<Post> findByCategory(Category category, Pageable pageable);

    List<Post> findByTitleContaining(String title);

//    @Query("select p from Post p where p.title like :title")
//    List<Post> findByTitleContaining(@Param("title") String title);
}
