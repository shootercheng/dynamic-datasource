package com.scd.thread;

/**
 * @author James
 */
public class TicketThread implements Runnable {
    private int sumTicket = 200;

    public TicketThread(int sumTicket) {
        this.sumTicket = sumTicket;
    }

    @Override
    public void run() {
        while (sumTicket > 0) {
            System.out.println(Thread.currentThread().getName()
                    + " ticket sum " + sumTicket);
            sumTicket--;
        }
    }
}
