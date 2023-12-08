package com.dcorp.hightech.api.users.photoappapiusers.controllers.ui;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateUserResponse {

    String firstName;

    String lastName;

    String userId;

    String email;
}
