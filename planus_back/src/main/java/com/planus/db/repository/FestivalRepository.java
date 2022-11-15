package com.planus.db.repository;

import com.planus.db.entity.Festival;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface FestivalRepository extends JpaRepository<Festival, Long> {
    Festival save(Festival festival);
    Page<Festival> findAllByEndDateAfterAndStartDateBefore(LocalDate today, LocalDate monthLater, Pageable pageable);
    void deleteAllInBatch();
}
