package com.fewok.biz.biz.createaccount;

import com.fewok.biz.biz.BaseLaunchProcessor;
import com.fewok.common.common.CommonInput;
import com.fewok.common.common.CommonOutput;
import com.fewok.common.createaccount.CreateAccountInput;
import com.fewok.common.createaccount.CreateAccountOutput;

/**
 * @author notreami on 18/3/26.
 */
public class CreateAccountService extends BaseLaunchProcessor<CreateAccountInput, CreateAccountOutput> {

    @Override
    protected CommonOutput<CreateAccountOutput> doProcess(CommonInput<CreateAccountInput> input) throws Exception {
        return null;
    }
}
