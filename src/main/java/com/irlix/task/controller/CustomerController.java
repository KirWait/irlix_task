package com.irlix.task.controller;

import com.irlix.task.dto.ProductResponseDto;
import com.irlix.task.entity.CategoryEntity;
import com.irlix.task.entity.ProductEntity;
import com.irlix.task.entity.TransactionEntity;
import com.irlix.task.entity.UserEntity;
import com.irlix.task.mapper.ProductMapper;
import com.irlix.task.service.CategoryService;
import com.irlix.task.service.ProductService;
import com.irlix.task.service.TransactionService;
import com.irlix.task.service.UserService;
import com.irlix.task.specification.ProductSpecificationBuilder;
import javassist.NotFoundException;
import org.mapstruct.factory.Mappers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import com.irlix.task.service.formatter.*;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    private final Logger logger = LoggerFactory.getLogger(CustomerController.class);
    private final ProductMapper productMapper = Mappers.getMapper(ProductMapper.class);
    private final ProductService productService;
    private final CategoryService categoryService;
    private final TransactionService transactionService;
    private final UserService userService;

    public CustomerController(ProductService productService,
                              CategoryService categoryService,
                              TransactionService transactionService,
                              UserService userService) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.transactionService = transactionService;
        this.userService = userService;
    }

    @GetMapping("/attributes_search")
    public ResponseEntity<List<ProductResponseDto>> attributesSearch(@RequestBody HashMap<String, String> filterMap) {

        List<ProductEntity> result = productService.findAllByFilter(filterMap);

        return new ResponseEntity<>(result.stream().map(productMapper::productEntityToProductResponseDto)
                                            .collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping("/spec_search")
    public ResponseEntity<List<ProductResponseDto>> filterSearch2(@RequestParam(value = "search") String search) {

        ProductSpecificationBuilder builder = new ProductSpecificationBuilder();
        Pattern pattern = Pattern.compile("(\\w+?)([:<>])(\\w+?),");
        Matcher matcher = pattern.matcher(search + ",");
        while (matcher.find()) {
            builder.with(matcher.group(1), matcher.group(2), matcher.group(3));
        }

        Specification<ProductEntity> spec = builder.build();

        List<ProductEntity> result = productService.getAllBySpec(spec);

        List<ProductResponseDto> resultResponseDto = result.stream()
                .map(productMapper::productEntityToProductResponseDto).collect(Collectors.toList());


        return new ResponseEntity<>(resultResponseDto, HttpStatus.OK);
    }

    @GetMapping("/category_products/{category_name}")
    public ResponseEntity<List<ProductResponseDto>> getAllCategoryProducts(@PathVariable(name = "category_name") String categoryName) {
        List<ProductResponseDto> result = productService.getAllByCategoryName(categoryName).stream()
                .map(productMapper::productEntityToProductResponseDto).collect(Collectors.toList());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductEntity>> getAllProducts() {
        return new ResponseEntity<>(productService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/products/{name}")
    public ResponseEntity<ProductEntity> findProductByName(@PathVariable(name = "name") String name)
            throws NotFoundException {
        return new ResponseEntity<>(productService.findByName(name), HttpStatus.OK);
    }

    @GetMapping("/categories")
    public ResponseEntity<List<CategoryEntity>> getAllCategories() {
        return new ResponseEntity<>(categoryService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/categories/{name}")
    public ResponseEntity<CategoryEntity> findCategoryById(@PathVariable(name = "name") String name)
            throws NotFoundException {
        return new ResponseEntity<>(categoryService.findByName(name), HttpStatus.OK);
    }

    @PostMapping("/balance/{amount}")
    public ResponseEntity<String> deposit(@PathVariable(name = "amount") Long amount) throws NotFoundException {
        String currentSessionUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity user = userService.findByUsername(currentSessionUsername);
        user.setBalance(user.getBalance() + amount);
        userService.update(user);
        logger.info(String.format("Successfully deposit %d amount to %s account, the balance now is %d",
                                  amount, currentSessionUsername, user.getBalance()));
        return new ResponseEntity<>(String.format("Successfully deposit %d amount to %s account, the balance now is %d",
                                                  amount, currentSessionUsername, user.getBalance()), HttpStatus.OK);
    }

    @PostMapping("/buy/{product_name}/{quantity}")
    public ResponseEntity<String> buyProduct(@PathVariable(name = "product_name") String productName,
                                             @PathVariable(name = "quantity") Long quantity) throws NotFoundException {
        ProductEntity product = productService.findByName(productName);
        if (product.getQuantity() < quantity) {
            throw new IllegalArgumentException(
                    String.format("Out of stock! Only %d of %s left!", product.getQuantity(), product.getName()));
        }
        Long totalPrice = product.getPrice() * quantity;
        UserEntity user = userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        if (user.getBalance() < totalPrice) {
            throw new IllegalArgumentException(
                    String.format("Not enough funds! The balance is %d, while total price is %d!",
                                  user.getBalance(), totalPrice));
        }

        product.setQuantity(product.getQuantity() - quantity);

        user.setBalance(user.getBalance() - totalPrice);

        TransactionEntity transaction = new TransactionEntity();
        transaction.setQuantity(quantity);
        transaction.setProductName(productName);
        transaction.setTime(MyDateFormatter.formatterWithTime.format(Calendar.getInstance().getTime()));
        transaction.setTotalPrice(totalPrice);
        transaction.setCustomerId(user.getId());
        transactionService.save(transaction);

        logger.info(String.format("%s have successfully bought %d of %s! Your balance is now %d!",
                                  user.getUsername(), quantity, product.getName(), user.getBalance()));
        return new ResponseEntity<>(String.format("%s have successfully bought %d of %s! Your balance is now %d!",
                                                  user.getUsername(), quantity, product.getName(), user.getBalance()),
                                    HttpStatus.OK);

    }
}
