package com.cma.test;

import java.util.concurrent.SynchronousQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Player2 implements Runnable {

    private static final Logger logger = Logger.getLogger(Player2.class.getName());

    private SynchronousQueue<String> queue;

    private Thread player1;

    public Player2(SynchronousQueue<String> queue) {
        this.queue = queue;
    }

    public void run() {
        try {
            while(!Thread.currentThread().isInterrupted()) {
                // получаем из очереди сообщение
                String msg = queue.poll();
                if (msg != null) {
                    int iteration = currnetIteration(msg);
                    // итерируем счетчик
                    String sendMsg = Player1.TEXT + ++iteration;
                    // кладем в очередь сообщение
                    queue.put(sendMsg);
                    System.out.println(String.format("%s: send %s", this.getClass().getSimpleName(), sendMsg));
                }
            }
        } catch (InterruptedException e) {
            logger.log(Level.SEVERE, e.getMessage());
        } finally {
            // сигнализируем пококу Player2 о завершении
            // необходим в случае если произошло исключение в текущем потоке
            player1.interrupt();
        }
        System.out.println(String.format("%s with player [%s] thread was shutdown",
                Thread.currentThread().getName(), this.getClass().getSimpleName()));
    }

    /*
    Вытягиваем из сообщения счетчик
     */
    private int currnetIteration(String msg) {
        String number = null;
        try {
            number = msg.substring(Player1.TEXT.length());
        } catch (IndexOutOfBoundsException e) {
            logger.log(Level.INFO, "It's ferst iteration.");
        }

        int count;
        if (number == null || number.isEmpty()) {
            count = 0;
        } else {
            count = Integer.parseInt(number);
        }
        return count;
    }

    public void setPlayer1(Thread player1) {
        this.player1 = player1;
    }
}
