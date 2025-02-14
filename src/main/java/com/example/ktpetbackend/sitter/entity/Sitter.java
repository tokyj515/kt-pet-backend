package com.example.ktpetbackend.sitter.entity;

import com.example.ktpetbackend.user.entity.User;
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
@Table(name = "`sitter`")
public class Sitter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;


    //이름, 연락처, 위치, 돌봄 가능 반려동물 종류, 돌봄 가능 시간, 요금 입력

    private String location;

    private String charge;

}
