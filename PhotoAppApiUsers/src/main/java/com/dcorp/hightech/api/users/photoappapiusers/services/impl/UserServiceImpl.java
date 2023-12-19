package com.dcorp.hightech.api.users.photoappapiusers.services.impl;

import com.dcorp.hightech.api.users.photoappapiusers.controllers.ui.AlbumResponseModel;
import com.dcorp.hightech.api.users.photoappapiusers.controllers.ui.UserDTO;
import com.dcorp.hightech.api.users.photoappapiusers.entites.UserEntity;
import com.dcorp.hightech.api.users.photoappapiusers.feign.AlbumsServiceClient;
import com.dcorp.hightech.api.users.photoappapiusers.repositories.UserRepository;
import com.dcorp.hightech.api.users.photoappapiusers.services.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
//    private final RestTemplate restTemplate;
    private final AlbumsServiceClient albumsServiceClient;
    @Override
    public UserDTO createUser(UserDTO user) {
        user.setUserId(UUID.randomUUID().toString());
        user.setEncryptedPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        UserEntity userEntity = mapper.map(user, UserEntity.class);

        return mapper.map(userRepository.save(userEntity), UserDTO.class);
    }

    @Override
    public UserDTO getUserDetailsByEmail(String email) {
        Optional<UserEntity> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException(email);
        }

        return new ModelMapper().map(user.get(), UserDTO.class);
    }

    @Override
    public UserDTO getUserByUserID(String id) {
        UserEntity user = userRepository.findByUserId(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found!"));
//        String albumUrl = String.format("http://ALBUMS-WS/users/%s/albums", id);
//
//        ResponseEntity<List<AlbumResponseModel>> albumResponse = restTemplate.exchange(albumUrl, HttpMethod.GET, null, new ParameterizedTypeReference<List<AlbumResponseModel>>() {
//        });
//        List<AlbumResponseModel> listAlbums = albumResponse.getBody();
        UserDTO response = new ModelMapper().map(user, UserDTO.class);
        response.addAllAlbums(albumsServiceClient.getAlbums(id));
        return response;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> user = userRepository.findByEmail(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException(username);
        }

        UserEntity userEntity = user.get();
        return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(), true, true, true, true, new ArrayList<>());
    }
}
