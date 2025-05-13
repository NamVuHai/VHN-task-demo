package com.assignment.auth.entities;

import com.assignment.core.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "USERS")
public class UserEntity  extends BaseEntity {

    @Id
    @UuidGenerator
    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "USER_NAME")
    private String userName;

    @Column(name = "PASSWORD")
    private String password;
}
