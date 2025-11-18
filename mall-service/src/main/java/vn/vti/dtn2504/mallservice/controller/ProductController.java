package vn.vti.dtn2504.mallservice.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.vti.dtn2504.mallservice.dto.request.CreateProductRequest;

@RestController
@RequestMapping(value = "/api/v1/products")
public class ProductController {


    @PostMapping
    public ResponseEntity<String> createProduct(
            @RequestBody CreateProductRequest request) {
        return ResponseEntity.ok("Hello world");
    }
}
