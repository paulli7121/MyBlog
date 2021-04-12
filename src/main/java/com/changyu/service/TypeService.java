package com.changyu.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.changyu.po.Type;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

public interface TypeService {

    Type getType(Long id);

    Type getTypeByName(String name);

    IPage<Type> listTypes(Page<?> page);

    List<Type> listTypes();

    List<Type> listTypeTop(Integer size);

    int saveType(Type type);

    int updateType(Long id, Type type);

    void deleteType(Long id);
}
