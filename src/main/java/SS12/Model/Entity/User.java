package SS12.Model.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table
@Entity
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String phone;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false, unique = true)
    private String email;
    public enum Role{ ROLE_USER , ROLE_STAFF, ROLE_ADMIN}
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Role role = Role.ROLE_USER;
    @Builder.Default
    private boolean enabled = true;
}
