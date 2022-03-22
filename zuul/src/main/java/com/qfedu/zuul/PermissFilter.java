package com.qfedu.zuul;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 百泽
 * @公众号 Java架构栈
 */

/**
 * 自定义过滤器，可以在这个过滤器中预处理到达的请求
 */
@Component
public class PermissFilter extends ZuulFilter {
    /**
     * 过滤器的类型
     * <p>
     * pre 表示在请求转发之前，先对请求进行预处理
     *
     * @return
     */
    @Override
    public String filterType() {
        return "pre";
    }

    /**
     * 优先级
     *
     * @return
     */
    @Override
    public int filterOrder() {
        return 0;
    }

    /**
     * 是否要拦截请求
     *
     * @return
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * 过滤器的处理逻辑
     *
     * @return 这个可以不返回，没有任何实质性作用
     * @throws ZuulException
     */
    @Override
    public Object run() throws ZuulException {
        //获取到当前请求
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        String token = request.getParameter("token");
        if ("123".equals(token)) {
            //令牌合法，请求继续
            //不用做任何事情
            return null;
        } else {
            //令牌不合法，请求被拦截
            //这里就不再进行请求转发，直接从这里给浏览器返回数据即可
            RequestContext ctx = RequestContext.getCurrentContext();
            ctx.setResponseBody("forbidden：非法访问");
            //请求不再转发
            ctx.setSendZuulResponse(false);
            //防止中文乱码
            ctx.addZuulResponseHeader("content-type","text/html;charset=utf-8");
        }
        return null;
    }
}
