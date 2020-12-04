import javax.sound.sampled.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class SoundPlay
{
    private Clip clip;
    private AudioInputStream audioInputStream;
    private ByteArrayInputStream bis;

    public SoundPlay(ByteArrayInputStream bis)  throws UnsupportedAudioFileException, IOException, LineUnavailableException
    {
        this.bis = bis;
        audioInputStream = AudioSystem.getAudioInputStream(this.bis);
        clip = AudioSystem.getClip();
        clip.open(audioInputStream);
        clip.loop(0);
    }

    public void play()
    {
        clip.start();
    }

    public void stop() {
        clip.stop();
        clip.close();
    }

}
