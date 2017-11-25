package com.amaraa.main.game;

import com.amaraa.main.game.objects.ID;
import com.amaraa.main.game.objects.entities.BasicEnemy;
import com.amaraa.main.game.objects.entities.Player;

import java.awt.*;
import java.util.Random;

public class GamePlay {

    Handler handler;
    Game game;
    private Random r = new Random();


    public GamePlay(Handler handler, Game game) {
        this.handler = handler;
        this.game = game;
        game();
    }

    public void game() {
        handler.addObject(new Player(Game.WIDTH / 2 - 32, Game.HEIGHT / 5 * 4 - 32, ID.Player, handler));

    }

    public void tick() {
        spawnEnemy();
    }

    public void render(Graphics g) {

    }

    private double time;
    private double lastTime = 0;
    private int difficulty = 1000;

    private void spawnEnemy() {
        time = System.nanoTime() / 100000000;

        if (time > (lastTime + r.nextInt(difficulty))) {
            if (difficulty >= 251){
                difficulty -= 25;
            }
            lastTime = time;
            for (int i = 0; i < r.nextInt(4); i++) {
                handler.addObject(new BasicEnemy(r.nextInt(Game.WIDTH - 48), -31, ID.BasicEnemy));
            }
        }
    }
}
