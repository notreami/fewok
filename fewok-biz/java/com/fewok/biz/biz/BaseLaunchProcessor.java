package com.fewok.biz.biz;

import com.fewok.common.common.BaseInput;
import com.fewok.common.common.ClientInfo;
import com.fewok.common.common.CommonInput;
import com.fewok.common.common.CommonOutput;
import com.fewok.common.util.JsonBinder;
import com.fewok.dsl.core.execute.BaseProcessor;
import com.fewok.dsl.core.execute.Processor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

/**
 * @author notreami on 18/3/21.
 */
@Slf4j
public abstract class BaseLaunchProcessor<Input extends BaseInput, Output> extends BaseProcessor<CommonInput<Input>, CommonOutput<Output>> {

    @Override
    protected void preProcess(CommonInput<Input> commonRequest) {
        if (commonRequest != null) {
            if (commonRequest.getClientInfo() == null) {
                ClientInfo clientInfo = new ClientInfo();
                commonRequest.setClientInfo(clientInfo);
            }
            ClientInfo clientInfo = commonRequest.getClientInfo();
            if (StringUtils.isBlank(clientInfo.getClientAppKey())) {
//                clientInfo.setClientAppKey(ClientInfoUtil.getClientAppKey());
            }
            if (StringUtils.isBlank(clientInfo.getClientIp())) {
//                clientInfo.setClientIp(ClientInfoUtil.getClientIp());
            }
        }
        log.info("preProcess:{}", JsonBinder.toJSONString(commonRequest));
    }

    @Override
    protected ValidResult checkInput(CommonInput<Input> commonRequest) {
        boolean reqChk = commonRequest.isValid();
        if (!reqChk) {
            return new ValidResult(false, "CommonInput入参错误");
        }
        reqChk = commonRequest.getData().isValid();
        return new ValidResult(reqChk, reqChk ? "验证成功" : "CommonInput<Input>入参错误");
    }


    @Override
    protected CommonOutput<Output> createValidFail(ValidResult validResult) {
        return CommonOutput.PROCESS_INFO;
    }

    @Override
    protected CommonOutput<Output> handleException(CommonInput<Input> commonRequest, CommonOutput<Output> commonResponse, Exception e) {
        log.error("BaseLaunchProcessor process error", e);
        return CommonOutput.SYS_ERROR;
    }
}
