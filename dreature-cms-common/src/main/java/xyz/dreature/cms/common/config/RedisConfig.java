package xyz.dreature.cms.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;

@Configuration
@ConfigurationProperties("spring.redis")
public class RedisConfig {
    private String host;
    private Integer port;
    private String password;
    private Integer timeOut;

    // 初始化Jedis的方法
    @Bean
    public Jedis initJedis() {
        Jedis jedis = new Jedis(host, port, timeOut);
        jedis.auth(password);
        return jedis;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(Integer timeOut) {
        this.timeOut = timeOut;
    }
}
