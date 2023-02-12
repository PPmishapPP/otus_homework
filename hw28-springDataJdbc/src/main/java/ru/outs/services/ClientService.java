package ru.outs.services;

import ru.outs.domain.Client;
import ru.outs.dto.ClientDTO;

public interface ClientService {
    Iterable<Client> findAll();

    Client saveClient(ClientDTO clientDTO);
}
