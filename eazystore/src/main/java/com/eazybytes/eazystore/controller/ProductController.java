package com.eazybytes.eazystore.controller;

import com.eazybytes.eazystore.dto.ErrorResponseDto;
import com.eazybytes.eazystore.dto.ProductDto;
import com.eazybytes.eazystore.service.IProductService;
import lombok.RequiredArgsConstructor;
//import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import java.time.LocalDateTime;

import java.util.List;


@RestController
@RequestMapping("api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final IProductService iProductService;
    @GetMapping
    public ResponseEntity<List<ProductDto>> getProducts() throws InterruptedException { // DTO Pattern
//        Thread.sleep(3000);
        List<ProductDto> productList = iProductService.getProducts();
//        System.out.println("Testing code changes 1");
        return ResponseEntity.ok().body(productList);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleGlobalException(Exception exception,
                                                                  WebRequest webRequest) {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(
                webRequest.getDescription(false), HttpStatus.SERVICE_UNAVAILABLE,
                exception.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(errorResponseDto, HttpStatus.SERVICE_UNAVAILABLE);
    }
}
