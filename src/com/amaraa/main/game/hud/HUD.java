package com.amaraa.main.game.hud;

import com.amaraa.main.game.Game;

import java.awt.*;

public class HUD {

    public static int HEALTH = 100;
    public static int SCORE = 0;

    public void tick() {

        HEALTH = Game.clamp(HEALTH, 0, 100);


    }

    public void render(Graphics g) {
        g.setColor(Color.green);
        Font font = new Font("SansSerif",Font.BOLD,16);
        g.setFont(font);
        g.drawString("Score: " + SCORE, 375,30);

        g.setColor(Color.gray);
        g.fillRect(10, 10, 200, 24);

        g.setColor(Color.green);
        g.fillRect(10, 10, HEALTH * 2, 24);

        g.setColor(Color.white);
        g.drawRect(10, 10, 200, 24);
    }

}
