package com.ll.bookstore.product.product.service;

import com.ll.bookstore.book.book.entity.Book;
import com.ll.bookstore.product.product.entity.Product;
import com.ll.bookstore.product.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {
    private final ProductRepository productRepository;

    @Transactional
    public Product createProduct(Book book) {
        if (book.getProduct() != null) return book.getProduct();

        Product product = Product.builder()
                .maker(book.getAuthor())
                .relTypeCode(book.getModelName())
                .relId(book.getId())
                .name(book.getTitle())
                .price(book.getPrice())
                .build();
        return product;
    }

}
