import java.awt.EventQueue;

import javax.swing.JFrame;

import java.awt.Color;

import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.SwingConstants;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.BorderFactory;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JPanel;
import javax.swing.JDialog;
import javax.swing.JSeparator;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import backend.*;


import javax.swing.BoxLayout;

//import org.eclipse.wb.swing.FocusTraversalOnArray;



import sun.java2d.loops.DrawLine;

import java.awt.BasicStroke;
import java.awt.Component;
import java.awt.Dimension;


import javax.swing.JSlider;

import java.awt.Rectangle;
import java.awt.GridLayout;
//import net.miginfocom.swing.MigLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.ArrayList;

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
	private void CreateAdmin()
	{
		//crea el administrador (parte fundamental del backend) y crea un proyecto miscelaneo 
		//automaticamente para poder ingresar tareas desde un comienzo
		
		admin= new Administrator();
		admin.AddProyect(new Proyect("Miscelaneo"));
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
		
		CreateAdmin();
		
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(0, 110, 142));
		frame.setBounds(100, 50, 1043, 697);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel panel_3 = new RoundedPanel();
		panel_3.setBackground(new Color(212, 227, 252));
		panel_3.setBounds(261, 182, 655, 85);
		frame.getContentPane().add(panel_3);
		panel_3.setLayout(null);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(106, 3, 531, 75);
		scrollPane_1.setBackground(new Color(212, 227, 252));
		panel_3.add(scrollPane_1);
		scrollPane_1.setBorder(BorderFactory.createEmptyBorder());
		scrollPane_1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.BLACK);
		scrollPane_1.setViewportView(panel_1);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.X_AXIS));
		
		JScrollPane scrollPane_3 = new JScrollPane();
		panel_1.add(scrollPane_3);
		scrollPane_3.setBorder(BorderFactory.createEmptyBorder());
		
		JPanel panel_2 = new JPanel();
		scrollPane_3.setViewportView(panel_2);
		panel_2.setBackground(new Color(212, 227, 252));
		GridBagLayout gbl_panel_2 = new GridBagLayout();
		gbl_panel_2.columnWidths = new int[] {76, 80, 79, 79, 79, 79, 79, 79, 79, 70, 70};
		gbl_panel_2.rowHeights = new int[]{51, 0};
		gbl_panel_2.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel_2.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		panel_2.setLayout(gbl_panel_2);
		
		NodeButton nodeButton_5 = new NodeButton("New button");
		nodeButton_5.setBackground(new Color(255, 102, 51));
		GridBagConstraints gbc_nodeButton_5 = new GridBagConstraints();
		gbc_nodeButton_5.insets = new Insets(0, 0, 0, 5);
		gbc_nodeButton_5.gridx = 4;
		gbc_nodeButton_5.gridy = 0;
		panel_2.add(nodeButton_5, gbc_nodeButton_5);
		nodeButton_5.setPreferredSize(new Dimension(50, 50));
		nodeButton_5.setAlignmentX(0.5f);
		
		JLabel lblProyecto = new JLabel("Tarea");
		lblProyecto.setBounds(40, 25, 39, 31);
		panel_3.add(lblProyecto);
		
		
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
				JDialog Ask = new JDialog (frame,"Nueva Tarea");
				Ask.setSize(400,400);
				Ask.setLocation(400, 200);
				JPanel Paneldialog = new JPanel ();
				Paneldialog.setLayout(null);
				Paneldialog.setBounds(0, 0, 0, 0);
				
				JLabel NombreTarea = new JLabel("Nombre de la tarea");
				NombreTarea.setHorizontalAlignment(SwingConstants.CENTER);
				NombreTarea.setForeground(new Color(0,0,0));
				NombreTarea.setFont(new Font("Arial Rounded MT Bold",Font.BOLD,15));
				//NombreTarea.setBounds(x, y, width, height);
				Paneldialog.add(NombreTarea);
				
				JTextField Tnombre = new JTextField();
				Tnombre.setBorder(null);
				Tnombre.setBackground(new Color(255, 255, 255));
				Tnombre.setHorizontalAlignment(SwingConstants.LEFT);
				Tnombre.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 14));
				Tnombre.setForeground(new Color(0,0,0));
				Tnombre.setBounds(37, 127, 147, 34);
				
				JLabel ProyectoTarea = new JLabel("Proyecto de la tarea");
				ProyectoTarea.setHorizontalAlignment(SwingConstants.CENTER);
				ProyectoTarea.setForeground(new Color(0,0,0));
				ProyectoTarea.setFont(new Font("Arial Rounded MT Bold",Font.BOLD,15));
				//ProyectoTarea.setBounds(x, y, width, height);
				Paneldialog.add(ProyectoTarea);
				
				JComboBox Proyectos = new JComboBox();
				Proyectos.setBorder(null);
				Proyectos.setEditable(true);
				Proyectos.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 12));
				Proyectos.setForeground(new Color(0,0,0));
				ArrayList<String> Lista=admin.ProjectNames();
				String[] opcionesp = new String[Lista.size()+1];
				for(int i=0; i<Lista.size();i++)
				{
					opcionesp[i]=Lista.get(i);
				}
				opcionesp[Lista.size()]="Nuevo Proyecto";
				Proyectos.setModel(new DefaultComboBoxModel(opcionesp));
				Proyectos.setSelectedIndex(0);
				Proyectos.setBackground(new Color(255, 255, 255));
				Proyectos.setBounds(37, 172, 147, 28);
				
				JLabel ContextoTarea = new JLabel("Contexto de la tarea");
				ContextoTarea.setHorizontalAlignment(SwingConstants.CENTER);
				ContextoTarea.setForeground(new Color(0,0,0));
				ContextoTarea.setFont(new Font("Arial Rounded MT Bold",Font.BOLD,15));
				//ContextoTarea.setBounds(x, y, width, height);
				Paneldialog.add(ContextoTarea);
				
				JLabel FechaTarea = new JLabel("Fecha de termino");
				FechaTarea.setHorizontalAlignment(SwingConstants.CENTER);
				FechaTarea.setForeground(new Color(0,0,0));
				FechaTarea.setFont(new Font("Arial Rounded MT Bold",Font.BOLD,15));
				//FechaTarea.setBounds(x, y, width, height);
				Paneldialog.add(FechaTarea);
				
				Ask.add(Paneldialog);
				Ask.setVisible(true);

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
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"       Miselaneo"}));
		comboBox.setSelectedIndex(0);
		comboBox.setBackground(new Color(255, 255, 255));
		comboBox.setBounds(37, 172, 147, 28);
		comboBox.addItem("Miselaneo");
		comboBox.addItem("patatas");
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
		
		JPanel TimeLinePane = new TimeLinePanel();
		TimeLinePane.setBounds(0, 69, 1026, 584);
		TimeLinePane.setBackground(new Color(0, 110, 142));
		frame.getContentPane().add(TimeLinePane);
		TimeLinePane.setVisible(false);
		TimeLinePane.setLayout(null);
		
		
		
		
		
	}
}
