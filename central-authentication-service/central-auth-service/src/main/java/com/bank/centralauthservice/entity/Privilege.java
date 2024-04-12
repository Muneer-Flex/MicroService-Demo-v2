package com.bank.centralauthservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "PRIVILEGE")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Privilege {

    @Id
    @Column(name = "PRIVILEGE_ID", nullable = false)
    private Long privilegeId;

    @Column(name = "PRIVILEGE_NAME", nullable = false)
    private String privilegeName;

    @Column(name = "CREATED_AT", nullable = false)
    private Date createdAt;
}
