package com.cma.test;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Player1 implements Runnable {

    private static final Logger logger = Logger.getLogger(Player1.class.getName());

    public static final String TEXT = "text";

    private SynchronousQueue<String> queue;

    private Thread player2;

    public Player1(SynchronousQueue<String> queue) {
        this.queue = queue;
    }

    public void run() {
        try {
            // первая подача из данного потока, первая итерация, запуск игры
            queue.put(TEXT);
            System.out.println(String.format("%s: send %s", this.getClass().getSimpleName(), TEXT));
            // поток получит сигнал на прерывание в случае исключения в потоке Player1
            while(!Thread.currentThread().isInterrupted()) {
                // получаем из очереди сообщение
                String msg = queue.poll();
                if (msg != null) {
                    int iteration = currnetIteration(msg);
                    System.out.println(String.format("------ %s recieved %d times.",
                            this.getClass().getSimpleName(), iteration));
                    // завершаем работу потока в случае получения 15 сообщений
                    // поскольку счетчик в сообщении итерируют 2 потока,
                    // первый поток получит 15 сообщений в случае если счетчик будет >= 29
                    if (iteration >= 29) {
                        break;
                    }
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
            player2.interrupt();
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
            number = msg.substring(TEXT.length());
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

    public void setPlayer2(Thread player2) {
        this.player2 = player2;
    }
}
