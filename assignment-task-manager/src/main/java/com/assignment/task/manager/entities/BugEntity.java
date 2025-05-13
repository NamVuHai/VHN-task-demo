package com.assignment.task.manager.entities;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@DiscriminatorValue("BUG")
@Data
@EqualsAndHashCode(callSuper=false)
public class BugEntity extends TaskEntity {
    private String severity;

    private String stepsToReproduce;
}
