package com.laytin.kafkaProjectSelectel.repository;

import com.laytin.kafkaProjectSelectel.model.FileCounter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
public interface FileRepository extends JpaRepository<FileCounter, Integer> {
    Optional<FileCounter> findByName(String imagename);
    List<FileCounter> findByTmBeforeOrCountIsLessThan(Timestamp tm, int count);
    void deleteByTmBeforeOrCountIsLessThan(Timestamp tm, int count);
}
