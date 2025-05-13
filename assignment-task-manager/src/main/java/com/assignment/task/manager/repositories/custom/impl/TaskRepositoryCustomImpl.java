package com.assignment.task.manager.repositories.custom.impl;

import com.assignment.core.model.PagingResponse;
import com.assignment.core.utils.QueryBuilder;
import com.assignment.task.manager.constant.TaskType;
import com.assignment.task.manager.entities.QBugEntity;
import com.assignment.task.manager.entities.QFeatureEntity;
import com.assignment.task.manager.entities.QTaskEntity;
import com.assignment.task.manager.entities.QUserEntity;
import com.assignment.task.manager.model.request.task.SearchTaskRequest;
import com.assignment.task.manager.model.response.task.SearchTaskResponse;
import com.assignment.task.manager.repositories.custom.TaskRepositoryCustom;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.QBean;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.PathBuilderFactory;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.Querydsl;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Repository
public class TaskRepositoryCustomImpl implements TaskRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;
    private final QUserEntity qUserEntity = QUserEntity.userEntity;
    private final QTaskEntity qTaskEntity = QTaskEntity.taskEntity;
    private final QBugEntity qBugEntity = QBugEntity.bugEntity;
    private final QFeatureEntity qFeatureEntity = QFeatureEntity.featureEntity;

    @Override
    public PagingResponse<SearchTaskResponse> search(SearchTaskRequest searchTaskRequest, Pageable pageable) {
        Querydsl querydsl = new Querydsl(entityManager, new PathBuilderFactory().create(SearchTaskResponse.class));
        JPQLQuery<SearchTaskResponse> query = new JPAQuery<>(entityManager);
        BooleanBuilder conditions = new BooleanBuilder();
        conditions.and(qTaskEntity.isDeleted.eq(false));
        QueryBuilder.buildSearchPredicate(conditions, QueryBuilder.EQUAL, qUserEntity.userId, searchTaskRequest.getUserId());
        QueryBuilder.buildSearchPredicate(conditions, QueryBuilder.EQUAL, qTaskEntity.type, searchTaskRequest.getType());
        QueryBuilder.buildSearchPredicate(conditions, QueryBuilder.EQUAL, qTaskEntity.status, searchTaskRequest.getStatus());
        if (Objects.nonNull(searchTaskRequest.getOtherText())) {
            BooleanBuilder conditions2 = new BooleanBuilder();
            conditions2.and(qTaskEntity.description.containsIgnoreCase(searchTaskRequest.getOtherText()));
            conditions2.or(qTaskEntity.name.containsIgnoreCase(searchTaskRequest.getOtherText()));
            conditions.and(conditions2);
        }
        query.select(buildSelectedField())
                .from(qTaskEntity)
                .leftJoin(qUserEntity).on(qTaskEntity.user.userId.eq(qUserEntity.userId))
                .where(conditions);
        List<SearchTaskResponse> response = querydsl.applyPagination(pageable,query).fetch();
        int totalElements =  (int) query.fetchCount();
        return PagingResponse.newResponse(totalElements,pageable.getPageNumber(),pageable.getPageSize(),response);
    }

    private QBean<SearchTaskResponse> buildSelectedField() {
        return Projections.bean(SearchTaskResponse.class,
                qTaskEntity.id,
                qTaskEntity.name,
                qTaskEntity.description,
                qTaskEntity.status,
                qTaskEntity.type,
                qUserEntity.userId
        );
    }
}
