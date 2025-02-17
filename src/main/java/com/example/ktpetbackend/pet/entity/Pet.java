package com.example.ktpetbackend.pet.entity;

import com.example.ktpetbackend.user.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

@DynamicInsert
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "`pet`")
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

//    @Enumerated(EnumType.STRING)
//    private PetType type;
    private String petType;

    private Long age;

    private String url;

    @JsonIgnore // ✅ Hibernate 프록시 문제 해결
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private Integer deleted;

}


