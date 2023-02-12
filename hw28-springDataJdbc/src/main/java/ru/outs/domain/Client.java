package ru.outs.domain;


import lombok.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;
import ru.outs.dto.ClientDTO;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Table("client")
@Value
public class Client {

    @Id
    Long id;
    String name;
    @MappedCollection(idColumn = "client_id")
    Address address;
    @MappedCollection(idColumn = "client_id")
    Set<Phone> phones;


    public Client(ClientDTO clientDTO) {
        id = clientDTO.id();
        this.name = clientDTO.name();
        this.address = new Address(null, clientDTO.address());
        this.phones = Arrays.stream(clientDTO.phones().split("\r\n"))
                .map(p -> new Phone(null, p))
                .collect(Collectors.toSet());
    }

    @PersistenceCreator
    public Client(Long id, String name, Address address, Set<Phone> phones) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phones = phones;
    }
}
