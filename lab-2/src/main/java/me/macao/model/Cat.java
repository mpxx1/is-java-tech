package me.macao.model;


import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Data
@Table(name = "cats")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Cat
    implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NonNull
    @Column(nullable = false, length = 100)
    private String name;

    @NonNull
    @Column(nullable = false)
    private LocalDate birthday;

    @NonNull
    @Column(nullable = false, length = 60)
    private String breed;

    @NonNull
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CatColor color;

    @ManyToOne
    @JoinColumn(nullable = false, name = "owner_id")
    private User owner;

    @ManyToMany
    @Builder.Default
    @JoinTable(
            name = "friends", joinColumns = {
            @JoinColumn(name = "first")
    },
            inverseJoinColumns = @JoinColumn(name = "second")
    )
    private Collection<Cat> friends = new ArrayList<>();

    @ManyToMany
    @Builder.Default
    @JoinTable(
            name = "friendshipRequest",
            joinColumns = { @JoinColumn(name = "source") },
            inverseJoinColumns = @JoinColumn(name = "target")
    )
    private Collection<Cat> friendshipRequests = new ArrayList<>();
}
