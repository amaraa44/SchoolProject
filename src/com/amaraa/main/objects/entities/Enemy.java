package com.amaraa.main.objects.entities;

import com.amaraa.main.objects.GameObject;
import com.amaraa.main.objects.ID;

import java.awt.*;

public class Enemy extends GameObject {


    public Enemy(int x, int y, ID id) {
        super(x, y, id);
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.red);
        g.fillRect(x,y,32,32);
    }
}
