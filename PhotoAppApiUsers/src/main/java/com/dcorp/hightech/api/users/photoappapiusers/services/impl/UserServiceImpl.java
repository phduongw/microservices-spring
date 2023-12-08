package com.dcorp.hightech.api.users.photoappapiusers.services.impl;

import com.dcorp.hightech.api.users.photoappapiusers.controllers.ui.UserDTO;
import com.dcorp.hightech.api.users.photoappapiusers.entites.UserEntity;
import com.dcorp.hightech.api.users.photoappapiusers.repositories.UserRepository;
import com.dcorp.hightech.api.users.photoappapiusers.services.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDTO createUser(UserDTO user) {
        user.setUserId(UUID.randomUUID().toString());
        user.setEncryptedPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        UserEntity userEntity = mapper.map(user, UserEntity.class);

        return mapper.map(userRepository.save(userEntity), UserDTO.class);
    }
}
