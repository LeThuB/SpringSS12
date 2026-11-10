package SS12.Exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
@Getter
@AllArgsConstructor
@Builder
public class ErrorResponse<T> {
    private int status;
    private String message;
    private LocalDateTime timestamp;
    private T data;
}
