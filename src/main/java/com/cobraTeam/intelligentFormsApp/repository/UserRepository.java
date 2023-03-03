package com.cobraTeam.intelligentFormsApp.repository;

import com.azure.spring.data.cosmos.repository.ReactiveCosmosRepository;
import com.cobraTeam.intelligentFormsApp.User;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface UserRepository extends ReactiveCosmosRepository<User, String> {
    Flux<User> findByFirstName(String firstName);

}
