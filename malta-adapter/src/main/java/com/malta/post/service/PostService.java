package com.malta.post.service;

import com.malta.post.dto.PostResponseDto;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    public List<PostResponseDto> getKakaoBlogPosts(JSONObject body) {
        List<PostResponseDto> dtos = new ArrayList<>();

        for (Object json : body.getJSONArray("documents")) {
            dtos.add(PostResponseDto.from(
                    ((JSONObject) json).getString("title"),
                    ((JSONObject) json).getString("blogname"),
                    ((JSONObject) json).getString("url"),
                    ((JSONObject) json).getString("contents"),
                    ((JSONObject) json).getString("datetime")
            ));
        }
        return dtos;
    }

    public List<PostResponseDto> getNaverBlogPosts(JSONObject body) {
        List<PostResponseDto> dtos = new ArrayList<>();

        for (Object json : body.getJSONArray("items")) {
            dtos.add(PostResponseDto.from(
                    ((JSONObject) json).getString("title"),
                    ((JSONObject) json).getString("bloggername"),
                    ((JSONObject) json).getString("link"),
                    ((JSONObject) json).getString("description"),
                    ((JSONObject) json).getString("postdate")
            ));
        }
        return dtos;
    }
}
