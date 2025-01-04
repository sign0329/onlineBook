package com.ll.bookstore.domain.book.book.repository;

import com.ll.bookstore.domain.book.book.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>{
}
