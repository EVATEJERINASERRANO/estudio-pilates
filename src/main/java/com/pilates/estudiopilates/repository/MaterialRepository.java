package com.pilates.estudiopilates.repository;

import com.pilates.estudiopilates.model.Material;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MaterialRepository extends JpaRepository<Material, Long> {
}