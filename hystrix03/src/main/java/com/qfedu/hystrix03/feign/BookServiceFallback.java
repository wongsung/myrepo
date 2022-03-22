package com.qfedu.hystrix03.feign;

import com.qfedu.demo.service.Book;
import org.springframework.stereotype.Component;

/**
 * @author 百泽
 * @公众号 Java架构栈
 */
@Component
public class BookServiceFallback implements BookService {
    /**
     * 这就是服务降级的方法
     *
     * @param id
     * @return
     */
    @Override
    public Book getBookById(Integer id) {
        Book b = new Book();
        b.setId(id);
        b.setName("服务降级了");
        return b;
    }
}
