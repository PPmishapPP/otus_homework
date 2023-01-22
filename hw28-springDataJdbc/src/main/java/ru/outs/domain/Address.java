package ru.outs.domain;

import lombok.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;


@Table(name = "address")
@Value
public class Address {
    @Id
    Long id;
    String street;
}
