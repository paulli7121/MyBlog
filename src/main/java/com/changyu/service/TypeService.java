package com.changyu.service;

import com.changyu.po.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TypeService {

    Type getType(Long id);

    Type getTypeByName(String name);

//    Page<Type> listType(Pageable pageable);

    List<Type> listTypes();

    List<Type> listTypeTop(Integer size);

    int saveType(Type type);

    int updateType(Long id, Type type);

    void deleteType(Long id);
}
