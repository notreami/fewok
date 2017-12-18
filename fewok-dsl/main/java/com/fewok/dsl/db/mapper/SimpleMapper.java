package com.fewok.dsl.db.mapper;


import com.fewok.dsl.db.domain.SimpleDomain;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author notreami on 17/6/24.
 */
@Mapper
@Repository
public interface SimpleMapper {

    List<SimpleDomain> selectSimpleDomainByAll();

    @Select("SELECT SYSDATE()")
    Date selectSysDate();
}
