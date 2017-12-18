package com.fewok.dsl.db.dao;

import com.fewok.dsl.db.domain.SimpleDomain;
import com.fewok.dsl.db.mapper.SimpleMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author notreami on 17/6/24.
 */
@Slf4j
@Component
public class SimpleDao {
    @Resource
    private SimpleMapper simpleMapper;

    public List<SimpleDomain> selectSimpleDomainByAll() {
        return simpleMapper.selectSimpleDomainByAll();
    }

    public Date selectSysDate(){
        return simpleMapper.selectSysDate();
    }
}
