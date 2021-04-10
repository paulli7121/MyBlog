package com.changyu.dao;

import com.changyu.po.Type;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TypeMapper {

    Type findByName(@Param("name") String name);

    Type findById(@Param("id") Long id);

    List<Type> listTypes();

    List<Type> listTop(@Param("size") Integer size);

    int saveType(Type type);

    int updateType(Type type);

    int deleteType(@Param("id") Long id);
}
