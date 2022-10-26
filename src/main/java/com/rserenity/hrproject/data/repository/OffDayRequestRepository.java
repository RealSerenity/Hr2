package com.rserenity.hrproject.data.repository;

import com.rserenity.hrproject.data.entity.OffDayRequestEntity;
import com.rserenity.hrproject.data.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;
@Repository
public interface OffDayRequestRepository extends JpaRepository<OffDayRequestEntity,Long> {

    @Query("SELECT t FROM OffDayRequestEntity t WHERE t.user = ?1")
    Set<OffDayRequestEntity> getRequestsOfUser(UserEntity entity);
}
