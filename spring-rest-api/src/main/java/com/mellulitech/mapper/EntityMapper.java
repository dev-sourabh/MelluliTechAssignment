package com.mellulitech.mapper;

import java.util.List;

public interface EntityMapper <D,E> {
    D toDTO(E entity);

    E toEntity(D dto);

    List<E> toEntity(List<D> dtoList);

    List<D> toDTO(List<E> entityList);
}
