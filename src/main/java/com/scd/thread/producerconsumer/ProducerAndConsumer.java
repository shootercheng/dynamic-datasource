package com.scd.thread.producerconsumer;

/**
 * @author James
 */
public class ProducerAndConsumer {

    public static void main(String[] args) {
        test1();
//        test2();
    }

    private static void test2() {
        Tickets tickets = new Tickets(10);
        Producer producer = new Producer(tickets);
        Consumer consumer = new Consumer(tickets);
        producer.start();
        consumer.start();
    }

    private static void test1() {
        Tickets tickets = new Tickets(10);
        Producer producer = new Producer(tickets);
        Consumer consumer = new Consumer(tickets);
        consumer.start();
        producer.start();
    }
}
