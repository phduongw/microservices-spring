package com.dcorp.hightech.api.users.photoappapiusers.controllers.ui;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDTO implements Serializable {

    String firstName;
    String lastName;
    String email;
    String password;
    String userId;
    String encryptedPassword;

}
