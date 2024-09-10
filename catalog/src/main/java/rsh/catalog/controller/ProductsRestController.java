package rsh.catalog.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import rsh.catalog.controller.payload.CreateProductPayload;
import rsh.catalog.entity.Product;
import rsh.catalog.service.ProductService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/catalog-api/products")
public class ProductsRestController {

    private final ProductService productService;

    @GetMapping
    public List<Product> getAllProducts() {
        return this.productService.findAllProducts();
    }

    @PostMapping
    public ResponseEntity<?> createProduct(@Valid @RequestBody CreateProductPayload payload,
            BindingResult bindingResult, UriComponentsBuilder uriComponentsBuilder)
            throws BindException {

        if (bindingResult.hasErrors()) {
            if (bindingResult instanceof BindException exception) {
                throw exception;
            } else {
                throw new BindException(bindingResult);
            }
        } else {
            Product product = this.productService.createProduct(payload.title(), payload.details());

            return ResponseEntity
                    .created(uriComponentsBuilder
                            .replacePath("/catalog-api/products/{productId}")
                            .build(Map.of("productId", product.getId())))
                    .body(product);
        }
    }
}
