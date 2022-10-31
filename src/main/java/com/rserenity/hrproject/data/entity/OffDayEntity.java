package com.rserenity.hrproject.data.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

import javax.persistence.*;
import java.time.LocalDate;

@NoArgsConstructor
@Log4j2
@Data
@Entity
@Table(name = "offdays")
public class OffDayEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id",nullable = false)
    private Long id;
    @Column(name = "date",nullable = false)
    private LocalDate date;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity user;
}
