package com.example.englishmaster_be.exception.handler;

import com.example.englishmaster_be.common.constant.error.ErrorEnum;
import com.example.englishmaster_be.common.dto.response.WrapperApiResponse;
import com.example.englishmaster_be.exception.template.BadRequestException;
import com.example.englishmaster_be.exception.template.CustomException;
import com.example.englishmaster_be.exception.template.RefreshTokenException;
import com.example.englishmaster_be.exception.template.ResourceNotFoundException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import jakarta.mail.MessagingException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

import java.nio.file.FileAlreadyExistsException;
import java.rmi.ServerException;
import java.util.*;

@RestControllerAdvice
public class  GlobalExceptionHandler {


    @ExceptionHandler(CustomException.class)
    public WrapperApiResponse.ExceptionResponse handleCustomException(CustomException e) {

        ErrorEnum error = e.getError();

        return WrapperApiResponse.ExceptionResponse.builder()
                .status(error.getStatusCode())
                .code(error.getStatusCode().value())
                .message(error.getMessage())
                .build();
    }

    @ExceptionHandler({
            ResourceNotFoundException.class,
            NoSuchElementException.class
    })
    public WrapperApiResponse.ExceptionResponse handlingResourceNotFoundException(ResourceNotFoundException ignored){

        String message = "Resource not found";

        if(ignored.getMessage() != null || ignored.getMessage().isEmpty())
            message = ignored.getMessage();

        return WrapperApiResponse.ExceptionResponse.builder()
                .status(HttpStatus.NOT_FOUND)
                .code(HttpStatus.NOT_FOUND.value())
                .message(message)
                .build();
    }


    @ExceptionHandler(HttpMessageNotReadableException.class)
    public WrapperApiResponse.ExceptionResponse handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {

        Throwable cause = e.getCause();

        Map<String, Object> errors = null;

        if(cause instanceof InvalidFormatException invalidFormatException){

            String fieldName = invalidFormatException.getPath().get(0).getFieldName();

            String invalidValue = invalidFormatException.getValue().toString();

//            String expectedType = invalidFormatException.getTargetType().getSimpleName();

            errors = new HashMap<>();

            errors.put("fieldName", fieldName);
            errors.put("invalidValue", invalidValue);
        }

        return WrapperApiResponse.ExceptionResponse.builder()
                .status(HttpStatus.BAD_REQUEST)
                .code(HttpStatus.BAD_REQUEST.value())
                .message("Invalid request")
                .errors(errors)
                .build();
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public WrapperApiResponse.ExceptionResponse handleValidationExceptions(MethodArgumentNotValidException exception){

        String message = "Ràng buộc thất bại";

        BindingResult bindingResult = exception.getBindingResult();

        List<FieldError> fieldErrors = bindingResult.getFieldErrors();

        Map<String, String> errors = null;

        if(!fieldErrors.isEmpty()) {

            int lastIndex = fieldErrors.size() - 1;

            errors = new HashMap<>();

            errors.put(fieldErrors.get(lastIndex).getField(), fieldErrors.get(lastIndex).getDefaultMessage());
        }
        return WrapperApiResponse.ExceptionResponse.builder()
                .status(HttpStatus.BAD_REQUEST)
                .code(HttpStatus.BAD_REQUEST.value())
                .message(message)
                .errors(errors)
                .build();
    }

    @ExceptionHandler({
            UsernameNotFoundException.class,
            BadCredentialsException.class
    })
    public WrapperApiResponse.ExceptionResponse handleBadCredentialsException(AuthenticationException ignored) {

        String message = "Sai tên tài khoản hoặc mât khẩu";

        return WrapperApiResponse.ExceptionResponse.builder()
                .status(HttpStatus.UNAUTHORIZED)
                .code(HttpStatus.UNAUTHORIZED.value())
                .message(message)
                .build();
    }

    @ExceptionHandler(DisabledException.class)
    public WrapperApiResponse.ExceptionResponse handleDisabledException(DisabledException ignored) {

        ErrorEnum error = ErrorEnum.ACCOUNT_DISABLED;

        return WrapperApiResponse.ExceptionResponse.builder()
                .status(error.getStatusCode())
                .code(error.getStatusCode().value())
                .message(error.getMessage())
                .build();
    }


    @ExceptionHandler(HttpClientErrorException.class)
    public WrapperApiResponse.ExceptionResponse handleHttpClientErrorException(HttpClientErrorException ignored) {

        ErrorEnum error = ErrorEnum.UPLOAD_FILE_FAILURE;

        return WrapperApiResponse.ExceptionResponse.builder()
                .status(error.getStatusCode())
                .code(error.getStatusCode().value())
                .message(error.getMessage())
                .build();
    }

    @ExceptionHandler(AuthenticationException.class)
    public WrapperApiResponse.ExceptionResponse handleAuthenticationException(AuthenticationException e) {

        return WrapperApiResponse.ExceptionResponse.builder()
                .status(HttpStatus.UNAUTHORIZED)
                .code(HttpStatus.UNAUTHORIZED.value())
                .message(e.getMessage())
                .build();
    }


    @ExceptionHandler({
            MessagingException.class,
            BadRequestException.class,
            IllegalArgumentException.class,
            FileAlreadyExistsException.class,
            UnsupportedOperationException.class
    })
    public WrapperApiResponse.ExceptionResponse handleIllegalArgumentException(Exception exception) {

        return WrapperApiResponse.ExceptionResponse.builder()
                .status(HttpStatus.BAD_REQUEST)
                .code(HttpStatus.BAD_REQUEST.value())
                .message(exception.getMessage())
                .build();
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public WrapperApiResponse.ExceptionResponse handleConflictException(Exception exception) {

        return WrapperApiResponse.ExceptionResponse.builder()
                .status(HttpStatus.CONFLICT)
                .code(HttpStatus.CONFLICT.value())
                .message(exception.getMessage())
                .build();
    }


    @ExceptionHandler({
            Exception.class,
            ServerException.class
    })
    public WrapperApiResponse.ExceptionResponse handleInternalException(Exception exception) {

        return WrapperApiResponse.ExceptionResponse.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(exception.getMessage())
                .build();
    }

    @ExceptionHandler(AccessDeniedException.class)
    public WrapperApiResponse.ExceptionResponse handleAccessDeniedException(AccessDeniedException ignored) {

        ErrorEnum error = ErrorEnum.UNAUTHORIZED;

        return WrapperApiResponse.ExceptionResponse.builder()
                .status(error.getStatusCode())
                .code(error.getStatusCode().value())
                .message(error.getMessage())
                .build();
    }

    @ExceptionHandler(RefreshTokenException.class)
    public WrapperApiResponse.ExceptionResponse handleTokenRefreshException(RefreshTokenException ex) {

        return WrapperApiResponse.ExceptionResponse.builder()
                .message(ex.getMessage())
                .status(HttpStatus.FORBIDDEN)
                .code(HttpStatus.FORBIDDEN.value())
                .build();
    }

}
