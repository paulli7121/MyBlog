package com.changyu.service.impl;

import com.changyu.dao.TypeRepository;
import com.changyu.exception.NotFoundException;
import com.changyu.po.Type;
import com.changyu.service.TypeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class TypeServiceImpl implements TypeService {

    @Autowired
    private TypeRepository typeRepository;

    @Transactional
    @Override
    public Type saveType(Type type) {

        return typeRepository.save(type);
    }

    @Transactional
    @Override
    public Type getType(Long id) {

        return typeRepository.findById(id).get();
    }

    @Override
    public Type getTypeByName(String name) {

        return typeRepository.findByName(name);
    }

    @Transactional
    @Override
    public Page<Type> listType(Pageable pageable) {

        return typeRepository.findAll(pageable);
    }

    @Transactional
    @Override
    public Type updateType(Long id, Type type) {
        Optional<Type> OptionalType = typeRepository.findById(id);
        if (!OptionalType.isPresent()) {
            throw new NotFoundException("不存在该类型");
        }
        Type updateType = OptionalType.get();
        BeanUtils.copyProperties(type, updateType);
        return typeRepository.save(updateType);
    }

    @Transactional
    @Override
    public void deleteType(Long id) {

        typeRepository.deleteById(id);
    }
}
