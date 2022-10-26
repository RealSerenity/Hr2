package com.rserenity.hrproject.security.Session;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface JSessionRepository extends JpaRepository<JSessionEntity,Long> {

    @Query("SELECT s FROM JSessionEntity s WHERE s.jSessionId = :sessionId AND s.id = (SELECT MAX(c.id) FROM JSessionEntity c)")
    JSessionEntity findByJSessionId(@Param("sessionId") String sessionId);
}
