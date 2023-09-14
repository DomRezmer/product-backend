package dev.kbe.produktbackend;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getSingleProduct(@PathVariable ObjectId id) {
        return productRepository.findById(id);
    }

    public Product createProduct(String name, String description, double price/*
                                                                               * , ObjectId id
                                                                               * Nicht sicher wegen ID,
                                                                               * möglicherweise anderen
                                                                               * indikator für
                                                                               * exestierendes Produkt
                                                                               * finden
                                                                               * ,Date availablilityDate
                                                                               */) {
        /*
         * Optional<Product> productExists = productRepository.findById(id);
         * if (productExists.isPresent()) {
         * throw new RuntimeException("Product already exists");
         * }
         */

        Product product = new Product(name, description, price /* ,availablilityDate */);
        productRepository.insert(product);

        return product;
    }
}
