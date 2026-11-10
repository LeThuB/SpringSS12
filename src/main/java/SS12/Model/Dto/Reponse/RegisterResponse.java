package SS12.Model.Dto.Reponse;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterResponse {
    private Long id;
    private String phone;
    private String email;
    private String message;
}

