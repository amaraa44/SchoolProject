package com.amaraa.main.objects.entities;

import com.amaraa.main.Game;
import com.amaraa.main.objects.GameObject;
import com.amaraa.main.Handler;
import com.amaraa.main.objects.ID;

import java.awt.*;

public class BasicEnemy extends GameObject {

    private int width = 32, height = 32;

    Handler handler;

    public BasicEnemy(int x, int y, ID id) {
        super(x, y, id);

        velY = 3;

        System.out.println(id + ":" + " X: " + x + ", Y: " + y);
        System.out.println(id + ":" + " Width: " + width + " Height: " + height);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public void tick() {

        handler = new Handler();

        if (y <= Game.HEIGHT) {
            y += SPEED;
        }
    }

    public void render(Graphics g) {
        g.setColor(Color.red);
        g.fillRect(x, y, width, height);
    }
}
