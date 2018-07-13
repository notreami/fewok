package com.fewok.test.general;

import com.google.common.collect.Lists;
import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author notreami on 17/11/26.
 */
@Slf4j
public class TestRxJava {

    public static void main(String[] args) throws InterruptedException {
        long time1=System.currentTimeMillis();
        Stream.of(1, 2, 3, 4).forEach(x -> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        long time2=System.currentTimeMillis();

        Arrays.asList(6, 2, 9, 4).parallelStream().forEach(x -> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        long time3=System.currentTimeMillis();

        System.out.println(time2-time1);
        System.out.println(time3-time2);

        Flux.range(1, 1000)
                .log()
                .parallel(8)
//                .runOn(Schedulers.parallel()) //parallel flux
                .sequential() //必须使用sequential来将这些异步线程的执行结果汇集成一个stream
                .map(e -> {
                    log.info("map thread:{},e:{}",Thread.currentThread().getName(),e);
                    return e*10;
                })
                .subscribe(e -> {
                    log.info("subscribe thread:{},e:{}",Thread.currentThread().getName(),e);
                });
    }
}
