package com.dcorp.hightech.api.users.photoappapiusers.controllers.ui;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateUserRequest {

    @NotNull(message = "First Name can't be null")
    @Size(min = 2, message = "First Name must not be less than 2 characters")
    String firstName;

    @NotNull(message = "Last Name can't be null")
    @Size(min = 2, message = "Last Name must not be less than 2 characters")
    String lastName;

    @NotNull(message = "Password can't be null")
    @Size(min = 8, max = 16, message = "Password must be equal or grater than 8 characters and less than 16 charactes")
    String password;

    @Email
    @NotNull(message = "Email can't be null")
    String email;
}
