package com.amaraa.main.game.objects.entities;

import com.amaraa.main.game.Game;
import com.amaraa.main.game.iamgeLoader.ImageLoader;
import com.amaraa.main.game.objects.GameObject;
import com.amaraa.main.game.Handler;
import com.amaraa.main.game.objects.ID;

import java.awt.image.BufferedImage;
import java.awt.Rectangle;
import java.awt.Graphics;

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

        if (y >= Game.HEIGHT){
            handler.removeObject(this);
        }
    }

    private BufferedImage img = ImageLoader.getImage("/images/tie.png",this);

    public void render(Graphics g) {
        g.drawImage(img,x,y,width,height,null);

    }
}
