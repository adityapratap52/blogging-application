package com.blogging.payloads;

import com.blogging.entities.Category;
import com.blogging.entities.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
public class PostDto {

    Integer postId;

    @NotBlank
    @Size(min = 3, message = "size must be min 3 chars")
    private String title;

    @NotBlank
    private String content;

    private String imageName;

    private Date addedDate;

    private Date updatedDate;

    private CategoryDto category;

    private UserDto user;

}
