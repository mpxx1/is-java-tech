package me.macao.lab4.percistence;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "cats")
public class Cat {

    @Id
    @SequenceGenerator(
            name = "cat_sequence",
            sequenceName = "cat_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "cat_sequence"
    )
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false)
    private LocalDate birthday;

    @Column(nullable = false, length = 60)
    private String breed;

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
    private Collection<Cat> reqsOut = new ArrayList<>();
}
