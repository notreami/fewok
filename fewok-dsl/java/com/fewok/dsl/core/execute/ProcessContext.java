package com.fewok.dsl.core.execute;

import com.fewok.common.common.BaseInput;
import com.fewok.common.common.CommonRequest;
import com.fewok.common.common.ObjectId;
import com.fewok.dsl.util.IdWorker;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author notreami on 17/11/23.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProcessContext<Input extends BaseInput> implements BaseInput, ObjectId {
    private CommonRequest<Input> commonRequest;


    public static <Input extends BaseInput> ProcessContext<Input> create(CommonRequest<Input> commonRequest) {
        ProcessContext<Input> processContext = new ProcessContext<>();
        processContext.setCommonRequest(commonRequest);
        return processContext;
    }


    @Override
    public Long getObjectId() {
        return IdWorker.getIdWorker().nextId();
    }

    @Override
    public boolean isVirtual() {
        return commonRequest.getData().isVirtual();
    }

    @Override
    public Long getUserId() {
        return commonRequest.getData().getUserId();
    }

    @Override
    public String getTokenId() {
        return commonRequest.getData().getTokenId();
    }

    @Override
    public boolean isValid() {
        return commonRequest.getData().isValid();
    }
}
