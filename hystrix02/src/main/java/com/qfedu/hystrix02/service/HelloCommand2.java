package com.qfedu.hystrix02.service;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.qfedu.demo.service.Book;
import org.springframework.web.client.RestTemplate;

/**
 * @author 百泽
 * @公众号 Java架构栈
 */
public class HelloCommand2 extends HystrixCommand<String> {

    RestTemplate restTemplate;
    Integer id;

    public HelloCommand2(RestTemplate restTemplate,Integer id) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("zhangsan")));
        this.restTemplate = restTemplate;
        this.id = id;
    }

    /**
     * 发起请求的地方
     *
     * @return
     * @throws Exception
     */
    @Override
    protected String run() throws Exception {
//        int i = 1 / 0;
        Book b = restTemplate.getForObject("http://storage/book/?id={1}", Book.class, id);
        System.out.println("b = " + b);
        return "aaa";
    }

    @Override
    protected String getCacheKey() {
        return String.valueOf(id);
    }

    /**
     * 这是服务降级的方法，即 run 方法执行失败的时候，会自动触发该方法的执行
     *
     * @return
     */
    @Override
    protected String getFallback() {
        //获取执行时的异常（即 run 方法抛出的异常）
        Throwable throwable = getExecutionException();
        return "error---fallback--->"+ throwable;
    }

}
