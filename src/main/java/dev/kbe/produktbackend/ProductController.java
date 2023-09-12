package dev.kbe.produktbackend;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
@RequestMapping("/api/account")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Product>> getProductById(@PathVariable ObjectId id) {
        return ResponseEntity.ok(productService.getSingleProduct(id));
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Map<String, String> payload) {
        String name = payload.get("name");
        String description = payload.get("description");
        double price = Double.parseDouble(payload.get("price"));
        return new ResponseEntity<Product>(productService.createProduct(name, description, price), HttpStatus.CREATED);
    }

    // TODO updateProduct, deleteProduct, addProducttoCart(RabbitMQ? an
    // CartBackend???)
}
