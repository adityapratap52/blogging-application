package com.blogging.payloads;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserDto {

    private Integer id;

    @NotNull
    @Size(min = 4, message = "username must be min 4 character")
    private String name;

    @Pattern(regexp = "[a-zA-Z][a-zA-Z0-9_.]*[a-zA-Z0-9]+@gmail[.]com", message = "This is not valid email")
    private String email;

    @NotNull
    @Size(min = 3, max = 10, message = "Password must be min 3 and max 10 chars")
    private String password;

    @NotNull
    private String about;

//    private Set<CommentDto> comments;
}
