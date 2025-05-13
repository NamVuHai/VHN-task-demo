package com.assignment.task.manager.repositories.custom.impl;

import com.assignment.core.model.PagingResponse;
import com.assignment.core.utils.QueryBuilder;
import com.assignment.task.manager.entities.QUserEntity;
import com.assignment.task.manager.model.request.user.SearchUserRequest;
import com.assignment.task.manager.model.response.user.UserDetailResponse;
import com.assignment.task.manager.repositories.custom.UserRepositoryCustom;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.QBean;
import com.querydsl.core.types.dsl.PathBuilderFactory;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.Querydsl;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserRepositoryCustomImpl implements UserRepositoryCustom {

    @PersistenceContext
    private final EntityManager entityManager;
    private static final QUserEntity qUserEntity = QUserEntity.userEntity;

    @Override
    public PagingResponse<UserDetailResponse> searchUserDetail(SearchUserRequest request, Pageable pageable) {
        Querydsl querydsl = new Querydsl(entityManager,(new PathBuilderFactory().create(UserDetailResponse.class)));
        JPQLQuery<UserDetailResponse> query = new JPAQuery<>(entityManager);
        BooleanBuilder conditions = new BooleanBuilder();
        conditions.and(qUserEntity.isDeleted.eq(false));
        QueryBuilder.buildSearchPredicate(conditions,QueryBuilder.LIKE,qUserEntity.userName,request.getUserName());
        QueryBuilder.buildSearchPredicate(conditions,QueryBuilder.EQUAL,qUserEntity.userId,request.getUserId());
        QueryBuilder.buildSearchPredicate(conditions,QueryBuilder.LIKE,qUserEntity.fullName,request.getFullName());
        query.select(buildSelectedField()).from(qUserEntity).where(conditions).orderBy(qUserEntity.createAt.desc());
        List<UserDetailResponse> userDetailResponses = querydsl.applyPagination(pageable,query).fetch();
        int totalElements =  (int) query.fetchCount();
        return PagingResponse.newResponse(totalElements,pageable.getPageNumber(),pageable.getPageSize(),userDetailResponses);
    }

    private QBean<UserDetailResponse> buildSelectedField(){
        return Projections.bean(UserDetailResponse.class,qUserEntity.userId,qUserEntity.userName,qUserEntity.fullName,qUserEntity.createAt);
    }
}
