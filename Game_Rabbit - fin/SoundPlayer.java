import java.io.BufferedInputStream;
import java.util.HashMap;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 *  class SoundPlayer 
 *
 * @author 
 * @version 
 */

public class SoundPlayer {

    private static HashMap<String, Clip> clips = new HashMap<String, Clip>();

    public SoundPlayer() {
    }

    public static void init() {
        loadSound("come-on", "/sound/come-on.wav");
        loadSound("maybe-next-time", "/sound/maybe-next-time.wav");
        loadSound("nice-work", "/sound/nice-work.wav");
        loadSound("oh-my-god", "/sound/oh-my-god.wav");
        loadSound("over-her", "/sound/over-her.wav");
        loadSound("this-is-delicious", "/sound/this-is-delicious.wav");
        loadSound("gunshot", "/sound/gunshot.wav");
}

    /**
     * WAV files only
     * 
     * @param name
     *            Name to store sound as
     * @param file
     *            Sound file
     */
    public static void loadSound(String name, String file) {
        try {
            System.out.print("Loading sound file: \"" + file + "\" into clip: \"" + name + "\", ");
            BufferedInputStream in = new BufferedInputStream(SoundPlayer.class.getResourceAsStream(file));
            AudioInputStream ain = AudioSystem.getAudioInputStream(in);

            Clip c = AudioSystem.getClip();
            c.open(ain);
            c.setLoopPoints(0, -1);
            clips.put(name, c);
            ain.close();
            in.close();
            System.out.println("Done.");
        } catch (Exception e) {
            System.out.println("Failed. (" + e.getMessage() + ")");
        }
    }

    public static boolean play(String name) {
        if (clips.containsKey(name)) {
            clips.get(name).setFramePosition(0);
            clips.get(name).start();
            return true;
        }
        return false;
    }

    public static boolean stop(String name) {
        if (clips.containsKey(name) && clips.get(name).isActive()) {
            clips.get(name).stop();
            clips.get(name).setFramePosition(0);
            return true;
        }
        return false;
    }

    public static boolean isPlaying(String name) {
        if (clips.containsKey(name) && clips.get(name).isRunning()) {
            return true;
        }
        return false;
    }

    public long getPosition(String name) {
        if (clips.containsKey(name) && clips.get(name).isRunning()) {
            return clips.get(name).getLongFramePosition();
        }
        return -1;
    }

}