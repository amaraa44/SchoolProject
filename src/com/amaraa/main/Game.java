package com.amaraa.main;

import com.amaraa.main.keymanager.KeyManager;
import com.amaraa.main.mydbmanager.MyDbManager;
import com.amaraa.main.objects.Handler;
import com.amaraa.main.objects.ID;
import com.amaraa.main.objects.entities.Enemy;
import com.amaraa.main.objects.entities.Player;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class Game extends Canvas implements Runnable {

    //4:3 aspect ratio
    public static final int HEIGHT = 360, WIDTH = HEIGHT / 3 * 4;
    private Thread thread;
    private boolean running = false;

    private Handler handler;

    public Game(){

        handler = new Handler();
        this.addKeyListener(new KeyManager(handler));

        //TODO: https://youtu.be/bWHYjLJZswQ?t=484 I'm here.

        new Window(WIDTH, HEIGHT, "The game", this);


        handler.addObject(new Player(100,100, ID.Player));
        handler.addObject(new Enemy(250,250,ID.Enemy));

    }

    public synchronized void start() {
        thread = new Thread(this);
        thread.start();
        running = true;

    }
    public synchronized void stop(){
        try{
            thread.join();
            running = false;
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;
        while (running){
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1){
                tick();
                delta--;
            }
            if (running)
                render();
            frames++;

            if (System.currentTimeMillis() - timer > 1000){
                timer += 1000;
                System.out.println("FPS: " + frames);
                frames = 0;
            }
        }
        stop();
    }
    private void tick(){
        handler.tick();
    }
    private void render(){
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null){
            this.createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();

        g.setColor(Color.black);
        g.fillRect(0,0,WIDTH, HEIGHT);

        handler.render(g);

        g.dispose();
        bs.show();
    }

    public static void main(String[] args){

        new Game();

//        MyDbManager.executeSQL("INSERT INTO USERS VALUES(null, 'nev');");
//        MyDbManager.insertInToUsers("ize");
//        MyDbManager.deleteFromUsers("nev");
//        MyDbManager.deleteFromUsers(2);
//        MyDbManager.updateNameInUsers(3,"nev");
//        MyDbManager.selectFromUsers(10);
//        System.out.println(MyDbManager.getUsersName(1));

    }
}
