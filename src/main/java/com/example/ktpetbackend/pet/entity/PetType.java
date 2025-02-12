package com.example.ktpetbackend.pet.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PetType {
    CAT("cat"),
    DOG("dog"),


    ;


    private final String value;

    @JsonCreator
    public static PetType from(String s) {
        return PetType.valueOf(s.toUpperCase());
    }
}