package com.dcorp.hightech.api.users.photoappapiusers.feign;

import com.dcorp.hightech.api.users.photoappapiusers.controllers.ui.AlbumResponseModel;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@FeignClient(name = "albums-ws")
public interface AlbumsServiceClient {

    @GetMapping("/users/{id}/albums")
    @CircuitBreaker(name = "albums-ws", fallbackMethod = "getAlbumsFallBack")
    List<AlbumResponseModel> getAlbums(@PathVariable String id);

    default List<AlbumResponseModel> getAlbumsFallBack(String id, Throwable exception) {
        System.out.println("Param = " + id);
        System.out.println("Exception took place = " + exception.getMessage());
        return new ArrayList<>();
    }
}


