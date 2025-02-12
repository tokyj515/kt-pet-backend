package com.example.ktpetbackend.user.entity;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import jakarta.persistence.*;

@DynamicInsert
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "`user`")
//@Where(clause = "delete_yn = 0")
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

    private String name;

    @Column(name = "user_role")
    private String userRole;

}
