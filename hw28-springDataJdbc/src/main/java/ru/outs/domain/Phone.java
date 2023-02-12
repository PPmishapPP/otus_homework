package ru.outs.domain;

import lombok.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;


@Table(name = "phone")
@Value
public class Phone {
    @Id
    Long id;
    String number;
}
