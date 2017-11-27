package com.amaraa.main.game.gameKeyManager;

import com.amaraa.main.game.Game;
import com.amaraa.main.game.objects.GameObject;
import com.amaraa.main.game.Handler;
import com.amaraa.main.game.objects.ID;
import com.amaraa.main.game.objects.weapon.Laser;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


public class KeyManager extends KeyAdapter {

    private Handler handler;

    public KeyManager(Handler handler) {
        this.handler = handler;
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        for (int i = 0; i < handler.objects.size(); i++) {
            GameObject tempObject = handler.objects.get(i);

            if (tempObject.getId() == ID.Player) {
                if (key == KeyEvent.VK_D) tempObject.setVelX(+4);
                if (key == KeyEvent.VK_A) tempObject.setVelX(-4);

                if (key == KeyEvent.VK_SPACE)
                    handler.addObject(new Laser(tempObject.getX() + 12, tempObject.getY(), ID.Laser, handler));

            }
        }

    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        for (int i = 0; i < handler.objects.size(); i++) {
            GameObject tempObject = handler.objects.get(i);

            if (tempObject.getId() == ID.Player) {
                if (key == KeyEvent.VK_D) tempObject.setVelX(0);
                if (key == KeyEvent.VK_A) tempObject.setVelX(0);
            }
        }
    }
}
