package vn.vti.dtn2504.mallservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.vti.dtn2504.mallservice.dto.request.CreateProductRequest;
import vn.vti.dtn2504.mallservice.dto.response.CreateProductResponse;
import vn.vti.dtn2504.mallservice.model.Product;
import vn.vti.dtn2504.mallservice.repository.ProductRepository;
import vn.vti.dtn2504.mallservice.service.ProductService;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    @Override
    public CreateProductResponse createProduct(CreateProductRequest request) {
        Product product = modelMapper.map(request, Product.class);
        product.setImage("default.png");
        product.setQuantity(100);
        product.setPrice(1000);
        Product savedProduct = productRepository.save(product);
        CreateProductResponse createProductResponse = modelMapper.map(savedProduct, CreateProductResponse.class);
        return createProductResponse;
    }
}
