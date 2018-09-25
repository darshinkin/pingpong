package com.cma.test;

import java.util.concurrent.SynchronousQueue;

public class Game {

    public static void main(String[] args) {
        // используется SynchronousQueue в качестве общего ресурса с помощью которого потоки общаются
        // это единственный общий ресурс для потоков
        SynchronousQueue<String> queue = new SynchronousQueue<String>();
        // создаются нити
        Runnable player1 = new Player1(queue);
        Runnable player2 = new Player2(queue);
        Thread thread1 = new Thread(player1);
        Thread thread2 = new Thread(player2);
        // потоки друг на друга необходимы для подачи сигнала на завершение другому потоку
        // в случае если в текущем потоку произойдет произойдет
        ((Player1) player1).setPlayer2(thread2);
        ((Player2) player2).setPlayer1(thread1);
        // запускаются нити
        thread1.start();
        thread2.start();
    }
}
