package books_management_system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
public class ResponseDto<T> {

    private String message;
    private List<String> errorMessage;
    private T data;

    public ResponseDto(String message, T data, List<String> errorMessage) {
        this.message = message;
        this.data = data;
        this.errorMessage = errorMessage;
    }

    public ResponseDto(String message, List<String> errorMessage) {
        this.message = message;
        this.errorMessage = errorMessage;
    }
}
