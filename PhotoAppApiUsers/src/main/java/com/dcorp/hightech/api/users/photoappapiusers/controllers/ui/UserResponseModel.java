package com.dcorp.hightech.api.users.photoappapiusers.controllers.ui;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponseModel {
    String userId;
    String firstName;
    String lastName;
    String email;
    List<AlbumResponseModel> albums = new ArrayList<>();

    public void addElement(AlbumResponseModel album) {
        this.albums.add(album);
    }

    public void addAllElement(List<AlbumResponseModel> albums) {
        this.albums.addAll(albums);
    }
}
