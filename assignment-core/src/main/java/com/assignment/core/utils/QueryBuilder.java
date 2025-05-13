package com.assignment.core.utils;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Ops;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.ComparableExpressionBase;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringExpression;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;

public class QueryBuilder {
    public static final String EQUAL = "=";
    public static final String NOT_EQUAL = "!=";
    public static final String LIKE = "%";
    public static final String GREATER_THAN = ">";

    public static final String LESS_THAN = "<";
    public static final String GREATER_THAN_OR_EQUAL = ">=";

    public static final String LESS_THAN_OR_EQUAL = "=<";
    public static final String IN = "IN";
    public static final String NOT_IN = "NOT_IN";
    public static final String CONTAIN_ONE_CONDITION = "CONTAIN_ONE";

    public static <T extends Comparable<?>> void buildSearchPredicate(BooleanBuilder condition, String operator, ComparableExpressionBase<T> field, T searchData) {
        if (Objects.nonNull(searchData)) {
            BooleanExpression expression = null;
            switch (operator) {
                case EQUAL -> expression = field.eq(searchData);
                case NOT_EQUAL -> expression = field.ne(searchData);
                case LIKE -> {
                    if (field instanceof StringExpression) {
                        expression = ((StringExpression) field).containsIgnoreCase(searchData.toString());
                    } else {
                        throw new IllegalArgumentException("Field does not support 'like' operation");
                    }
                }
                case GREATER_THAN ->
                        expression = Expressions.booleanOperation(Ops.GT, field, Expressions.constant(searchData));
                case LESS_THAN ->
                        expression = Expressions.booleanOperation(Ops.LT, field, Expressions.constant(searchData));
                case GREATER_THAN_OR_EQUAL ->
                        expression = Expressions.booleanOperation(Ops.GOE, field, Expressions.constant(searchData));
                case LESS_THAN_OR_EQUAL ->
                        expression = Expressions.booleanOperation(Ops.LOE, field, Expressions.constant(searchData));
                default -> throw new IllegalArgumentException("Not Support operator: " + operator);
            }
            if (expression != null) {
                condition.and(expression);
            }
        }
    }

    public static <T extends Comparable<?>> void buildSearchPredicate(BooleanBuilder conditions, String operator, ComparableExpressionBase<T> field, List<T> searchData) {
        if (Objects.nonNull(searchData) && !CollectionUtils.isEmpty(searchData)) {
            switch (operator) {
                case IN -> conditions.and(field.in(searchData));
                case NOT_IN -> conditions.and(field.notIn(searchData));
                default -> throw new IllegalArgumentException("Not Support operator: " + operator);

            }
        }
    }
}
