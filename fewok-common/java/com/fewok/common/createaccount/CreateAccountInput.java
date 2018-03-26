package com.fewok.common.createaccount;

import com.fewok.common.common.BaseInput;

/**
 * @author notreami on 18/3/26.
 */
public class CreateAccountInput implements BaseInput {

    @Override
    public String getTokenId() {
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
}
