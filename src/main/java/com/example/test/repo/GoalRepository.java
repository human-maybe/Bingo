package com.example.test.repo;

import com.example.test.model.Goal;
import com.example.test.model.enums.Proirity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface GoalRepository extends JpaRepository<Goal, Long> {

    @Query("select g from Goal g where g.proirity = :proirity")
    List<Goal> findByProirity(@Param("proirity") Proirity proirity);

    @Query("select g from Goal g where g.proirity = :proirity and g.isActive = :isActive")
    List<Goal> findByProirityAndIsActive(@Param("proirity") Proirity proirity, @Param("isActive") Boolean isActive);

    @Query("select g from Goal g where g.isActive = :isActive and g.id not in :ids")
    List<Goal> findAnyGoalsBut(@Param("isActive") Boolean isActive, @Param("ids") Collection<Long> ids);
}