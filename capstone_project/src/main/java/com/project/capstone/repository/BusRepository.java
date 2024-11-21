package com.project.capstone.repository;

import com.project.capstone.entity.Bus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BusRepository extends JpaRepository<Bus,Long> {
    boolean existsById(Long id);
    List<Bus> findBySourceAndDestinationAndDate(String source, String destination, String date);
}
