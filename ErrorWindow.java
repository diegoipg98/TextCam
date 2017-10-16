import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class ErrorWindow extends JFrame implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4485055549617342834L;


	public ErrorWindow(String message) {
		super("TextCam");
		getContentPane().setLayout(null);
		setSize(300,170);
		setResizable(false);
		JLabel lblNewLabel = new JLabel(message, SwingConstants.CENTER);
		lblNewLabel.setBounds(10, 50, 274, 14);
		getContentPane().add(lblNewLabel);
		
		JButton btnNewButton = new JButton("OK");
		btnNewButton.setBounds(195, 107, 89, 23);
		btnNewButton.setActionCommand("btnNewButton");
		btnNewButton.addActionListener(this);
		getContentPane().add(btnNewButton);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(dim.width / 2 - getSize().width / 2, dim.height / 2 - getSize().height / 2);
		addWindowListener(new WindowAdapter()
		{
		    public void windowClosing(WindowEvent e)
		    {	
		    	Main.ErrorWindowButton = true;
		        setVisible(false);
		    }
		});
	}
	public void actionPerformed(ActionEvent arg0) {
		setVisible(false);
		Main.ErrorWindowButton = true;
	}
}
