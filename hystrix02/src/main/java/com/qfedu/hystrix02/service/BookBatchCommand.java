package com.qfedu.hystrix02.service;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.qfedu.demo.service.Book;

import java.util.List;

/**
 * @author 百泽
 * @公众号 Java架构栈
 */
public class BookBatchCommand extends HystrixCommand<List<Book>> {

    private List<Integer> ids;
    private BookService bookService;

    public BookBatchCommand(List<Integer> ids, BookService bookService) {
        super(HystrixCommand.Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("batchCmd")).andCommandKey(HystrixCommandKey.Factory.asKey("batchKey")));
        this.ids = ids;
        this.bookService = bookService;
    }

    @Override
    protected List<Book> run() throws Exception {
        return bookService.getBooksByIds(ids);
    }

    @Override
    protected List<Book> getFallback() {
        return null;
    }
}
