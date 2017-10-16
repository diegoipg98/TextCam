import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import org.bytedeco.javacpp.opencv_core.IplImage;
import org.bytedeco.javacv.FFmpegFrameRecorder;

public class Recorder {
	public static String extension = null;

	public static void Record(List<String> charFrames, double fps, double frameLength) {
		FFmpegFrameRecorder recorder = new FFmpegFrameRecorder(Main.VIDEO, Main.imgx, Main.imgy);
		Window.lblNewLabel.setText("Overall process: 67.81%");
		Window.progressBar.setValue(68);
		Main.win.setWindowTitle("67.81% - ");
		Window.lblNewLabel_1.setText("Rendering video:  0.0%");
		Window.progressBar_1.setValue(0);
		try {
			if (Window.checkbox) {
				recorder.setVideoQuality(17);
			}else{
				recorder.setVideoQuality(12.5);
			}
			recorder.setVideoCodec(13);
			if (extension == null) extension = "mp4";
			recorder.setFormat(extension);
			recorder.setPixelFormat(0);
			recorder.setFrameRate(fps);
			recorder.start();
			System.out.println();
			for (int i = 0; i < frameLength; i++) {
				if(Window.cancel){
					Main.close();
					return;
				}
				recorder.record(IplImage.createFrom(ImageIO.read(new File(charFrames.get(i)))),
						org.bytedeco.javacpp.avutil.AV_PIX_FMT_RGB32_1);
				double percentage = (double) ((int) Math.round(((double) (i + 1) / (double) (frameLength)) * 10000)) / 100;
				StringBuilder sb1 = new StringBuilder();
				sb1.append("Overall process: ");
				sb1.append(((double)(Math.round((percentage*0.3219+67.81)*100)))/100);
				sb1.append("%");
				Window.lblNewLabel.setText(sb1.toString());
				sb1 = new StringBuilder();
				sb1.append(((double)(Math.round((percentage*0.3219+67.81)*100)))/100);
				sb1.append("% - ");
				Main.win.setWindowTitle(sb1.toString());
				sb1 = new StringBuilder();
				sb1.append("Rendering video: ");
				sb1.append(percentage);
				sb1.append("%");
				Window.lblNewLabel_1.setText(sb1.toString());
				Window.progressBar.setValue((int) Math.round(percentage*0.3219+67.81));
				Window.progressBar_1.setValue((int) percentage);
			}
			recorder.stop();
		} catch (Exception e) {
			e.printStackTrace();
		}
		merge();
	}

	private static void merge() {
		if(Main.replaceFile && (new File(Main.outputPath).exists() && !new File(Main.outputPath).isDirectory())){
			new File(Main.outputPath).delete();
		}
		StringBuilder sb1 = new StringBuilder();
		sb1.append("ffmpeg.exe -i ");
		sb1.append(Main.AUDIO);
		sb1.append(" -i ");
		sb1.append(Main.VIDEO);
		sb1.append(" -acodec copy -vcodec copy ");
		sb1.append(Main.outputPath);
		try {
			Window.textArea.append("Merging audio and video...");
			Window.textArea.append(System.getProperty("line.separator"));
			Window.textArea.setCaretPosition(Window.textArea.getDocument().getLength());
			System.out.println(sb1.toString());
			Runtime.getRuntime().exec(sb1.toString());
			Window.textArea.append("Audio and video Merged.");
			Window.textArea.append(System.getProperty("line.separator"));
			Window.textArea.setCaretPosition(Window.textArea.getDocument().getLength());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}