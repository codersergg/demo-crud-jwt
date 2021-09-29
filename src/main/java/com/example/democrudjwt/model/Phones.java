package com.example.democrudjwt.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Phones")
@Table(
        name = "phones",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "phones_values_unique",
                        columnNames = "values")
        }
)
public class Phones implements Serializable, Persistable<Long> {

    @SequenceGenerator(
            name = "phones_id_sequence",
            sequenceName = "phones_id_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "phones_id_sequence"
    )
    @Column(
            name = "id",
            updatable = false
    )
    private Long id;

    @Column(
            name = "values",
            nullable = false
    )
    private String values;

    @ManyToOne
    @JoinColumn(
            name = "users_email",
            nullable = false,
            referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name = "phones_users_id_fk"
            )
    )
    private User usersEmail;

    @JsonIgnore
    @Override
    public boolean isNew() {
        return id == null;
    }
}
