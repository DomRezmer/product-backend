package dev.kbe.produktbackend;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "products")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    private ObjectId id;
    private String name;
    private String description;
    private double price;
    // private Date availablilityDate;

    public Product(String name, String description, double price/* , Date availablilityDate */) {
        this.name = name;
        this.description = description;
        this.price = price;
        // this.availablilityDate = availablilityDate;
    }
}
