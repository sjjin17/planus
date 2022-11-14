package com.planus.db.repository;

import com.planus.db.entity.Festival;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface FestivalRepository extends JpaRepository<Festival, Long> {
    List<Festival> findAllByEndDateBefore(LocalDate targetDate);
}
