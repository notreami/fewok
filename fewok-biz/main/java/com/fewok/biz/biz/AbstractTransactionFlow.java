package com.fewok.biz.biz;

/**
 * @author notreami on 17/11/23.
 */

import com.fewok.common.common.BaseInput;
import org.springframework.beans.factory.InitializingBean;

import java.util.List;
import java.util.Map;

public abstract class AbstractTransactionFlow<IN extends BaseInput, OUT> extends BaseProcessInstance implements InitializingBean {
        protected abstract List<BaseProcessInstance<IN, OUT>> getProcessorList();
        protected abstract Map<BaseProcessInstance<IN, OUT>,List<BaseProcessInstance<IN, OUT>>> getProcessorMap();


        }
