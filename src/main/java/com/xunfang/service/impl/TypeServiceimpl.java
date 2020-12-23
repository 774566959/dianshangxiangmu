package com.xunfang.service.impl;

import com.xunfang.dao.TypeDao;
import com.xunfang.pojo.Type;
import com.xunfang.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service("typeService")
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
public class TypeServiceimpl implements TypeService {
    @Autowired
    private TypeDao typeDao;

    @Override
    public List<Type> getAll() {
        return typeDao.findAll();
    }

    @Override
    public int addType(Type type) {
        return typeDao.insertType(type);
    }

    @Override
    public void update(Type type) {
        typeDao.updateType(type);
    }

    @Override
    public int deleType(String ids) {
        return typeDao.deleType(ids);
    }
}
