package com.changyu.service.impl;

import com.changyu.dao.TypeMapper;
import com.changyu.exception.NotFoundException;
import com.changyu.po.Type;
import com.changyu.service.TypeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TypeServiceImpl implements TypeService {

    @Autowired
    private TypeMapper typeMapper;

    @Override
    public Type getType(Long id) {

        return typeMapper.findById(id);
    }

    @Override
    public Type getTypeByName(String name) {

        return typeMapper.findByName(name);
    }

    @Override
    public List<Type> listTypes() {
        return typeMapper.listTypes();
    }

    @Override
    public List<Type> listTypeTop(Integer size) {
        return typeMapper.listTop(size);
    }

    @Transactional
    @Override
    public int saveType(Type type) {
        return typeMapper.saveType(type);
    }

    @Transactional
    @Override
    public int updateType(Long id, Type type) {
        Type updateType = typeMapper.findById(id);
        if (updateType == null) {
            throw new NotFoundException("不存在该类型");
        }
        BeanUtils.copyProperties(type, updateType);
        return typeMapper.updateType(updateType);
    }

    @Transactional
    @Override
    public void deleteType(Long id) {

        typeMapper.deleteType(id);
    }
}
