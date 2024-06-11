package controller;

import javax.sound.sampled.*;
import java.net.URL;

public class Sound {
    Clip musicClip;
    private static URL[] url = new URL[10];
    //Constructor
    public Sound() {
        url[0] = getClass().getResource("/background.wav");
        url[1] = getClass().getResource("/delete_line.wav");
        url[2] = getClass().getResource("/gameover.wav");
        url[3] = getClass().getResource("/rotation.wav");
        url[4] = getClass().getResource("/touch_floor.wav");
    }
    //Method
    public void play(int i, boolean music) {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(url[i]);
            Clip clip = AudioSystem.getClip();

            if (music) {
                musicClip = clip;
            }

            clip.open(ais);
            clip.addLineListener(new LineListener() {
                public void update(LineEvent event) {
                    if (event.getType() == LineEvent.Type.STOP) {
                        clip.close();
                    }
                }
            });
            ais.close();
            clip.start();
        }
        catch (Exception e) {

        }
    }


    public void loop() {
        musicClip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop() {
        musicClip.stop();
        musicClip.close();
    }
//    public void main

}
