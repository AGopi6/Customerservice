
package com.rabo.customerservice.handler.exception;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.rabo.customerservice.exception.CustomerNotFoundException;
import com.rabo.customerservice.util.ErrorCodes;

@ControllerAdvice
@RestController
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
	private static final Logger log = LoggerFactory.getLogger(CustomizedResponseEntityExceptionHandler.class);

	@ExceptionHandler(Exception.class)
	public final ResponseEntity<ExceptionResponse> handleAllExceptions(Exception ex, WebRequest request) {
		ExceptionResponse exceptionResponseMsg = new ExceptionResponse(new Date(), ex.getMessage().toString(),
				request.getDescription(false));
		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getCause().getCause().toString(),
				request.getDescription(false));
		log.error("Exception messge=" + exceptionResponseMsg.getMessage());
		log.error("Exception root cause messge=" + exceptionResponse.getMessage());
		if (exceptionResponse.getMessage().toString().contains("propertyPath=custId")) {
			ExceptionResponse exceptionResponse2 = new ExceptionResponse(new Date(), ErrorCodes.CUST_ID_MANDATORY,
					request.getDescription(false));
			return new ResponseEntity<>(exceptionResponse2, HttpStatus.BAD_REQUEST);
		}
		if (exceptionResponse.getMessage().toString().contains("propertyPath=fname")) {
			ExceptionResponse exceptionResponse2 = new ExceptionResponse(new Date(), ErrorCodes.FNAME_MANDATORY,
					request.getDescription(false));
			return new ResponseEntity<>(exceptionResponse2, HttpStatus.BAD_REQUEST);
		} else if (exceptionResponse.getMessage().toString().contains("propertyPath=lname")) {
			ExceptionResponse exceptionResponse2 = new ExceptionResponse(new Date(), ErrorCodes.LNAME_MANDATORY,
					request.getDescription(false));
			return new ResponseEntity<>(exceptionResponse2, HttpStatus.BAD_REQUEST);
		} else if ((exceptionResponse.getMessage().toString().contains("propertyPath=age"))
				&& (exceptionResponse.getMessage().toString().contains("Min.message"))) {
			ExceptionResponse exceptionResponse2 = new ExceptionResponse(new Date(), ErrorCodes.AGE_MANDATORY_MIN,
					request.getDescription(false));
			return new ResponseEntity<>(exceptionResponse2, HttpStatus.BAD_REQUEST);
		} else if ((exceptionResponse.getMessage().toString().contains("propertyPath=age"))
				&& (exceptionResponse.getMessage().toString().contains("Max.message"))) {
			ExceptionResponse exceptionResponse2 = new ExceptionResponse(new Date(), ErrorCodes.AGE_MANDATORY_MAX,
					request.getDescription(false));
			return new ResponseEntity<>(exceptionResponse2, HttpStatus.BAD_REQUEST);
		} else if (exceptionResponse.getMessage().toString().contains("propertyPath=age")) {
			ExceptionResponse exceptionResponse2 = new ExceptionResponse(new Date(), ErrorCodes.AGE_MANDATORY,
					request.getDescription(false));
			return new ResponseEntity<>(exceptionResponse2, HttpStatus.BAD_REQUEST);
		} else if (exceptionResponse.getMessage().toString().contains("propertyPath=address")) {
			ExceptionResponse exceptionResponse2 = new ExceptionResponse(new Date(), ErrorCodes.ADDRESS_MANDATORY,
					request.getDescription(false));
			return new ResponseEntity<>(exceptionResponse2, HttpStatus.BAD_REQUEST);
		} else if (exceptionResponse.getMessage().toString().contains("propertyPath=city")) {
			ExceptionResponse exceptionResponse2 = new ExceptionResponse(new Date(), ErrorCodes.CITY_MANDATORY,
					request.getDescription(false));
			return new ResponseEntity<>(exceptionResponse2, HttpStatus.BAD_REQUEST);
		} else if (exceptionResponse.getMessage().toString().contains("propertyPath=zipCode")) {
			ExceptionResponse exceptionResponse2 = new ExceptionResponse(new Date(), ErrorCodes.ZIPCODE_MANDATORY,
					request.getDescription(false));
			return new ResponseEntity<>(exceptionResponse2, HttpStatus.BAD_REQUEST);
		} else if (exceptionResponse.getMessage().toString().contains("propertyPath=Country")) {
			ExceptionResponse exceptionResponse2 = new ExceptionResponse(new Date(), ErrorCodes.COUNTRY_MANDATORY,
					request.getDescription(false));
			return new ResponseEntity<>(exceptionResponse2, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(CustomerNotFoundException.class)
	public final ResponseEntity<ExceptionResponse> handleUserNotFoundException(CustomerNotFoundException ex,
			WebRequest request) {
		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage(),
				request.getDescription(false));
		return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<Object> handleTypeMismatch(MethodArgumentTypeMismatchException ex, WebRequest request) {

		// List<String> details = new ArrayList<>();
		String name = ex.getName();
		String type = ex.getRequiredType().getSimpleName();
		Object value = ex.getValue();
		String message = String.format("'%s' should be a valid '%s' and '%s' isn't the expecpted value", name, type,
				value);
		// details.add(message);

		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), message, request.getDescription(false));

		return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		ExceptionResponse errorDetails = new ExceptionResponse(new Date(), ErrorCodes.METHOD_ARG_NOT_VALID,
				ex.getBindingResult().toString());
		return new ResponseEntity<Object>(errorDetails, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(javax.validation.ConstraintViolationException.class)
	protected ResponseEntity<Object> handleConstraintViolation(javax.validation.ConstraintViolationException ex) {
		ExceptionResponse errorDetails = new ExceptionResponse(new Date(), ErrorCodes.CONSTRAINT_VALID_FAILED,
				ex.getConstraintViolations().toString());
		return new ResponseEntity<Object>(errorDetails, HttpStatus.BAD_REQUEST);

	}

}