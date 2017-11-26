package com.amaraa.main.game.objects.weapon;

import com.amaraa.main.game.Game;
import com.amaraa.main.game.Handler;
import com.amaraa.main.game.hud.HUD;
import com.amaraa.main.game.iamgeLoader.ImageLoader;
import com.amaraa.main.game.objects.GameObject;
import com.amaraa.main.game.objects.ID;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Laser extends GameObject{

    private int width = 8, height = 24;
    private Handler handler;

    public Laser(int x, int y, ID id, Handler handler) {
        super(x, y, id);
        this.handler = handler;
    }

    public void tick() {

        y -= 1;

//        for (int i = 0; i < handler.objects.size(); i++) {
//
//            GameObject tempObject = handler.objects.get(i);
//            if (tempObject.getId() == ID.Laser){
//                if (tempObject.getY() <= 0){
//                    handler.removeObject(this);
//                }
//            }
//
//        }

        if (y <= 0){
            handler.removeObject(this);
        }
        collision();
    }

    private void collision() {

        for (int i = 0; i < handler.objects.size(); i++) {

            GameObject tempObject = handler.objects.get(i);
            if (tempObject.getId() == ID.BasicEnemy) {
                if (getBounds().intersects(tempObject.getBounds())) {
                    HUD.SCORE += 10;
                    handler.removeObject(this);
                    handler.removeObject(tempObject);
                }
            }

        }

    }

    private BufferedImage img = ImageLoader.getImage("/images/green_laser.png",this);

    public void render(Graphics g) {
//        imageLoader();
        g.drawImage(img,x,y,width,height,null);

//        g.setColor(Color.blue);
//        g.fillRect(x,y,width,height);
    }

    public Rectangle getBounds() {
        return new Rectangle(x,y,width,height);
    }
}
