package xyz.dreature.cms.comment;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableEurekaClient
@MapperScan("xyz.dreature.cms.comment.mapper")
public class CommentServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(CommentServiceApplication.class, args);
    }

    // 为ribbon客户端准备的内部调用其他服务的对象
    @Bean
    @LoadBalanced
    public RestTemplate initRestTemplateComment() {
        return new RestTemplate();
    }
}
