package com.voronov.market.repository;

import com.voronov.market.model.UserEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

/**
 * @author Alexey Voronov on 19.07.2023
 */
public interface UserRepository extends R2dbcRepository<UserEntity, Long> {
    Mono<UserEntity> findByUsername(String username);
}
