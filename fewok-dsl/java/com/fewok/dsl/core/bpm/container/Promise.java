package com.fewok.dsl.core.bpm.container;

import com.fewok.dsl.core.bpm.processor.BaseActivityProcessor;
import lombok.Getter;
import lombok.NonNull;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 流程编排
 *
 * @author notreami on 18/5/31.
 */
@Getter
public class Promise {

    /**
     * 活动流程
     */
    private List<BaseActivityProcessor> activityProcessorList = new ArrayList<>();

    /**
     * 活动流程的恢复活动流程
     */
    private Map<Integer, List<BaseActivityProcessor>> reviseActivityProcessorMap = new HashMap<>();

    public static PromiseBuilder builder() {
        return new PromiseBuilder();
    }

    public static class PromiseBuilder {

        private Promise promise = new Promise();

        /**
         * 增加一个活动流程
         *
         * @param activityProcessor 一个活动流程
         * @return PromiseBuilder
         */
        public PromiseBuilder then(@NonNull BaseActivityProcessor activityProcessor) {
            promise.getActivityProcessorList().add(activityProcessor);
            return this;
        }

        /**
         * 增加多个活动流程
         *
         * @param activityProcessors 多个活动流程
         * @return PromiseBuilder
         */
        public PromiseBuilder all(@NonNull BaseActivityProcessor... activityProcessors) {
            for (BaseActivityProcessor activityProcessor : activityProcessors) {
                then(activityProcessor);
            }
            return this;
        }

        /**
         * 增加恢复流程（用于恢复当前活动流程）
         *
         * @param activityProcessors 恢复流程
         * @return PromiseBuilder
         */
        public PromiseBuilder revise(@NonNull BaseActivityProcessor... activityProcessors) {
            List<BaseActivityProcessor> activityProcessorList = getCurrentActivityProcessor();
            if (activityProcessorList == null) {
                return this;
            }

            for (BaseActivityProcessor activityProcessor : activityProcessors) {
                if (activityProcessor == null) {
                    continue;
                }
                activityProcessorList.add(activityProcessor);
            }
            return this;
        }

        /**
         * 合并之前增加恢复流程，作为当前活动流程的恢复流程
         *
         * @return PromiseBuilder
         */
        public PromiseBuilder mergeBeforeRevise() {
            List<BaseActivityProcessor> activityProcessorList = getCurrentActivityProcessor();
            if (activityProcessorList == null) {
                return this;
            }

            Integer subscript = promise.getActivityProcessorList().size() - 1;
            Map<Integer, List<BaseActivityProcessor>> reviseActivityProcessorMap = promise.getReviseActivityProcessorMap();
            for (int i = 0; i < subscript; i++) {
                List<BaseActivityProcessor> temp = reviseActivityProcessorMap.get(subscript);
                if (CollectionUtils.isEmpty(temp)) {
                    continue;
                }
                activityProcessorList.addAll(temp);
            }
            return this;
        }

        /**
         * 生成
         *
         * @return Promise
         */
        public Promise build() {
            return promise;
        }

        private List<BaseActivityProcessor> getCurrentActivityProcessor() {
            List<BaseActivityProcessor> activityProcessorList = promise.getActivityProcessorList();
            if (CollectionUtils.isEmpty(activityProcessorList)) {
                return null;
            }

            Integer subscript = activityProcessorList.size() - 1;
            Map<Integer, List<BaseActivityProcessor>> reviseActivityProcessorMap = promise.getReviseActivityProcessorMap();
            activityProcessorList = reviseActivityProcessorMap.get(subscript);
            if (activityProcessorList == null) {
                activityProcessorList = new ArrayList<>();
                reviseActivityProcessorMap.put(subscript, activityProcessorList);
            }
            return activityProcessorList;
        }
    }
}
