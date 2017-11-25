package com.amaraa.main.game.objects.weapon;

import com.amaraa.main.game.Game;
import com.amaraa.main.game.Handler;
import com.amaraa.main.game.hud.HUD;
import com.amaraa.main.game.objects.GameObject;
import com.amaraa.main.game.objects.ID;

import java.awt.*;

public class Laser extends GameObject{

    private int width = 16, height = 16;
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

    public void render(Graphics g) {
        g.setColor(Color.blue);
        g.fillRect(x,y,width,height);
    }

    public Rectangle getBounds() {
        return new Rectangle(x,y,width,height);
    }
}
