package com.fewok.biz.biz.opt;

import com.fewok.biz.biz.BaseProcessInstance;
import com.fewok.biz.biz.AbstractSequenceFlow;
import com.fewok.common.enums.ProcessType;
import com.fewok.dsl.bpm.annotation.Processor;

import java.util.List;

/**
 * @author notreami on 17/11/23.
 */
@Processor
public class CreateAccountFlow extends AbstractSequenceFlow {
    @Override
    public ProcessType getProcessType() {
        return ProcessType.CREATE_ACCOUNT;
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }

    @Override
    protected List<BaseProcessInstance> getProcessors() {
        return null;
    }
}
