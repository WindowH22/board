package com.fastcampus.board.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

@Configuration
@EnableJpaAuditing
public class JpaConfig {
    @Bean // 생성자,수정자를 찾는 방법
    public AuditorAware<String> auditorAware(){
        return () -> Optional.of("chang"); // TODO: 스프링 시큐리티로 인증 기능을 붙이게 될 떄 , 수정하자
    }
}
