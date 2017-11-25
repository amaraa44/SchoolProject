package com.amaraa.main;

import com.amaraa.main.hud.HUD;
import com.amaraa.main.keymanager.KeyManager;
import com.amaraa.main.objects.ID;
import com.amaraa.main.objects.entities.BasicEnemy;
import com.amaraa.main.objects.entities.Player;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.Random;

public class Game extends Canvas implements Runnable {

    //4:3 aspect ratio
//    public static final int HEIGHT = 360, WIDTH = HEIGHT / 3 * 4;
    public static final int WIDTH = 480, HEIGHT = 360;
    private Thread thread;
    private boolean running = false;
    private Random r;
    private int frames;

    private Handler handler;
    private HUD hud;
    private GamePlay gamePlay;

    public Game() {

        //TODO: https://www.youtube.com/watch?v=5ufOPX8N1Rg

        handler = new Handler();
        this.addKeyListener(new KeyManager(handler));

        new Window(WIDTH, HEIGHT, "The game", this);

        hud = new HUD();

        r = new Random();
        System.out.println(WIDTH + " " + HEIGHT);

        gamePlay = new GamePlay(handler, this);

//        handler.addObject(new Player(WIDTH/2-32,HEIGHT/5*4-32, ID.Player, handler));
//        for (int i = 0; i < 10; i++) {
//            handler.addObject(new BasicEnemy(r.nextInt(WIDTH-48),-31, ID.BasicEnemy));
//        }
//
//        handler.addObject(new BasicEnemy(r.nextInt(WIDTH-64),-31, ID.BasicEnemy));
//        handler.addObject(new BasicEnemy(r.nextInt(WIDTH-64),-31, ID.BasicEnemy));

    }

    public synchronized void start() {
        thread = new Thread(this);
        thread.start();
        running = true;

    }

    public synchronized void stop() {
        try {
            thread.join();
            running = false;
        } catch (Exception e) {
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
        frames = 0;
        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1) {
                tick();
                delta--;
            }
            if (running)
                render();
            frames++;

            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                System.out.println("FPS: " + frames);
                frames = 0;
            }
        }
        stop();
    }

    private void tick() {
        handler.tick();
        hud.tick();
        gamePlay.tick();
        spawner();
    }

    private void render() {
        this.requestFocus();
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();

        g.setColor(Color.black);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        handler.render(g);
        hud.render(g);
        gamePlay.render(g);

        g.dispose();
        bs.show();
    }

    private void spawner() {
        if (frames == 500) {
            handler.addObject(new BasicEnemy(r.nextInt(Game.WIDTH - 48), -31, ID.BasicEnemy));
        }
    }

    public static int clamp(int var, int min, int max) {
        if (var <= min) {
            return min;
        } else if (var >= max) {
            return max;
        } else {
            return var;
        }
    }

    public int getFrames() {
        return frames;
    }

    public static void main(String[] args) {

        new Game();

    }
}
