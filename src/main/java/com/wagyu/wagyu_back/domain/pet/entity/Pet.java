package com.wagyu.wagyu_back.domain.pet.entity;

import com.wagyu.wagyu_back.domain.breed.entity.Breed;
import com.wagyu.wagyu_back.domain.breed.entity.BreedDisease;
import com.wagyu.wagyu_back.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "pets")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User owner;

    @Column(nullable = false, length = 20)
    private String name;

    @Column(nullable = false)
    private Short age;

    @JoinColumn(name = "breed_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Breed breed;

    @Column(nullable = false, length = 1)
    private Character gender;

    @Column(name = "created_at")
    @CreatedDate
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Column(nullable = false, name = "is_deleted")
    private Boolean isDeleted;

    @OneToMany(mappedBy = "pet")
    private List<PetDisease> petDiseases;

    public void update(String name, Short age, Breed breed, Character gender) {
        this.name = name;
        this.age = age;
        this.breed = breed;
        this.gender = gender;
    }

    public void delete() {
        this.isDeleted = true;
    }
}
