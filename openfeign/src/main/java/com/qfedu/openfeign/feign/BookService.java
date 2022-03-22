package com.qfedu.openfeign.feign;

import com.qfedu.service.api.IBookController;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author 百泽
 * @公众号 Java架构栈
 */

@FeignClient("storage")
public interface BookService extends IBookController {
}
