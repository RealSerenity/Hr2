package com.rserenity.hrproject.data.entity;

import com.rserenity.hrproject.security.auth.RequestStatus;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@NoArgsConstructor
@Log4j2
@Data
@Entity
@Table(name = "OffDayRequests")
public class OffDayRequestEntity extends BaseEntity{

    @Column(name = "date",nullable = false)
    private LocalDate requestDate;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity user;

    @Column(name = "status",nullable = false)
    RequestStatus status;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OffDayRequestEntity entity = (OffDayRequestEntity) o;
        return this.getId() == entity.getId();
    }
}
