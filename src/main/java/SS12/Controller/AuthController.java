package SS12.Controller;

import SS12.Model.Dto.Reponse.ApiResponse;
import SS12.Model.Dto.Reponse.LoginResponse;
import SS12.Model.Dto.Reponse.RegisterResponse;
import SS12.Model.Dto.Request.LoginRequestDTO;
import SS12.Model.Dto.Request.RegisterRequest;
import SS12.Model.Entity.User;
import SS12.Service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    @Autowired
    private UserService userService;



    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@Valid @RequestBody RegisterRequest registerRequest) {
        User user = userService.registerRequest(registerRequest);
        RegisterResponse response = RegisterResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .phone(user.getPhone())
                .message("Dang ky thanh cong").build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequestDTO loginRequestDTO) {
        LoginResponse response = userService.loginResponse(loginRequestDTO);
        return ResponseEntity.ok(response);
    }
}
