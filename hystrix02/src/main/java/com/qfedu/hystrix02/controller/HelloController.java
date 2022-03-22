package com.qfedu.hystrix02.controller;

import com.netflix.hystrix.HystrixCollapser;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import com.qfedu.demo.service.Book;
import com.qfedu.hystrix02.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @author 百泽
 * @公众号 Java架构栈
 */
@RestController
public class HelloController {

    @Autowired
    HelloService helloService;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    BookService2 bookService2;
    @Autowired
    BookService bookService;

    @GetMapping("/test02")
    public void test02() throws ExecutionException, InterruptedException {
        HystrixRequestContext ctx = HystrixRequestContext.initializeContext();
        BookCollapseCommand command0 = new BookCollapseCommand(100, bookService);
        BookCollapseCommand command1 = new BookCollapseCommand(101, bookService);
        BookCollapseCommand command2 = new BookCollapseCommand(102, bookService);
        Future<Book> f0 = command0.queue();
        Future<Book> f1 = command1.queue();
        Future<Book> f2 = command2.queue();
        Book b0 = f0.get();
        Book b1 = f1.get();
        Book b2 = f2.get();
        System.out.println("b0="+b0);
        System.out.println("b1="+b1);
        System.out.println("b2="+b2);
        Thread.sleep(1000);
        BookCollapseCommand command3 = new BookCollapseCommand(103, bookService);
        Future<Book> f3 = command3.queue();
        Book b3 = f3.get();
        System.out.println("b3="+b3);
        ctx.close();
    }

    @GetMapping("/test01")
    public void test01() {
        //缓存有一个范围
        HystrixRequestContext ctx = HystrixRequestContext.initializeContext();
        //初始化上下文之后，缓存就开始了
        Book b1 = bookService2.getBookById(99,"");
        Book b2 = bookService2.getBookById(99,"");
        //到上下文关闭，缓存结束
        ctx.close();
        //虽然 b3 的 id 也是 99，但是由于上下文已经关闭，之前的缓存已经失效，所以这里还是会发生网络请求
        //凡是带有 @CacheResult 注解的方法，都应该在 ctx 未关闭之前调用
//        Book b3 = bookService.getBookById(99);
        System.out.println("b1 = " + b1);
        System.out.println("b2 = " + b2);
//        System.out.println("b3 = " + b3);
    }

    @GetMapping("/hello")
    public String hello() {
//        return helloService.hello();
        //一个 helloCommand 对象只能执行一次
        HelloCommand helloCommand = new HelloCommand(restTemplate);
        //同步执行请求方法，这个会发生阻塞
        String execute = helloCommand.execute();
        //异步调用，不会阻塞
//        Future<String> future = helloCommand.queue();
        return execute;
    }


}
