package Src;

import Src.controller.Direction;
import Src.controller.GameController;
import Src.controller.PaintController;
import Src.view.GamePanel;
import Src.view.LeftPanel;
import Src.view.RightPanel;
import controller.Sound;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Start extends JFrame {
    private GameController gameController = GameController.getINSTANCE();
    private GamePanel gamePanel = GamePanel.getINSTANCE();
    private RightPanel rightPanel = RightPanel.getINSTANCE();
    private LeftPanel leftPanel = LeftPanel.getINSTANCE();
    public static Sound music = new Sound();


    public Start() {
        setLayout(new BorderLayout());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        add(gamePanel, BorderLayout.CENTER);
        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.EAST);
        pack();
        setLocationRelativeTo(null);

        setVisible(true);
        music.play(0,true);

        addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent e) {

                switch (e.getKeyCode()) {
                    case KeyEvent.VK_LEFT:
                        if (gameController.canMoveShape(Direction.LEFT, gameController.getCurrentShape()))
                            gameController.move(Direction.LEFT, gameController.getCurrentShape());
                        gamePanel.repaint();

                        break;
                    case KeyEvent.VK_RIGHT:
                        if (gameController.canMoveShape(Direction.RIGHT, gameController.getCurrentShape()))
                            gameController.move(Direction.RIGHT, gameController.getCurrentShape());
                        gamePanel.repaint();

                        break;
                    case KeyEvent.VK_DOWN:
                        if (gameController.canShapeStepDown(gameController.getCurrentShape())) {
                            gameController.stepDownShape(gameController.getCurrentShape());
                        }
                        gamePanel.repaint();

                        break;
                    case KeyEvent.VK_SPACE:
                        while (gameController.canShapeStepDown(gameController.getCurrentShape())) {
                            gameController.stepDownShape(gameController.getCurrentShape());
                            gamePanel.repaint();
                        }
                        music.play(1,true);
                        break;
                    case KeyEvent.VK_UP:
                        gameController.rotate(gameController.getCurrentShape());
                        gamePanel.repaint();
                        break;
                    case KeyEvent.VK_P:
                        if (gameController.isGameRunning()) {
                            gameController.setPaused(!gameController.isPaused());
                        }
                        break;
                    case KeyEvent.VK_R:
                        if (gameController.isGameRunning()) {
                            gamePanel.getTimer().setDelay(gameController.getGameSpeed());
                            gameController.resetGame();
                            gameController.setGameRunning(true);
                            gamePanel.repaint();
                            leftPanel.repaint();
                            rightPanel.repaint();
                            PaintController.getINSTANCE().setShowRecordNameInput(false);
                            music.play(4,true);
                        }
                        break;
                    case KeyEvent.VK_ENTER:
                        music.play(3,true);
                        if (!gameController.isGameRunning()) {
                            gameController.resetGame();
                            gameController.setGameRunning(true);
                            leftPanel.repaint();
                            gamePanel.repaint();
                        } else if (gameController.isGameOver()) {
                            gameController.resetGame();
                            leftPanel.repaint();
                            rightPanel.repaint();
                            gamePanel.repaint();
                            PaintController.getINSTANCE().setShowRecordNameInput(false);
                        }
                        break;
                }
            }

//            @Override
//            public void keyReleased(KeyEvent e) {
//                switch (e.getKeyCode()) {
//                    case KeyEvent.VK_DOWN:
//                        gamePanel.getTimer().setDelay(gameController.getGameSpeed()
//                                + gameController.getAcceleration() - gameController.getAcceleration() * gameController.getLevel());
//                        break;
//                }
//            }
        });
    }

    public static void main(String[] args) {
        Start start = new Start();

    }
}
