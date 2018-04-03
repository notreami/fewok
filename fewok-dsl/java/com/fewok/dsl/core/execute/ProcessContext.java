package com.fewok.dsl.core.execute;

import com.fewok.common.common.BaseInput;
import com.fewok.common.common.CommonInput;
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
    private CommonInput<Input> commonInput;


    public static <Input extends BaseInput> ProcessContext<Input> create(CommonInput<Input> commonInput) {
        ProcessContext<Input> processContext = new ProcessContext<>();
        processContext.setCommonInput(commonInput);
        return processContext;
    }


    @Override
    public Long getObjectId() {
        return IdWorker.getIdWorker().nextId();
    }

    @Override
    public boolean isVirtual() {
        return commonInput.getData().isVirtual();
    }

    @Override
    public Long getUserId() {
        return commonInput.getData().getUserId();
    }

    @Override
    public String getTokenId() {
        return commonInput.getData().getTokenId();
    }

    @Override
    public boolean isValid() {
        return commonInput.getData().isValid();
    }
}
