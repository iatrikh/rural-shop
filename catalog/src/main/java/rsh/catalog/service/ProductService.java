package rsh.catalog.service;

import java.util.List;
import java.util.Optional;

import rsh.catalog.entity.Product;

public interface ProductService {

    List<Product> findAllProducts();

    Product createProduct(String title, String deatails);

    Optional<Product> findProduct(int productId);

    void updateProduct(Integer id, String title, String details);

    void deleteProduct(Integer id);
}
