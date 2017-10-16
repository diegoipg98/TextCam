import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class ColoredPainter extends Thread implements Runnable{
	private String inputPath;
	private int id;
	public static int imgx;
	public static int imgy;

	public ColoredPainter(String path, int id) {
		inputPath = path;
		this.id = id;
	}

	public void run() {
		float[] hsb;
		int x;
		int y;
		Color textChar;
		ArrayList<Color> gspixels = new ArrayList<Color>();
		ArrayList<Color> characters = new ArrayList<Color>();
		ArrayList<Integer> satLevels = new ArrayList<Integer>();
		ArrayList<Integer> areaSatLevels = new ArrayList<Integer>();
		File img = new File(inputPath);
		BufferedImage image = null;
		try {
			image = ImageIO.read(img);
		} catch (IOException e) {
			e.printStackTrace();
		}
		imgx = image.getWidth();
		imgx = ((int) (((double) (imgx)) / 8)) * 8;
		imgy = image.getHeight();
		imgy = ((int) (((double) (imgy)) / 18)) * 18;
		for (int height = 0; height < imgy; height += 18) {
			for (int width = 0; width < imgx; width += 8) {
				for (y = 0; y < 18; y++) {
					for (x = 0; x < 8; x++) {
						int pixelColor = image.getRGB(x + (width), y + (height));
						int red = (pixelColor & 0x00ff0000) >> 16;
						int green = (pixelColor & 0x0000ff00) >> 8;
						int blue = pixelColor & 0x000000ff;
						hsb = new float[3];
						Color.RGBtoHSB(red,green,blue,hsb); 
						satLevels.add((int)(hsb[1]*255));
						
						pixelColor = Color.HSBtoRGB(hsb[0], 1, hsb[2]);
						red = (pixelColor & 0x00ff0000) >> 16;
						green = (pixelColor & 0x0000ff00) >> 8;
						blue = pixelColor & 0x000000ff;
						gspixels.add(new Color(red,green,blue));
					}
				}
				areaSatLevels.add(calcIntAvg(satLevels));
				satLevels = new ArrayList<Integer>();
				
				textChar = calcAvg(gspixels);
				gspixels = new ArrayList<Color>();
				characters.add(textChar);
			}
		}
		try {
			image = ImageIO.read(img);
		} catch (IOException e) {
			e.printStackTrace();
		}
		image = createResizedCopy(image, imgx, imgy, false);
		StringBuilder sb1 = new StringBuilder();
		sb1.append(Main.IMAGE);
		sb1.append(id);
		sb1.append(".jpg");
		File output = new File(sb1.toString());
		try {
			ImageIO.write(image, "png", output);
		} catch (IOException e) {
			e.printStackTrace();
		}
		int counter = 0;
		
		for (int crtr : areaSatLevels) {
			if (crtr > 0 && crtr < 22) {
				if (22 - crtr <= 10) {crtr = 22;} else {crtr = 0;}
			} else if (crtr > 22 && crtr < 28) {
				if (28 - crtr <= 2) {crtr = 28;} else {crtr = 22;}
			} else if (crtr > 28 && crtr < 33) {
				if (33 - crtr <= 2) {crtr = 33;} else {crtr = 28;}
			} else if (crtr > 33 && crtr < 39) {
				if (39 - crtr <= 2) {crtr = 39;} else {crtr = 33;}
			} else if (crtr > 39 && crtr < 44) {
				if (44 - crtr <= 2) {crtr = 44;} else {crtr = 39;}
			} else if (crtr > 44 && crtr < 49) {
				if (49 - crtr <= 2) {crtr = 49;} else {crtr = 44;}
			} else if (crtr > 49 && crtr < 55) {
				if (55 - crtr <= 2) {crtr = 55;} else {crtr = 49;}
			} else if (crtr > 55 && crtr < 61) {
				if (61 - crtr <= 2) {crtr = 61;} else {crtr = 55;}
			} else if (crtr > 61 && crtr < 66) {
				if (66 - crtr <= 2) {crtr = 66;} else {crtr = 61;}
			} else if (crtr > 66 && crtr < 72) {
				if (72 - crtr <= 2) {crtr = 72;} else {crtr = 66;}
			} else if (crtr > 72 && crtr < 77) {
				if (77 - crtr <= 2) {crtr = 77;} else {crtr = 72;}
			} else if (crtr > 77 && crtr < 83) {
				if (83 - crtr <= 2) {crtr = 83;} else {crtr = 77;}
			} else if (crtr > 83 && crtr < 88) {
				if (88 - crtr <= 2) {crtr = 88;} else {crtr = 83;}
			} else if (crtr > 88 && crtr < 94) {
				if (94 - crtr <= 2) {crtr = 94;} else {crtr = 88;}
			} else if (crtr > 94 && crtr < 99) {
				if (99 - crtr <= 2) {crtr = 99;} else {crtr = 94;}
			} else if (crtr > 99 && crtr < 105) {
				if (105 - crtr <= 2) {crtr = 105;} else {crtr = 99;}
			} else if (crtr > 105 && crtr < 110) {
				if (110 - crtr <= 2) {crtr = 110;} else {crtr = 105;}
			} else if (crtr > 110 && crtr < 116) {
				if (116 - crtr <= 2) {crtr = 116;} else {crtr = 110;}
			} else if (crtr > 116 && crtr < 127) {
				if (127 - crtr <= 5) {crtr = 127;} else {crtr = 116;}
			} else if (crtr > 127 && crtr < 132) {
				if (132 - crtr <= 2) {crtr = 132;} else {crtr = 127;}
			} else if (crtr > 132 && crtr < 138) {
				if (138 - crtr <= 2) {crtr = 138;} else {crtr = 132;}
			} else if (crtr > 138 && crtr < 143) {
				if (143 - crtr <= 2) {crtr = 143;} else {crtr = 138;}
			} else if (crtr > 143 && crtr < 154) {
				if (154 - crtr <= 5) {crtr = 154;} else {crtr = 143;}
			} else if (crtr > 154 && crtr < 160) {
				if (160 - crtr <= 2) {crtr = 160;} else {crtr = 154;}
			} else if (crtr > 160 && crtr < 165) {
				if (165 - crtr <= 2) {crtr = 165;} else {crtr = 160;}
			} else if (crtr > 165 && crtr < 171) {
				if (171 - crtr <= 2) {crtr = 171;} else {crtr = 165;}
			} else if (crtr > 171 && crtr < 176) {
				if (176 - crtr <= 2) {crtr = 176;} else {crtr = 171;}
			} else if (crtr > 176 && crtr < 182) {
				if (182 - crtr <= 2) {crtr = 182;} else {crtr = 176;}
			} else if (crtr > 182 && crtr < 187) {
				if (187 - crtr <= 2) {crtr = 187;} else {crtr = 182;}
			} else if (crtr > 187 && crtr < 193) {
				if (193 - crtr <= 2) {crtr = 193;} else {crtr = 187;}
			} else if (crtr > 193 && crtr < 198) {
				if (198 - crtr <= 2) {crtr = 198;} else {crtr = 193;}
			} else if (crtr > 198 && crtr < 204) {
				if (204 - crtr <= 2) {crtr = 204;} else {crtr = 198;}
			} else if (crtr > 204 && crtr < 209) {
				if (209 - crtr <= 2) {crtr = 209;} else {crtr = 204;}
			} else if (crtr > 209 && crtr < 214) {
				if (214 - crtr <= 2) {crtr = 214;} else {crtr = 209;}
			} else if (crtr > 214 && crtr < 220) {
				if (220 - crtr <= 2) {crtr = 220;} else {crtr = 214;}
			} else if (crtr > 220 && crtr < 222) {
				crtr = 222;
			} else if (crtr > 222 && crtr < 246) {
				if (246 - crtr <= 10) {crtr = 246;} else {crtr = 222;}
			} else if (crtr > 246 && crtr < 248) {
				crtr = 248;
			} else if (crtr > 248 && crtr < 255) {
				if (255 - crtr <= 3) {crtr = 255;} else {crtr = 248;}
			}
			areaSatLevels.set(counter, crtr);
			counter++;
		}

		counter = 0;
		ArrayList<String> textchar = new ArrayList<String>();
		for (int crtr : areaSatLevels) {
			textchar.add(null);
			if (crtr == 0) {
				textchar.set(counter, "@");
			} else if (crtr == 22) {
				textchar.set(counter, "¶");
			} else if (crtr == 28) {
				textchar.set(counter, "W");
			} else if (crtr == 33) {
				textchar.set(counter, "©");
			} else if (crtr == 39) {
				textchar.set(counter, "M");
			} else if (crtr == 44) {
				textchar.set(counter, "%");
			} else if (crtr == 49) {
				textchar.set(counter, "#");
			} else if (crtr == 55) {
				textchar.set(counter, "0");
			} else if (crtr == 61) {
				textchar.set(counter, "Q");
			} else if (crtr == 66) {
				textchar.set(counter, "$");
			} else if (crtr == 72) {
				textchar.set(counter, "&");
			} else if (crtr == 77) {
				textchar.set(counter, "m");
			} else if (crtr == 83) {
				textchar.set(counter, "€");
			} else if (crtr == 88) {
				textchar.set(counter, "g");
			} else if (crtr == 94) {
				textchar.set(counter, "µ");
			} else if (crtr == 99) {
				textchar.set(counter, "I");
			} else if (crtr == 105) {
				textchar.set(counter, "A");
			} else if (crtr == 110) {
				textchar.set(counter, "4");
			} else if (crtr == 116) {
				textchar.set(counter, "Y");
			} else if (crtr == 127) {
				textchar.set(counter, "h");
			} else if (crtr == 132) {
				textchar.set(counter, "e");
			} else if (crtr == 138) {
				textchar.set(counter, "½");
			} else if (crtr == 143) {
				textchar.set(counter, "2");
			} else if (crtr == 154) {
				textchar.set(counter, "5");
			} else if (crtr == 160) {
				textchar.set(counter, "t");
			} else if (crtr == 165) {
				textchar.set(counter, "T");
			} else if (crtr == 171) {
				textchar.set(counter, "f");
			} else if (crtr == 176) {
				textchar.set(counter, "s");
			} else if (crtr == 182) {
				textchar.set(counter, "*");
			} else if (crtr == 187) {
				textchar.set(counter, "•");
			} else if (crtr == 193) {
				textchar.set(counter, "+");
			} else if (crtr == 198) {
				textchar.set(counter, "=");
			} else if (crtr == 204) {
				textchar.set(counter, ";");
			} else if (crtr == 209) {
				textchar.set(counter, "¦");
			} else if (crtr == 214) {
				textchar.set(counter, ",");
			} else if (crtr == 220) {
				textchar.set(counter, String.valueOf('"'));
			} else if (crtr == 222) {
				textchar.set(counter, ":");
			} else if (crtr == 246) {
				textchar.set(counter, "'");
			} else if (crtr == 248) {
				textchar.set(counter, ".");
			} else if (crtr == 255) {
				textchar.set(counter, "`");
			}
			counter++;
		}
		counter = 0;
		BufferedImage outputFile = null;
		try {
			outputFile = ImageIO.read(output);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Graphics2D outputGraphics = outputFile.createGraphics();
		outputGraphics.setPaint(new Color(255,255,255));
		outputGraphics.fillRect(0, 0, outputFile.getWidth(), outputFile.getHeight());
		outputGraphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
		outputGraphics.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS,
				RenderingHints.VALUE_FRACTIONALMETRICS_ON);
		outputGraphics.setFont(new Font("Consolas", Font.PLAIN, 15));
		for (int height = 0; height < imgy; height += 18) {
			for (int width = 0; width < imgx; width += 8) {
				outputGraphics.setPaint(characters.get(counter));
				outputGraphics.drawString(textchar.get(counter), width, height + 14);
				counter++;
			}
		}
		outputGraphics.dispose();
		try {
			ImageIO.write(outputFile, "png", output);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private static Color calcAvg(ArrayList<Color> pixels) {
		double redSum = 0;
		double greenSum = 0;
		double blueSum = 0;
		if (!pixels.isEmpty()) {
			for (Color pixel : pixels) {
				redSum+=pixel.getRed();
				greenSum+=pixel.getGreen();
				blueSum+=pixel.getBlue();
			}
			return new Color((int)Math.round(redSum/pixels.size()),(int)Math.round(greenSum/pixels.size()),(int)Math.round(blueSum/pixels.size()));
		}
		return new Color(0,0,0);
	}
	private static int calcIntAvg(ArrayList<Integer> pixels) {
		double sum = 0;
		if (!pixels.isEmpty()) {
			for (Integer pixel : pixels) {
				sum += pixel;
			}
			return (int) Math.round(sum / pixels.size());
		}
		return (int) sum;
	}

	static BufferedImage createResizedCopy(BufferedImage originalImage, int scaledWidth, int scaledHeight,
			boolean preserveAlpha) {
		int imageType = preserveAlpha ? BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB;
		BufferedImage scaledBI = new BufferedImage(scaledWidth, scaledHeight, imageType);
		Graphics2D g = scaledBI.createGraphics();
		if (preserveAlpha) {
			g.setComposite(AlphaComposite.Src);
		}
		g.drawImage(originalImage, 0, 0, scaledWidth, scaledHeight, null);
		g.dispose();
		return scaledBI;
	}
}
