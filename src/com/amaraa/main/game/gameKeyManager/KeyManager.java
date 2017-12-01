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

    private boolean[] keyDown = new boolean[4];


    public KeyManager(Handler handler) {
        this.handler = handler;

        keyDown[0] = false;
        keyDown[1] = false;
        keyDown[2] = false;
        keyDown[3] = false;
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        for (int i = 0; i < handler.objects.size(); i++) {
            GameObject tempObject = handler.objects.get(i);

            if (tempObject.getId() == ID.Player) {
                if (key == KeyEvent.VK_D) { tempObject.setVelX(+4); keyDown[0] = true; }
                if (key == KeyEvent.VK_A) { tempObject.setVelX(-4); keyDown[1] = true; }
//                if (key == KeyEvent.VK_W) { tempObject.setVelY(-4); keyDown[2] = true; }
//                if (key == KeyEvent.VK_S) { tempObject.setVelY(+4); keyDown[3] = true; }

                if (key == KeyEvent.VK_SPACE)
                    //TODO: WE NEED TO REFINE IT
                    handler.addObject(new Laser(tempObject.getX() + 12, tempObject.getY(), ID.Laser, handler));

            }
        }

    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        for (int i = 0; i < handler.objects.size(); i++) {
            GameObject tempObject = handler.objects.get(i);

            if (tempObject.getId() == ID.Player) {
                if (key == KeyEvent.VK_D) keyDown[0]=false;
                if (key == KeyEvent.VK_A) keyDown[1]=false;
//                if (key == KeyEvent.VK_W) keyDown[2]=false;
//                if (key == KeyEvent.VK_S) keyDown[3]=false;

                if (!keyDown[0] && !keyDown[1]) tempObject.setVelX(0);
//                if (!keyDown[2] && !keyDown[3]) tempObject.setVelY(0);
            }
        }
    }
}
