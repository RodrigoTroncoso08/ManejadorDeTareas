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
import javax.swing.ScrollPaneConstants;
import backend.*;

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
		txtSearch.setBackground(new Color(255, 255, 255));
		txtSearch.setHorizontalAlignment(SwingConstants.LEFT);
		txtSearch.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 14));
		txtSearch.setForeground(new Color(102, 204, 204));
		txtSearch.setText("   Search...");
		txtSearch.setBounds(37, 127, 147, 34);
		panel.add(txtSearch);
		txtSearch.setColumns(10);
		
		JLabel lblGlosary = new JLabel("Glosary");
		lblGlosary.setHorizontalAlignment(SwingConstants.CENTER);
		lblGlosary.setForeground(new Color(255, 255, 255));
		lblGlosary.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 30));
		lblGlosary.setBounds(46, 235, 128, 34);
		panel.add(lblGlosary);
		
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
		
		RoundedButton roundedButton = new RoundedButton("+ Task");
		roundedButton.setText("Trabajo");
		roundedButton.setForeground(Color.WHITE);
		roundedButton.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 16));
		roundedButton.setBackground(new Color(255, 204, 51));
		roundedButton.setBounds(37, 453, 147, 34);
		panel.add(roundedButton);
		
		RoundedButton roundedButton_1 = new RoundedButton("+ Task");
		roundedButton_1.setText("Micelaneo");
		roundedButton_1.setForeground(Color.WHITE);
		roundedButton_1.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 16));
		roundedButton_1.setBackground(new Color(102, 204, 153));
		roundedButton_1.setBounds(37, 387, 147, 43);
		panel.add(roundedButton_1);
		
		RoundedButton roundedButton_2 = new RoundedButton("+ Task");
		roundedButton_2.setText("Clases");
		roundedButton_2.setForeground(Color.WHITE);
		roundedButton_2.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 16));
		roundedButton_2.setBackground(new Color(0, 0, 102));
		roundedButton_2.setBounds(37, 318, 147, 43);
		panel.add(roundedButton_2);
		
		RoundedPanel panel_1 = new RoundedPanel();
		panel_1.setBackground(new Color(255, 255, 255));
		panel_1.setBounds(0, 77, 1005, 576);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(null);
	}
}
