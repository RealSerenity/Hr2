package com.rserenity.hrproject.data.repository;

import com.rserenity.hrproject.data.entity.OffDayEntity;
import com.rserenity.hrproject.data.entity.OffDayRequestEntity;
import com.rserenity.hrproject.data.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
@Repository
public interface OffDayRequestRepository extends JpaRepository<OffDayRequestEntity,Long> {

    @Query("SELECT t FROM OffDayRequestEntity t WHERE t.user = ?1 order by t.requestDate desc")
    OffDayRequestEntity[] getRequestsOfUser(UserEntity entity);
    // above and below methods are equal
    List<OffDayRequestEntity> findOffDayRequestEntitiesByUserOrderByRequestDateDesc(UserEntity user);

    List<OffDayRequestEntity> findOffDayRequestEntitiesByUserOrderByRequestDateDescIdDesc(UserEntity user);
}

