package com.amaraa.main.game.menu;

import com.amaraa.main.game.Game;
import com.amaraa.main.game.Handler;
import com.amaraa.main.game.objects.ID;
import com.amaraa.main.game.objects.entities.Player;
import com.amaraa.main.mydbmanager.MyDbManager;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import static com.sun.java.accessibility.util.AWTEventMonitor.addKeyListener;

public class Menu extends MouseAdapter {



    private Game game;
    private Handler handler;

    private String text = "";

    public Menu(Game game, Handler handler) {
        this.game = game;
        this.handler = handler;



    }

    public void mousePressed(MouseEvent e) {

        int my = e.getY();
        int mx = e.getX();

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
        if (game.gameState == Game.STATE.GameSettings){
            //TODO: DIFFICULTIES
            // MOUSE OVER EASY

            // MOUSE OVER MEDIUM

            // MOUSE OVER HARD

            // MOUSE OVER START GAME
        }


    }


    public void mouseReleased(MouseEvent e) {
        super.mouseReleased(e);
    }



    public void tick(){

        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                System.out.println("Pressed");
            }
        });

    }



    private boolean mouseOver(int mx, int my, int x, int y, int width, int height){
        if (mx > x && mx < x + width){
            return my > y && my < y + height;
        }else return false;
    }

    public void mouseEntered(MouseEvent e) {
        int mx = e.getX();
        int my = e.getY();
        if (game.gameState == Game.STATE.Menu) {
            // MOUSE OVER PLAYER
            if (mouseOver(mx, my, Game.WIDTH / 2 - 100, 100, 200, 50)) {
                System.out.println(e);
            }
        }
    }


    public void render(Graphics g){
        Color swYellow = new Color(255,215,0);
        if (game.gameState == Game.STATE.Menu){
            g.setColor(swYellow);
            Font fnt = new Font("arial", Font.BOLD, 30);
            g.setFont(fnt);
            g.drawString("MAIN MENU", Game.WIDTH / 2 - 88, 50);

            Font fnt2 = new Font("arial", Font.BOLD, 25);
            g.setFont(fnt2);

            //PLAY BUTTON
            g.drawRect(Game.WIDTH / 2 - 100, 150, 200,50);
            g.drawString("PLAY", Game.WIDTH / 2 - 32,185);



            //SCORES BUTTON
            g.drawRect(Game.WIDTH / 2 - 100, 225, 200,50);
            g.drawString("SCORES", Game.WIDTH / 2 - 50, 260);


            //QUIT BUTTON
            g.drawRect(Game.WIDTH / 2 - 100, 300, 200,50);
            g.drawString("QUIT", Game.WIDTH / 2 - 30, 335);


        //SCORE STATE
        }else if(game.gameState == Game.STATE.Scores){
            g.setColor(swYellow);
            Font fnt = new Font("arial", Font.BOLD, 30);
            g.setFont(fnt);
            g.drawString("SCORES", Game.WIDTH / 2 - 65, 50);

            Font fnt2 = new Font("arial", Font.BOLD, 20);
            g.setFont(fnt2);



            //TODO: SELECT THE SCORES
            ArrayList<String> scores = MyDbManager.selectScores();
            for (int i = 0; i < scores.size(); i++) {
                if (i >= 9){
                    g.drawString((i+1) + ". " + scores.get(i), Game.WIDTH / 2 - 40, 80 + i*24);
                }else if (i <= 2){
                    g.setColor(Color.green);
                    g.drawString((i + 1) + ". " + scores.get(i), Game.WIDTH / 2 - 30, 80 + i * 24);
                }else{
                    g.setColor(swYellow);
                    g.drawString((i + 1) + ". " + scores.get(i), Game.WIDTH / 2 - 30, 80 + i * 24);
                }
            }
            //BACK BUTTON
            g.drawRect(Game.WIDTH - 125, Game.HEIGHT - 100, 100,40);
            g.drawString("BACK", Game.WIDTH - 105, Game.HEIGHT - 73);

        }

    }

}
