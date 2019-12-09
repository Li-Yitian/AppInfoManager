package cn.sp.appinfo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

//实现web
@Configuration
public class MyMvcLogin implements WebMvcConfigurer  {
    @Resource
    private DevLoginHandlerInterceptor loginHandlerInterceptor;
    @Resource
    private BackendLoginHandler backendLoginHandler;
    //重写视图解析器,初始时会加载
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("/index");
        registry.addViewController("/index").setViewName("/index");
        registry.addViewController("/appinfo").setViewName("/index");
    }

    /**
     * 添加拦截器
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginHandlerInterceptor)
                .addPathPatterns("/dev/flatform/**","/jsp/**")
                .excludePathPatterns("/","index");

        registry.addInterceptor(backendLoginHandler)
                .addPathPatterns("/manager/backend/**","/jsp/**")
                .excludePathPatterns("/","index");
    }
}
