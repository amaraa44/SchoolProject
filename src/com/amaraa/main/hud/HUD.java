package com.amaraa.main.hud;

import com.amaraa.main.Game;

import java.awt.*;

public class HUD {

    public static int HEALTH = 10;

    public void tick(){

        HEALTH = Game.clamp(HEALTH, 0, 100);

    }
    public void render(Graphics g){
        g.setColor(Color.gray);
        g.fillRect(10,10,200,24);

        g.setColor(Color.green);
        g.fillRect(10,10,HEALTH * 2, 24);

        g.setColor(Color.white);
        g.drawRect(10,10,200,24);
    }

}
