package SS12.Service;

import SS12.Exception.CustomException.DulicateResourceException;
import SS12.Exception.CustomException.InvalidException;
import SS12.Model.Dto.Reponse.LoginResponse;
import SS12.Model.Dto.Request.LoginRequestDTO;
import SS12.Model.Dto.Request.RegisterRequest;
import SS12.Model.Entity.User;
import SS12.Repository.UserRepository;
import SS12.Security.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
   @Autowired
   private UserRepository userRepository;
   @Autowired
   private PasswordEncoder passwordEncoder;
   @Autowired
   private JwtProvider jwtProvider;
   public User registerRequest(RegisterRequest registerRequest){
       if(userRepository.existsByEmail(registerRequest.getEmail())){
           throw new DulicateResourceException("Email da ton tai");
       }
       User user = User.builder()
               .phone(registerRequest.getPhone())
               .email(registerRequest.getEmail())
               .password(passwordEncoder.encode(registerRequest.getPassword())).build();
       return userRepository.save(user);
   }
public LoginResponse loginResponse(LoginRequestDTO loginRequestDTO){
    System.out.println("===== LOGIN CONTROLLER =====");
       User user = userRepository.findByEmail(loginRequestDTO.getEmail()).orElseThrow(()->
               new InvalidException("Email hoac mat khau khong chinh xac"));
       //kiem tra password
    if(!passwordEncoder.matches(loginRequestDTO.getPassword(), user.getPassword())){
        throw new InvalidException("Email hoac mat khau khong chinh xac");
    }
    //neu dung thi tao token
    String accessToken = jwtProvider.generateAccessToken(user);
    String refreshToken = jwtProvider.generrateRefreshToken(user);
    return new LoginResponse(accessToken,refreshToken);
}
}
