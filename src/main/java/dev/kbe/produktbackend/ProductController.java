package dev.kbe.produktbackend;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dev.kbe.produktbackend.config.RabbitMQConfig;
import dev.kbe.produktbackend.config.RabbitMQSender;

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
@RequestMapping("/api/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Autowired
    RabbitMQSender rabbitMQSender;

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

    @PostMapping("/queue")
    public ResponseEntity<String> createProductQueue(@RequestBody Product product) {
        RabbitMQConfig rabbitMQConfig = new RabbitMQConfig();
        amqpTemplate.convertAndSend(rabbitMQConfig.getQueue(), product);
        return ResponseEntity.ok("Product sent to queue: " + product.getName());
    }

    @GetMapping("/shoppingcart")
    public String sendProduct(@RequestParam("productName") String productName,
            @RequestParam("productDescription") String productDescription, @RequestParam("price") double price /*
                                                                                                                * Product
                                                                                                                * product
                                                                                                                */) {
        Product product = new Product(productName, productDescription, price);
        rabbitMQSender.send(product);
        return "Product sent to queue: " + product.getName();
    }
}

// TODO updateProduct, deleteProduct, addProducttoCart(RabbitMQ? an
// CartBackend???)
