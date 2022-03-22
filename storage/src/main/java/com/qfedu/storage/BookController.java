package com.qfedu.storage;

import com.qfedu.demo.service.Book;
import com.qfedu.service.api.IBookController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 百泽
 * @公众号 Java架构栈
 *
 * Hystrix Resilience4j Sentinel
 *
 */
@Controller
public class BookController implements IBookController {

    /**
     * ids 是调用方法传来的参数，这个参数格式是自定义的
     *
     * 1,2,3,4,5
     * @param ids
     * @return
     */
    @GetMapping("/books")
    @ResponseBody
    public List<Book> getBooksByIds(String ids) {
        //调用方传来的id
        List<Integer> list = Arrays.stream(ids.split(",")).map(id -> Integer.parseInt(id)).collect(Collectors.toList());
        List<Book> books = new ArrayList<>();
        for (Integer id : list) {
            Book b = new Book();
            b.setId(id);
            books.add(b);
        }
        System.out.println("ids = " + ids);
        return books;
    }

    @Override
    public Book getBookById(Integer id) {
        Book book = new Book();
        book.setId(id);
        System.out.println("getBookById:" + id);
        return book;
    }

    @Override
    public void deleteBookById(Integer id) {
        System.out.println("id = " + id);
    }

    @Override
    public Book addBook(Book book) {
        return book;
    }

    @PostMapping("/add")
    public String addBook2(@RequestBody Book book) {
        System.out.println("addBook2 = " + book);
        return "redirect:/index";
    }

    @Override
    public void updateBook(Book book) {
        System.out.println("book = " + book);
    }

}
