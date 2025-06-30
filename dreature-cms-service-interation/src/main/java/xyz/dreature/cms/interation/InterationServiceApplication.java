package xyz.dreature.cms.interation;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableEurekaClient
@MapperScan("xyz.dreature.cms.interation.mapper")
public class InterationServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(InterationServiceApplication.class, args);
    }

    //内部服务调用的RestTemplate对象
    @Bean
    @LoadBalanced
    public RestTemplate initCollectRestTemplate() {
        return new RestTemplate();
    }
}
