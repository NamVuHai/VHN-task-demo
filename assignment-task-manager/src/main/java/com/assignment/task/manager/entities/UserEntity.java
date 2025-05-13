package com.assignment.task.manager.entities;

import com.assignment.core.common.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class UserEntity extends BaseEntity {

    @UuidGenerator
    @Id
    private String userId;

    private String userName;

    private String fullName;

    private String password;

    @OneToMany(mappedBy = "user")
    private List<TaskEntity> tasks;
}
