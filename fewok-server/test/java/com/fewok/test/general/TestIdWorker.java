package com.fewok.test.general;


import com.fewok.dsl.core.bpm.util.IdGenerator;
import com.fewok.dsl.core.bpm.util.IdWorker;

/**
 * 唯一id生成器
 * @author notreami on 17/11/11.
 */
public class TestIdWorker {
    private static final IdGenerator idGenerator = new IdGenerator();

    public static void main(String[] args) {
        System.out.println(System.currentTimeMillis());
        IdWorker snowFlake = IdWorker.getIdWorker();
        for (int i = 0; i < 100; i++) {
            long id = snowFlake.nextId();
            System.out.println(id);
        }
    }
}
