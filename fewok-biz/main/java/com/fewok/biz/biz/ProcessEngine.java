package com.fewok.biz.biz;

import com.google.common.collect.ImmutableMap;
import com.fewok.dsl.bpm.annotation.Processor;
import com.fewok.biz.config.ProcessConfig;
import com.fewok.common.common.CommonInput;
import com.fewok.common.common.CommonOutput;
import com.fewok.common.common.ErrorInfo;
import com.fewok.common.enums.ProcessType;
import com.fewok.common.util.JsonBinder;
import com.fewok.dsl.bpm.common.ProcessDefinition;
import com.fewok.dsl.bpm.common.ProcessTypeIdentify;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 流程引擎
 * @author notreami on 17/11/22.
 */
@Slf4j
@Processor
public class ProcessEngine implements ProcessDefinition, InitializingBean {
    @Autowired
    private List<BaseProcessInstance> processInstanceList;

    private ImmutableMap<ProcessType, BaseProcessInstance> processInstanceListMap;

    @Override
    public void afterPropertiesSet() throws Exception {
        ImmutableMap.Builder<ProcessType, BaseProcessInstance> builder = ImmutableMap.builder();
        builder.putAll(processInstanceList.stream().collect(Collectors.toMap(ProcessTypeIdentify::getProcessType, v -> v)));
        processInstanceListMap = builder.build();
    }

    /**
     * 校验输入数据
     *
     * @param commonInput
     * @return
     */
    @Override
    public CommonOutput checkInput(CommonInput commonInput) {
        if (ProcessConfig.SERVICE_MAINTAINING) {
            log.info("系统维护中 --> ProcessType={},commonInput={}", commonInput.getProcessType(), JsonBinder.toJSONString(commonInput));
            return CommonOutput.createError(ErrorInfo.create(ErrorInfo.ErrorCode.PROCESS_ERROR, "系统维护中,请稍后在试!"));
        }

        if (commonInput.getData() == null) {
            log.error("流程操作参数错误 --> ProcessType={},commonInput={}", commonInput.getProcessType(), JsonBinder.toJSONString(commonInput));
            return CommonOutput.createError(ErrorInfo.create(ErrorInfo.ErrorCode.PROCESS_ERROR, "流程操作参数错误"));
        }
        return CommonOutput.OK;
    }

    /**
     * 流程
     *
     * @param commonInput
     * @return
     */
    @Override
    public CommonOutput process(CommonInput commonInput) {
        CommonOutput commonOutput = checkInput(commonInput);
        if (!commonOutput.isSuccess()) {
            return commonOutput;
        }

        BaseProcessInstance processInstance = processInstanceListMap.get(commonInput.getProcessType());
        if (processInstance == null) {
            log.error("不支持的流程 --> ProcessType={},commonInput={}", commonInput.getProcessType(), JsonBinder.toJSONString(commonInput));
            return CommonOutput.createError(ErrorInfo.create(ErrorInfo.ErrorCode.PROCESS_ERROR, "不支持的流程"));
        }
        return processInstance.process(commonInput);
    }
}
