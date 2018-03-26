package com.fewok.dsl.core.execute;

import com.fewok.common.common.BaseInput;
import com.fewok.common.common.CommonInput;
import com.fewok.common.common.ObjectId;
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
    /**
     * 备注
     */
    private String description;


    public static <Input extends BaseInput> ProcessContext<Input> create(CommonInput<Input> commonInput) {
        ProcessContext<Input> processContext = new ProcessContext<>();
        processContext.setCommonInput(commonInput);
        return processContext;
    }


    @Override
    public Long getObjectId() {
        return null;
    }

    @Override
    public boolean isVirtual() {
        return false;
    }

    @Override
    public Long getUserId() {
        return null;
    }

    @Override
    public boolean isValid() {
        return false;
    }

    @Override
    public String getTokenId() {
        return null;
    }
}
