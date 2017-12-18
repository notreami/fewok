package com.fewok.test.general;

import com.fewok.dsl.util.IdWorker;

/**
 * 唯一id生成器
 * @author notreami on 17/11/11.
 */
public class TestIdWorker {

    public static void main(String[] args) {
        IdWorker.getInstance(0, 0);
        IdWorker snowFlake = IdWorker.getIdWorker();
        for (int i = 0; i < 100; i++) {
            long id = snowFlake.nextId();
            System.out.println(id);
        }
    }
}
