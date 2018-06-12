package com.fewok.dsl.core.bpm.processor;

import com.fewok.common.common.BaseInput;
import com.fewok.common.common.StatusInfo;
import com.fewok.common.util.JsonBinder;
import com.fewok.dsl.core.bpm.ProcessExecutor;
import com.fewok.dsl.core.bpm.container.*;
import com.fewok.dsl.core.bpm.type.InvokeType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * 执行流程
 *
 * @author notreami on 18/5/31.
 */
@Slf4j
public abstract class BaseExecutorProcessor<Input extends BaseInput, Holder> extends BaseProcessor<ProcessContext<Input, Holder>, ProcessResult> implements InitializingBean {

    /**
     * 流程编排
     *
     * @return 流程编排
     */
    protected abstract Promise getPromise();

    /**
     * 流程执行器
     *
     * @return 流程执行器
     */
    protected abstract ProcessExecutor<ProcessContext<Input, Holder>, ExecuteResult> getProcessExecutor();

    /**
     * 流程编排
     */
    private Promise promise;

    /**
     * 流程执行器
     */
    private ProcessExecutor<ProcessContext<Input, Holder>, ExecuteResult> processExecutor;

    @Override
    public void afterPropertiesSet() throws Exception {
        promise = getPromise();
        processExecutor = getProcessExecutor();
    }

    @Override
    protected ProcessResult doProcess(ProcessContext<Input, Holder> processContext) throws Exception {
        ExecuteContext executeContext = new ExecuteContext(promise);
        ExecuteContext.Arrange arrange = executeContext.getArrange();
        if (arrange == null) {
            return ProcessResult.SKIP;
        }
        while (arrange.isAllSuccess() && arrange.getEndIndex() + 1 < arrange.getExecuteInfoList().size()) {
            arrangeExecute(arrange, processContext);
            if (arrange.isNotSync()) {
                waitExecuteResultAndRetry(arrange, processContext);
            }
        }
        if (!arrange.isAllSuccess()) {
            ExecuteContext.ExecuteInfo executeInfo;
            ExecuteResult executeResult;
            for (int i = 0; i <= arrange.getEndIndex(); i++) {
                executeInfo = arrange.getExecuteInfoList().get(i);
                executeResult = executeInfo.getExecuteResultList().get(executeInfo.getExecuteResultList().size() - 1);
                if (executeResult == null || executeResult.isSuccess()) {
                    continue;
                }
                //多个执行失败如何回滚
                ExecuteContext.Arrange reviseArrange = executeContext.getReviseArrangeMap().get(i);
                while (reviseArrange.isAllSuccess() && reviseArrange.getEndIndex() + 1 < reviseArrange.getExecuteInfoList().size()) {
                    arrangeExecute(reviseArrange, processContext);
                    if (reviseArrange.isNotSync()) {
                        waitExecuteResultAndRetry(reviseArrange, processContext);
                    }
                }
            }
        }
        return mergeExecuteResult(executeContext.getArrange());
    }

    private void arrangeExecute(ExecuteContext.Arrange arrange, ProcessContext<Input, Holder> processContext) {
        ExecuteContext.ExecuteInfo executeInfo;
        BaseActivityProcessor activityProcessor;
        ExecuteResult executeResult;
        arrange.setBeginIndex(arrange.getEndIndex());
        for (int i = arrange.getBeginIndex(); i < arrange.getExecuteInfoList().size(); i++) {
            arrange.setEndIndex(i);
            executeInfo = arrange.getExecuteInfoList().get(i);
            activityProcessor = executeInfo.getActivityProcessor();
            if (arrange.isNotSync() && activityProcessor.isWaitBeforeProcessor() && i != arrange.getBeginIndex()) {
                break;
            }


            executeProcessor(executeInfo, processContext);
            if (activityProcessor.getInvokeType() != InvokeType.SYNC) {
                if (!arrange.isNotSync()) {
                    arrange.setNotSync(true);
                }
                continue;
            }

            executeResult = getExecuteResult(executeInfo);
            if (executeResult != null && executeResult.isSuccess()) {
                continue;
            }

            int executeTotal = executeInfo.getExecuteTotal();
            for (int r = 1; r < executeTotal; r++) {
                executeProcessor(executeInfo, processContext);
                executeResult = getExecuteResult(executeInfo);
                if (executeResult != null && executeResult.isSuccess()) {
                    break;
                }
            }
            if (executeResult == null || !executeResult.isSuccess()) {
                //中断
                arrange.setAllSuccess(false);
                break;
            }
        }
    }

    private void waitExecuteResultAndRetry(ExecuteContext.Arrange arrange, ProcessContext<Input, Holder> processContext) {
        boolean isFinish = true;

        ExecuteContext.ExecuteInfo executeInfo;
        BaseActivityProcessor activityProcessor;
        for (int i = arrange.getBeginIndex(); i < arrange.getEndIndex(); i++) {
            executeInfo = arrange.getExecuteInfoList().get(i);
            activityProcessor = executeInfo.getActivityProcessor();
            if (activityProcessor.getInvokeType() == InvokeType.SYNC) {
                continue;
            }

            ExecuteResult executeResult = getExecuteResult(executeInfo);
            if (executeResult == null || executeResult.isSuccess()) {
                continue;
            }
            if (executeInfo.getExecuteCount() >= executeInfo.getExecuteTotal()) {
                if (arrange.isAllSuccess()) {
                    arrange.setAllSuccess(false);
                }
                continue;
            }

            isFinish = false;
            executeProcessor(executeInfo, processContext);
        }

        if (!isFinish) {
            waitExecuteResultAndRetry(arrange, processContext);
        }
    }

    private ProcessResult mergeExecuteResult(ExecuteContext.Arrange arrange) {
        ExecuteResult executeResult = null;
        ExecuteContext.ExecuteInfo executeInfo;
        for (int i = arrange.getEndIndex(); i >= 0; i--) {
            executeInfo = arrange.getExecuteInfoList().get(i);
            ExecuteResult temp = executeInfo.getExecuteResultList().get(executeInfo.getExecuteResultList().size() - 1);
            if (temp != null && temp.getStatusInfo().getCode() != StatusInfo.SKIP.getCode()) {
                executeResult = temp;
                break;
            }
        }


        if (arrange.isAllSuccess()) {
            return ProcessResult.createSuccess(executeResult == null ? null : executeResult.getData(), StatusInfo.OK);
        } else if (executeResult == null) {
            return ProcessResult.createError(StatusInfo.UNAUTHORIZED_ERROR);
        }
        return ProcessResult.createError(executeResult.getData(), executeResult.getStatusInfo());
    }

    /**
     * 执行activityProcessor
     *
     * @param executeInfo
     * @param processContext
     */
    private void executeProcessor(ExecuteContext.ExecuteInfo executeInfo, ProcessContext<Input, Holder> processContext) {
        BaseActivityProcessor activityProcessor = executeInfo.getActivityProcessor();
        executeInfo.setExecuteCount(executeInfo.getExecuteCount() + 1);
        Future<ExecuteResult> future = processExecutor.execute(activityProcessor, processContext);
        executeInfo.setFuture(future);
    }

    /**
     * 获取执行结果（无future，则返回上次获取的结果）
     *
     * @param executeInfo
     * @return
     */
    private ExecuteResult getExecuteResult(ExecuteContext.ExecuteInfo executeInfo) {
        Future<ExecuteResult> future = executeInfo.getFuture();
        if (future == null) {
            return executeInfo.getExecuteResultList().get(executeInfo.getExecuteResultList().size() - 1);
        }

        ExecuteResult executeResult = null;
        BaseActivityProcessor activityProcessor = executeInfo.getActivityProcessor();
        try {
            executeResult = future.get(activityProcessor.getTimeoutMillis(), TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            log.error("获取Process执行结果异常", e);
        } catch (TimeoutException e) {
            log.error("获取Process执行结果异常", e);
        } catch (Exception e) {
            log.error("获取Process执行结果异常", e);
        }

        if (executeResult == null) {
            executeResult = ExecuteResult.UNKNOWN_ERROR;
        }

        executeInfo.setFuture(null);
        executeInfo.getExecuteResultList().add(executeResult);
        return executeResult;
    }

    @Override
    protected ProcessResult handleCheckFail(ExecuteResult executeResult) {
        return ProcessResult.PARAM_ERROR;
    }

    @Override
    protected ProcessResult handleException(ProcessContext<Input, Holder> processContext, ProcessResult processResult, Exception e) {
        log.error("Process[{}]处理异常,processId={},processContext={}", getProcessorName(), processContext.getProcessId(), JsonBinder.toJSONString(processContext), e);
        if (processResult == null) {
            processResult = new ProcessResult();
        }
        processResult.setSuccess(false);
        processResult.setStatusInfo(StatusInfo.UNKNOWN_ERROR);
        processResult.setException(e);
        return processResult;
    }
}
