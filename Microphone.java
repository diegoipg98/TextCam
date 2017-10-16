import java.io.File;
import it.sauronsoftware.jave.*;

public class Microphone extends Thread {
	public void run() {
		Window.textArea.append("Capturing audio...");
		Window.textArea.append(System.getProperty("line.separator"));
		Window.textArea.setCaretPosition(Window.textArea.getDocument().getLength());
		File audioFile = new File(Main.AUDIO);
		AudioAttributes audio = new AudioAttributes();
		audio.setCodec("libmp3lame");
		audio.setBitRate(128000);
		audio.setChannels(2);
		audio.setSamplingRate(44100);
		EncodingAttributes attr = new EncodingAttributes();
		attr.setFormat("mp3");
		attr.setAudioAttributes(audio);
		Encoder ncd1 = new Encoder();
		try {
			ncd1.encode(new File(Main.inputPath), audioFile, attr);
		} catch (IllegalArgumentException | EncoderException e) {
			e.printStackTrace();
		}
		Window.textArea.append("Audio captured.");
		Window.textArea.append(System.getProperty("line.separator"));
		Window.textArea.setCaretPosition(Window.textArea.getDocument().getLength());
	}
}