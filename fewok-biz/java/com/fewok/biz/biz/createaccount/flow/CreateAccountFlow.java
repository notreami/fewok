package com.fewok.biz.biz.createaccount.flow;

import com.fewok.biz.biz.BaseProcessContextFlowProcessor;
import com.fewok.biz.biz.bean.CreateAccountContext;
import com.fewok.dsl.core.execute.BaseProcessor;
import com.fewok.dsl.core.execute.logic.LogicResult;
import org.springframework.beans.factory.InitializingBean;

/**
 * 校验参数、判断是否已经创建账号、创建账号、生成并保存登陆token
 * @author notreami on 18/3/26.
 */
public class CreateAccountFlow extends BaseProcessContextFlowProcessor<CreateAccountContext> implements InitializingBean {
    @Override
    protected LogicResult createValidFail(ValidResult validResult) {
        return null;
    }

    @Override
    protected LogicResult doProcess(CreateAccountContext input) throws Exception {
        return null;
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }
}
