package Src.Mino;

import java.awt.*;

public class Mino_I extends Mino {
    public Mino_I() {
        create(Color.PINK);
    }

    @Override
    public void setXY(int x, int y) {
//        o     b[1]
//        o     b[0]
//        o     b[2]
//        o     b[3]
        super.setXY(x, y);
        b[0].x = x;
        b[0].y = y;
        b[1].x = b[0].x;
        b[1].y = b[0].y - Block.SIZE;
        b[2].x = b[0].x;
        b[2].y = b[0].y + Block.SIZE;
        b[3].x = b[0].x;
        b[3].y = b[0].y + Block.SIZE * 2;
    }
    public void getDirection1() {
//        o     b[1]
//        o     b[0]
//        o     b[2]
//        o     b[3]
        tempB[0].x = b[0].x;
        tempB[0].y = b[0].y;
        tempB[1].x = b[0].x;
        tempB[1].y = b[0].y - Block.SIZE;
        tempB[2].x = b[0].x;
        tempB[2].y = b[0].y + Block.SIZE;
        tempB[3].x = b[0].x;
        tempB[3].y = b[0].y + Block.SIZE * 2;
        updateXY(1);
    }
    public void getDirection2() {
//
//        o o o o    b[1] b[0] b[2] b[3]
//
        tempB[0].x = b[0].x;
        tempB[0].y = b[0].y;
        tempB[1].x = b[0].x - Block.SIZE;
        tempB[1].y = b[0].y;
        tempB[2].x = b[0].x + Block.SIZE;
        tempB[2].y = b[0].y;
        tempB[3].x = b[0].x + Block.SIZE * 2;
        tempB[3].y = b[0].y;
        updateXY(2);
    }
    public void getDirection3() {
//        o     b[1]
//        o     b[0]
//        o     b[2]
//        o     b[3]
        tempB[0].x = b[0].x;
        tempB[0].y = b[0].y;
        tempB[1].x = b[0].x;
        tempB[1].y = b[0].y - Block.SIZE;
        tempB[2].x = b[0].x;
        tempB[2].y = b[0].y + Block.SIZE;
        tempB[3].x = b[0].x;
        tempB[3].y = b[0].y + Block.SIZE * 2;
        updateXY(3);
    }
    public void getDirection4() {
//
//        o o o o    b[1] b[0] b[2] b[3]
//
        tempB[0].x = b[0].x;
        tempB[0].y = b[0].y;
        tempB[1].x = b[0].x - Block.SIZE;
        tempB[1].y = b[0].y;
        tempB[2].x = b[0].x + Block.SIZE;
        tempB[2].y = b[0].y;
        tempB[3].x = b[0].x + Block.SIZE * 2;
        tempB[3].y = b[0].y;
        updateXY(4);
    }
}
