package com.example.democrudjwt.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Profiles")
@Table(name = "profiles")
public class Profiles implements Serializable, Persistable<Long> {

    @SequenceGenerator(
            name = "profiles_id_sequence",
            sequenceName = "profiles_id_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "profiles_id_sequence"
    )
    @Column(
            name = "id",
            updatable = false
    )
    private Long id;

    @Column(
            name = "cash",
            nullable = false
    )
    private BigDecimal cash;

    @OneToOne(
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    @JoinColumn(
            name = "users_email",
            nullable = false,
            referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name = "profiles_users_id_fk"
            )
    )
    private Users usersEmail;

    @JsonIgnore
    @Override
    public boolean isNew() {
        return id == null;
    }
}
