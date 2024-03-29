package com.jsp.ums.utility;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.jsp.ums.exception.UserNotFoundById;

@RestControllerAdvice
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {

	private ResponseEntity<Object> structure(HttpStatus status,String message,Object rootcause){
		return new ResponseEntity<Object>(Map.of(
				"status",status.value(),
				"message",message,
				"rootcause",rootcause),status
				);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		List<ObjectError> allErrors = ex.getAllErrors();

		Map<String, String> errors=new HashMap<String,String>();

		allErrors.forEach(error->{
			FieldError fieldError=(FieldError)error; 
			errors.put(fieldError.getField(), fieldError.getDefaultMessage());
		});

		return structure(HttpStatus.BAD_REQUEST, "Failed to Save the data", errors);

	}


	@ExceptionHandler(UserNotFoundById.class)
	public ResponseEntity<Object> handlerUserNotFoundById(UserNotFoundById ex){
		return structure(HttpStatus.NOT_FOUND, ex.getMessage(), "user Not found WIth the given id");
	}


	@ExceptionHandler(UsernameNotFoundException.class)
	public ResponseEntity<Object> handleUserNotFoundByName(UsernameNotFoundException ex){
		return structure(HttpStatus.NOT_FOUND, ex.getMessage(), "Failed to authenicate user");
	}

}

