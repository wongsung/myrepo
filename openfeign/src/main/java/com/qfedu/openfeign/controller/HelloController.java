package com.qfedu.openfeign.controller;

import com.qfedu.demo.service.Book;
import com.qfedu.openfeign.feign.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author 百泽
 * @公众号 Java架构栈
 */
@RestController
public class HelloController {

    @Autowired
    BookService bookService;

    @GetMapping("/test01")
    public void test01() {
        Book b1 = bookService.getBookById(1);
        System.out.println("b1 = " + b1);
        System.out.println("bookService.addBook(b1) = " + bookService.addBook(b1));
//        System.out.println("bookService.addBook2(b1) = " + bookService.addBook2(b1));
        bookService.deleteBookById(99);
        bookService.updateBook(b1);
    }
}
