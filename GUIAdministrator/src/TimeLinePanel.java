import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.SwingConstants;


public class TimeLinePanel extends JPanel {

	
	public TimeLinePanel()
	{
		super();
		RoundedPanel WhiteBase = new RoundedPanel();
		WhiteBase.setBounds(6, 6, 1005, 576);
		this.add(WhiteBase);
		WhiteBase.setForeground(Color.DARK_GRAY);
		WhiteBase.setBackground(new Color(255, 255, 255));
		WhiteBase.setLayout(null);
		WhiteBase.setVisible(true);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setForeground(Color.BLUE);
		separator_1.setBounds(385, 86, 550, 2);
		
		WhiteBase.add(separator_1);
		
		JSeparator separator_6 = new JSeparator();
		separator_6.setForeground(Color.BLUE);
		separator_6.setOrientation(SwingConstants.VERTICAL);
		separator_6.setBounds(432, 86, 2, 411);
		WhiteBase.add(separator_6);
		
		JSeparator separator_7 = new JSeparator();
		separator_7.setForeground(Color.BLUE);
		separator_7.setOrientation(SwingConstants.VERTICAL);
		separator_7.setBounds(586, 86, 2, 411);
		WhiteBase.add(separator_7);
		
		JSeparator separator_8 = new JSeparator();
		separator_8.setForeground(Color.BLUE);
		separator_8.setOrientation(SwingConstants.VERTICAL);
		separator_8.setBounds(740, 86, 2, 411);
		WhiteBase.add(separator_8);
		
		JSeparator separator_9 = new JSeparator();
		separator_9.setForeground(Color.BLUE);
		separator_9.setOrientation(SwingConstants.VERTICAL);
		separator_9.setBounds(355, 23, 2, 474);
		
		WhiteBase.add(separator_9);
		
		JSeparator separator_10 = new JSeparator();
		separator_10.setForeground(Color.BLUE);
		separator_10.setOrientation(SwingConstants.VERTICAL);
		separator_10.setBounds(509, 86, 2, 411);
		WhiteBase.add(separator_10);
		
		JSeparator separator_11 = new JSeparator();
		separator_11.setForeground(Color.BLUE);
		separator_11.setOrientation(SwingConstants.VERTICAL);
		separator_11.setBounds(663, 86, 2, 411);
		WhiteBase.add(separator_11);
		
		JSeparator separator_12 = new JSeparator();
		separator_12.setForeground(Color.BLUE);
		separator_12.setOrientation(SwingConstants.VERTICAL);
		separator_12.setBounds(817, 86, 2, 411);
		WhiteBase.add(separator_12);
		
		JSeparator separator_13 = new JSeparator();
		separator_13.setForeground(Color.BLUE);
		separator_13.setOrientation(SwingConstants.VERTICAL);
		separator_13.setBounds(894, 86, 2, 411);
		WhiteBase.add(separator_13);
		
		JSlider slider = new JSlider();
		slider.setForeground(new Color(102, 204, 204));
		slider.setBackground(new Color(153, 204, 255));
		slider.setMaximum(30);
		slider.setMajorTickSpacing(4);
		slider.setBounds(396, 23, 513, 21);
		WhiteBase.add(slider);
		
		JLabel lblDate = new JLabel("17/4");
		lblDate.setFont(new Font("Arial", Font.PLAIN, 12));
		lblDate.setHorizontalAlignment(SwingConstants.CENTER);
		lblDate.setForeground(new Color(51, 204, 204));
		lblDate.setBounds(446, 58, 55, 30);
		WhiteBase.add(lblDate);
		
		JLabel lblDate_1 = new JLabel("17/4");
		lblDate_1.setFont(new Font("Arial", Font.PLAIN, 12));
		lblDate_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblDate_1.setForeground(new Color(51, 204, 204));
		lblDate_1.setBounds(519, 58, 55, 30);
		WhiteBase.add(lblDate_1);
		
		JLabel lblDate_2 = new JLabel("17/4");
		lblDate_2.setFont(new Font("Arial", Font.PLAIN, 12));
		lblDate_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblDate_2.setForeground(new Color(51, 204, 204));
		lblDate_2.setBounds(600, 58, 55, 30);
		WhiteBase.add(lblDate_2);
		
		JLabel lblDate_5 = new JLabel("17/4");
		lblDate_5.setFont(new Font("Arial", Font.PLAIN, 12));
		lblDate_5.setHorizontalAlignment(SwingConstants.CENTER);
		lblDate_5.setForeground(new Color(51, 204, 204));
		lblDate_5.setBounds(831, 58, 55, 30);
		WhiteBase.add(lblDate_5);
		
		JLabel lblDate_3 = new JLabel("17/4");
		lblDate_3.setFont(new Font("Arial", Font.PLAIN, 12));
		lblDate_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblDate_3.setForeground(new Color(51, 204, 204));
		lblDate_3.setBounds(673, 58, 55, 30);
		WhiteBase.add(lblDate_3);
		
		JLabel lblDate_4 = new JLabel("17/4");
		lblDate_4.setFont(new Font("Arial", Font.PLAIN, 12));
		lblDate_4.setHorizontalAlignment(SwingConstants.CENTER);
		lblDate_4.setForeground(new Color(51, 204, 204));
		lblDate_4.setBounds(750, 58, 55, 30);
		WhiteBase.add(lblDate_4);
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setForeground(Color.BLUE);
		separator_2.setBounds(385, 58, 550, 2);
		WhiteBase.add(separator_2);
		
		JLabel lblTareas = new JLabel("Tasks");
		lblTareas.setForeground(new Color(0, 153, 204));
		lblTareas.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 26));
		lblTareas.setBounds(256, 23, 117, 53);
		WhiteBase.add(lblTareas);
		
	}
}
