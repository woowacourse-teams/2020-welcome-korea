package wooteco.team.ittabi.legenoaroundhere.controller;

import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import wooteco.team.ittabi.legenoaroundhere.dto.LoginRequest;
import wooteco.team.ittabi.legenoaroundhere.dto.TokenResponse;
import wooteco.team.ittabi.legenoaroundhere.dto.UserCreateRequest;
import wooteco.team.ittabi.legenoaroundhere.dto.UserResponse;
import wooteco.team.ittabi.legenoaroundhere.dto.UserUpdateRequest;
import wooteco.team.ittabi.legenoaroundhere.service.UserService;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/join")
    public ResponseEntity<Void> join(@RequestBody UserCreateRequest userCreateRequest) {
        Long userId = userService.createUser(userCreateRequest);
        return ResponseEntity
            .created(URI.create("/users/" + userId))
            .build();
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest loginRequest) {
        TokenResponse token = userService.login(loginRequest);
        return ResponseEntity
            .ok(token);
    }

    @GetMapping("/users/myinfo")
    public ResponseEntity<UserResponse> findUser() {
        UserResponse user = userService.findUser();
        return ResponseEntity
            .ok(user);
    }

    @PutMapping("/users/myinfo")
    public ResponseEntity<UserResponse> updateUser(
        @RequestBody UserUpdateRequest userUpdateRequest) {
        UserResponse user = userService.updateUser(userUpdateRequest);
        return ResponseEntity
            .ok(user);
    }

    @DeleteMapping("/users/myinfo")
    public ResponseEntity<Void> deleteUser() {
        userService.deleteUser();
        return ResponseEntity
            .noContent()
            .build();
    }
}
