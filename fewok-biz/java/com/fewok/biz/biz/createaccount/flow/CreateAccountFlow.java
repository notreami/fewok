package com.fewok.biz.biz.createaccount.flow;

import com.fewok.biz.biz.BaseProcessContextFlowProcessor;
import com.fewok.biz.biz.BaseProcessContextProcessor;
import com.fewok.biz.biz.bean.CreateAccountContext;
import com.fewok.dsl.core.execute.logic.LogicResult;
import org.springframework.beans.factory.InitializingBean;

import java.util.List;

/**
 * 校验参数、判断是否已经创建账号、创建账号、生成并保存登陆token
 *
 * @author notreami on 18/3/26.
 */
public class CreateAccountFlow extends BaseProcessContextFlowProcessor<CreateAccountContext> {

    @Override
    protected void afterProperties() {

    }

    @Override
    protected List<BaseProcessContextProcessor<CreateAccountContext>> getProcessors() {
        return null;
    }


}
