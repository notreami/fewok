//package com.fewok.test.coroutine;
//
//import co.paralleluniverse.fibers.Fiber;
//import co.paralleluniverse.fibers.SuspendExecution;
//import co.paralleluniverse.strands.Strand;
//import co.paralleluniverse.strands.concurrent.CountDownLatch;
//
//import java.util.concurrent.ExecutionException;
//import java.util.concurrent.atomic.AtomicInteger;
//
///**
// * @author notreami on 18/1/29.
// */
//public class TestQuasar {
//
//
//    public static void main(String[] args) throws ExecutionException, InterruptedException, SuspendExecution {
//        int FiberNumber = 1_000_000;
//        CountDownLatch latch = new CountDownLatch(1);
//        AtomicInteger counter = new AtomicInteger(0);
//
//        for (int i = 0; i < FiberNumber; i++) {
//            new Fiber(() -> {
//                counter.incrementAndGet();
//                if (counter.get() == FiberNumber) {
//                    System.out.println("done");
//                    System.exit(0);
//                }
//                Strand.sleep(1000000);
//            }).start();
//        }
//        latch.await();
//    }
//}