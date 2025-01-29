package com.example.API_Gateway.factory;

import com.example.API_Gateway.controller.response.GeneralResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class ResponseFactory {

    public ResponseEntity success() {
        GeneralResponse<Object> responseObject = new GeneralResponse();
        responseObject.setCode("success");
        return ResponseEntity.ok(responseObject);
    }

    public ResponseEntity success(Object data, Class clazz) {
        GeneralResponse<Object> responseObject = new GeneralResponse();
        responseObject.setCode("success");
        responseObject.setData(clazz.cast(data));
        return ResponseEntity.ok(responseObject);
    }

    public ResponseEntity error(HttpStatus httpStatus, String errorCode) {
        GeneralResponse<Object> responseObject = new GeneralResponse<>();
        responseObject.setCode(errorCode);
        return new ResponseEntity(responseObject, httpStatus);
    }
}
