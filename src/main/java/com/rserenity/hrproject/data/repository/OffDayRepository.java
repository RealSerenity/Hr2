package com.rserenity.hrproject.data.repository;

import com.rserenity.hrproject.data.entity.OffDayEntity;
import com.rserenity.hrproject.data.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.util.Set;

@Repository
public interface OffDayRepository extends JpaRepository<OffDayEntity,Long> {

    @Query("SELECT t FROM OffDayEntity t WHERE t.user = ?1")
    OffDayEntity[] findAllByUser(UserEntity user);
}
