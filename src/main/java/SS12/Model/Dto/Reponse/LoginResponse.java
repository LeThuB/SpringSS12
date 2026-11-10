package SS12.Model.Dto.Reponse;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class LoginResponse {
    private String accessToken;
    private String refreshToken;
}
