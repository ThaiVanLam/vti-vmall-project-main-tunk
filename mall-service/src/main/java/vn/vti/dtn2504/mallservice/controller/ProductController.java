package vn.vti.dtn2504.mallservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.vti.dtn2504.mallservice.dto.request.CreateProductRequest;
import vn.vti.dtn2504.mallservice.dto.response.CreateProductResponse;
import vn.vti.dtn2504.mallservice.service.ProductService;

@RestController
@RequestMapping(value = "/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping(value = "/test")
    public ResponseEntity<String> createProductTest(
            @RequestBody CreateProductRequest request) {
        return ResponseEntity.ok("Hello world");
    }

    @PostMapping
    public ResponseEntity<CreateProductResponse> createProduct(@RequestBody CreateProductRequest request) {
        CreateProductResponse createProductResponse = productService.createProduct(request);
        return ResponseEntity.ok(createProductResponse);
    }
}
