package com.scd.thread.producerconsumer;

/**
 * @author James
 */
public class Producer extends Thread {

    Tickets t;

    public Producer(Tickets t) {
        this.t = t;
    }

    @Override
    public void run() {
        while (t.number < t.size) {
            System.out.println("Producer puts ticket " + (++t.number));
            t.avaliable = true;
        }
    }
}
