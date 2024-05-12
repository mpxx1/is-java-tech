package me.macao.model;


import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User
    implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NonNull
    @Column(unique = true, length = 100, nullable = false)
    private String email;

    @NonNull
    @Column(length = 200, nullable = false)
    private String passwdHash;

    @NonNull
    @Column(length = 50, nullable = false)
    private String name;

    @NonNull
    @Column(nullable = false)
    private LocalDate birthday;

    @NonNull
    @Builder.Default
    @OneToMany(mappedBy = "owner")
    private Collection<Cat> catList = new ArrayList<>();
}
