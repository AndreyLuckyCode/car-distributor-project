package andrey.code.api.exceptions;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Log4j2
@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Object> handleBadRequestException(BadRequestException ex, WebRequest request) throws Exception {
        log.error("Bad Request Exception", ex);
        return handleException(ex, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(NotFoundException ex, WebRequest request) throws Exception {
        log.error("Not Found Exception", ex);
        return handleException(ex, HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGenericException(Exception ex, WebRequest request) throws Exception {
        log.error("Exception during execution of application", ex);
        return handleException(ex, HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    private ResponseEntity<Object> handleException(Exception ex, HttpStatus status, WebRequest request) {
        // Обработка и возврат ResponseEntity с нужным статусом HTTP и дополнительной информацией об ошибке
        return ResponseEntity
                .status(status)
                .body(ErrorDTO.builder()
                        .error(status.getReasonPhrase())
                        .errorDescription(ex.getMessage())
                        .build());
    }
}
