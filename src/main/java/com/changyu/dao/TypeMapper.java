package com.changyu.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.changyu.po.Type;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TypeMapper extends BaseMapper<Type> {

    Type findByName(@Param("name") String name);

    Type findById(@Param("id") Long id);

    List<Type> listTypes();

    IPage<Type> listTypesPage(Page<?> page);

    List<Type> listTop(@Param("size") Integer size);

    int saveType(Type type);

    int updateType(Type type);

    int deleteType(@Param("id") Long id);
}
