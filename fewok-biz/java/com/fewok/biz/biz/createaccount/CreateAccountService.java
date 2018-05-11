package com.fewok.biz.biz.createaccount;

import com.fewok.biz.biz.BaseLaunchProcessor;
import com.fewok.common.common.CommonRequest;
import com.fewok.common.common.CommonResponse;
import com.fewok.common.createaccount.CreateAccountInput;
import com.fewok.common.createaccount.CreateAccountOutput;
import com.fewok.dsl.core.execute.BaseProcessor;

/**
 * @author notreami on 18/3/26.
 */
public class CreateAccountService extends BaseLaunchProcessor<CreateAccountInput, CreateAccountOutput> {

    @Override
    protected CommonResponse<CreateAccountOutput> doProcess(CommonRequest<CreateAccountInput> input) throws Exception {
        return null;
    }
}
