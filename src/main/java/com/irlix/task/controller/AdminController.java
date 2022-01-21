package com.irlix.task.controller;

import com.irlix.task.dto.*;
import com.irlix.task.entity.CategoryEntity;
import com.irlix.task.entity.ProductEntity;
import com.irlix.task.entity.TransactionEntity;
import com.irlix.task.exception.InvalidInputException;
import com.irlix.task.mapper.CategoryMapper;
import com.irlix.task.mapper.ProductMapper;
import com.irlix.task.mapper.TransactionMapper;
import com.irlix.task.service.CategoryService;
import com.irlix.task.service.ProductService;
import com.irlix.task.service.TransactionService;
import com.irlix.task.service.formatter.MyDateFormatter;
import javassist.NotFoundException;
import org.mapstruct.factory.Mappers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin")
public class AdminController {


    private final Logger logger = LoggerFactory.getLogger(AdminController.class);
    private final CategoryMapper categoryMapper = Mappers.getMapper(CategoryMapper.class);
    private final ProductMapper productMapper = Mappers.getMapper(ProductMapper.class);
    private final TransactionMapper transactionMapper = Mappers.getMapper(TransactionMapper.class);
    private final CategoryService categoryService;
    private final ProductService productService;
    private final TransactionService transactionService;

    public AdminController(CategoryService categoryService,
                           ProductService productService,
                           TransactionService transactionService) {
        this.categoryService = categoryService;
        this.productService = productService;
        this.transactionService = transactionService;
    }

    @PostMapping("/categories")
    public ResponseEntity<CategoryResponseDto> createCategory(@RequestBody CategoryRequestDto requestDto){

       CategoryEntity category = categoryMapper.categoryRequestDtoToCategoryEntity(requestDto);
       categoryService.save(category);
       CategoryResponseDto responseDto = categoryMapper.categoryEntityToCategoryResponseDto(category);

       logger.info(String.format("Successfully saved a %s category!", category.getName()));

    return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @PutMapping("/categories")
    public ResponseEntity<CategoryResponseDto> updateCategory(@RequestBody CategoryRequestDto requestDto){

        CategoryEntity category = categoryMapper.categoryRequestDtoToCategoryEntity(requestDto);
        categoryService.save(category);
        CategoryResponseDto responseDto = categoryMapper.categoryEntityToCategoryResponseDto(category);

        logger.info(String.format("Successfully updated a %s category!", category.getName()));

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @DeleteMapping("/products/{name}")
    public ResponseEntity<String> deleteProductByName(@PathVariable(name = "name") String name) {
        productService.deleteByName(name);
        return new ResponseEntity<>(String.format(
                "The product with the name: %s has been deleted successfully!", name), HttpStatus.OK);
    }

    @DeleteMapping("/categories/{name}")
    public ResponseEntity<String> deleteCategoryByName(@PathVariable(name = "name") String name) {
        categoryService.deleteByName(name);
        return new ResponseEntity<>(String.format(
                "The category with the name: %s has been deleted successfully!", name), HttpStatus.OK);
    }

    @PostMapping("/products")
    public ResponseEntity<ProductResponseDto> createProduct(@RequestBody ProductRequestDto requestDto)
            throws InvalidInputException, NotFoundException {

        productService.setUpProductRequestDto(requestDto);
        ProductEntity product = productMapper.productRequestDtoToProductEntity(requestDto);
        productService.save(product);
        ProductResponseDto responseDto = productMapper.productEntityToProductResponseDto(product);

        logger.info(String.format("Successfully saved a %s product!", product.getName()));

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @PutMapping("/products")
    public ResponseEntity<ProductResponseDto> updateProduct(@RequestBody ProductRequestDto requestDto)
            throws InvalidInputException, NotFoundException {

        productService.setUpProductRequestDto(requestDto);
        ProductEntity product = productMapper.productRequestDtoToProductEntity(requestDto);
        productService.save(product);
        ProductResponseDto responseDto = productMapper.productEntityToProductResponseDto(product);

        logger.info(String.format("Successfully saved a %s product!", product.getName()));

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @GetMapping("/products/statistics")
    public ResponseEntity<List<TransactionResponseDto>> getStatistics(@RequestParam(name = "start") String start,
                                                                      @RequestParam(name = "end") String end) throws ParseException {
        List<TransactionEntity> transactions = transactionService.getAll();
        Date startDate = MyDateFormatter.formatterWithoutTime.parse(start);
        Date endDate = MyDateFormatter.formatterWithoutTime.parse(end);

         List<TransactionEntity> result = transactions.stream().filter(transaction -> {
            try {
                long transactionTime = MyDateFormatter.formatterWithTime.parse(transaction.getTime()).getTime();
                return  transactionTime < endDate.getTime() && transactionTime > startDate.getTime() ;
            } catch (ParseException e) {
                e.printStackTrace();
            }
             return false;
         }).collect(Collectors.toList());


         return new ResponseEntity<>(result.stream().map(transactionMapper::transactionEntityToTransactionResponseDto)
                                             .collect(Collectors.toList()), HttpStatus.OK);
    }


}
