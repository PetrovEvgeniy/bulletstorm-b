package abstracts;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class A_SoundSystem {
    private Map<String, Clip> soundClips;

    public A_SoundSystem() {
        soundClips = new HashMap<>();
    }

    public void loadSound(String soundName, String soundFilePath) {
        try {
            // Create a File object for the sound file
            File soundFile = new File(soundFilePath);

            // Create an AudioInputStream from the sound file
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);

            // Get the audio format of the sound file
            AudioFormat audioFormat = audioInputStream.getFormat();

            // Create a DataLine.Info object for the source data line
            DataLine.Info dataLineInfo = new DataLine.Info(Clip.class, audioFormat);

            // Open the clip
            Clip clip = (Clip) AudioSystem.getLine(dataLineInfo);
            clip.open(audioInputStream);

            // Store the clip in the soundClips map
            soundClips.put(soundName, clip);
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            e.printStackTrace();
        }
    }

    public void playSound(String soundName) {
        Clip clip = soundClips.get(soundName);
        if (clip != null) {
            if (clip.isRunning() && !soundName.equals("explosion")) {
                clip.stop();
            }
            clip.setFramePosition(0);
            clip.start();
        }
    }

    public void stopAllSounds() {
        for (Clip clip : soundClips.values()) {
            if (clip.isRunning()) {
                clip.stop();
            }
        }
    }
}