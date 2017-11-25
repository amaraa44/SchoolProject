package com.amaraa.main.objects.entities;

import com.amaraa.main.Game;
import com.amaraa.main.objects.GameObject;
import com.amaraa.main.objects.ID;

import java.awt.*;

public class Player extends GameObject {

    private int width = 32, height = 32;

    public Player(int x, int y, ID id) {
        super(x, y, id);

        System.out.println(id + ":" + " X: " + x + ", Y: " + y);
        System.out.println(id + ":" + " Width: " + width + " Height: " + height);
    }

    public Rectangle getBounds() {
        return new Rectangle(x,y,width,height);
    }

    public void tick() {

        x += velX;
        y += velY;

        // I DON'T UNDERSTAND WHY -48, BUT -48... :/
        x = Game.clamp(x, 0, Game.WIDTH-48);

    }

    public void render(Graphics g) {
        g.setColor(Color.white);
        g.fillRect(x,y,width,height);
    }
}
