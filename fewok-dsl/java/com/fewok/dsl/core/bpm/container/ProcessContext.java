package com.fewok.dsl.core.bpm.container;

import com.fewok.common.common.BaseInput;
import com.fewok.common.common.CommonInput;
import com.fewok.common.common.ProcessId;
import com.fewok.dsl.core.bpm.util.IdWorker;
import lombok.Data;

/**
 * 流程上下文
 *
 * @author notreami on 18/5/31.
 */
@Data
public class ProcessContext<Input extends BaseInput, Holder> implements BaseInput, ProcessId {

    private CommonInput<Input> commonInput;
    private Holder holder;
    private Long processId = IdWorker.getIdWorker().nextId();

    @Override
    public Long getProcessId() {
        return processId;
    }

    @Override
    public String getTokenId() {
        return null;
    }

    @Override
    public Long getUserId() {
        return null;
    }
}
