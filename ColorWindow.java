import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.colorchooser.ColorSelectionModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class ColorWindow extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3144218759873864161L;
	private final JButton btnNewButton = new JButton("Save");
	private static Color old;
	public ColorWindow(int i){
		super("TextCam");
		old = null;
		if(i==0){
			old = Window.bColor.getBackground();
		}else if(i==1){
			old = Window.fColor.getBackground();
		}
		JColorChooser colorChooser = new JColorChooser(old);
		ColorSelectionModel model = colorChooser.getSelectionModel();
		model.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent arg0) {
				if(i==0){
					Window.bColor.setBackground(colorChooser.getColor());
					Painter.background = colorChooser.getColor();
				}else if(i==1){
					Window.fColor.setBackground(colorChooser.getColor());
					Painter.foreground = colorChooser.getColor();
				}
			}
		});
		setSize(658,395);
		setResizable(false);
		getContentPane().setLayout(null);
		colorChooser.setBounds(10, 11, 632, 310);
		getContentPane().add(colorChooser);
		btnNewButton.setFont(new Font("Verdana", Font.PLAIN, 11));
		btnNewButton.setActionCommand("btnNewButton");
		btnNewButton.addActionListener(this);
		btnNewButton.setBounds(554, 332, 88, 23);
		getContentPane().add(btnNewButton);
		addWindowListener(new WindowAdapter()
		{
		    public void windowClosing(WindowEvent e)
		    {	
		    	if(i==0){
		    		Window.bColor.setBackground(old);
		    		Painter.background = old;
				}else if(i==1){
					Window.fColor.setBackground(old);
					Painter.foreground = old;
				}
		        setVisible(false);
		    }
		});
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(dim.width / 2 - getSize().width / 2, dim.height / 2 - getSize().height / 2);
	}
	public void actionPerformed(ActionEvent e) {
		String name = e.getActionCommand();
		if (name.equals("btnNewButton")) {
			setVisible(false);
		}
	}
}
