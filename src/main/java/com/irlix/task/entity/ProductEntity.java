package com.irlix.task.entity;

import com.vladmihalcea.hibernate.type.basic.PostgreSQLHStoreType;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import javax.persistence.*;
import javax.validation.constraints.Min;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

@Entity
@Table(name = "products")
@TypeDef(name = "hstore", typeClass = PostgreSQLHStoreType.class)
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "quantity")
    private Long quantity;

    @Column(name = "name")
    private String name;

    @Min(value = 1)
    @Column(name = "price")
    private Long price;

    @Type(type = "hstore")
    @Column(name = "attributes", columnDefinition = "hstore")
    private Map<String, String> attributes = new HashMap<>();

    @ManyToMany
    @JoinTable (name="categories_products",
            joinColumns=@JoinColumn (name="product_id"),
            inverseJoinColumns=@JoinColumn(name="category_id"))
    private List<CategoryEntity> categories;


    public ProductEntity(ProductEntity entity) {
        this.id = entity.id;
        this.categories = entity.categories;
        this.price = entity.price;
        this.attributes = entity.attributes;
        this.quantity = entity.quantity;
        this.name = entity.name;
    }

    public ProductEntity() {

    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
    }

    public List<CategoryEntity> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryEntity> categories) {
        this.categories = categories;
    }

    @Override
    public String toString() {
        return "ProductEntity{" +
                "id=" + id +
                ", quantity=" + quantity +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", attributes=" + attributes;
    }
}
