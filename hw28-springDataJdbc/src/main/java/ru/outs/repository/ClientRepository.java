package ru.outs.repository;

import org.springframework.data.repository.CrudRepository;
import ru.outs.domain.Client;

public interface ClientRepository extends CrudRepository<Client, Long> {

}
