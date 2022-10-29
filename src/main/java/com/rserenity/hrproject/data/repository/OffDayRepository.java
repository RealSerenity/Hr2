package com.rserenity.hrproject.data.repository;

import com.rserenity.hrproject.data.entity.OffDayEntity;
import com.rserenity.hrproject.data.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OffDayRepository extends JpaRepository<OffDayEntity,Long> {

    @Query("SELECT t FROM OffDayEntity t WHERE t.user = ?1 order by t.date desc")
    OffDayEntity[] getAllOfUser(UserEntity user);
}
