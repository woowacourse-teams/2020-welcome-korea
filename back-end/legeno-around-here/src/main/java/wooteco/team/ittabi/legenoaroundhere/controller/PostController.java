package wooteco.team.ittabi.legenoaroundhere.controller;

import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wooteco.team.ittabi.legenoaroundhere.dto.PostRequest;
import wooteco.team.ittabi.legenoaroundhere.dto.PostResponse;
import wooteco.team.ittabi.legenoaroundhere.service.PostService;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public ResponseEntity<Void> createPost(Authentication authentication,
        @RequestBody PostRequest postRequest) {
        Long postId = postService.createPost(authentication, postRequest).getId();
        return ResponseEntity
            .created(URI.create("/posts/" + postId))
            .build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> findPost(Authentication authentication,
        @PathVariable Long id) {
        PostResponse post = postService.findPost(authentication, id);
        return ResponseEntity
            .ok()
            .body(post);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updatePost(Authentication authentication, @PathVariable Long id,
        @RequestBody PostRequest postRequest) {
        postService.updatePost(authentication, id, postRequest);
        return ResponseEntity
            .ok()
            .build();
    }

    @GetMapping
    public ResponseEntity<List<PostResponse>> findAllPost(Authentication authentication) {
        List<PostResponse> posts = postService.findAllPost(authentication);
        return ResponseEntity
            .ok()
            .body(posts);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(Authentication authentication, @PathVariable Long id) {
        postService.deletePost(authentication, id);
        return ResponseEntity
            .noContent()
            .build();
    }
}
