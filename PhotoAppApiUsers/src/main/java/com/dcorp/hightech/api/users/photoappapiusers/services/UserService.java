package com.dcorp.hightech.api.users.photoappapiusers.services;

import com.dcorp.hightech.api.users.photoappapiusers.controllers.ui.UserDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    UserDTO createUser(UserDTO user);
    UserDTO getUserDetailsByEmail(String email);
    UserDTO getUserByUserID(String id);
}
