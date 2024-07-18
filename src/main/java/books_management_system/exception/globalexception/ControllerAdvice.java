package books_management_system.exception.globalexception;

import books_management_system.dto.ResponseDto;
import books_management_system.entity.Book;
import books_management_system.exception.BMSException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

import static books_management_system.constant.BMSConstant.*;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(BMSException.class)
    public ResponseEntity<ResponseDto<?>> handlesBMSException(Exception e) {
        return new ResponseEntity<>(new ResponseDto<>(
                BMS_EXCEPTION,
                List.of(e.getMessage())
                ), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseDto<Book>> handlesException(Exception e) {
        return new ResponseEntity<>(new ResponseDto<>(
                SOMETHING_WENT_WRONG,
                List.of(e.getMessage())
        ), HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseDto<Book>> handleInvalidInputs(MethodArgumentNotValidException e) {
        List<String> errorMessages = new ArrayList<>();
        e.getBindingResult().getFieldErrors().forEach(
                er -> errorMessages.add(er.getDefaultMessage())
        );
        return new ResponseEntity<>(new ResponseDto<>(ARGUMENT_NOT_VALID, errorMessages), HttpStatus.BAD_REQUEST);
    }

}
