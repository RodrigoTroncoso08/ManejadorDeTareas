import java.awt.EventQueue;

import javax.swing.JFrame;

import java.awt.Color;

import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.SwingConstants;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.geom.Ellipse2D;

import javax.swing.BorderFactory;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JPanel;
//import com.jgoodies.forms.layout.FormLayout;
//import com.jgoodies.forms.layout.ColumnSpec;
//import com.jgoodies.forms.layout.RowSpec;
import javax.swing.JSeparator;
import javax.swing.JScrollBar;
import javax.swing.AbstractListModel;
import javax.swing.LookAndFeel;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import backend.*;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

import javax.swing.BoxLayout;

import org.eclipse.wb.swing.FocusTraversalOnArray;

import sun.java2d.loops.DrawLine;

import java.awt.BasicStroke;
import java.awt.Component;
import java.awt.Dimension;
import javax.swing.JSlider;

public class GUIBase {

	private JFrame frame;
	private JTextField txtSearch;
	private Administrator admin;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUIBase window = new GUIBase();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GUIBase() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		try {
		    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		        if ("Nimbus".equals(info.getName())) {
		            UIManager.setLookAndFeel(info.getClassName());
		            break;
		        }
		    }
		} catch (Exception e) {
		    // If Nimbus is not available, you can set the GUI to another look and feel.
		}
		
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(0, 110, 142));
		frame.setBounds(100, 50, 1043, 697);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		
		RoundedPanel panel = new RoundedPanel();
		panel.setBackground(new Color(212, 227, 252));
		panel.setForeground(new Color(255, 255, 255));
		panel.setBounds(10, 91, 239, 545);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		
		
		
		JButton AddTask = new RoundedButton("+ Task");
		AddTask.setText("+ Task  ");
		AddTask.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		AddTask.setVerticalAlignment(SwingConstants.BOTTOM);
		AddTask.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 16));
		AddTask.setBackground(new Color(255, 255, 255));
		AddTask.setForeground(new Color(153, 204, 255));
		
		AddTask.setBounds(37, 51, 147, 28);
		panel.add(AddTask);
		
		JLabel lblNewLabel = new JLabel("Menu");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setForeground(new Color(110, 160, 250));
		lblNewLabel.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 30));
		lblNewLabel.setBounds(61, 11, 99, 34);
		panel.add(lblNewLabel);
		
		RoundedButton rndbtnProyect = new RoundedButton("+ Project");
		rndbtnProyect.setVerticalAlignment(SwingConstants.BOTTOM);
		rndbtnProyect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				
			}
		});
		//rndbtnProyect.setText("+ Proyect  ");
		rndbtnProyect.setForeground(new Color(153, 204, 255));
		rndbtnProyect.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 16));
		rndbtnProyect.setBackground(Color.WHITE);
		rndbtnProyect.setBounds(37, 88, 147, 28);
		panel.add(rndbtnProyect);
		
		txtSearch = new JTextField();
		txtSearch.setBorder(null);
		txtSearch.setBackground(new Color(255, 255, 255));
		txtSearch.setHorizontalAlignment(SwingConstants.LEFT);
		txtSearch.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 14));
		txtSearch.setForeground(new Color(102, 204, 204));
		txtSearch.setText("   Search...");
		txtSearch.setBounds(37, 127, 147, 34);
		panel.add(txtSearch);
		txtSearch.setColumns(10);
		
		JLabel GlosayLabel = new JLabel("Glosary");
		GlosayLabel.setHorizontalAlignment(SwingConstants.CENTER);
		GlosayLabel.setForeground(new Color(255, 255, 255));
		GlosayLabel.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 30));
		GlosayLabel.setBounds(56, 231, 128, 34);
		panel.add(GlosayLabel);
		
		JButton MiselaneoItem = new RoundedButton("Miselaneo");
		MiselaneoItem.setVerticalAlignment(SwingConstants.BOTTOM);
		MiselaneoItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		MiselaneoItem.setText("+ Proyect  ");
		MiselaneoItem.setForeground(new Color(153, 204, 255));
		MiselaneoItem.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 16));
		MiselaneoItem.setBackground(Color.WHITE);
		MiselaneoItem.setBounds(33, 83, 147, 48);
		
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBorder(null);
		comboBox.setEditable(true);
		comboBox.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 12));
		comboBox.setForeground(new Color(0, 204, 255));
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"       Miselaneo", "\tMiselaneo"}));
		comboBox.setSelectedIndex(0);
		comboBox.setBackground(new Color(255, 255, 255));
		comboBox.setBounds(37, 172, 147, 28);
		comboBox.addItem("Miselaneo");
		
		panel.add(comboBox);
		
		JSeparator separator = new JSeparator();
		separator.setForeground(new Color(153, 204, 255));
		separator.setBackground(new Color(255, 255, 255));
		separator.setBounds(10, 217, 198, 2);
		panel.add(separator);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 298, 207, 162);
		panel.add(scrollPane);
		scrollPane.setBorder(BorderFactory.createEmptyBorder());
		
		JPanel GlosaryPane = new JPanel();
		GlosaryPane.setForeground(Color.WHITE);
		scrollPane.setViewportView(GlosaryPane);
		GlosaryPane.setLayout(new BoxLayout(GlosaryPane, BoxLayout.Y_AXIS));
		GlosaryPane.setBackground(new Color(212, 227, 252));
		
		
		RoundedButton rndbtnClases = new RoundedButton("+ Task");
		rndbtnClases.setAlignmentX(Component.CENTER_ALIGNMENT);
		GlosaryPane.add(rndbtnClases);
		rndbtnClases.setText("Clases");
		rndbtnClases.setForeground(Color.WHITE);
		rndbtnClases.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 20));
		rndbtnClases.setBackground(new Color(0, 0, 102));
		
		JLabel lblNewLabel_1 = new JLabel("  ");
		GlosaryPane.add(lblNewLabel_1);
		
		RoundedButton rndbtnMicelaneo = new RoundedButton("+ Task");
		rndbtnMicelaneo.setAlignmentX(Component.CENTER_ALIGNMENT);
		GlosaryPane.add(rndbtnMicelaneo);
		rndbtnMicelaneo.setText("Micelaneo");
		rndbtnMicelaneo.setForeground(Color.WHITE);
		rndbtnMicelaneo.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 20));
		rndbtnMicelaneo.setBackground(new Color(102, 204, 153));
		
		JLabel label = new JLabel("  ");
		GlosaryPane.add(label);
		
		RoundedButton roundedButton = new RoundedButton("+ Task");
		roundedButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		GlosaryPane.add(roundedButton);
		roundedButton.setText("Trabajo");
		roundedButton.setForeground(Color.WHITE);
		roundedButton.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 20));
		roundedButton.setBackground(new Color(255, 204, 51));
		
		JLabel label_1 = new JLabel("  ");
		GlosaryPane.add(label_1);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(0, 69, 1026, 584);
		panel_2.setBackground(new Color(0, 110, 142));
		frame.getContentPane().add(panel_2);
		panel_2.setLayout(null);
		
		RoundedPanel panel_1 = new RoundedPanel();
		panel_1.setBounds(6, 6, 1005, 576);
		panel_2.add(panel_1);
		panel_1.setForeground(Color.DARK_GRAY);
		panel_1.setBackground(new Color(255, 255, 255));
		panel_1.setLayout(null);
		panel_1.setVisible(true);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setForeground(Color.BLUE);
		separator_1.setBounds(385, 86, 550, 2);
		
		panel_1.add(separator_1);
		
		JSeparator separator_6 = new JSeparator();
		separator_6.setForeground(Color.BLUE);
		separator_6.setOrientation(SwingConstants.VERTICAL);
		separator_6.setBounds(432, 86, 2, 411);
		panel_1.add(separator_6);
		
		JSeparator separator_7 = new JSeparator();
		separator_7.setForeground(Color.BLUE);
		separator_7.setOrientation(SwingConstants.VERTICAL);
		separator_7.setBounds(586, 86, 2, 411);
		panel_1.add(separator_7);
		
		JSeparator separator_8 = new JSeparator();
		separator_8.setForeground(Color.BLUE);
		separator_8.setOrientation(SwingConstants.VERTICAL);
		separator_8.setBounds(740, 86, 2, 411);
		panel_1.add(separator_8);
		
		JSeparator separator_9 = new JSeparator();
		separator_9.setForeground(Color.BLUE);
		separator_9.setOrientation(SwingConstants.VERTICAL);
		separator_9.setBounds(355, 23, 2, 474);
		
		panel_1.add(separator_9);
		
		JSeparator separator_10 = new JSeparator();
		separator_10.setForeground(Color.BLUE);
		separator_10.setOrientation(SwingConstants.VERTICAL);
		separator_10.setBounds(509, 86, 2, 411);
		panel_1.add(separator_10);
		
		JSeparator separator_11 = new JSeparator();
		separator_11.setForeground(Color.BLUE);
		separator_11.setOrientation(SwingConstants.VERTICAL);
		separator_11.setBounds(663, 86, 2, 411);
		panel_1.add(separator_11);
		
		JSeparator separator_12 = new JSeparator();
		separator_12.setForeground(Color.BLUE);
		separator_12.setOrientation(SwingConstants.VERTICAL);
		separator_12.setBounds(817, 86, 2, 411);
		panel_1.add(separator_12);
		
		JSeparator separator_13 = new JSeparator();
		separator_13.setForeground(Color.BLUE);
		separator_13.setOrientation(SwingConstants.VERTICAL);
		separator_13.setBounds(894, 86, 2, 411);
		panel_1.add(separator_13);
		
		JSlider slider = new JSlider();
		slider.setForeground(new Color(102, 204, 204));
		slider.setBackground(new Color(153, 204, 255));
		slider.setMaximum(30);
		slider.setMajorTickSpacing(4);
		slider.setBounds(396, 23, 513, 21);
		panel_1.add(slider);
		
		JLabel lblDate = new JLabel("17/4");
		lblDate.setFont(new Font("Arial", Font.PLAIN, 12));
		lblDate.setHorizontalAlignment(SwingConstants.CENTER);
		lblDate.setForeground(new Color(51, 204, 204));
		lblDate.setBounds(446, 58, 55, 30);
		panel_1.add(lblDate);
		
		JLabel lblDate_1 = new JLabel("17/4");
		lblDate_1.setFont(new Font("Arial", Font.PLAIN, 12));
		lblDate_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblDate_1.setForeground(new Color(51, 204, 204));
		lblDate_1.setBounds(519, 58, 55, 30);
		panel_1.add(lblDate_1);
		
		JLabel lblDate_2 = new JLabel("17/4");
		lblDate_2.setFont(new Font("Arial", Font.PLAIN, 12));
		lblDate_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblDate_2.setForeground(new Color(51, 204, 204));
		lblDate_2.setBounds(600, 58, 55, 30);
		panel_1.add(lblDate_2);
		
		JLabel lblDate_5 = new JLabel("17/4");
		lblDate_5.setFont(new Font("Arial", Font.PLAIN, 12));
		lblDate_5.setHorizontalAlignment(SwingConstants.CENTER);
		lblDate_5.setForeground(new Color(51, 204, 204));
		lblDate_5.setBounds(831, 58, 55, 30);
		panel_1.add(lblDate_5);
		
		JLabel lblDate_3 = new JLabel("17/4");
		lblDate_3.setFont(new Font("Arial", Font.PLAIN, 12));
		lblDate_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblDate_3.setForeground(new Color(51, 204, 204));
		lblDate_3.setBounds(673, 58, 55, 30);
		panel_1.add(lblDate_3);
		
		JLabel lblDate_4 = new JLabel("17/4");
		lblDate_4.setFont(new Font("Arial", Font.PLAIN, 12));
		lblDate_4.setHorizontalAlignment(SwingConstants.CENTER);
		lblDate_4.setForeground(new Color(51, 204, 204));
		lblDate_4.setBounds(750, 58, 55, 30);
		panel_1.add(lblDate_4);
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setForeground(Color.BLUE);
		separator_2.setBounds(385, 58, 550, 2);
		panel_1.add(separator_2);
		
		JLabel lblTareas = new JLabel("Tareas");
		lblTareas.setForeground(new Color(0, 153, 204));
		lblTareas.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 26));
		lblTareas.setBounds(256, 23, 117, 53);
		panel_1.add(lblTareas);
		
		
		
	}
}
