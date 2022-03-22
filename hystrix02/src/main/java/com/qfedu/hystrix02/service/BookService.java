package com.qfedu.hystrix02.service;

import com.qfedu.demo.service.Book;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

/**
 * @author 百泽
 * @公众号 Java架构栈
 */
@Service
public class BookService {
    @Autowired
    RestTemplate restTemplate;

    /**
     * 调用 storage 中的接口
     * @param ids
     * @return
     */
    public List<Book> getBooksByIds(List<Integer> ids) {
        Book[] books = restTemplate.getForObject("http://storage/books?ids={1}", Book[].class, StringUtils.join(ids, ","));
        return Arrays.asList(books);
    }
}
