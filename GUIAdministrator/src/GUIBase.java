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
		admin.AddContext("Miscelaneo");
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
		
		
		RoundedPanel MenuPanel = new RoundedPanel();
		MenuPanel.setBackground(new Color(212, 227, 252));
		MenuPanel.setForeground(new Color(255, 255, 255));
		MenuPanel.setBounds(10, 91, 214, 545);
		frame.getContentPane().add(MenuPanel);
		MenuPanel.setLayout(null);
		
		
		
		
		JButton AddTask = new RoundedButton("+ Task");
		AddTask.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JDialog Ask = new JDialog (frame,"Nueva Tarea");
				Ask.setSize(400,320);
				Ask.setLocation(400, 200);
				JPanel Paneldialog = new JPanel ();
				Paneldialog.setLayout(null);
				Paneldialog.setBounds(0, 0, 0, 0);
				
				JLabel NombreTarea = new JLabel("Nombre de la tarea");
				NombreTarea.setHorizontalAlignment(SwingConstants.CENTER);
				NombreTarea.setForeground(new Color(0,0,0));
				NombreTarea.setFont(new Font("Arial Rounded MT Bold",Font.BOLD,13));
				NombreTarea.setBounds(10, 10, 200, 50);
				Paneldialog.add(NombreTarea);
				
				JTextField Tnombre = new JTextField();
				Tnombre.setBorder(null);
				Tnombre.setBackground(new Color(255, 255, 255));
				Tnombre.setHorizontalAlignment(SwingConstants.LEFT);
				Tnombre.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));
				Tnombre.setForeground(new Color(0,0,0));
				Tnombre.setBounds(220, 10, 160, 50);
				Paneldialog.add(Tnombre);
				
				JLabel ProyectoTarea = new JLabel("Proyecto de la tarea");
				ProyectoTarea.setHorizontalAlignment(SwingConstants.CENTER);
				ProyectoTarea.setForeground(new Color(0,0,0));
				ProyectoTarea.setFont(new Font("Arial Rounded MT Bold",Font.BOLD,13));
				ProyectoTarea.setBounds(10, 70, 200, 50);
				Paneldialog.add(ProyectoTarea);
				
				JComboBox Proyectos = new JComboBox();
				Proyectos.setBorder(null);
				Proyectos.setEditable(true);
				Proyectos.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 13));
				Proyectos.setForeground(new Color(0,0,0));
				ArrayList<String> Listap=admin.ProjectNames();
				String[] opcionesp = new String[Listap.size()+1];
				for(int i=0; i<Listap.size();i++)
				{
					opcionesp[i]=Listap.get(i);
				}
				opcionesp[Listap.size()]="Nuevo Proyecto";
				Proyectos.setModel(new DefaultComboBoxModel(opcionesp));
				Proyectos.setSelectedIndex(0);
				Proyectos.setBackground(new Color(255, 255, 255));
				Proyectos.setBounds(220, 70, 160, 50);
				Paneldialog.add(Proyectos);
				
				JLabel ContextoTarea = new JLabel("Contexto de la tarea");
				ContextoTarea.setHorizontalAlignment(SwingConstants.CENTER);
				ContextoTarea.setForeground(new Color(0,0,0));
				ContextoTarea.setFont(new Font("Arial Rounded MT Bold",Font.BOLD,13));
				ContextoTarea.setBounds(10, 130, 200, 50);
				Paneldialog.add(ContextoTarea);
				
				JComboBox Contextos = new JComboBox();
				Contextos.setBorder(null);
				Contextos.setEditable(true);
				Contextos.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 13));
				Contextos.setForeground(new Color(0,0,0));
				ArrayList<String> Listac=admin.getPosibleContext();
				String[] opcionesc = new String[Listac.size()+1];
				for(int i=0; i<Listac.size();i++)
				{
					opcionesc[i]=Listac.get(i);
				}
				opcionesc[Listac.size()]="Nuevo Contexto";
				Contextos.setModel(new DefaultComboBoxModel(opcionesc));
				Contextos.setSelectedIndex(0);
				Contextos.setBackground(new Color(255, 255, 255));
				Contextos.setBounds(220, 130, 160, 50);
				Paneldialog.add(Contextos);
				
				JLabel FechaTarea = new JLabel("Fecha de termino");
				FechaTarea.setHorizontalAlignment(SwingConstants.CENTER);
				FechaTarea.setForeground(new Color(0,0,0));
				FechaTarea.setFont(new Font("Arial Rounded MT Bold",Font.BOLD,13));
				FechaTarea.setBounds(10, 190, 200, 50);
				Paneldialog.add(FechaTarea);
				
				JLabel DiaTarea = new JLabel("dia");
				DiaTarea.setHorizontalAlignment(SwingConstants.CENTER);
				DiaTarea.setForeground(new Color(0,0,0));
				DiaTarea.setFont(new Font("Arial Rounded MT Bold",Font.BOLD,13));
				DiaTarea.setBounds(220, 220, 30, 25);
				Paneldialog.add(DiaTarea);
				JLabel MesTarea = new JLabel("mes");
				MesTarea.setHorizontalAlignment(SwingConstants.CENTER);
				MesTarea.setForeground(new Color(0,0,0));
				MesTarea.setFont(new Font("Arial Rounded MT Bold",Font.BOLD,13));
				MesTarea.setBounds(260, 220, 30, 25);
				Paneldialog.add(MesTarea);
				JLabel AñoTarea = new JLabel("año");
				AñoTarea.setHorizontalAlignment(SwingConstants.CENTER);
				AñoTarea.setForeground(new Color(0,0,0));
				AñoTarea.setFont(new Font("Arial Rounded MT Bold",Font.BOLD,13));
				AñoTarea.setBounds(300, 220, 40, 25);
				Paneldialog.add(AñoTarea);
				JTextField Tdia = new JTextField();
				Tdia.setBorder(null);
				Tdia.setBackground(new Color(255, 255, 255));
				Tdia.setHorizontalAlignment(SwingConstants.LEFT);
				Tdia.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));
				Tdia.setForeground(new Color(0,0,0));
				Tdia.setBounds(220, 195, 30, 25);
				Paneldialog.add(Tdia);
				JTextField Tmes = new JTextField();
				Tmes.setBorder(null);
				Tmes.setBackground(new Color(255, 255, 255));
				Tmes.setHorizontalAlignment(SwingConstants.LEFT);
				Tmes.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));
				Tmes.setForeground(new Color(0,0,0));
				Tmes.setBounds(260, 195, 30, 25);
				Paneldialog.add(Tmes);
				JTextField Taño = new JTextField();
				Taño.setBorder(null);
				Taño.setBackground(new Color(255, 255, 255));
				Taño.setHorizontalAlignment(SwingConstants.LEFT);
				Taño.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));
				Taño.setForeground(new Color(0,0,0));
				Taño.setBounds(300, 195, 40, 25);
				Paneldialog.add(Taño);
				
				RoundedButton okbotom = new RoundedButton("OK");
				okbotom.setVerticalAlignment(SwingConstants.BOTTOM);
				okbotom.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) 
					{
						for (int i=0; i < admin.getProyects().size();i++)
						{
						if(admin.getProyects().get(i).getName()== Proyectos.getSelectedItem().toString())	
							admin.getProyects().get(i).AddTask(new Task(Tnombre.getText()));						
						}
						Ask.setVisible(false);
						Ask.dispose(); //para cerrar el dialogo una vez que se acepta
					}
				});
				okbotom.setForeground(new Color(153, 204, 255));
				okbotom.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));
				okbotom.setBackground(Color.WHITE);
				okbotom.setBounds(310,245 ,70 ,30 );
				Paneldialog.add(okbotom);
				
				Ask.getContentPane().add(Paneldialog);
				Ask.setVisible(true);
					

			}
		});
		AddTask.setVerticalAlignment(SwingConstants.BOTTOM);
		AddTask.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 16));
		AddTask.setBackground(new Color(255, 255, 255));
		AddTask.setForeground(new Color(153, 204, 255));
		
		AddTask.setBounds(25, 51, 147, 28);
		MenuPanel.add(AddTask);
		
		JLabel lblNewLabel = new JLabel("Menu");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setForeground(new Color(110, 160, 250));
		lblNewLabel.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 30));
		lblNewLabel.setBounds(49, 6, 99, 34);
		MenuPanel.add(lblNewLabel);
		
		RoundedButton rndbtnProyect = new RoundedButton("+ Project");
		rndbtnProyect.setVerticalAlignment(SwingConstants.BOTTOM);
		rndbtnProyect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				JDialog AddProject = new JDialog(frame,"Nuevo Proyecto");
				AddProject.setSize(320, 100);
				AddProject.setLocation(300,300);
				JPanel panelsin = new JPanel();
				panelsin.setBounds(0, 0, 0, 0);
				panelsin.setLayout(null);
				
				JTextField Pnombre = new JTextField();
				Pnombre.setText("Nombre Proyecto");
				Pnombre.setBounds(20, 20, 200, 50);
				
				RoundedButton okbotom = new RoundedButton("OK");
				okbotom.setVerticalAlignment(SwingConstants.BOTTOM);
				okbotom.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) 
					{
						admin.AddProyect(new Proyect(Pnombre.getText()));
						AddProject.setVisible(false);
						AddProject.dispose(); //pa cerrar el dialogo una vez que se acepta
						
					}
				});
				okbotom.setForeground(new Color(153, 204, 255));
				okbotom.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));
				okbotom.setBackground(Color.WHITE);
				okbotom.setBounds(240, 20, 50, 50);
				
				panelsin.add(Pnombre);
				panelsin.add(okbotom);
				AddProject.getContentPane().add(panelsin);
				AddProject.setVisible(true);
			}
		});
		rndbtnProyect.setForeground(new Color(153, 204, 255));
		rndbtnProyect.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 16));
		rndbtnProyect.setBackground(Color.WHITE);
		rndbtnProyect.setBounds(25, 88, 147, 28);
		MenuPanel.add(rndbtnProyect);
		
		txtSearch = new JTextField();
		txtSearch.setBorder(null);
		txtSearch.setBackground(new Color(255, 255, 255));
		txtSearch.setHorizontalAlignment(SwingConstants.LEFT);
		txtSearch.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 14));
		txtSearch.setForeground(new Color(102, 204, 204));
		txtSearch.setText("   Search...");
		txtSearch.setBounds(25, 127, 147, 34);
		MenuPanel.add(txtSearch);
		txtSearch.setColumns(10);
		
		JLabel GlosayLabel = new JLabel("Glosary");
		GlosayLabel.setHorizontalAlignment(SwingConstants.CENTER);
		GlosayLabel.setForeground(new Color(255, 255, 255));
		GlosayLabel.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 30));
		GlosayLabel.setBounds(34, 233, 128, 34);
		MenuPanel.add(GlosayLabel);
		
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
		comboBox.setBounds(25, 172, 147, 28);
		comboBox.addItem("Miselaneo");
		comboBox.addItem("patatas");
		MenuPanel.add(comboBox);
		
		JSeparator separator = new JSeparator();
		separator.setForeground(new Color(153, 204, 255));
		separator.setBackground(new Color(255, 255, 255));
		separator.setBounds(10, 217, 187, 2);
		MenuPanel.add(separator);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 297, 176, 162);
		MenuPanel.add(scrollPane);
		scrollPane.setBorder(BorderFactory.createEmptyBorder());
		
		JPanel GlosaryPanel = new JPanel();
		GlosaryPanel.setForeground(Color.WHITE);
		scrollPane.setViewportView(GlosaryPanel);
		GlosaryPanel.setLayout(new BoxLayout(GlosaryPanel, BoxLayout.Y_AXIS));
		GlosaryPanel.setBackground(new Color(212, 227, 252));
		
		
		RoundedButton rndbtnClases = new RoundedButton("+ Task");
		rndbtnClases.setAlignmentX(Component.CENTER_ALIGNMENT);
		GlosaryPanel.add(rndbtnClases);
		rndbtnClases.setText("Clases");
		rndbtnClases.setForeground(Color.WHITE);
		rndbtnClases.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 20));
		rndbtnClases.setBackground(new Color(0, 0, 102));
		
		JLabel lblNewLabel_1 = new JLabel("  ");
		GlosaryPanel.add(lblNewLabel_1);
		
		RoundedButton rndbtnMicelaneo = new RoundedButton("+ Task");
		rndbtnMicelaneo.setAlignmentX(Component.CENTER_ALIGNMENT);
		GlosaryPanel.add(rndbtnMicelaneo);
		rndbtnMicelaneo.setText("Micelaneo");
		rndbtnMicelaneo.setForeground(Color.WHITE);
		rndbtnMicelaneo.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 20));
		rndbtnMicelaneo.setBackground(new Color(102, 204, 153));
		
		JLabel label = new JLabel("  ");
		GlosaryPanel.add(label);
		
		RoundedButton roundedButton = new RoundedButton("+ Task");
		roundedButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		GlosaryPanel.add(roundedButton);
		roundedButton.setText("Trabajo");
		roundedButton.setForeground(Color.WHITE);
		roundedButton.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 20));
		roundedButton.setBackground(new Color(255, 204, 51));
		
		JLabel label_1 = new JLabel("  ");
		GlosaryPanel.add(label_1);
		
		RoundedPanel WhiteBase = new RoundedPanel();
		WhiteBase.setBounds(6, 69, 1005, 576);
		frame.getContentPane().add(WhiteBase);
		WhiteBase.setForeground(Color.DARK_GRAY);
		WhiteBase.setBackground(new Color(255, 255, 255));
		WhiteBase.setLayout(null);
		
		JPanel ProyectPanel = new RoundedPanel();
		ProyectPanel.setBounds(230, 64, 758, 121);
		WhiteBase.add(ProyectPanel);
		ProyectPanel.setBackground(new Color(212, 227, 252));
		ProyectPanel.setLayout(null);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(106, 6, 632, 109);
		scrollPane_1.setBackground(new Color(212, 227, 252));
		ProyectPanel.add(scrollPane_1);
		scrollPane_1.setBorder(BorderFactory.createEmptyBorder());
		scrollPane_1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.BLACK);
		scrollPane_1.setViewportView(panel_1);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.X_AXIS));
		
		JScrollPane scrollPane_3 = new JScrollPane();
		panel_1.add(scrollPane_3);
		scrollPane_3.setBorder(BorderFactory.createEmptyBorder());
		
		JPanel NodeGrid = new JPanel();
		scrollPane_3.setViewportView(NodeGrid);
		NodeGrid.setBackground(new Color(212, 227, 252));
		GridBagLayout gbl_NodeGrid = new GridBagLayout();
		gbl_NodeGrid.columnWidths = new int[] {76, 80, 79, 79, 79, 79, 79, 79, 79, 70, 70};
		gbl_NodeGrid.rowHeights = new int[] {10, 40, 10};
		gbl_NodeGrid.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_NodeGrid.rowWeights = new double[]{0.0, 0.0, 0.0};
		NodeGrid.setLayout(gbl_NodeGrid);
		
		JLabel lblNewLabel_2 = new JLabel("17/7");
		lblNewLabel_2.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 12));
		lblNewLabel_2.setForeground(new Color(0, 128, 128));
		lblNewLabel_2.setBackground(new Color(255, 250, 250));
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_2.gridx = 4;
		gbc_lblNewLabel_2.gridy = 0;
		NodeGrid.add(lblNewLabel_2, gbc_lblNewLabel_2);
		
		NodeButton nodeButton_5 = new NodeButton("New button");
		nodeButton_5.setForeground(new Color(255, 255, 255));
		GridBagConstraints gbc_nodeButton_5 = new GridBagConstraints();
		gbc_nodeButton_5.insets = new Insets(0, 0, 5, 5);
		gbc_nodeButton_5.gridx = 4;
		gbc_nodeButton_5.gridy = 1;
		NodeGrid.add(nodeButton_5, gbc_nodeButton_5);
		nodeButton_5.setBackground(new Color(255, 102, 51));
		nodeButton_5.setPreferredSize(new Dimension(50, 50));  //maximo 50 para que quepan, minimo 10 para que se vea
		nodeButton_5.setAlignmentX(0.5f);
		
		JLabel TaskLavel = new JLabel("Activity");
		TaskLavel.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 12));
		GridBagConstraints gbc_TaskLavel = new GridBagConstraints();
		TaskLavel.setForeground(new Color(112, 150, 252));
		gbc_TaskLavel.insets = new Insets(0, 0, 0, 5);
		gbc_TaskLavel.gridx = 4;
		gbc_TaskLavel.gridy = 2;
		NodeGrid.add(TaskLavel, gbc_TaskLavel);
		
		JLabel ProyectLabel = new JLabel("Tarea");
		ProyectLabel.setForeground(new Color(0, 128, 128));
		ProyectLabel.setHorizontalAlignment(SwingConstants.CENTER);
		ProyectLabel.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 18));
		ProyectLabel.setBounds(6, 45, 101, 31);
		ProyectPanel.add(ProyectLabel);
		
		JPanel TimeLinePane = new TimeLinePanel();
		TimeLinePane.setBounds(0, 69, 1026, 584);
		TimeLinePane.setBackground(new Color(0, 110, 142));
		frame.getContentPane().add(TimeLinePane);
		TimeLinePane.setVisible(false);
		TimeLinePane.setLayout(null);
		WhiteBase.setVisible(true);
		
		ProyectPanel prueba= new ProyectPanel("VamosCTM");
		prueba.setLocation(230, 222);
		
		
		Task t = new Task("PorfavorCTM");
		Proyect p = new Proyect("VamosCTM");
		p.AddTask(t);
		prueba.AddTask(0, t);
		WhiteBase.add(prueba);
		
		
	}
}
