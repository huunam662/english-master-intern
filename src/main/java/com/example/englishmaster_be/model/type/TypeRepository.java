package com.example.englishmaster_be.model.type;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;


public interface TypeRepository extends JpaRepository<TypeEntity, UUID> {
}
