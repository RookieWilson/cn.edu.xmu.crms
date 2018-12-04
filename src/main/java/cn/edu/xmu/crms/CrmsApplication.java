package cn.edu.xmu.crms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author SongLingbing
 * @date 2018/11/29 15:03
 */
@SpringBootApplication
@EnableTransactionManagement
@EnableCaching
@EnableRedisHttpSession
public class CrmsApplication extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(CrmsApplication.class, args);
    }
    @Override
    protected SpringApplicationBuilder configure(
            SpringApplicationBuilder builder) {
        return builder.sources(this.getClass());
    }
}
