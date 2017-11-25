package com.amaraa.main;

import com.amaraa.main.objects.ID;
import com.amaraa.main.objects.entities.BasicEnemy;
import com.amaraa.main.objects.entities.Player;

import java.awt.*;
import java.util.Random;

public class GamePlay {

    Handler handler;
    Game game;
    private int ize;
    private Random r = new Random();


    public GamePlay(Handler handler, Game game) {
        this.handler = handler;
        this.game = game;
        game();
    }

    public void game(){
        handler.addObject(new Player(Game.WIDTH/2-32,Game.HEIGHT/5*4-32, ID.Player, handler));
//        for (int i = 0; i < 10; i++) {
//            Random r = new Random();
//            handler.addObject(new BasicEnemy(r.nextInt(Game.WIDTH-48),-31, ID.BasicEnemy));
//        }

    }

    public void tick(){
        spawnEnemy();
    }

    public void render(Graphics g){

    }

    private double time;
    private double lastTime = 0;
    private void spawnEnemy(){
        time = System.nanoTime()/1000000000;
        if (time > lastTime){
            lastTime = time;
            for (int i = 0; i < r.nextInt(4); i++) {
                handler.addObject(new BasicEnemy(r.nextInt(Game.WIDTH-48),-31,ID.BasicEnemy));
            }
        }
    }
}
