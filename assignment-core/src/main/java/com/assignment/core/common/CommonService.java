package com.assignment.core.common;

import com.assignment.core.constant.ErrorCode;
import com.assignment.core.exception.BusinessException;
import jakarta.persistence.EntityNotFoundException;
import lombok.SneakyThrows;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public abstract class CommonService <E,ID,R extends CommonRepository<E,ID>>{
    protected final R repo;

    protected CommonService(R repo) {
        this.repo = repo;
    }

    public E save(E entity) {
        return repo.save(entity);
    }

    public List<E> save(List<E> entities) {
        return repo.saveAll(entities);
    }

    public Optional<E> get(ID id) throws BusinessException {
        try {
            return repo.findById(id);
        } catch (EntityNotFoundException entityNotFoundException) {
            throw new BusinessException( entityNotFoundException.getMessage());
        }
    }

    @SneakyThrows
    public E getOrElseThrow(ID id) {
        return get(id).orElseThrow(() -> new BusinessException( notFoundMessage()));
    }

    public E getOrElseThrow(ID id, String message) {
        return get(id).orElseThrow(() -> new BusinessException(notFoundMessage()));
    }

    public E updateOnField(ID id , Consumer<E> fieldConsumer){
        E entity = getOrElseThrow(id);
        return update(entity,fieldConsumer);
    }

    public E update (E entity, Consumer<E> fieldConsumer){
        fieldConsumer.accept(entity);
        return save(entity);
    }

    public abstract E delete(E entity);
    public abstract E deleteById(ID id);
    protected abstract String notFoundMessage();
}
