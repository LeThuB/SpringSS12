package SS12.Exception;

import SS12.Exception.CustomException.DulicateResourceException;
import SS12.Exception.CustomException.InvalidException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.method.HandleAuthorizationDenied;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalHandleException {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse<Map<String,String>>>validException(MethodArgumentNotValidException ex){
        Map<String,String>errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(fieldError -> {
            errors.put(fieldError.getField(),fieldError.getDefaultMessage());
        });
        ErrorResponse<Map<String,String>>errorResponse = ErrorResponse.<Map<String,String>>builder()
                .status(400)
                .message("Loi validate")
                .timestamp(LocalDateTime.now())
                .data(errors).build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(DulicateResourceException.class)
    public ResponseEntity<ErrorResponse<String>> dulicateException(DulicateResourceException ex){
        ErrorResponse<String> errorResponse = ErrorResponse.<String>builder()
                .status(409)
                .message(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .data("Email da ton tai").build();
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }
    @ExceptionHandler(InvalidException.class)
    public ResponseEntity<ErrorResponse<String>> invalidEx(InvalidException ex){
        ErrorResponse<String> invalid = ErrorResponse.<String>builder()
                .status(401)
                .message(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .data(null)
                .build();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(invalid);
    }
}
