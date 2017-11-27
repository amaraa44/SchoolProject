package com.amaraa.main.game.objects.entities;

import com.amaraa.main.game.Game;
import com.amaraa.main.game.hud.HUD;
import com.amaraa.main.game.iamgeLoader.ImageLoader;
import com.amaraa.main.game.objects.GameObject;
import com.amaraa.main.game.Handler;
import com.amaraa.main.game.objects.ID;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends GameObject {

    private int width = 32, height = 32;
    Handler handler;

    public Player(int x, int y, ID id, Handler handler) {
        super(x, y, id);
        this.handler = handler;

//        imageLoader();

        System.out.println(id + ":" + " X: " + x + ", Y: " + y);
        System.out.println(id + ":" + " Width: " + width + " Height: " + height);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public void tick() {

        x += velX;
        y += velY;

        // I DON'T UNDERSTAND WHY -48, BUT -48... :/
        x = Game.clamp(x, 0, Game.WIDTH - 48);

        collision();

    }

    private void collision() {
        for (int i = 0; i < handler.objects.size(); i++) {

            GameObject tempObject = handler.objects.get(i);
            if (tempObject.getId() == ID.BasicEnemy) {
                if (getBounds().intersects(tempObject.getBounds())) {
                    HUD.HEALTH -= 20;
                    handler.removeObject(tempObject);
                }
            }

        }
    }

    private BufferedImage img = ImageLoader.getImage("/images/x_wing.png", this);

    public void render(Graphics g) {

        g.drawImage(img, x, y, width, height, null);

//        g.setColor(Color.white);
//        g.fillRect(x, y, width, height);
    }
}
