package com.bank.governingstate.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "GOVERNING_STATE")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoverningStateEntity implements Serializable {

    @Id
    @SequenceGenerator(name = "governingState", sequenceName = "GOVERNING_STATE_SEQUENCE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "governingState")
    @Column(name = "GOVERNING_STATE_ID", nullable = false)
    private Long governingStateId;

    @Column(name = "ZIP_CODE", nullable = false)
    private String zipCode;

    @Column(name = "GOVERNING_STATE", nullable = false)
    private String governingState;

    @CreationTimestamp
    @Column(name = "CREATED_ON", nullable = false)
    private Date createdOn;

    public Long getGoverningStateId() {
        return governingStateId;
    }

    public void setGoverningStateId(Long governingStateId) {
        this.governingStateId = governingStateId;
    }
}
