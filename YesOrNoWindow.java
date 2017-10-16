import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class YesOrNoWindow extends JFrame implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4485055549617342834L;

	public YesOrNoWindow(String message) {
		super("TextCam");
		getContentPane().setLayout(null);
		setSize(305,170);
		setResizable(false);
		JLabel lblNewLabel = new JLabel(message, SwingConstants.CENTER);
		lblNewLabel.setBounds(10, 34, 279, 46);
		getContentPane().add(lblNewLabel);
		
		JButton btnNewButton = new JButton("Yes");
		btnNewButton.setBounds(101, 107, 89, 23);
		btnNewButton.addActionListener(this);
		getContentPane().add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("No");
		btnNewButton_1.setBounds(200, 107, 89, 23);
		btnNewButton_1.addActionListener(this);
		getContentPane().add(btnNewButton_1);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(dim.width / 2 - getSize().width / 2, dim.height / 2 - getSize().height / 2);
	}
	public void actionPerformed(ActionEvent e) {
		String name = e.getActionCommand();
		if (name.equals("Yes")) {
			setVisible(false);
		} else if (name.equals("No")) {
			Main.replaceFile = false;
			setVisible(false);
		}
	}
	@Override
	public void dispose(){
		Main.replaceFile = false;
		super.dispose();
	}
}
