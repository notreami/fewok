package com.fewok.biz.biz.createaccount;

import com.fewok.common.common.CommonInput;
import com.fewok.common.common.CommonOutput;
import com.fewok.common.createaccount.CreateAccountInput;
import com.fewok.common.createaccount.CreateAccountOutput;
import com.fewok.dsl.core.bpm.annotation.Processor;
import com.fewok.dsl.core.bpm.processor.BaseActivityProcessor;
import com.fewok.dsl.core.bpm.processor.BaseFlowProcessor;

/**
 * @author notreami on 18/3/26.
 */
@Processor
public class CreateAccountFlow extends BaseFlowProcessor<CreateAccountInput, CreateAccountOutput> {

    @Override
    protected CommonOutput<CreateAccountOutput> doProcess(CommonInput<CreateAccountInput> commonInput) throws Exception {
        return null;
    }
}
