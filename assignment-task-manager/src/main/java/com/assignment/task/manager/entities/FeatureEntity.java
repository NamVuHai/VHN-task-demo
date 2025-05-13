package com.assignment.task.manager.entities;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Entity
@DiscriminatorValue("FEATURE")
@Data
@EqualsAndHashCode(callSuper=false)
public class FeatureEntity extends TaskEntity {
    private String businessValue;
    private LocalDate deadline;
}
