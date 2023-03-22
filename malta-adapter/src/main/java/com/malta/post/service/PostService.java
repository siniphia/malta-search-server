package com.malta.post.service;

import com.malta.post.dto.PostResponseDto;
import com.malta.post.util.Pagination;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    public Pagination<PostResponseDto> getKakaoBlogPosts(String response, Integer page, Integer size) {
        JSONObject json = new JSONObject(response);

        List<PostResponseDto> dtos = new ArrayList<>();
        for (Object body : json.getJSONArray("documents")) {
            dtos.add(PostResponseDto.from(
                    ((JSONObject) body).getString("title"),
                    ((JSONObject) body).getString("blogname"),
                    ((JSONObject) body).getString("url"),
                    ((JSONObject) body).getString("contents"),
                    ((JSONObject) body).getString("datetime")
            ));
        }

        return new Pagination<>(page, size, json.getJSONObject("meta").getInt("pageable_count"), dtos);
    }

    public Pagination<PostResponseDto> getNaverBlogPosts(String response, Integer page, Integer size) {
        JSONObject json = new JSONObject(response);

        List<PostResponseDto> dtos = new ArrayList<>();
        for (Object body : json.getJSONArray("items")) {
            dtos.add(PostResponseDto.from(
                    ((JSONObject) body).getString("title"),
                    ((JSONObject) body).getString("bloggername"),
                    ((JSONObject) body).getString("link"),
                    ((JSONObject) body).getString("description"),
                    ((JSONObject) body).getString("postdate")
            ));
        }

        return new Pagination<>(page, size, json.getInt("total"), dtos);
    }
}
