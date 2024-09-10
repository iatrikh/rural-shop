package rsh.catalog.repository;

import java.util.List;
import java.util.Optional;

import rsh.catalog.entity.Product;

public interface ProductRepository {

    List<Product> findAll();

    Product save(Product product);

    Optional<Product> findById(Integer productId);

    void deleteById(Integer id);
}
