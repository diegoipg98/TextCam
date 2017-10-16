import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.DefaultCaret;

public class Window extends JFrame implements ActionListener {
	/**
	 * 
	 */
	public static JTextField inField;
	public static JTextField outField;
	public static JLabel lblNewLabel;
	public static JLabel lblNewLabel_1;
	public static int prcnt1 = 0;
	public static int prcnt2 = 0;
	public static JProgressBar progressBar;
	public static JProgressBar progressBar_1;
	public static JPanel fColor;
	public static JPanel bColor;
	public static JTextArea textArea;
	public static JCheckBox chckbxNewCheckBox;
	public static boolean checkbox = false;
	private static final long serialVersionUID = -2026461808345592152L;
	private static JLabel lblBackgroundColor;
	private static JLabel lblForegroundColor;
	public static JButton btnNewButton;
	public static JButton start;
	public static boolean cancel = false;
	public Window() {
		super("TextCam");
		setResizable(false);
		setSize(960, 315);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		start = new JButton("Start");
		start.setFont(new Font("Verdana", Font.PLAIN, 11));
		start.setBounds(791, 175, 81, 23);
		start.addActionListener(this);
		JButton outBrowse = new JButton("Browse");
		outBrowse.setFont(new Font("Verdana", Font.PLAIN, 11));
		outBrowse.setBounds(791, 141, 81, 23);
		outBrowse.setActionCommand("browseOutput");
		outBrowse.addActionListener(this);
		outField = new JTextField(25);
		outField.setBounds(149, 143, 632, 20);
		outField.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent e) {
				setPath();
			}

			public void insertUpdate(DocumentEvent arg0) {
				setPath();
			}

			public void removeUpdate(DocumentEvent arg0) {
				setPath();
			}

			public void setPath() {
				Main.outputPath = outField.getText();
				StringBuilder sb1 = new StringBuilder();
				if (Main.outputPath.length() < 5) {
					sb1.append(Main.outputPath);
					sb1.append(".");
					sb1.append(Recorder.extension);
					Main.outputPath = sb1.toString();
				} else if (!(Main.outputPath.substring(Main.outputPath.length() - 4, Main.outputPath.length() - 3))
						.equals(".")) {
					sb1.append(Main.outputPath);
					sb1.append(".");
					sb1.append(Recorder.extension);
					Main.outputPath = sb1.toString();
				} else if (!Main.outputPath.substring(Main.outputPath.length() - 3).equals(Recorder.extension)
						&& Main.outWasOpened) {
					Recorder.extension = Main.outputPath.substring(Main.outputPath.length() - 3);
				}
			}
		});
		inField = new JTextField(25);
		inField.setBounds(149, 110, 632, 20);
		inField.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent e) {
				setPath();
			}

			public void insertUpdate(DocumentEvent arg0) {
				setPath();
			}

			public void removeUpdate(DocumentEvent arg0) {
				setPath();
			}

			public void setPath() {
				Main.inputPath = inField.getText();
				StringBuilder sb1 = new StringBuilder();
				if (Main.inputPath.length() < 5) {
					sb1.append(Main.inputPath);
					sb1.append(".");
					sb1.append(Recorder.extension);
					Main.inputPath = sb1.toString();
				} else if (!(Main.inputPath.substring(Main.inputPath.length() - 4, Main.inputPath.length() - 3))
						.equals(".")) {
					sb1.append(Main.inputPath);
					sb1.append(".");
					sb1.append(Recorder.extension);
					Main.inputPath = sb1.toString();
				}
			}
		});
		getContentPane().setLayout(null);
		JLabel inLabel = new JLabel("Input Path");
		inLabel.setFont(new Font("Verdana", Font.PLAIN, 11));
		inLabel.setForeground(Color.BLACK);
		inLabel.setBounds(72, 112, 67, 18);
		getContentPane().add(inLabel);
		getContentPane().add(inField);
		JButton inBrowse = new JButton("Browse");
		inBrowse.setFont(new Font("Verdana", Font.PLAIN, 11));
		inBrowse.setBounds(791, 108, 81, 23);
		inBrowse.setActionCommand("browseInput");
		inBrowse.addActionListener(this);
		getContentPane().add(inBrowse);
		JLabel outLabel = new JLabel("Output Path");
		outLabel.setFont(new Font("Verdana", Font.PLAIN, 11));
		outLabel.setForeground(Color.BLACK);
		outLabel.setBounds(72, 143, 67, 20);
		getContentPane().add(outLabel);
		getContentPane().add(outField);
		getContentPane().add(outBrowse);
		getContentPane().add(start);
		lblNewLabel = new JLabel("Starting...");
		lblNewLabel.setFont(new Font("Verdana", Font.PLAIN, 11));
		lblNewLabel.setBounds(72, 285, 800, 14);
		getContentPane().add(lblNewLabel);

		lblNewLabel_1 = new JLabel("Starting...");
		lblNewLabel_1.setFont(new Font("Verdana", Font.PLAIN, 11));
		lblNewLabel_1.setBounds(72, 341, 800, 14);
		getContentPane().add(lblNewLabel_1);

		progressBar = new JProgressBar(0, 100);
		progressBar.setValue(prcnt1);
		progressBar.setBounds(72, 310, 800, 20);
		getContentPane().add(progressBar);

		progressBar_1 = new JProgressBar(0, 100);
		progressBar_1.setValue(prcnt2);
		progressBar_1.setBounds(72, 366, 800, 20);

		getContentPane().add(progressBar_1);
		JLabel lblTextcam = new JLabel("TextCam");
		lblTextcam.setForeground(Color.BLACK);
		lblTextcam.setFont(new Font("Verdana", Font.BOLD | Font.ITALIC, 48));
		lblTextcam.setBounds(350, 35, 237, 62);
		getContentPane().add(lblTextcam);

		JButton backColor = new JButton("");
		backColor.setBackground(Color.WHITE);
		backColor.setBounds(288, 209, 89, 23);
		backColor.setActionCommand("backColor");
		backColor.addActionListener(this);
		backColor.setContentAreaFilled(false);
		getContentPane().add(backColor);

		JButton foreColor = new JButton("");
		foreColor.setBackground(Color.BLACK);
		foreColor.setBounds(625, 209, 89, 23);
		foreColor.setActionCommand("foreColor");
		foreColor.addActionListener(this);
		foreColor.setContentAreaFilled(false);
		getContentPane().add(foreColor);

		lblBackgroundColor = new JLabel("Background Color");
		lblBackgroundColor.setFont(new Font("Verdana", Font.PLAIN, 11));
		lblBackgroundColor.setBounds(175, 209, 103, 23);
		getContentPane().add(lblBackgroundColor);

		lblForegroundColor = new JLabel("Text Font Color");
		lblForegroundColor.setFont(new Font("Verdana", Font.PLAIN, 11));
		lblForegroundColor.setBounds(515, 209, 100, 23);
		getContentPane().add(lblForegroundColor);

		textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setBounds(72, 403, 800, 90);

		DefaultCaret caret = (DefaultCaret) textArea.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		textArea.setCaret(caret);
		textArea.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(171, 173, 179)),
				BorderFactory.createEmptyBorder(2, 2, 2, 2)));
		getContentPane().add(textArea);

		chckbxNewCheckBox = new JCheckBox("Use original colors (Lower video quality)");
		chckbxNewCheckBox.setFont(new Font("Verdana", Font.PLAIN, 11));
		chckbxNewCheckBox.setBounds(72, 176, 353, 23);
		chckbxNewCheckBox.addItemListener(new CheckBoxListener());
		getContentPane().add(chckbxNewCheckBox);

		btnNewButton = new JButton("Cancel");
		btnNewButton.setFont(new Font("Verdana", Font.PLAIN, 11));
		btnNewButton.setBounds(791, 209, 81, 23);
		btnNewButton.addActionListener(this);
		getContentPane().add(btnNewButton);

		bColor = new JPanel();
		bColor.setBackground(Color.WHITE);
		bColor.setBounds(288, 209, 89, 23);
		bColor.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(171, 173, 179)),
				BorderFactory.createEmptyBorder(0, 0, 0, 0)));
		getContentPane().add(bColor);

		fColor = new JPanel();
		fColor.setBackground(Color.BLACK);
		fColor.setBounds(625, 209, 89, 23);
		fColor.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(171, 173, 179)),
				BorderFactory.createEmptyBorder(0, 0, 0, 0)));
		getContentPane().add(fColor);
		btnNewButton.setVisible(false);

		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(dim.width / 2 - getSize().width / 2, dim.height / 2 - getSize().height / 2);
	}

	public void actionPerformed(ActionEvent e) {
		String name = e.getActionCommand();
		if (name.equals("browseInput")) {
			Main.inputExists = Main.browseInput();
		} else if (name.equals("browseOutput")) {
			Main.outputExists = Main.browseOutput();
		} else if (name.equals("Start")) {
			boolean inputExists = !(inField.getText().isEmpty());
			boolean outputExists = !(outField.getText().isEmpty());
			if (inputExists && outputExists) {
				Main.inputPath = inField.getText();
				Main.outputPath = outField.getText();
				Main.inputExists = true;
				Main.outputExists = true;
			} else if (inputExists && !outputExists) {
				new ErrorWindow("No input path specified.").setVisible(true);
				Main.inputPath = inField.getText();
				Main.inputExists = true;
			} else if (!inputExists && outputExists) {
				new ErrorWindow("No output path specified.").setVisible(true);
				Main.outputPath = outField.getText();
				Main.outputExists = true;
			} else {
				new ErrorWindow("No input or output path specified.").setVisible(true);
			}
			Main.doRun = true;
		} else if (name.equals("backColor")) {
			new ColorWindow(0).setVisible(true);
		} else if (name.equals("foreColor")) {
			new ColorWindow(1).setVisible(true);
		} else if (name.equals("Cancel")) {
			cancel = true;
		} else if (name.equals("Use original colors (Lower video quality)")){
			fColor.setVisible(!fColor.isVisible());
			bColor.setVisible(!bColor.isVisible());
			lblForegroundColor.setVisible(!lblForegroundColor.isVisible());
			lblBackgroundColor.setVisible(!lblBackgroundColor.isVisible());
		}
	}

	public void extend() {
		int y = getHeight();
		int locationHeight = (Toolkit.getDefaultToolkit().getScreenSize()).height / 2 - getSize().height / 2;
		for (int i = 0; i < 28; i += 1) {
			setSize(960, y + i*10);
			setLocation((Toolkit.getDefaultToolkit().getScreenSize()).width / 2 - getSize().width / 2 ,
					locationHeight - i*5);
			repaint();
		}
	}

	public void retract() {
		int y = getHeight();
		int locationHeight = (Toolkit.getDefaultToolkit().getScreenSize()).height / 2 - getSize().height / 2;
		for (int i = 0; i < 28; i += 1) {
			setSize(960, y - i*10);
			setLocation((Toolkit.getDefaultToolkit().getScreenSize()).width / 2 - getSize().width / 2 ,
					locationHeight + i*5);
			repaint();
		}
	}
	
	public void setWindowTitle(String text){
		StringBuilder sb1 = new StringBuilder();
		sb1.append(text);
		sb1.append("TextCam");
		setTitle(sb1.toString());
	}
	private class CheckBoxListener implements ItemListener {
		public void itemStateChanged(ItemEvent e) {
			if (e.getSource() == chckbxNewCheckBox) {
				if (chckbxNewCheckBox.isSelected()) {
					checkbox = true;
				} else {
					checkbox = false;
				}
			}
		}
	}
}
