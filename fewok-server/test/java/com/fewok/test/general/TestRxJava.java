package com.fewok.test.general;

import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author notreami on 17/11/26.
 */
public class TestRxJava {

    public static void main(String[] args) throws InterruptedException {
        Flowable.just("Hello world").subscribe(System.out::println);


        Flowable.fromCallable(() -> {
            Thread.sleep(1000); //  imitate expensive computation
            return "Done";
        })
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.single())
                .subscribe(System.out::println, Throwable::printStackTrace);

        Thread.sleep(2000); // <--- wait for the flow to finish


        Flowable.range(1, 10)
                .observeOn(Schedulers.computation())
                .map(v -> v * v)
                .blockingSubscribe(System.out::println);
    }
}
