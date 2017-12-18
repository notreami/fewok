package com.fewok.biz.service;

import com.fewok.dsl.db.dao.SimpleDao;
import com.fewok.dsl.db.domain.SimpleDomain;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author notreami on 17/6/24.
 */
@Slf4j
@Service
public class SimpleService {
    @Resource
    private SimpleDao simpleDao;

    public List<SimpleDomain> selectSimpleDomainByAll() {
        return simpleDao.selectSimpleDomainByAll();
    }

    public Date selectSysDate() {
        return simpleDao.selectSysDate();
    }
}
