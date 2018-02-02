package com.amaraa.main.game;

import com.amaraa.main.Window;
import com.amaraa.main.game.hud.HUD;
import com.amaraa.main.game.iamgeLoader.ImageLoader;
import com.amaraa.main.game.gameKeyManager.KeyManager;
import com.amaraa.main.game.objects.GameObject;
import com.amaraa.main.game.objects.ID;
import com.amaraa.main.game.objects.entities.BasicEnemy;
import com.amaraa.main.game.objects.entities.Player;
import com.amaraa.main.game.menu.Menu;
import com.amaraa.main.mydbmanager.MyDbManager;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Game extends Canvas implements Runnable {

    //4:3 aspect ratio
//    public static final int HEIGHT = 360, WIDTH = HEIGHT / 3 * 4;
    public static final int WIDTH = 640, HEIGHT = 480;
    private Window window;
    private Thread thread;
    private boolean running = false;
    private Random r;

    private Handler handler;
    private HUD hud;

    private Menu menu;

    public enum STATE {
        Menu,
        Game,
        Scores,
        GameSettings
    }

    public STATE gameState = STATE.Menu;

    public Game() {

        new MyDbManager();

        window = new Window(WIDTH, HEIGHT, "Space shooter", this);

        handler = new Handler();
        menu = new Menu(this,handler);
        this.addMouseListener(menu);
        this.addKeyListener(new KeyManager(handler,this));

        hud = new HUD();





        System.out.println(WIDTH + " " + HEIGHT);

        if (gameState == STATE.Game){
            handler.addObject(new Player(Game.WIDTH / 2 - 32, Game.HEIGHT / 5 * 4 - 32, ID.Player, handler));


        }




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
        this.requestFocus();
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;
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
        if (gameState == STATE.Game){
            hud.tick();
            spawnEnemy();
            if (HUD.HEALTH <= 0){
                if (HUD.SCORE > 0){
                    MyDbManager.addScore(HUD.SCORE);
                    System.out.println("Score added to the database. Score: " + HUD.SCORE);
                    HUD.SCORE = 0;
                }else{
                    System.out.println("Score is less than 0.");
                }
                gameState = STATE.Menu;
                removeAllObject();
            }
        }else if(gameState == STATE.Menu){
            HUD.HEALTH = 100;
            menu.tick();
        }else if (gameState == STATE.Scores){
            menu.tick();
        }

        removeBEnemy();

    }
    private void removeAllObject(){
        for (int i = 0; i < handler.objects.size(); i++) {
            handler.removeObject(handler.objects.get(i));
        }
    }
    private void removeBEnemy(){
        for (int i = 0; i < handler.objects.size(); i++) {
            GameObject tempObject = handler.objects.get(i);
            if (tempObject.getId() == ID.BasicEnemy){
                if (tempObject.getY() >= HEIGHT){
                    handler.removeObject(tempObject);
                }
            }
            
        }
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


        handler.render(g);

        if (gameState == STATE.Game){
            hud.render(g);
        }else if(gameState == STATE.Menu){
            menu.render(g);
        }else if (gameState == STATE.Scores){
            menu.render(g);
        }



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



    //TODO: IF THE SCORE >= 100 OR 200 THEN SPAWN A SMARTER AI WHAT IS TRY TO CATCH THE PLAYER ( https://youtu.be/JrSjwQbTldg?t=577 )
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
