package com.dcorp.hightech.api.users.photoappapiusers.controllers.ui;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AlbumResponseModel {

    String albumId;
    String userId;
    String name;
    String description;

}
