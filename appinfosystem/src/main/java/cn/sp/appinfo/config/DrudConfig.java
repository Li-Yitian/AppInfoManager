package cn.sp.appinfo.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DrudConfig {
    /*
     将自定义的 Druid数据源添加到容器中，不再让 Spring Boot 自动创建
     绑定全局配置文件中的 druid 数据源属性到 com.alibaba.druid.pool.DruidDataSource从而让它们生效
     @ConfigurationProperties(prefix = "spring.datasource")：作用就是将 全局配置文件中
     前缀为 spring.datasource的属性值注入到 com.alibaba.druid.pool.DruidDataSource 的同名参数中
   */
    @ConfigurationProperties(prefix = "spring.datasource")
    @Bean
    public DataSource drudDataSource(){
        return new DruidDataSource();
    }
    //配置 Druid 监控管理后台的Servlet；
    //内置 Servler 容器时没有web.xml文件，所以使用 Spring Boot 的注册 Servlet 方式
    @Bean
    public ServletRegistrationBean statViewServlet() {
        ServletRegistrationBean bean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
        Map<String, String> initParams = new HashMap<>();
        initParams.put("loginUsername", "admin"); //后台管理界面的登录账号
        initParams.put("loginPassword", "123456"); //后台管理界面的登录密码
        initParams.put("allow", "");
        //设置初始化参数
        bean.setInitParameters(initParams);
        return bean;
        //这些参数可以在 com.alibaba.druid.support.http.StatViewServlet 的父类 com.alibaba.druid.support.http.ResourceServlet 中找到
    }
}
