package com.fewok.dsl.core.bpm.container;

import com.fewok.dsl.core.bpm.processor.BaseActivityProcessor;
import lombok.Data;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

/**
 * 流程执行上下文
 *
 * @author notreami on 18/5/31.
 */
@Data
public class ExecuteContext {

    private Arrange arrange = null;
    private Map<Integer, Arrange> reviseArrangeMap;

    @Data
    public static class Arrange {
        Arrange(List<ExecuteInfo> executeInfoList) {
            this.executeInfoList = executeInfoList;
        }

        private int beginIndex = 0;
        private int endIndex = 0;
        private boolean isNotSync = false;
        private boolean isAllSuccess = true;
        private List<ExecuteInfo> executeInfoList;
    }


    /**
     * 执行单元（记录活动流程执行情况）
     */
    @Data
    public static class ExecuteInfo {

        /**
         * 活动流程
         */
        private BaseActivityProcessor activityProcessor;

        /**
         * 执行次数
         */
        private int executeCount = 0;

        /**
         * 执行总次数
         */
        private int executeTotal = 0;

        /**
         *
         */
        private Future<ExecuteResult> future;

        /**
         * 执行结果集
         */
        private List<ExecuteResult> executeResultList;
    }

    public ExecuteContext(Promise promise) {
        if (promise == null) {
            return;
        }
        List<BaseActivityProcessor> activityProcessorList = promise.getActivityProcessorList();
        if (CollectionUtils.isEmpty(activityProcessorList)) {
            return;
        }

        List<ExecuteInfo> executeInfoList = new ArrayList<>(activityProcessorList.size());
        for (BaseActivityProcessor activityProcessor : activityProcessorList) {
            executeInfoList.add(wrapExecuteInfo(activityProcessor));
        }
        arrange = new Arrange(executeInfoList);


        Map<Integer, List<BaseActivityProcessor>> reviseActivityProcessorMap = promise.getReviseActivityProcessorMap();
        if (MapUtils.isEmpty(reviseActivityProcessorMap)) {
            return;
        }

        reviseArrangeMap = new HashMap<>(reviseActivityProcessorMap.size());
        for (Map.Entry<Integer, List<BaseActivityProcessor>> reviseActivityProcessor : reviseActivityProcessorMap.entrySet()) {
            activityProcessorList = reviseActivityProcessor.getValue();
            if (CollectionUtils.isEmpty(activityProcessorList)) {
                continue;
            }

            executeInfoList = new ArrayList<>(activityProcessorList.size());
            for (BaseActivityProcessor activityProcessor : activityProcessorList) {
                executeInfoList.add(wrapExecuteInfo(activityProcessor));
            }
            reviseArrangeMap.put(reviseActivityProcessor.getKey(), new Arrange(executeInfoList));
        }
    }

    private ExecuteInfo wrapExecuteInfo(BaseActivityProcessor activityProcessor) {
        int retryCount = activityProcessor.getRetryCount();
        int executeTotal = retryCount <= 0 ? 1 : retryCount + 1;

        ExecuteInfo executeInfo = new ExecuteInfo();
        executeInfo.setActivityProcessor(activityProcessor);
        executeInfo.setExecuteTotal(executeTotal);
        executeInfo.setExecuteResultList(new ArrayList<>(executeTotal));
        return executeInfo;
    }
}
