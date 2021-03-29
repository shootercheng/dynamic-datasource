package com.scd.thread;

/**
 * @author James
 */
public class TicketThreadTest {

    public static void main(String[] args) {
        System.out.println("main thread start...");
        TicketThread ticketThread = new TicketThread(300);
        Thread thread1 = new Thread(ticketThread, "thread1");
        Thread thread2 = new Thread(ticketThread, "thread2");
        Thread thread3 = new Thread(ticketThread, "thread3");
        thread1.start();
        thread2.start();
        thread3.start();
        System.out.println("main thread end");
    }
}
