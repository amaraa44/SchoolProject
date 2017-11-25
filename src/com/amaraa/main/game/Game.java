package com.amaraa.main.game;

import com.amaraa.main.Window;
import com.amaraa.main.game.hud.HUD;
import com.amaraa.main.game.keymanager.KeyManager;
import com.amaraa.main.game.objects.ID;
import com.amaraa.main.game.objects.entities.BasicEnemy;

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

        handler = new Handler();
        this.addKeyListener(new KeyManager(handler));

        new Window(WIDTH, HEIGHT, "The game", this);

        hud = new HUD();

        System.out.println(WIDTH + " " + HEIGHT);

        gamePlay = new GamePlay(handler, this);


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

}
