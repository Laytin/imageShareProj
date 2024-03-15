package com.laytin.kafkaProjectSelectel.repository;

import com.laytin.kafkaProjectSelectel.model.ImageCounter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ImageRepository extends JpaRepository<ImageCounter, Integer> {
    Optional<ImageCounter> findByName(String imagename);
}
