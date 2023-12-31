package com.dcorp.hightech.api.users.photoappapiusers.controllers.ui;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDTO implements Serializable {

    String firstName;
    String lastName;
    String email;
    String password;
    String userId;
    String encryptedPassword;
    List<AlbumResponseModel> albums = new ArrayList<>();

    public void addAlbum(AlbumResponseModel album) {
        this.albums.add(album);
    }

    public void addAllAlbums(List<AlbumResponseModel> albums) {
        this.albums.addAll(albums);
    }

}
