package com.qfedu.hystrix02.service;

import com.netflix.hystrix.HystrixCollapser;
import com.netflix.hystrix.HystrixCollapserKey;
import com.netflix.hystrix.HystrixCollapserProperties;
import com.netflix.hystrix.HystrixCommand;
import com.qfedu.demo.service.Book;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 百泽
 * @公众号 Java架构栈
 */
public class BookCollapseCommand extends HystrixCollapser<List<Book>,Book,Integer> {

    private Integer id;
    private BookService bookService;

    public BookCollapseCommand(Integer id,BookService bookService) {
        //200 表示 200 毫秒
        super(HystrixCollapser.Setter.withCollapserKey(HystrixCollapserKey.Factory.asKey("UserCollapseCommand")).andCollapserPropertiesDefaults(HystrixCollapserProperties.Setter().withTimerDelayInMilliseconds(200)));
        this.id = id;
        this.bookService = bookService;
    }

    /**
     * 获取请求参数
     * @return
     */
    @Override
    public Integer getRequestArgument() {
        return id;
    }

    /**
     * 合并请求
     * @param collection 框架已经收集好的请求
     * @return
     */
    @Override
    protected HystrixCommand<List<Book>> createCommand(Collection<CollapsedRequest<Book, Integer>> collection) {
//        for (CollapsedRequest<Book, Integer> request : collection) {
//            //获取每个请求的参数，即 id
//            Integer argument = request.getArgument();
//        }
        List<Integer> ids = collection.stream().map(r -> r.getArgument()).collect(Collectors.toList());
        return new BookBatchCommand(ids, bookService);
    }

    /**
     * 分发请求结果
     * @param bookList 这个是调用的结果
     * @param collection 合并起来的请求在这个集合中
     */
    @Override
    protected void mapResponseToRequests(List<Book> bookList, Collection<CollapsedRequest<Book, Integer>> collection) {
        int index = 0;
        for (CollapsedRequest<Book, Integer> request : collection) {
            request.setResponse(bookList.get(index++));
        }
    }
}
