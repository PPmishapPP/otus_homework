package ru.outs.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.outs.domain.Client;
import ru.outs.dto.ClientDTO;
import ru.outs.repository.ClientRepository;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    @Override
    public Iterable<Client> findAll() {
        return clientRepository.findAll();
    }

    @Override
    public Client saveClient(ClientDTO clientDTO) {
        return clientRepository.save(new Client(clientDTO));
    }
}
