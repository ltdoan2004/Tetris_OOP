package Src.Main;

import Mino.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class PlayManager {
    //Main Play Area
    int WIDTH = 360;
    int HEIGHT = 600;
    public static int left_x;
    public static int right_x;
    public static int top_y;
    public static int bottom_y;
    //Mino
    Mino currentMino;
    final int MINO_START_X;
    final int MINO_START_Y;
    Mino nextMino;
    final int NEXTMINO_X;
    final int NEXTMINO_Y;
    public static ArrayList<Block> staticBlocks = new ArrayList<>();
    //Others
    public static int dropInterval = 60;    //Mino drop in every 60 frames
    boolean gameOver;
    //Effect
    boolean effectCounterOn;
    int effectCounter;
    ArrayList<Integer> effectY = new ArrayList<Integer>();
    //Score
    int level = 1;
    int lines;
    int score;

    public PlayManager() {
        //Main Play Area Frame
        left_x = (GamePanel.WIDTH / 2) - (WIDTH / 2);   //1280/2 - 360/2 = 460
        right_x = left_x + WIDTH;
        top_y = 50;
        bottom_y = top_y + HEIGHT;

        MINO_START_X = left_x + (WIDTH / 2) - Block.SIZE;
        MINO_START_Y = top_y + Block.SIZE;

        NEXTMINO_X = left_x - 215;
        NEXTMINO_Y = top_y + 150;

        //Set the starting Mino
        currentMino = pickMino();
        currentMino.setXY(MINO_START_X, MINO_START_Y);
        nextMino = pickMino();
        nextMino.setXY(NEXTMINO_X, NEXTMINO_Y);
    }

    //Pick a random Mino
    private Mino pickMino() {
        Mino mino = null;
        int i = new Random().nextInt(7);
        switch (i) {
            case 0: mino = new Mino_L1();break;
            case 1: mino = new Mino_L2();break;
            case 2: mino = new Mino_Square();break;
            case 3: mino = new Mino_I();break;
            case 4: mino = new Mino_T();break;
            case 5: mino = new Mino_Z1();break;
            case 6: mino = new Mino_Z2();break;
        }
        return mino;
    }

    public void update() {
        //Check if the current Mino is active
        if(currentMino.active == false) {
            //If the mino is not active, put it into the staticBlocks
            staticBlocks.add(currentMino.b[0]);
            staticBlocks.add(currentMino.b[1]);
            staticBlocks.add(currentMino.b[2]);
            staticBlocks.add(currentMino.b[3]);
            //Check if the game over or not
            if (currentMino.b[0].x == MINO_START_X && currentMino.b[0].y == MINO_START_Y) {
                gameOver = true;
                GamePanel.music.stop();
                GamePanel.se.play(2, false);
            }

            currentMino.sliding = false;
            //Replace Mino
            currentMino = nextMino;
            currentMino.setXY(MINO_START_X, MINO_START_Y);
            nextMino = pickMino();
            nextMino.setXY(NEXTMINO_X, NEXTMINO_Y);
            //Check line can be delete or not
            checkDelete();
        }
        else {
            currentMino.update();
        }
    }

    private void checkDelete() {
        int x = left_x;
        int y = top_y;
        int blockCount = 0;
        int lineCount = 0;

        while (x < right_x && y < bottom_y) {
            for (int i = 0; i < staticBlocks.size(); i++) {
                if (staticBlocks.get(i).x == x && staticBlocks.get(i).y == y) {
                    //Increase the count if there is a static block
                    blockCount++;
                }
            }
            x += Block.SIZE;
            if (x == right_x) {
                //Delete line
                if (blockCount == 12){
                    effectCounterOn = true;
                    effectY.add(y);
                    for (int i = staticBlocks.size()-1; i > -1; i--) {
                        if (staticBlocks.get(i).y == y) {
                            staticBlocks.remove(i);
                        }
                    }

                    lineCount++;
                    lines++;
                    //Drop Speed
                    if (lines % 10 == 0 && dropInterval > 1) {
                        level++;
                        if (dropInterval > 10) {
                            dropInterval -= 10;
                        } else {
                            dropInterval -= 1;
                        }
                    }

                    //Slide down the above line
                    for (int i = 0; i < staticBlocks.size(); i++) {
                        if (staticBlocks.get(i).y < y) {
                            staticBlocks.get(i).y += Block.SIZE;
                        }
                    }
                }
                blockCount = 0;
                x = left_x;
                y += Block.SIZE;
            }
        }
        //Add score
        if (lineCount == 1) {
            GamePanel.se.play(1, false);
            int singleLineScore = 10 * level;
            score += singleLineScore * lineCount;
        }
        if (lineCount > 1) {
            GamePanel.se.play(1, false);
            int LineScore = 15 * level;
            score += LineScore * lineCount;
        }
    }

    public void draw(Graphics2D g2) {
        //Draw Play Area Frame
        g2.setColor(Color.white);
        g2.setStroke(new BasicStroke(4f));
        g2.drawRect(left_x - 4, top_y - 4, WIDTH + 8, HEIGHT + 8);

        //Draw Next Mino Frame
        int x = left_x - 300;
        int y = top_y;
        g2.drawRect(x, y, 200, 300);
        g2.setFont(new Font("Arial", Font.PLAIN, 30));
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.drawString("NEXT", x + 60, y + 60);

        //Draw Score Frame
        x = right_x + 100;
        y = bottom_y - 200;
        g2.drawRect(x, top_y, 250, 300);
        x += 40;
        y = top_y + 90;
        g2.drawString("LEVEL: " + level, x, y);y += 70;
        g2.drawString("LINES: " + lines, x, y);y += 70;
        g2.drawString("SCORE: " + score, x, y);

        //Draw the current Mino
        if (currentMino != null) {
            currentMino.draw(g2);
        }
        //Draw the nextMino
        nextMino.draw(g2);

        //Draw Static Blocks
        for (int i = 0; i < staticBlocks.size(); i++) {
            staticBlocks.get(i).draw(g2);
        }

        //Draw Effect
        if (effectCounterOn) {
            effectCounter++;
            g2.setColor(Color.CYAN);
            for (int i = 0; i < effectY.size(); i++) {
                g2.fillRect(left_x, effectY.get(i), WIDTH, Block.SIZE);
            }
            if (effectCounter == 15) {
                effectCounterOn = false;
                effectCounter = 0;
                effectY.clear();
            }
        }

        //Draw Pause
        if (KeyHandler.pausePressed) {
            g2.setColor(Color.DARK_GRAY);
            g2.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
            g2.setColor(Color.yellow);
            g2.setFont(g2.getFont().deriveFont(50f));
            if (KeyHandler.pausePressed) {
                x = left_x + 70;
                y = top_y + 320;
                g2.drawString("PAUSED", x, y);
            }
        }

        //Draw Game Over
        if (gameOver) {
            g2.setColor(Color.yellow);
            g2.setFont(g2.getFont().deriveFont(50f));
            x = left_x + 25;
            y = top_y + 320;
            g2.drawString("GAME OVER", x, y);
        }
    }
}
