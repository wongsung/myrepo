package com.qfedu.hystrix03.feign;

import com.qfedu.demo.service.Book;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author 百泽
 * @公众号 Java架构栈
 */

/**
 * fallback = BookServiceFallback.class 表示处理服务降级的类
 */
@FeignClient(value = "storage",fallback = BookServiceFallback.class)
public interface BookService {

    @GetMapping("/book/")
    Book getBookById(@RequestParam("id") Integer id);
}
