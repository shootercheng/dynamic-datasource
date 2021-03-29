package com.scd.thread.producerconsumer;

/**
 * @author James
 */
public class Consumer extends Thread {
    Tickets t;
    int i = 0;

    public Consumer(Tickets t) {
        this.t = t;
    }

    @Override
    public void run() {
        while (i < t.size) {
            if (t.avaliable && i <= t.number) {
                System.out.println("Consumer buys ticket "+(++i));
            }
            if (i == t.number) {
//                try {
//                    Thread.sleep(1);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
                t.avaliable = false;
            }
        }
    }
}
