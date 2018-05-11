package com.fewok.biz.biz;

import com.fewok.common.common.BaseInput;
import com.fewok.common.common.ClientInfo;
import com.fewok.common.common.CommonRequest;
import com.fewok.common.common.CommonResponse;
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
public abstract class BaseLaunchProcessor<Input extends BaseInput, Output> extends BaseProcessor<CommonRequest<Input>, CommonResponse<Output>> {

    @Override
    protected void preProcess(CommonRequest<Input> commonRequest) {
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
    protected ValidResult checkInput(CommonRequest<Input> commonRequest) {
        boolean reqChk = commonRequest.isValid();
        if (!reqChk) {
            return new ValidResult(false, "CommonRequest入参错误");
        }
        reqChk = commonRequest.getData().isValid();
        return new ValidResult(reqChk, reqChk ? "验证成功" : "CommonRequest<Input>入参错误");
    }


    @Override
    protected CommonResponse<Output> createValidFail(ValidResult validResult) {
        return CommonResponse.PROCESS_INFO;
    }

    @Override
    protected CommonResponse<Output> handleException(CommonRequest<Input> commonRequest, CommonResponse<Output> commonResponse, Exception e) {
        log.error("BaseLaunchProcessor process error", e);
        return CommonResponse.SYS_ERROR;
    }
}
