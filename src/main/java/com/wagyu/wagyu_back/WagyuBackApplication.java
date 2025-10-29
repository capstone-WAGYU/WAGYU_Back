package com.wagyu.wagyu_back;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class WagyuBackApplication {

    public static void main(String[] args) {
        SpringApplication.run(WagyuBackApplication.class, args);
    }

}
