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
@Table(name = "USER_PRIVILEGE")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserPrivilege {

    @Id
    @Column(name = "USER_PRIVILEGE_ID", nullable = false)
    private Long userPrivilegeId;

    @Column(name = "USER_ID", nullable = false)
    private Long userId;

    @Column(name = "PRIVILEGE_ID", nullable = false)
    private Long privilegeId;

    @Column(name = "CREATED_AT", nullable = false)
    private Date createdAt;
}
