package com.amaraa.main.game.states;

import com.amaraa.main.Window;
import com.amaraa.main.game.Game;
import com.amaraa.main.game.Handler;
import com.amaraa.main.game.hud.HUD;
import com.amaraa.main.game.objects.ID;
import com.amaraa.main.game.objects.entities.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class Menu extends MouseAdapter {

    private Color swYellow = new Color(255,215,0);

    private Game game;
    private Handler handler;

    private int my;
    private int mx;

    public Menu(Game game, Handler handler) {
        this.game = game;
        this.handler = handler;
    }

    public void mousePressed(MouseEvent e) {

        my = e.getY();
        mx = e.getX();

        if (game.gameState == Game.STATE.Menu){
            // MOUSE OVER PLAYER
            if (mouseOver(mx,my,Game.WIDTH / 2 - 100, 100, 200,50)){
                game.gameState = Game.STATE.Game;
                handler.addObject(new Player(Game.WIDTH / 2 - 32, Game.HEIGHT / 5 * 4 - 32, ID.Player, handler));

                System.out.println("GameState changed.");
            }
            // MOUSE OVER SCORES
            if (mouseOver(mx,my,Game.WIDTH / 2 - 100, 175, 200,50)){
                System.out.println("Over SCORES button.");
                game.gameState = Game.STATE.Scores;
            }
            // MOUSE OVER QUIT
            if (mouseOver(mx,my,Game.WIDTH / 2 - 100, 250, 200,50)){
                System.out.println("Close the window.");
                System.exit(0);
            }
        }
        if (game.gameState == Game.STATE.Scores){
            // MOUSE OVER BACK
            if (mouseOver(mx,my,Game.WIDTH - 125, Game.HEIGHT - 100, 100,40)){
                game.gameState = Game.STATE.Menu;
            }
        }


    }


    public void mouseReleased(MouseEvent e) {
        super.mouseReleased(e);
    }

    private boolean mouseOverPlay;
    public void tick(){
        if (mouseOver(mx,my,Game.WIDTH / 2 - 100, 100, 200,50)){
            mouseOverPlay = true;
        }else mouseOverPlay = false;

    }


    private boolean mouseOver(int mx, int my, int x, int y, int width, int height){
        if (mx > x && mx < x + width){
            if (my > y && my < y + height){
                return true;
            }else return false;
        }else return false;
    }
    public void render(Graphics g){
        if (game.gameState == Game.STATE.Menu){
            g.setColor(swYellow);
            Font fnt = new Font("arial", Font.BOLD, 30);
            g.setFont(fnt);
            g.drawString("MENU", Game.WIDTH / 2 - 44, 50);

            Font fnt2 = new Font("arial", Font.BOLD, 25);
            g.setFont(fnt2);

            //PLAY BUTTON
            g.drawRect(Game.WIDTH / 2 - 100, 100, 200,50);
            g.drawString("PLAY", Game.WIDTH / 2 - 32,135);



            //SCORES BUTTON
            g.drawRect(Game.WIDTH / 2 - 100, 175, 200,50);
            g.drawString("SCORES", Game.WIDTH / 2 - 50, 210);


            //QUIT BUTTON
            g.drawRect(Game.WIDTH / 2 - 100, 250, 200,50);
            g.drawString("QUIT", Game.WIDTH / 2 - 30, 285);
        }else if(game.gameState == Game.STATE.Scores){
            g.setColor(swYellow);
            Font fnt = new Font("arial", Font.BOLD, 30);
            g.setFont(fnt);
            g.drawString("SCORES", Game.WIDTH / 2 - 65, 50);

            Font fnt2 = new Font("arial", Font.BOLD, 20);
            g.setFont(fnt2);



            //TODO: SELECT THE SCORES
            ArrayList<Integer> scores = new ArrayList<>();
            scores.add(20);
            scores.add(20);
            scores.add(20);
            scores.add(20);
            scores.add(20);
            scores.add(20);
            scores.add(20);
            scores.add(20);
            scores.add(20);
            scores.add(20);
            scores.add(20);
            scores.add(20);
            scores.add(20);
            for (int i = 1; i < 11; i++) {
                g.drawString(i + ". " + scores.get(i-1), Game.WIDTH / 2 - 30, 60 + i*24);
            }
            //BACK BUTTON
            g.drawRect(Game.WIDTH - 125, Game.HEIGHT - 100, 100,40);
            g.drawString("BACK", Game.WIDTH - 105, Game.HEIGHT - 73);

        }

    }

}
