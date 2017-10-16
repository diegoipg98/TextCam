import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.FrameGrabber.Exception;

public class Main {
	private static StringBuilder sb1;
	public static final String IMAGE = "charFrames/image";
	public static final String AUDIO = "audio/outputaudio.mp3";
	public static final String VIDEO = "noAudioVideo/video.mp4";
	public static String outputPath;
	public static String inputPath;
	public static boolean inputExists;
	public static boolean outputExists;
	public static boolean inWasOpened;
	public static boolean outWasOpened;
	public static boolean doRun;
	public static boolean ErrorWindowButton;
	public static boolean replaceFile;
	public static Window win;
	public static int imgx;
	public static int imgy;

	public static void main(String[] args) {

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e1) {
			e1.printStackTrace();
		}
		win = new Window();
		win.setVisible(true);
		while (true) {
			replaceFile = true;
			doRun = false;
			ErrorWindowButton = false;
			while (!doRun) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			run();
		}
	}

	public static void run() {
		if (outputExists) {
			if (new File(Main.outputPath).exists() && !new File(Main.outputPath).isDirectory()) {
				StringBuilder sb1 = new StringBuilder();
				sb1.append(
						"<html>The output path selected already contains a file.<br>Would you like to continue?</html>");
				YesOrNoWindow yon = new YesOrNoWindow(sb1.toString());
				yon.setVisible(true);
				while (yon.isVisible()) {
					try {
						Thread.sleep(100);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
				}
			}
		}
		if ((inputExists && outputExists) && replaceFile) {
			win.extend();
			Window.btnNewButton.setVisible(true);
			Window.start.setVisible(false);
			sb1 = new StringBuilder();
			FFmpegFrameGrabber splitter = new FFmpegFrameGrabber(inputPath);
			try {
				splitter.start();
			} catch (Exception e) {
				e.printStackTrace();
			}
			double timeLength = splitter.getLengthInTime() / 1000000;
			double frameLength = splitter.getLengthInFrames() - 1;
			double fps = frameLength / timeLength;
			Microphone mic1 = new Microphone();
			mic1.start();
			Window.lblNewLabel.setText("Overall process: 0.0%");
			Window.progressBar.setValue(0);
			win.setWindowTitle("0.0% - ");
			Window.lblNewLabel_1.setText("Capturing frames:  0.0%");
			Window.progressBar_1.setValue(0);
			for (int i = 0; i < frameLength; i++) {
				sb1 = new StringBuilder();
				sb1.append("frames/video-frame-");
				sb1.append(i);
				sb1.append(".jpg");
				try {
					ImageIO.write(splitter.grab().getBufferedImage(), "jpg", new File(sb1.toString()));
				} catch (IOException | Exception e) {
					e.printStackTrace();
				}
				double percentage = (double) ((int) Math.round(((double) (i + 1) / (double) (frameLength)) * 10000))
						/ 100;
				sb1 = new StringBuilder();
				sb1.append("Overall process: ");
				sb1.append(((double) (Math.round(percentage * 0.1554 * 100))) / 100);
				sb1.append("%");
				Window.lblNewLabel.setText(sb1.toString());
				sb1 = new StringBuilder();
				sb1.append(((double) (Math.round(percentage * 0.1554 * 100))) / 100);
				sb1.append("% - ");
				win.setWindowTitle(sb1.toString());
				sb1 = new StringBuilder();
				sb1.append("Capturing frames: ");
				sb1.append(percentage);
				sb1.append("%");
				Window.lblNewLabel_1.setText(sb1.toString());
				Window.progressBar.setValue((int) Math.round(percentage * 0.1554));
				Window.progressBar_1.setValue((int) percentage);
				if (Window.cancel) {
					close();
					return;
				}
			}
			try {
				splitter.stop();
			} catch (Exception e) {
				e.printStackTrace();
			}
			List<String> charFrames;
			if (Window.checkbox) {
				int threads = (int) (Runtime.getRuntime().availableProcessors() * (1.0));
				ColoredPainter[] ptr = new ColoredPainter[threads];
				int threadsToDo;
				try {
					mic1.join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				Window.lblNewLabel.setText("Overall process: 15.54%");
				Window.progressBar.setValue(16);
				win.setWindowTitle("15.54% - ");
				Window.lblNewLabel_1.setText("Processing frames: 0.0%");
				Window.progressBar_1.setValue(0);
				for (int i = 0; i < frameLength; i += threads) {
					if (frameLength - i < threads) {
						threadsToDo = (int) (frameLength - i);
					} else {
						threadsToDo = threads;
					}
					for (int b = 0; b < threadsToDo; b++) {
						if (Window.cancel) {
							close();
							return;
						}
						sb1 = new StringBuilder();
						sb1.append("frames");
						sb1.append(System.getProperty("file.separator"));
						sb1.append("video-frame-");
						sb1.append((b + i));
						sb1.append(".jpg");
						ptr[b] = new ColoredPainter(sb1.toString(), b + i);
					}
					for (int b = 0; b < threadsToDo; b++) {
						if (Window.cancel) {
							close();
							return;
						}
						ptr[b].start();
					}
					for (int b = 0; b < threadsToDo; b++) {
						try {
							ptr[b].join();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						if (Window.cancel) {
							close();
							return;
						}
					}
					double percentage = (double) ((int) Math
							.round(((double) (i + threadsToDo) / (double) (frameLength)) * 10000)) / 100;
					sb1 = new StringBuilder();
					sb1.append("Overall process: ");
					sb1.append(((double) (Math.round((percentage * 0.5227 + 15.54) * 100))) / 100);
					sb1.append("%");
					Window.lblNewLabel.setText(sb1.toString());
					sb1 = new StringBuilder();
					sb1.append(((double) (Math.round((percentage * 0.5227 + 15.54) * 100))) / 100);
					sb1.append("% - ");
					win.setWindowTitle(sb1.toString());
					sb1 = new StringBuilder();
					sb1.append("Processing frames: ");
					sb1.append(percentage);
					sb1.append("%");
					Window.lblNewLabel_1.setText(sb1.toString());
					Window.progressBar.setValue((int) Math.round(percentage * 0.5227 + 15.54));
					Window.progressBar_1.setValue((int) percentage);
				}
				charFrames = new ArrayList<String>();
				for (int i = 0; i < frameLength; i++) {
					if (Window.cancel) {
						close();
						return;
					}
					sb1 = new StringBuilder();
					sb1.append(Main.IMAGE);
					sb1.append(i);
					sb1.append(".jpg");
					charFrames.add(sb1.toString());
					imgx = ColoredPainter.imgx;
					imgy = ColoredPainter.imgy;
				}
			} else {
				int threads = (int) (Runtime.getRuntime().availableProcessors() * (1.0));
				Painter[] ptr = new Painter[threads];
				int threadsToDo;
				try {
					mic1.join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				Window.lblNewLabel.setText("Overall process: 15.54%");
				Window.progressBar.setValue(16);
				win.setWindowTitle("15.54% - ");
				Window.lblNewLabel_1.setText("Processing frames: 0.0%");
				Window.progressBar_1.setValue(0);
				for (int i = 0; i < frameLength; i += threads) {
					if (frameLength - i < threads) {
						threadsToDo = (int) (frameLength - i);
					} else {
						threadsToDo = threads;
					}
					for (int b = 0; b < threadsToDo; b++) {
						if (Window.cancel) {
							close();
							return;
						}
						sb1 = new StringBuilder();
						sb1.append("frames");
						sb1.append(System.getProperty("file.separator"));
						sb1.append("video-frame-");
						sb1.append((b + i));
						sb1.append(".jpg");
						ptr[b] = new Painter(sb1.toString(), b + i);
					}
					for (int b = 0; b < threadsToDo; b++) {
						if (Window.cancel) {
							close();
							return;
						}
						ptr[b].start();
					}
					for (int b = 0; b < threadsToDo; b++) {
						try {
							ptr[b].join();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						if (Window.cancel) {
							close();
							return;
						}
					}
					double percentage = (double) ((int) Math
							.round(((double) (i + threadsToDo) / (double) (frameLength)) * 10000)) / 100;
					sb1 = new StringBuilder();
					sb1.append("Overall process: ");
					sb1.append(((double) (Math.round((percentage * 0.5227 + 15.54) * 100))) / 100);
					sb1.append("%");
					Window.lblNewLabel.setText(sb1.toString());
					sb1 = new StringBuilder();
					sb1.append(((double) (Math.round((percentage * 0.5227 + 15.54) * 100))) / 100);
					sb1.append("% - ");
					win.setWindowTitle(sb1.toString());
					sb1 = new StringBuilder();
					sb1.append("Processing frames: ");
					sb1.append(percentage);
					sb1.append("%");
					Window.lblNewLabel_1.setText(sb1.toString());
					Window.progressBar.setValue((int) Math.round(percentage * 0.5227 + 15.54));
					Window.progressBar_1.setValue((int) percentage);
				}
				charFrames = new ArrayList<String>();
				for (int i = 0; i < frameLength; i++) {
					if (Window.cancel) {
						close();
						return;
					}
					sb1 = new StringBuilder();
					sb1.append(Main.IMAGE);
					sb1.append(i);
					sb1.append(".jpg");
					charFrames.add(sb1.toString());
				}
				imgx = Painter.imgx;
				imgy = Painter.imgy;
			}
			Window.lblNewLabel.setText("Overall process: 67.81%");
			Window.progressBar_1.setValue(68);
			win.setWindowTitle("67.81% - ");
			Window.lblNewLabel_1.setText("Rendering video: 0.0%");
			Window.progressBar.setValue(0);
			Recorder.Record(charFrames, fps, frameLength);
			Window.textArea.append("Deleting temporary files...");
			Window.textArea.append(System.getProperty("line.separator"));
			Window.textArea.setCaretPosition(Window.textArea.getDocument().getLength());
			for (File file : new File("frames/").listFiles())
				file.delete();
			for (File file : new File("charFrames/").listFiles())
				file.delete();
			for (File file : new File("audio/").listFiles())
				file.delete();
			for (File file : new File("noAudioVideo/").listFiles())
				file.delete();
			Window.textArea.append("Temporary files deleted.");
			Window.textArea.append(System.getProperty("line.separator"));
			Window.textArea.setCaretPosition(Window.textArea.getDocument().getLength());
			new ErrorWindow("Done.").setVisible(true);
			while (!ErrorWindowButton) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			win.retract();
			win.setWindowTitle("");
			Window.btnNewButton.setVisible(false);
			Window.start.setVisible(true);
			Window.textArea.setText("");
		} else if (inputExists && !outputExists) {
			new ErrorWindow("No input path specified.").setVisible(true);
		} else if (!inputExists && outputExists) {
			new ErrorWindow("No output path specified.").setVisible(true);
		} else if (!inputExists && !outputExists) {
			new ErrorWindow("No input or output path specified.").setVisible(true);
		}
	}

	public static boolean browseInput() {
		sb1 = new StringBuilder();
		JFileChooser inChooser = new JFileChooser();
		sb1.append(System.getProperty("user.home"));
		sb1.append(System.getProperty("file.separator"));
		sb1.append("Desktop");
		inChooser.setCurrentDirectory(new File(sb1.toString()));
		inChooser.setDialogTitle("Input File");
		inChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		inChooser.setAcceptAllFileFilterUsed(false);
		inChooser.setPreferredSize(new Dimension(864, 486));
		inChooser.addChoosableFileFilter(new FileNameExtensionFilter("MPEG4 (*.mp4)", "mp4"));
		inChooser.addChoosableFileFilter(new FileNameExtensionFilter("AVI (*.avi)", "avi"));
		inChooser.setMultiSelectionEnabled(false);
		if (inChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			inputPath = inChooser.getSelectedFile().toString();
			if (!(inputPath.substring(inputPath.length() - 4, inputPath.length() - 3)).equals(".")) {
				sb1 = new StringBuilder();
				sb1.append(inputPath);
				sb1.append(inChooser.getFileFilter().getDescription().substring(
						inChooser.getFileFilter().getDescription().length() - 5,
						inChooser.getFileFilter().getDescription().length() - 1));
				inputPath = sb1.toString();
			} else if (!(inputPath.substring(inputPath.length() - 4)
					.equals(inChooser.getFileFilter().getDescription().substring(
							inChooser.getFileFilter().getDescription().length() - 5,
							inChooser.getFileFilter().getDescription().length() - 1)))) {
				new ErrorWindow("File extension doesn't match file type.").setVisible(true);
				return false;
			}
			Window.inField.setText(inputPath);
			inWasOpened = true;
			return true;
		} else {
			return false;
		}
	}

	public static boolean browseOutput() {
		sb1 = new StringBuilder();
		JFileChooser outChooser = new JFileChooser();
		sb1.append(System.getProperty("user.home"));
		sb1.append(System.getProperty("file.separator"));
		sb1.append("Desktop");
		outChooser.setCurrentDirectory(new File(sb1.toString()));
		outChooser.setDialogTitle("Output File");
		outChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		outChooser.setAcceptAllFileFilterUsed(false);
		outChooser.setPreferredSize(new Dimension(864, 486));
		outChooser.addChoosableFileFilter(new FileNameExtensionFilter("MPEG4 (*.mp4)", "mp4"));
		outChooser.addChoosableFileFilter(new FileNameExtensionFilter("AVI (*.avi)", "avi"));
		outChooser.setMultiSelectionEnabled(false);
		if (outChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
			outputPath = outChooser.getSelectedFile().toString();
			if (!(outputPath.substring(outputPath.length() - 4, outputPath.length() - 3)).equals(".")) {
				sb1 = new StringBuilder();
				sb1.append(outputPath);
				sb1.append(outChooser.getFileFilter().getDescription().substring(
						outChooser.getFileFilter().getDescription().length() - 5,
						outChooser.getFileFilter().getDescription().length() - 1));
				outputPath = sb1.toString();
			} else if (!(outputPath.substring(outputPath.length() - 4)
					.equals(outChooser.getFileFilter().getDescription().substring(
							outChooser.getFileFilter().getDescription().length() - 5,
							outChooser.getFileFilter().getDescription().length() - 1)))) {
				new ErrorWindow("File extension doesn't match file type.").setVisible(true);
				return false;
			}
			Recorder.extension = outChooser.getFileFilter().getDescription().substring(
					outChooser.getFileFilter().getDescription().length() - 4,
					outChooser.getFileFilter().getDescription().length() - 1);
			Window.outField.setText(outputPath);
			outWasOpened = true;
			return true;
		} else {
			return false;
		}
	}

	public static void close() {
		for (File file : new File("frames/").listFiles())
			file.delete();
		for (File file : new File("charFrames/").listFiles())
			file.delete();
		for (File file : new File("audio/").listFiles())
			file.delete();
		for (File file : new File("noAudioVideo/").listFiles())
			file.delete();
		win.retract();
		Window.cancel = false;
		Window.lblNewLabel.setText("Overall process: 0.0%");
		Window.progressBar.setValue(0);
		win.setWindowTitle("");
		Window.lblNewLabel_1.setText("0.0%");
		Window.progressBar_1.setValue(0);
		Window.btnNewButton.setVisible(false);
		Window.start.setVisible(true);
		Window.textArea.setText("");
	}
}
