package es.alvarochueca.inventory.exception;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ProductNotFoundException.class)
	public ResponseEntity<Map<String, String>> handlerResourceNotFoundException(ProductNotFoundException ex, WebRequest request) {
		Map<String, String> errorDetails = new HashMap<>();
		errorDetails.put("timestamp", LocalDateTime.now().toString());
		errorDetails.put("message", ex.getMessage());
		errorDetails.put("path", request.getDescription(false));
		return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(DuplicateSkuException.class)
	public ResponseEntity<Map<String, String>> handlerDuplicateSkuException(DuplicateSkuException ex, WebRequest request){
		Map<String, String> errorDetails = new HashMap<>();
		errorDetails.put("timestamp", LocalDateTime.now().toString());
		errorDetails.put("message", ex.getMessage());
		errorDetails.put("path", request.getDescription(false));
		return new ResponseEntity<>(errorDetails, HttpStatus.CONFLICT);
	}

	@ExceptionHandler(InsufficientStockException.class)
	public ResponseEntity<Map<String, String>> handlerInsufficientStockException(InsufficientStockException ex, WebRequest request){
		Map<String, String> errorDetails = new HashMap<>();
		errorDetails.put("timestamp", LocalDateTime.now().toString());
		errorDetails.put("message", ex.getMessage());
		errorDetails.put("path", request.getDescription(false));
		return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Object> handlerValidationExceptions(MethodArgumentNotValidException ex){
		List<String> errors = ex.getBindingResult()
				.getAllErrors()
				.stream()
				.map(DefaultMessageSourceResolvable::getDefaultMessage)
				.toList();

		Map<String, Object> body = new HashMap<>();
		body.put("timestamp", LocalDateTime.now());
		body.put("status", HttpStatus.BAD_REQUEST.value());
		body.put("errors", errors);
		body.put("message", "Validation failed");

		return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<Map<String, String>> handleIllegalArgument(IllegalArgumentException ex, WebRequest request){
		Map<String, String> errorDetails = new HashMap<>();
		errorDetails.put("timestamp", LocalDateTime.now().toString());
		errorDetails.put("message", ex.getMessage());
		errorDetails.put("path", request.getDescription(false));
		return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Map<String, String>> handleGlobalException(Exception ex, WebRequest request) {
		Map<String, String> errorDetails = new HashMap<>();
		errorDetails.put("timestamp", LocalDateTime.now().toString());
		errorDetails.put("message", "An unexpected error occurred: " + ex.getMessage());
		errorDetails.put("path", request.getDescription(false));
		return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
