package vn.vti.dtn2504.mallservice.service;

import vn.vti.dtn2504.mallservice.dto.request.CreateProductRequest;
import vn.vti.dtn2504.mallservice.dto.response.CreateProductResponse;

public interface ProductService {
    CreateProductResponse createProduct(CreateProductRequest request);
}
