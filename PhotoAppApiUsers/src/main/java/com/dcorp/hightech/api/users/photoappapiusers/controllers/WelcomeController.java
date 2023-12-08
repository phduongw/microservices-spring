package com.dcorp.hightech.api.users.photoappapiusers.controllers;

import com.dcorp.hightech.api.users.photoappapiusers.controllers.ui.CreateUserRequest;
import com.dcorp.hightech.api.users.photoappapiusers.controllers.ui.CreateUserResponse;
import com.dcorp.hightech.api.users.photoappapiusers.controllers.ui.UserDTO;
import com.dcorp.hightech.api.users.photoappapiusers.services.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class WelcomeController {

    private final UserService userService;

    @GetMapping("/status/check")
    public String welcome() {
        return "Welcome to D-corp High Tech";
    }

    @PostMapping
    public ResponseEntity<CreateUserResponse> createUser(@Valid @RequestBody CreateUserRequest request) {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        UserDTO user = userService.createUser(mapper.map(request, UserDTO.class));

        CreateUserResponse response = mapper.map(user, CreateUserResponse.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
