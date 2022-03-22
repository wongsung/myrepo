package com.qfedu.service.api;

import com.qfedu.demo.service.Book;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author 百泽
 * @公众号 Java架构栈
 */
//@Controller
public interface IBookController {

    @GetMapping("/book/")
    @ResponseBody
    Book getBookById(@RequestParam("id") Integer id);

    @DeleteMapping("/book/{id}")
    @ResponseBody
    void deleteBookById(@PathVariable("id") Integer id);

    @PostMapping("/book/")
    @ResponseBody
    Book addBook(@RequestBody Book book);

    @PutMapping("/book/")
    @ResponseBody
    void updateBook(@RequestBody Book book);

}
