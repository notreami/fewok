package com.fewok.biz.biz.createaccount.flow;

import com.fewok.biz.biz.bean.CreateAccountHolder;
import com.fewok.common.createaccount.CreateAccountInput;
import com.fewok.dsl.core.bpm.ProcessExecutor;
import com.fewok.dsl.core.bpm.container.ExecuteResult;
import com.fewok.dsl.core.bpm.container.ProcessContext;
import com.fewok.dsl.core.bpm.container.Promise;
import com.fewok.dsl.core.bpm.execute.SimpleProcessExecutor;
import com.fewok.dsl.core.bpm.processor.BaseExecutorProcessor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * 校验参数、判断是否已经创建账号、创建账号、生成并保存登陆token
 *
 * @author notreami on 18/3/26.
 */
public class CreateAccountExecutor extends BaseExecutorProcessor<CreateAccountInput, CreateAccountHolder> {

    private ThreadPoolTaskExecutor taskExecutor;

    @Override
    protected Promise getPromise() {
        return Promise.builder()
                .then(null)
                .all()
                .then(null).revise()
                .then(null).mergeBeforeRevise()
                .build();
    }

    @Override
    protected ProcessExecutor<ProcessContext<CreateAccountInput, CreateAccountHolder>, ExecuteResult> getProcessExecutor() {
        return new SimpleProcessExecutor<>(taskExecutor);
    }
}
