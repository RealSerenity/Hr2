package com.rserenity.hrproject.security.Session;

import com.rserenity.hrproject.data.entity.UserEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

import javax.persistence.*;
import java.util.Date;

@NoArgsConstructor
@Log4j2
@Data
@Entity
@Table(name = "session")
public class JSessionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id",nullable = false)
    private Long id;
    @Column(name = "jSessionId",nullable = false)
    private String jSessionId;
    @Column(name = "userId",nullable = false)
    private Long userId;
}
