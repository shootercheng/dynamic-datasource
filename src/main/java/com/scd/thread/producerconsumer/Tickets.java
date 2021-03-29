package com.scd.thread.producerconsumer;

/**
 * @author James
 */
public class Tickets {
    int number;
    int size;
    boolean avaliable = false;

    public Tickets(int size) {
        this.size = size;
    }
}
