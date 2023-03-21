package com.malta.post.service;

import com.malta.post.dto.PostResponseDto;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    public Flux<PostResponseDto> getKakaoBlogPosts(Mono<String> response) {
        return response
                .map(JSONObject::new)
                .flatMapMany(obj -> Flux.fromIterable(obj.getJSONArray("documents")))
                .map(json -> PostResponseDto.from(
                        ((JSONObject) json).getString("title"),
                        ((JSONObject) json).getString("blogname"),
                        ((JSONObject) json).getString("url"),
                        ((JSONObject) json).getString("contents"),
                        ((JSONObject) json).getString("datetime")
                ));
    }

    public Flux<PostResponseDto> getNaverBlogPosts(Mono<String> response) {
        return response
                .map(JSONObject::new)
                .flatMapMany(obj -> Flux.fromIterable(obj.getJSONArray("items")))
                .map(json -> PostResponseDto.from(
                        ((JSONObject) json).getString("title"),
                        ((JSONObject) json).getString("bloggername"),
                        ((JSONObject) json).getString("link"),
                        ((JSONObject) json).getString("description"),
                        ((JSONObject) json).getString("postdate")
                ));
    }
}
