package ru.otus.crm.model;

import lombok.*;

import jakarta.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "phone")
@With
public class Phone {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;

    @Column(name = "number")
    private String number;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    public Phone(Long id, String number) {
        this.id = id;
        this.number = number;
    }

    public Phone(Phone phone) {
        this.id = phone.getId();
        this.number = phone.getNumber();
        this.client = phone.getClient();
    }
}
