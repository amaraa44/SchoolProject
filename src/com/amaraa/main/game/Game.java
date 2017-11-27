package com.amaraa.main.game;

import com.amaraa.main.Window;
import com.amaraa.main.game.hud.HUD;
import com.amaraa.main.game.iamgeLoader.ImageLoader;
import com.amaraa.main.game.gameKeyManager.KeyManager;
import com.amaraa.main.game.objects.ID;
import com.amaraa.main.game.objects.entities.BasicEnemy;
import com.amaraa.main.game.objects.entities.Player;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
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



    public Game() {

        handler = new Handler();
        this.addKeyListener(new KeyManager(handler));

        new Window(WIDTH, HEIGHT, "Space shooter", this);

        hud = new HUD();

        System.out.println(WIDTH + " " + HEIGHT);

        handler.addObject(new Player(Game.WIDTH / 2 - 32, Game.HEIGHT / 5 * 4 - 32, ID.Player, handler));


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
        spawnEnemy();


    }

    private BufferedImage bgImg = ImageLoader.getImage("/images/star_bg.gif", this);

    private void render() {
        this.requestFocus();
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();

        g.drawImage(bgImg, 0, 0, WIDTH, HEIGHT, null);
//        g.setColor(Color.black);
//        g.fillRect(0, 0, WIDTH, HEIGHT);

        handler.render(g);
        hud.render(g);


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

    //TODO: DIFFICULTIES
    //80 = MEDIUM DIFFICULTY
    private int difficulty = 80;
    private int lastSpawnPoint = 0;

    private void spawnEnemy() {
        Random r = new Random();
        int random1 = r.nextInt(difficulty);
        int xSpawnPoint = r.nextInt(Game.WIDTH - 48);
        while (random1 == 1 && lastSpawnPoint != xSpawnPoint) {
            handler.addObject(new BasicEnemy(xSpawnPoint, -31, ID.BasicEnemy));
            lastSpawnPoint = xSpawnPoint;
            random1 = r.nextInt(difficulty);
        }
    }


}
