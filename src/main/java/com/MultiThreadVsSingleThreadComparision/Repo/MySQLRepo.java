package com.MultiThreadVsSingleThreadComparision.Repo;

import com.MultiThreadVsSingleThreadComparision.Entities.NewEnitity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface MySQLRepo extends JpaRepository<NewEnitity,Long> {
    @Query(value = "CALL GetEntitiesByIdRange(:startId, :endId)", nativeQuery = true)
    @Transactional(readOnly = true)
    List<NewEnitity> getEntitiesByIdRange(@Param("startId") Long startId, @Param("endId") Long endId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE new_enitity SET name = :newStatus WHERE id BETWEEN :startId AND :endId", nativeQuery = true)
    void updateStatusByIdRange(@Param("startId") Long startId,
                               @Param("endId") Long endId,
                               @Param("newStatus") String newStatus);



    long count();

}

