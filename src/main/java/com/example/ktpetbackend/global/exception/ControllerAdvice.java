package com.example.ktpetbackend.global.exception;

import com.example.ktpetbackend.user.entity.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MultipartException;


@RestControllerAdvice
public class ControllerAdvice {

//    400
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({BadRequestException.class})
    public ResponseEntity<ApiResponse<String>> handleBadRequestException(BadRequestException e) {
        ApiResponse<String> httpRes = new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(httpRes);
    }

//    401
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler({UnauthorizedException.class})
    public ResponseEntity<ApiResponse<String>> handleUnauthorizedException(UnauthorizedException e) {
        ApiResponse<String> httpRes = new ApiResponse<>( HttpStatus.UNAUTHORIZED.value(), e.getMessage());
        return new ResponseEntity<>(httpRes, HttpStatus.UNAUTHORIZED);
    }

//    500
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({ServerErrorException.class})
    public ResponseEntity<ApiResponse<String>> handleServerErrorException(ServerErrorException e) {
        ApiResponse<String> httpRes = new ApiResponse<>( HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        return new ResponseEntity<>(httpRes, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    //404
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<ApiResponse<String>> handleNotFoundException(NotFoundException e) {
        ApiResponse<String> httpRes = new ApiResponse<>(HttpStatus.NOT_FOUND.value(), e.getMessage());
        return new ResponseEntity<>(httpRes, HttpStatus.NOT_FOUND);
    }
  
    //    MultipartFile 잘못 전달받았을 경우
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MultipartException.class})
    public ResponseEntity<ApiResponse<String>> handleMultipartException(MultipartException e) {
        ApiResponse<String> httpRes = new ApiResponse<>( HttpStatus.BAD_REQUEST.value(), "잘못된 형식의 파일입니다.");
        return new ResponseEntity<>(httpRes, HttpStatus.BAD_REQUEST);
    }
}
