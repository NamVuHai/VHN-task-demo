package com.assignment.task.manager.entities;

import com.assignment.core.common.BaseEntity;
import com.assignment.task.manager.constant.TaskStatus;
import com.assignment.task.manager.constant.TaskType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.UuidGenerator;


@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "task_type")
@Table(name = "tasks")
@Data
@EqualsAndHashCode(callSuper=false)
public abstract class TaskEntity extends BaseEntity {
    @Id
    @UuidGenerator()
    private String id;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TaskStatus status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TaskType type;

    private String description;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;
}
