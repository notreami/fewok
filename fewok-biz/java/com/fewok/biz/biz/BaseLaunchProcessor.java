package com.fewok.biz.biz;

import com.fewok.common.common.BaseInput;
import com.fewok.common.common.CommonInput;
import com.fewok.common.common.CommonOutput;
import com.fewok.dsl.core.execute.BaseProcessor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author notreami on 18/3/21.
 */
@Slf4j
public abstract class BaseLaunchProcessor<Input extends BaseInput, Output> extends BaseProcessor<CommonInput<Input>, CommonOutput<Output>> {


    @Override
    protected ValidResult checkInput(CommonInput<Input> input) {
        return super.checkInput(input);

    }

    @Override
    protected CommonOutput<Output> createValidFail(ValidResult validResult) {
        return null;
    }
}
