import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.JFrame;

import java.awt.Color;

import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.SwingConstants;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.FocusEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.FocusAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JCheckBox;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JPanel;
import javax.swing.JDialog;
import javax.swing.JSeparator;
import javax.swing.ScrollPaneConstants;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import backend.*;

import java.awt.Dimension;

import javax.swing.JSlider;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class GUIBase {
	private JFrame frame;
	private JTextField txtSearch;
	private Administrator admin;
	private ArrayList<ProyectPanel> ProyectUI = new ArrayList<ProyectPanel>();
	private ArrayList<ProjectLine> Parreglo = new ArrayList<ProjectLine>();
	RoundedPanel WhiteBase = new RoundedPanel();
	RoundedPanel TaskDetail = new RoundedPanel();
	RoundedPanel WhiteBase2 = new RoundedPanel();
	JPanel GlosaryPanel = new JPanel();
	private JLabel Titulo;
	private Task SelectedTask=null;	//importante para poder editar las tareas
	private Proyect selectedProyect= null;
	private TimeLinePanel TimeLinePanel;
	private JScrollPane scrollMainView;
	private JScrollPane scrollTaskPane;
	private JScrollPane scrollTime = new JScrollPane();
	private boolean recovered = false;
	JLabel 	ProyectName;
	MailSender mailSender;
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
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public GUIBase() throws ClassNotFoundException, IOException {
		initialize();
	}
	private void CreateAdmin()
	{
		//crea el administrador (parte fundamental del backend) y crea un proyecto miscelaneo 
		//automaticamente para poder ingresar tareas desde un comienzo
		
		admin= new Administrator();
		Proyect p =new Proyect("Miscelaneo");
		p.setState(State.Delayed);
		admin.AddProyect(p);
		GlosaryPanel.setPreferredSize(new Dimension(GlosaryPanel.getPreferredSize().width,GlosaryPanel.getPreferredSize().height+45));
	}
	/**
	 * Initialize the contents of the frame.
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	private void initialize() throws ClassNotFoundException, IOException {
		
		try {
		    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		        if ("Nimbus".equals(info.getName())) {
		            UIManager.setLookAndFeel(info.getClassName());
		            break;
		            
		        }
		    }
		} catch (Exception e) {
		    System.out.println(":(");
		}
		
		File directory = new File("Data");
		File objectData = new File("Data/"+"Admin"+".data");
		if (directory.exists()&&objectData.exists()) 			// reccupera el administrador serializado
		{
			admin = (Administrator) deSerialize("Admin");
			TimeLinePanel = new TimeLinePanel(admin);
			for(int p=0; p<admin.getProyects().size();p++ )
			{
				Proyect proyect = admin.getProyects().get(p);
				ProyectPanel PP = new ProyectPanel(proyect.getName(),proyect);
				//PP.setColorName(p.getColor());
				ProjectLine PL = new ProjectLine(proyect,null);
				
				Parreglo.add(PL); //los projectLine se agragan en el mismo orden que los proyectPanel
				PL.setBounds(0,0,440,545);
				PL.setVisible(true);
				
				ProyectUI.add(PP);
				WhiteBase.add(PP);
				WhiteBase.setPreferredSize(new Dimension(WhiteBase.getPreferredSize().width, WhiteBase.getPreferredSize().height+155));
				WhiteBase.revalidate();
				GlosaryPanel.setPreferredSize(new Dimension(GlosaryPanel.getPreferredSize().width,
						GlosaryPanel.getPreferredSize().height+45));
				for(int t=0; t<proyect.getTasks().size();t++)
				{
					NodeButton n=PP.AddTask(proyect.getTasks().get(t));
					NodeButton n2 = new NodeButton(n.getTask().getName(), n.getTask());
					n.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							// TODO Auto-generated method stub
							WhiteBase.setVisible(false);
							scrollMainView.setVisible(false);
	                		WhiteBase2.setVisible(true);
	                		TaskDetail.setVisible(true);
	                		scrollTime.setVisible(false);
	                		frame.getContentPane().setComponentZOrder(scrollTime,2);
	                		frame.getContentPane().setComponentZOrder(scrollMainView,2);
	                		frame.getContentPane().setComponentZOrder(WhiteBase2,1);
	                		Parreglo.get(n.getTask().getProyectId()).SelectTask(n);
	                		scrollTaskPane.setViewportView(Parreglo.get(n.getTask().getProyectId()));
	                		SelectedTask = n.getTask();
	                		selectedProyect=null;
	                		DetallarTarea(SelectedTask);
	                		ProyectName.setText(proyect.getName());
	                		ProyectName.setForeground(proyect.getColor());
						}
					});
					PL.AddTask(n2);
					n2.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							// TODO Auto-generated method stub
							WhiteBase.setVisible(false);
							scrollMainView.setVisible(false);
	                		WhiteBase2.setVisible(true);
	                		TaskDetail.setVisible(true);
	                		scrollTime.setVisible(false);
	                		frame.getContentPane().setComponentZOrder(scrollTime,2);
	                		frame.getContentPane().setComponentZOrder(scrollMainView,2);
	                		frame.getContentPane().setComponentZOrder(WhiteBase2,1);
	                		Parreglo.get(n2.getTask().getProyectId()).SelectTask(n2);
	                		scrollTaskPane.setViewportView(Parreglo.get(n2.getTask().getProyectId()));
	                		SelectedTask = n2.getTask();
	                		selectedProyect=null;
	                		DetallarTarea(SelectedTask);
	                		ProyectName.setText(proyect.getName());
	                		ProyectName.setForeground(proyect.getColor());
						}
					});
					
					TimeLinePanel.AddTasks(n.getTask());
				}
				
				RoundedButton b_1= new RoundedButton(proyect.getName());
				b_1.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						WhiteBase.setVisible(false);
						scrollMainView.setVisible(false);
		        		WhiteBase2.setVisible(true);
		        		TaskDetail.setVisible(true);
		        		scrollTime.setVisible(false);
		        		frame.getContentPane().setComponentZOrder(scrollMainView,2);
		        		frame.getContentPane().setComponentZOrder(WhiteBase2,1);
		        		scrollTaskPane.setViewportView(Parreglo.get(admin.getProyects().indexOf(proyect)));
		        		ProyectName.setText(proyect.getName());
                		ProyectName.setForeground(proyect.getColor());
                		selectedProyect=proyect;
                		SelectedTask=null;
                		DetallarProyecto(PL.pro);
					}
				});
				b_1.shady=false;
				b_1.setBackground(proyect.getColor());
				b_1.setForeground(Color.WHITE);
				b_1.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 20));
				b_1.setBounds(10, 5+GlosaryPanel.getComponentCount()*45, 150, 35);
				GlosaryPanel.add(b_1);
			}
			for(int c=0;c<admin.getPosibleContext().size();c++)
			{
				///comunicacion backend
				Context context = admin.getPosibleContextColor().get(c);
				ProjectLine PL = new ProjectLine(null,context);
				Parreglo.add(PL); //los projectLine se agragan en el mismo orden que los proyectPanel
				PL.setBounds(0,0,440,545);
				PL.setVisible(true);
				
				//// agregar a glosario
				ContextButton cButton = new ContextButton(context);
				cButton.setBounds(10, 5+GlosaryPanel.getComponentCount()*45, 200, 35);
				cButton.addMouseListener(new MouseListener() {
					
					@Override
					public void mouseReleased(MouseEvent arg0) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void mousePressed(MouseEvent arg0) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void mouseExited(MouseEvent arg0) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void mouseEntered(MouseEvent arg0) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void mouseClicked(MouseEvent arg0) {
						// TODO Auto-generated method stub
						WhiteBase.setVisible(false);
                		WhiteBase2.setVisible(true);
                		TaskDetail.setVisible(true);
                		scrollMainView.setVisible(false);
                		scrollTime.setVisible(false);
                		frame.getContentPane().setComponentZOrder(scrollMainView,2);
                		frame.getContentPane().setComponentZOrder(WhiteBase2,1);
                		scrollTaskPane.setViewportView(PL);
                		ProyectName.setText(PL.context.getName());
                		ProyectName.setForeground(PL.context.getColor());
					}
				});
				GlosaryPanel.add(cButton);
				GlosaryPanel.setPreferredSize(new Dimension(GlosaryPanel.getPreferredSize().width,
				GlosaryPanel.getPreferredSize().height+45));
				
				////agregar tareas 
				for(int i = 0; i<context.getTasks().size();i++)
				{
					NodeButton n3 = new NodeButton("", context.getTasks().get(i));
					PL.AddTask(n3);
					n3.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							// TODO Auto-generated method stub
							SelectedTask = n3.getTask();
							selectedProyect=null;
	                		DetallarTarea(SelectedTask);
	                		PL.SelectTask(n3);
						}
					});
				}
			}
			recovered= true;
		}
		else
		{
			CreateAdmin();
		}
		
		
		//////////////////////////empieza
		
		mailSender = new MailSender("rorotm@hotmail.com");
		frame = new JFrame();
		
		frame.addWindowListener(new WindowListener() {
			
			@Override
			public void windowOpened(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowIconified(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowDeiconified(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowDeactivated(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowClosing(WindowEvent arg0) {
				// TODO Auto-generated method stub
				try {
					Serialize(admin, "Admin");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					System.out.println("Serializacion inconlusa");
				}
			}
			
			@Override
			public void windowClosed(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowActivated(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		frame.getContentPane().setBackground(new Color(0, 110, 142));
		frame.setBounds(100, 50, 1043, 697);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		JButton size = new JButton();
		size.setVisible(false);
		size.setSize(frame.getSize());
		frame.setVisible(false);
		
		scrollTaskPane= new JScrollPane();
		scrollTaskPane.setBounds(230,50, 510, 518);
		scrollTaskPane.setBorder(BorderFactory.createEmptyBorder());
		scrollTaskPane.setViewportBorder(BorderFactory.createEmptyBorder());
		scrollTaskPane.setOpaque(false);
		scrollTaskPane.getViewport().setOpaque(false);
		scrollTaskPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollTaskPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollTaskPane.getVerticalScrollBar().setUI(new myScrollBarUI('V'));
		ProyectName = new JLabel("Micelaneo");
		ProyectName.setHorizontalAlignment(JLabel.CENTER);
		ProyectName.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 25));
		ProyectName.setForeground(new Color(0,110,142));
		ProyectName.setBounds(264, 0, 471, 50);
		WhiteBase2.add(ProyectName);
		
		
		
		//esta parte se usara para hacer que todo se mueva junto al agrandar la ventana. No es requerimiento. Po ahora dejo el frame sin movilidad
		/*
		frame.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				// TODO Auto-generated method stub
			JFrame frame=(JFrame)e.getComponent();
			int Xbase = size.getSize().width;
			int ybase = size.getSize().height;
			super.componentResized(e);
			size.setSize(frame.getSize());
			double XchangeRate = (double)frame.getSize().width/(double)Xbase;
			double YchangeRate = (double)frame.getSize().height/(double)ybase;
				for(Component c : frame.getContentPane().getComponents())
				{
					int prueba = (int)(c.getSize().width*XchangeRate);
					
					c.setSize((int)(c.getSize().width*XchangeRate),(int)(c.getSize().height*YchangeRate));
					Dimension d= new Dimension((int)(c.getSize().width*XchangeRate),(int)(c.getSize().height*YchangeRate));
					c.setPreferredSize(d);
					c.validate();
					c.repaint();
				}
			}
		});
			
			*/	
		if(!recovered)
		{
			JButton MiselaneoItem = new RoundedButton("Miselaneo");
			MiselaneoItem.setVerticalAlignment(SwingConstants.BOTTOM);
			MiselaneoItem.setText("+ Proyect  ");
			MiselaneoItem.setForeground(new Color(153, 204, 255));
			MiselaneoItem.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 16));
			MiselaneoItem.setBackground(Color.WHITE);
			MiselaneoItem.setBounds(33, 83, 147, 48);
			RoundedButton b_1= new RoundedButton("Miscelaneo");
			b_1.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					WhiteBase.setVisible(false);
					scrollMainView.setVisible(false);
	        		WhiteBase2.setVisible(true);
	        		TaskDetail.setVisible(true);
	        		scrollTime.setVisible(false);
	        		frame.getContentPane().setComponentZOrder(scrollMainView,2);
	        		frame.getContentPane().setComponentZOrder(WhiteBase2,1);
	        		scrollTaskPane.setViewportView(Parreglo.get(0));
	        		ProyectName.setText(Parreglo.get(0).pro.getName());
            		ProyectName.setForeground(admin.getProyects().get(0).getColor());
				}
			});
			b_1.shady=false;
			b_1.setBackground(admin.getProyects().get(0).getColor());
			b_1.setForeground(Color.WHITE);
			b_1.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 20));
			b_1.setBounds(10, 5+GlosaryPanel.getComponentCount()*45, 150, 35);
			GlosaryPanel.add(b_1);
		}
		
		
		RoundedPanel MenuPanel = new RoundedPanel();
		MenuPanel.arcs= new Dimension(10,10);
		MenuPanel.shady=false;
		MenuPanel.setBounds(15, 80, 214, 545);
		frame.getContentPane().add(MenuPanel);
		frame.getContentPane().setComponentZOrder(MenuPanel, 0);
		MenuPanel.setBackground(new Color(212, 227, 252));
		MenuPanel.setForeground(new Color(255, 255, 255));
		MenuPanel.setLayout(null);
		
		
		JButton AddTask = new RoundedButton("+ Task"); 
		AddTask.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JDialog Ask = new JDialog (frame,"Nueva Tarea");
				Ask.setSize(400,380);
				Ask.setLocation(400, 200);
				JPanel Paneldialog = new JPanel ();
				Paneldialog.setLayout(null);
				Paneldialog.setBounds(0, 0, 0, 0);
				
				JLabel NombreTarea = new JLabel("Nombre de la tarea");
				NombreTarea.setHorizontalAlignment(SwingConstants.CENTER);
				NombreTarea.setForeground(new Color(0,110,141));
				NombreTarea.setFont(new Font("Arial Rounded MT Bold",Font.BOLD,13));
				NombreTarea.setBounds(10, 10, 200, 50);
				Paneldialog.add(NombreTarea);
				
				JTextField Tnombre = new JTextField();
				Tnombre.setBorder(BorderFactory.createLineBorder(new Color(1,110,141),2));
				Tnombre.setBackground(new Color(255, 255, 255));
				Tnombre.setHorizontalAlignment(SwingConstants.LEFT);
				Tnombre.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));
				Tnombre.setForeground(new Color(0,110,141));
				Tnombre.setBounds(220, 10, 160, 50);
				
				Paneldialog.add(Tnombre);
				
				
				JLabel ProyectoTarea = new JLabel("Proyecto de la tarea");
				ProyectoTarea.setHorizontalAlignment(SwingConstants.CENTER);
				ProyectoTarea.setForeground(new Color(0,110,141));
				ProyectoTarea.setFont(new Font("Arial Rounded MT Bold",Font.BOLD,13));
				ProyectoTarea.setBounds(10, 70, 200, 50);
				Paneldialog.add(ProyectoTarea);
				
				JComboBox Proyectos = new JComboBox();
				Proyectos.setBorder(null);
				Proyectos.setEditable(false);
				Proyectos.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 13));
				Proyectos.setForeground(new Color(213,227,254));
				ArrayList<String> Listap=admin.ProjectNames();
				String[] opcionesp = new String[Listap.size()+1];
				for(int i=0; i<Listap.size();i++)
				{
					opcionesp[i]=Listap.get(i);
				}
				Proyectos.setModel(new DefaultComboBoxModel(opcionesp));
				Proyectos.setSelectedIndex(0);
				Proyectos.setBackground(new Color(255, 255, 255));
				Proyectos.setBounds(220, 70, 160, 50);
				Paneldialog.add(Proyectos);
				
				JLabel ContextoTarea = new JLabel("Contexto de la tarea");
				ContextoTarea.setHorizontalAlignment(SwingConstants.CENTER);
				ContextoTarea.setForeground(new Color(0,110,141));
				ContextoTarea.setFont(new Font("Arial Rounded MT Bold",Font.BOLD,13));
				ContextoTarea.setBounds(10, 130, 200, 50);
				Paneldialog.add(ContextoTarea);
				
				JComboBox Contextos = new JComboBox();
				Contextos.setBorder(null);
				Contextos.setEditable(false);
				Contextos.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 13));
				Contextos.setForeground(new Color(213,227,254));
				ArrayList<String> Listac=admin.getPosibleContext();
				String[] opcionesc = new String[Listac.size()];
				for(int i=0; i<Listac.size();i++)
				{
					opcionesc[i]=Listac.get(i);
				}
				Contextos.setModel(new DefaultComboBoxModel(opcionesc));
				Contextos.setSelectedIndex(0);
				Contextos.setBackground(new Color(255, 255, 255));
				Contextos.setBounds(220, 130, 160, 50);
				Paneldialog.add(Contextos);
				
				JLabel FechaTarea = new JLabel("Fecha de termino");
				FechaTarea.setHorizontalAlignment(SwingConstants.CENTER);
				FechaTarea.setForeground(new Color(0,110,141));
				FechaTarea.setFont(new Font("Arial Rounded MT Bold",Font.BOLD,13));
				FechaTarea.setBounds(10, 190, 200, 50);
				Paneldialog.add(FechaTarea);
				
				JLabel DiaTarea = new JLabel("dia");
				DiaTarea.setHorizontalAlignment(SwingConstants.CENTER);
				DiaTarea.setForeground(new Color(0,110,141));
				DiaTarea.setFont(new Font("Arial Rounded MT Bold",Font.BOLD,13));
				DiaTarea.setBounds(220, 220, 30, 25);
				Paneldialog.add(DiaTarea);
				JLabel MesTarea = new JLabel("mes");
				MesTarea.setHorizontalAlignment(SwingConstants.CENTER);
				MesTarea.setForeground(new Color(0,110,141));
				MesTarea.setFont(new Font("Arial Rounded MT Bold",Font.BOLD,13));
				MesTarea.setBounds(260, 220, 30, 25);
				Paneldialog.add(MesTarea);
				JLabel AñoTarea = new JLabel("año");
				AñoTarea.setHorizontalAlignment(SwingConstants.CENTER);
				AñoTarea.setForeground(new Color(0,110,141));
				AñoTarea.setFont(new Font("Arial Rounded MT Bold",Font.BOLD,13));
				AñoTarea.setBounds(300, 220, 40, 25);
				Paneldialog.add(AñoTarea);
				JTextField Tdia = new JTextField();
				Tdia.setBorder(BorderFactory.createLineBorder(new Color(1,110,141),2));
				Tdia.setBackground(new Color(255, 255, 255));
				Tdia.setHorizontalAlignment(SwingConstants.LEFT);
				Tdia.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));
				Tdia.setForeground(new Color(0,110,141));
				Tdia.setBounds(220, 195, 30, 25);
				Paneldialog.add(Tdia);
				JTextField Tmes = new JTextField();
				Tmes.setBorder(BorderFactory.createLineBorder(new Color(1,110,141),2));
				Tmes.setBackground(new Color(255, 255, 255));
				Tmes.setHorizontalAlignment(SwingConstants.LEFT);
				Tmes.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));
				Tmes.setForeground(new Color(0,110,141));
				Tmes.setBounds(260, 195, 30, 25);
				Paneldialog.add(Tmes);
				JTextField Taño = new JTextField();
				Taño.setBorder(BorderFactory.createLineBorder(new Color(1,110,141),2));
				Taño.setBackground(new Color(255, 255, 255));
				Taño.setHorizontalAlignment(SwingConstants.LEFT);
				Taño.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));
				Taño.setForeground(new Color(0,110,141));
				Taño.setBounds(300, 195, 40, 25);
				Paneldialog.add(Taño);
				
				
				JComboBox Importancia = new  JComboBox();
				Importancia.setBorder(null);
				Importancia.setEditable(false);
				Importancia.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 13));
				Importancia.setForeground(new Color(213,227,254));
				String[] bla = new String[3];
				bla[0]="Normal";
				bla[1]="Importante";
				bla[2]="Muy Importante";
				Importancia.setModel(new DefaultComboBoxModel(bla));
				Importancia.setSelectedIndex(0);
				Importancia.setBackground(new Color(255,255,255));
				Importancia.setBounds(220, 250, 160, 50);
				Paneldialog.add(Importancia);
				JLabel imp = new JLabel("Importancia");
				imp.setHorizontalAlignment(SwingConstants.CENTER);
				imp.setForeground(new Color(0,110,141));
				imp.setFont(new Font("Arial Rounded MT Bold",Font.BOLD,13));
				imp.setBounds(10, 250, 200, 
						50);
				Paneldialog.add(imp);
				
				RoundedButton okbotom = new RoundedButton("OK");
				Ask.getRootPane().setDefaultButton(okbotom);
				okbotom.setVerticalAlignment(SwingConstants.BOTTOM); //////////////////+ Tarea
				okbotom.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) 
					{
						for (int i=0; i < admin.getProyects().size();i++)
						{
							if(admin.getProyects().get(i).getName()== Proyectos.getSelectedItem().toString())
							{
								Task t = new Task(Tnombre.getText());
								Timer StateCheck = new Timer(10000,new ActionListener() {
								
									@Override
									public void actionPerformed(ActionEvent arg0) {
										// TODO Auto-generated method stub
										if(t.getChange())
										{
											t.isCheck(1);
											
											DateFormat format2=new SimpleDateFormat("EEEE"); 
											String finalDay=format2.format(t.getDeadline().getTime());
											JOptionPane.showMessageDialog(null, "Recuerde que el plazo de la tarea "+t.getName()+" vencio el día "+finalDay+
													" "+t.getDeadline().get(Calendar.DAY_OF_MONTH)+"/"+(t.getDeadline().get(Calendar.MONTH)+1)+"/"+
													t.getDeadline().get(Calendar.YEAR),	
													"Recordatorio Vencimiento", JOptionPane.INFORMATION_MESSAGE);
											mailSender.sendMail(t);
											
										}
									}
								});
								StateCheck.setInitialDelay(1000);
								StateCheck.start();
								try {
									Thread.sleep(100);
								} catch (InterruptedException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
								try{
								Calendar c = Calendar.getInstance();
								c.clear();
								if(Integer.parseInt(Tmes.getText())>12|| Integer.parseInt(Tdia.getText())>31)
									throw new Exception();
								c.set(Integer.parseInt(Taño.getText()), Integer.parseInt(Tmes.getText())-1, Integer.parseInt(Tdia.getText()));
								t.setDeadline(c);
								}
								catch(Exception ex)
								{
									JOptionPane.showMessageDialog(null, "Recuerde ingresar una fecha cuando pueda",	"Recordatorio de Fecha", JOptionPane.INFORMATION_MESSAGE);
									t.setRelevance(Importancia.getSelectedIndex());
									Calendar c = Calendar.getInstance();
									c.clear();
									c.set(10000, 0, 0);
									t.setDeadline(c);
									Ask.setVisible(false);
									Ask.dispose();
								}
								t.setProyectId(i);
								t.setRelevance(Importancia.getSelectedIndex());
								Context context = admin.AddContext((String)Contextos.getSelectedItem());
								t.setContext(context);
								context.AddTask(t);
								t.setColor(admin.getProyects().get(i).getColor());
								admin.getProyects().get(i).AddTask(t);
								NodeButton n =ProyectUI.get(i).AddTask(t); //es importante que los proyectos se agreguen logica y visualmente en el mismo orden
								NodeButton n2 = new NodeButton(n.getText(),n.getTask());
								NodeButton n3 = new NodeButton(n.getText(),n.getTask());
								n2.setSize(n.getSize());
								n3.setSize(n.getSize());
								Parreglo.get(i).AddTask(n2);
								Parreglo.get(admin.getPosibleContextColor().indexOf(context)+admin.getProyects().size()).AddTask(n3);
				                n.addActionListener(new ActionListener() {
									
									@Override
									public void actionPerformed(ActionEvent e) {
										// TODO Auto-generated method stub
										WhiteBase.setVisible(false);
										scrollMainView.setVisible(false);
				                		WhiteBase2.setVisible(true);
				                		TaskDetail.setVisible(true);
				                		scrollTime.setVisible(false);
				                		frame.getContentPane().setComponentZOrder(scrollMainView,2);
				                		frame.getContentPane().setComponentZOrder(WhiteBase2,1);
				                		Parreglo.get(n.getTask().getProyectId()).SelectTask(n);
				                		scrollTaskPane.setViewportView(Parreglo.get(n.getTask().getProyectId()));
				                		SelectedTask = n.getTask();
				                		selectedProyect=null;
				                		DetallarTarea(SelectedTask);
				                		ProyectName.setText(Parreglo.get(n.getTask().getProyectId()).pro.getName());
				                		ProyectName.setForeground(Parreglo.get(n.getTask().getProyectId()).pro.getColor());
									}
								});
				                n2.addActionListener(new ActionListener() {
									
									@Override
									public void actionPerformed(ActionEvent e) {
										// TODO Auto-generated method stub
										WhiteBase.setVisible(false);
										scrollMainView.setVisible(false);
				                		WhiteBase2.setVisible(true);
				                		TaskDetail.setVisible(true);
				                		scrollTime.setVisible(false);
				                		frame.getContentPane().setComponentZOrder(scrollMainView,2);
				                		frame.getContentPane().setComponentZOrder(WhiteBase2,1);
				                		Parreglo.get(n2.getTask().getProyectId()).SelectTask(n2);
				                		scrollTaskPane.setViewportView(Parreglo.get(n2.getTask().getProyectId()));
				                		SelectedTask = n2.getTask();
				                		selectedProyect=null;
				                		DetallarTarea(SelectedTask);
				                		ProyectName.setText(Parreglo.get(n2.getTask().getProyectId()).pro.getName());
				                		ProyectName.setForeground(Parreglo.get(n.getTask().getProyectId()).pro.getColor());
									}
								});
				                TimeLinePanel.AddTasks(t);
								
							}
							
						}
						
						
						Ask.setVisible(false);
						Ask.dispose(); //para cerrar el dialogo una vez que se acepta
					}
				});
				okbotom.setForeground(new Color(153, 204, 255));
				okbotom.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));
				okbotom.setBackground(Color.WHITE);
				okbotom.setBounds(310,305 ,70 ,30 );
				Paneldialog.add(okbotom);
				Paneldialog.setBackground(new Color(213,227,254));
				Ask.getContentPane().add(Paneldialog);
				Ask.setResizable(false);
				Ask.setVisible(true);
					

			}
		});
		AddTask.setVerticalAlignment(SwingConstants.BOTTOM);
		AddTask.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 16));
		AddTask.setBackground(new Color(255, 255, 255));
		AddTask.setForeground(new Color(153, 204, 255));
		
		AddTask.setBounds(30, 60, 147, 28);
		MenuPanel.add(AddTask);
		
		
		JButton Suggest = new RoundedButton("Sugerencias");
		Suggest.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				JDialog UrgenciaOPlan = new JDialog (frame,"criterio de sugerencia");
				UrgenciaOPlan.setSize(325,150);
				UrgenciaOPlan.setLocation(400, 350);
				JPanel Paneldia = new JPanel();
				Paneldia.setLayout(null);
				Paneldia.setBounds(0, 0, 0, 0);
				
				JButton U = new JButton("Urgentes");
				U.setBounds(25, 20, 125, 60);
				U.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						String[] contenido = admin.SugerenciaUrgencia();
						JDialog Mostrar = new JDialog(frame,"Tareas Urgentes");
						Mostrar.setVisible(true);
						Mostrar.setSize(500,500);
						Mostrar.setLocation(300,300);
						JPanel pa = new JPanel();
						pa.setLayout(null);
						pa.setBounds(0,0,0,0);
						Mostrar.getContentPane().add(pa);
						JScrollPane s1 = new JScrollPane(); 
						s1.setBounds(10,20,480,230);
						s1.setBorder(BorderFactory.createEmptyBorder());
						s1.setViewportBorder(BorderFactory.createEmptyBorder());
						s1.setOpaque(false);
						s1.getViewport().setOpaque(false);
						s1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
						s1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
						JPanel pa1 = new JPanel();
						pa1.setLayout(null);
						JLabel sugeridas = new JLabel(contenido[0]);
						pa1.add(sugeridas);
						s1.setViewportView(pa1);
						pa.add(s1);
						
						JScrollPane s2 = new JScrollPane(); 
						s2.setBounds(10,250,480,230);
						s2.setBorder(BorderFactory.createEmptyBorder());
						s2.setViewportBorder(BorderFactory.createEmptyBorder());
						s2.setOpaque(false);
						s2.getViewport().setOpaque(false);
						s2.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
						s2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
						JPanel pa2 = new JPanel();
						pa2.setLayout(null);
						JLabel requisitos = new JLabel(contenido[1]);
						pa2.add(requisitos);
						s2.setViewportView(pa2);
						pa.add(s2);
						
						UrgenciaOPlan.setVisible(false);
						UrgenciaOPlan.dispose();
					}
				});
				
				JButton P = new JButton("planificación");
				P.setBounds(175, 20, 125, 60);
				P.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						String[] contenido = admin.SugerenciaPlanificada();
						JDialog Mostrar = new JDialog(frame,"Planificacion por contexto");
						Mostrar.setSize(500,500);
						Mostrar.setLocation(300,300);
						JPanel pa = new JPanel();
						pa.setLayout(null);
						pa.setBounds(0,0,0,0);
						Mostrar.getContentPane().add(pa);
						JScrollPane s1 = new JScrollPane(); 
						s1.setBounds(10,20,480,230);
						s1.setBorder(BorderFactory.createEmptyBorder());
						s1.setViewportBorder(BorderFactory.createEmptyBorder());
						s1.setOpaque(false);
						s1.getViewport().setOpaque(false);
						s1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
						s1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
						JPanel pa1 = new JPanel();
						pa1.setLayout(null);
						JLabel sugeridas = new JLabel(contenido[0]);
						pa1.add(sugeridas);
						s1.setViewportView(pa1);
						pa.add(s1);
						
						JScrollPane s2 = new JScrollPane(); 
						s2.setBounds(10,250,480,230);
						s2.setBorder(BorderFactory.createEmptyBorder());
						s2.setViewportBorder(BorderFactory.createEmptyBorder());
						s2.setOpaque(false);
						s2.getViewport().setOpaque(false);
						s2.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
						s2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
						JPanel pa2 = new JPanel();
						pa2.setLayout(null);
						JLabel requisitos = new JLabel(contenido[1]);
						pa2.add(requisitos);
						s2.setViewportView(pa2);
						pa.add(s2);
					
						UrgenciaOPlan.setVisible(false);
						UrgenciaOPlan.dispose();
					}
				});
				
				U.setForeground(new Color(153, 204, 255));
				P.setForeground(new Color(153, 204, 255));
				U.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));
				P.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));
				U.setBackground(Color.WHITE);
				P.setBackground(Color.WHITE);
				Paneldia.add(P);
				Paneldia.add(U);
				Paneldia.setBackground(new Color(210,227,253));
				UrgenciaOPlan.getContentPane().add(Paneldia);
				UrgenciaOPlan.setVisible(true);
			}
		});
		Suggest.setBounds(30, 180, 147, 28);
		Suggest.setVerticalAlignment(SwingConstants.BOTTOM);
		Suggest.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 16));
		Suggest.setBackground(new Color(255, 255, 255));
		Suggest.setForeground(new Color(153, 204, 255));
		MenuPanel.add(Suggest);

		
		JLabel lblNewLabel = new JLabel("Menu");
		lblNewLabel.setOpaque(true);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 30));
		lblNewLabel.setBackground(new Color(212, 227, 252));
		lblNewLabel.setBounds(2, 21, 210, 28);
		MenuPanel.add(lblNewLabel);
		
		RoundedButton rndbtnProyect = new RoundedButton("+ Project");
		rndbtnProyect.setVerticalAlignment(SwingConstants.BOTTOM);
		rndbtnProyect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				JDialog AddProject = new JDialog(frame,"Nuevo Proyecto");
				
				AddProject.setSize(320, 230);
				AddProject.setLocation(500,300);
				JPanel panelsin = new JPanel();
				panelsin.setBounds(0, 0, 0, 0);
				panelsin.setLayout(null);
				
				JTextField Pnombre = new JTextField();
				Pnombre.setText("Nombre Proyecto");
				Pnombre.setBounds(20, 20, 200, 50);
				Pnombre.setForeground(new Color(0,111,141));
				Pnombre.setBorder(BorderFactory.createLineBorder(new Color(0,110,141)));
				Pnombre.addMouseListener(new MouseListener() {
					
					@Override
					public void mouseReleased(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void mousePressed(MouseEvent e) {
						// TODO Auto-generated method stub
						
						
					}
					
					@Override
					public void mouseExited(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void mouseEntered(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void mouseClicked(MouseEvent e) {
						// TODO Auto-generated method stub
						Pnombre.setText("");
					}
				});
				
				JTextField dia = new JTextField();
				dia.setBounds(110, 90, 30, 20);
				dia.setForeground(new Color(0,111,141));
				dia.setBorder(BorderFactory.createLineBorder(new Color(0,110,141)));
				JTextField mes = new JTextField();
				mes.setBounds(150, 90, 30, 20);
				mes.setForeground(new Color(0,111,141));
				mes.setBorder(BorderFactory.createLineBorder(new Color(0,110,141)));
				JTextField ano = new JTextField();
				ano.setBounds(190, 90, 30, 20);
				ano.setForeground(new Color(0,111,141));
				ano.setBorder(BorderFactory.createLineBorder(new Color(0,110,141)));
				AddProject.add(dia);
				AddProject.add(mes);
				AddProject.add(ano);
				
				JLabel fecha= new JLabel("Fecha :");
				fecha.setBounds(20, 87, 70, 30);
				fecha.setBackground(new Color(210,227,254));
				fecha.setForeground(new Color(0,111,141));
				fecha.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 16));
				AddProject.add(fecha);
				
				RoundedButton okbotom = new RoundedButton("OK");
				AddProject.getRootPane().setDefaultButton(okbotom);
				okbotom.setVerticalAlignment(SwingConstants.BOTTOM);
				okbotom.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) 			////////////////////+ Proyecto
					{
						///comunicacion backend
						Proyect p = new Proyect(Pnombre.getText());
						p.setState(State.Active);
						try{
							Calendar c = Calendar.getInstance();
							c.clear();
							if(Integer.parseInt(mes.getText())>12|| Integer.parseInt(dia.getText())>31)
								throw new Exception();
							c.set(Integer.parseInt(ano.getText()), Integer.parseInt(mes.getText())-1, Integer.parseInt(dia.getText()));
							p.setDeadline(c);
							}
							catch(Exception ex)
							{
								JOptionPane.showMessageDialog(null, "Recuerde ingresar una fecha cuando pueda",	"Recordatorio de Fecha", JOptionPane.INFORMATION_MESSAGE);
								Calendar c = Calendar.getInstance();
								c.clear();
								c.set(10000, 0, 0);
								p.setDeadline(c);
							}
						if(admin.AddProyect(p))
						{
						////Agregar a interfaz
						ProyectPanel PP = new ProyectPanel(Pnombre.getText(),p);
						//PP.setColorName(p.getColor());
						ProjectLine PL = new ProjectLine(p,null);
						
						Parreglo.add(admin.getProyects().size()-1,PL); //los projectLine se agragan en el mismo orden que los proyectPanel
						PL.setBounds(0,0,440,545);
						PL.setVisible(true);
						
						ProyectUI.add(PP);
						WhiteBase.add(PP);
						WhiteBase.setPreferredSize(new Dimension(WhiteBase.getPreferredSize().width, WhiteBase.getPreferredSize().height+155));
						WhiteBase.revalidate();
						WhiteBase.repaint();
						AddProject.setVisible(false);
						AddProject.dispose(); //pa cerrar el dialogo una vez que se acepta
						
						//// agregar a glosario
						RoundedButton b= new RoundedButton(Pnombre.getText());
						b.shady=false;
						b.setBackground(p.getColor());
						b.setForeground(Color.WHITE);
						b.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 20));
						b.setBounds(10, 5+(admin.getProyects().size()-1)*45, 150, 35);
						GlosaryPanel.add(b,admin.getProyects().size()-1);
						for(int i = admin.getProyects().size();i<GlosaryPanel.getComponentCount();i++)
						{
							Component c= GlosaryPanel.getComponent(i);
							c.setBounds(10, 5+i*45, 150, 35);
						}
						b.addActionListener(new ActionListener() {
					
							@Override
							public void actionPerformed(ActionEvent e) {
								// TODO Auto-generated method stub
								WhiteBase.setVisible(false);
		                		WhiteBase2.setVisible(true);
		                		TaskDetail.setVisible(true);
		                		scrollMainView.setVisible(false);
		                		scrollTime.setVisible(false);
		                		frame.getContentPane().setComponentZOrder(scrollMainView,2);
		                		frame.getContentPane().setComponentZOrder(WhiteBase2,1);
		                		scrollTaskPane.setViewportView(PL);
		                		ProyectName.setText(PL.pro.getName());
		                		ProyectName.setForeground(PL.pro.getColor());
		                		selectedProyect=PL.pro;
		                		DetallarProyecto(PL.pro);
		                		SelectedTask=null;
							}
						});
						
						GlosaryPanel.setPreferredSize(new Dimension(GlosaryPanel.getPreferredSize().width,
								GlosaryPanel.getPreferredSize().height+45));
						frame.revalidate();
						frame.repaint();
						}
						else
						{
							JDialog aviso = new JDialog(frame,"Warning");
							JButton close = new JButton();
							close.addActionListener(new ActionListener() {
								
								@Override
								public void actionPerformed(ActionEvent e) {
									// TODO Auto-generated method stub
									aviso.setVisible(false);
									aviso.dispose();
									AddProject.requestFocus();
								}
							});
							aviso.getContentPane().add(close);
							aviso.setSize(300,100);
							aviso.setLocation(310,310);
							JPanel panelaviso = new JPanel();
							panelaviso.setBounds(0,0,0,0);
							panelaviso.setLayout(null);
							JLabel Nosepuede = new JLabel("Ya existe un proyecto con ese nombre");
							Nosepuede.setHorizontalAlignment(SwingConstants.CENTER);
							Nosepuede.setForeground(new Color(0,0,0));
							Nosepuede.setFont(new Font("Arial Rounded MT Bold",Font.BOLD,11));
							Nosepuede.setBounds(10, 10, 250, 40);
							panelaviso.add(Nosepuede);
							aviso.getContentPane().add(panelaviso);
							aviso.setVisible(true);
							aviso.getRootPane().setDefaultButton(close);
							
						}
						
					}
				});
				okbotom.setForeground(new Color(153, 204, 255));
				okbotom.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));
				okbotom.setBackground(Color.WHITE);
				okbotom.setBounds(240, 35, 50, 30);
				
				panelsin.add(Pnombre);
				panelsin.add(okbotom);
				panelsin.setBackground(new Color(210,227,254));
				AddProject.getContentPane().add(panelsin);
				AddProject.setVisible(true);
			}
		});
		rndbtnProyect.setForeground(new Color(153, 204, 255));
		rndbtnProyect.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 16));
		rndbtnProyect.setBackground(Color.WHITE);
		rndbtnProyect.setBounds(30, 94, 147, 28);
		MenuPanel.add(rndbtnProyect);
		
		
		RoundedButton addContext = new RoundedButton("+ Context");
		addContext.setVerticalAlignment(SwingConstants.BOTTOM);
		addContext.setForeground(new Color(153, 204, 255));
		addContext.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 16));
		addContext.setBackground(Color.WHITE);
		addContext.setBounds(30, 128, 147, 28);
		MenuPanel.add(addContext);
		addContext.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) 
			{
				JDialog AddContext= new JDialog(frame,"Nuevo Proyecto");
				
				AddContext.setSize(320, 150);
				AddContext.setLocation(300,300);
				JPanel panelsin = new JPanel();
				panelsin.setBounds(0, 0, 0, 0);
				panelsin.setLayout(null);
				
				JTextField Cnombre = new JTextField();
				Cnombre.setText("Nombre Contexto");
				Cnombre.setBounds(20, 20, 200, 50);
				Cnombre.setBorder(BorderFactory.createLineBorder(new Color(0,111,141)));
				Cnombre.setForeground(new Color(0,110,141));
				Cnombre.addMouseListener(new MouseListener() {
					
					@Override
					public void mouseReleased(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void mousePressed(MouseEvent e) {
						// TODO Auto-generated method stub
						
						
					}
					
					@Override
					public void mouseExited(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void mouseEntered(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void mouseClicked(MouseEvent e) {
						// TODO Auto-generated method stub
						Cnombre.setText("");
					}
				});
				
				RoundedButton okbotom = new RoundedButton("OK");
				AddContext.getRootPane().setDefaultButton(okbotom);
				okbotom.setVerticalAlignment(SwingConstants.BOTTOM);
				okbotom.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) 			////////////////////+ Contexto
					{
						///comunicacion backend
						Context c = admin.AddContext(Cnombre.getText());;
						
						ProjectLine PL = new ProjectLine(null,c);
						
						Parreglo.add(PL); 
						PL.setBounds(0,0,440,545);
						PL.setVisible(true);
						
						AddContext.setVisible(false);
						AddContext.dispose(); //pa cerrar el dialogo una vez que se acepta
						
						//// agregar a glosario
						ContextButton cButton = new ContextButton(c);
						cButton.setBounds(10, 5+GlosaryPanel.getComponentCount()*45, 200, 35);
						cButton.addMouseListener(new MouseListener() {
							
							@Override
							public void mouseReleased(MouseEvent arg0) {
								// TODO Auto-generated method stub
								
							}
							
							@Override
							public void mousePressed(MouseEvent arg0) {
								// TODO Auto-generated method stub
								
							}
							
							@Override
							public void mouseExited(MouseEvent arg0) {
								// TODO Auto-generated method stub
								
							}
							
							@Override
							public void mouseEntered(MouseEvent arg0) {
								// TODO Auto-generated method stub
								
							}
							
							@Override
							public void mouseClicked(MouseEvent arg0) {
								// TODO Auto-generated method stub
								WhiteBase.setVisible(false);
		                		WhiteBase2.setVisible(true);
		                		TaskDetail.setVisible(true);
		                		scrollMainView.setVisible(false);
		                		scrollTime.setVisible(false);
		                		frame.getContentPane().setComponentZOrder(scrollMainView,2);
		                		frame.getContentPane().setComponentZOrder(WhiteBase2,1);
		                		scrollTaskPane.setViewportView(PL);
		                		ProyectName.setText(PL.context.getName());
		                		ProyectName.setForeground(PL.context.getColor());
		                		
							}
						});
						GlosaryPanel.add(cButton);
						GlosaryPanel.setPreferredSize(new Dimension(GlosaryPanel.getPreferredSize().width,
								GlosaryPanel.getPreferredSize().height+45));
						frame.revalidate();
						frame.repaint();
					}
				});
				okbotom.setForeground(new Color(153, 204, 255));
				okbotom.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));
				okbotom.setBackground(Color.WHITE);
				okbotom.setBounds(240, 35, 50, 30);
				
				panelsin.add(Cnombre);
				panelsin.add(okbotom);
				panelsin.setBackground(new Color(210,227,253));
				AddContext.getContentPane().add(panelsin);
				AddContext.setVisible(true);
			}
		});
		
		JLabel GlosayLabel = new JLabel("Glosary");
		GlosayLabel.setOpaque(true);
		GlosayLabel.setHorizontalAlignment(SwingConstants.CENTER);
		GlosayLabel.setForeground(new Color(255, 255, 255));
		GlosayLabel.setBackground(new Color(212, 227, 252));
		GlosayLabel.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 30));
		GlosayLabel.setBounds(2, 250, 209, 28);
		MenuPanel.add(GlosayLabel);
		
		
		JSeparator separator = new JSeparator();
		separator.setForeground(new Color(153, 204, 255));
		separator.setBackground(new Color(255, 255, 255));
		separator.setBounds(10, 240, 187, 2);
		MenuPanel.add(separator);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 297, 176, 162);
		MenuPanel.add(scrollPane);
		scrollPane.setBorder(BorderFactory.createEmptyBorder());
		scrollPane.getVerticalScrollBar().setUI(new myScrollBarUI('V'));
		scrollPane.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {
			
			@Override
			public void adjustmentValueChanged(AdjustmentEvent arg0) {
				// TODO Auto-generated method stub
				scrollPane.repaint();
			}
		});
		
		
		GlosaryPanel.setForeground(Color.WHITE);
		scrollPane.setViewportView(GlosaryPanel);
		GlosaryPanel.setBackground(new Color(212, 227, 252));
		GlosaryPanel.setLayout(null);
		
		RoundedButton HomeView = new RoundedButton("Home");
		HomeView.setBackground(Color.WHITE);
		HomeView.setBounds(30, 470, 147, 28);
		HomeView.setForeground(new Color(153, 204, 255));
		HomeView.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 16));
		MenuPanel.add(HomeView);
		HomeView.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				WhiteBase.setVisible(true);
				scrollMainView.setVisible(true);
        		WhiteBase2.setVisible(false);
        		TaskDetail.setVisible(false);
        		TimeLinePanel.setVisible(false);
        		scrollTime.setVisible(false);
        		frame.getContentPane().setComponentZOrder(scrollMainView,1);
        		frame.getContentPane().setComponentZOrder(WhiteBase2,2);
        		frame.getContentPane().setComponentZOrder(scrollTime,2);
        		MenuPanel.repaint();
			}
		});
		
		RoundedButton TimeView = new RoundedButton("TimeLine");
		TimeView.setBackground(Color.WHITE);
		TimeView.setBounds(30, 505, 147, 28);
		TimeView.setForeground(new Color(153, 204, 255));
		TimeView.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 16));
		MenuPanel.add( TimeView);
		TimeView.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				WhiteBase.setVisible(false);
				scrollMainView.setVisible(false);
        		WhiteBase2.setVisible(false);
        		TaskDetail.setVisible(false);
        		TimeLinePanel.setVisible(true);
        		scrollTime.setVisible(true);
        		frame.getContentPane().setComponentZOrder(scrollMainView,2);
        		frame.getContentPane().setComponentZOrder(WhiteBase2,2);
        		frame.getContentPane().setComponentZOrder(scrollTime,1);
        		MenuPanel.repaint();
        		
			}
		});
		
		scrollMainView = new JScrollPane();
		scrollMainView.setSize(new Dimension(100, 100));
		scrollMainView.setPreferredSize(new Dimension(1000, 1000));
		scrollMainView.setBounds(6, 69, 1005, 576);
		scrollMainView.getViewport().setBackground(new Color(0, 110, 142));
		scrollMainView.setBorder(BorderFactory.createEmptyBorder());
		scrollMainView.getVerticalScrollBar().setUI(new myScrollBarUI('V'));
		scrollMainView.getHorizontalScrollBar().setUI(new myScrollBarUI('H'));
		
		
		frame.getContentPane().add(scrollMainView);
		if(!recovered)
		{
			ProyectPanel PP_1 = new ProyectPanel("Miselaneo", admin.getProyects().get(0));
			PP_1.setLocation(230, 25);
			//PP.setColorName(admin.getProyects().get(0).getColor());
			ProyectUI.add(PP_1);
			WhiteBase.setSize(new Dimension(100, 1000));
			WhiteBase.setPreferredSize(new Dimension(500, 140));
			WhiteBase.add(PP_1);
			ProjectLine PL_1 = new ProjectLine(admin.getProyects().get(0),null); //el paralelo del proyecto, donde se ven los detalles
			Parreglo.add(PL_1);
			scrollTaskPane.setViewportView(PL_1);
			PL_1.setBounds(0, 0, 440, 545);
			
			
			Context c = admin.AddContext("Miscelaneo");
			ProjectLine context = new ProjectLine(null, c);
			Parreglo.add(context);
			ContextButton cB = new ContextButton(c);
			cB.setBounds(10, 5+GlosaryPanel.getComponentCount()*45, 200, 35);
			GlosaryPanel.add(cB);
			cB.addMouseListener(new MouseListener() {
				
				@Override
				public void mouseReleased(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mousePressed(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mouseExited(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mouseEntered(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mouseClicked(MouseEvent e) {
					// TODO Auto-generated method stub
					WhiteBase.setVisible(false);
            		WhiteBase2.setVisible(true);
            		TaskDetail.setVisible(true);
            		scrollMainView.setVisible(false);
            		scrollTime.setVisible(false);
            		frame.getContentPane().setComponentZOrder(scrollMainView,2);
            		frame.getContentPane().setComponentZOrder(WhiteBase2,1);
            		scrollTaskPane.setViewportView(context);
            		ProyectName.setText(context.context.getName());
            		ProyectName.setForeground(context.context.getColor());
				}
			});
			
			
		}
		scrollMainView.setViewportView(WhiteBase);
		
		WhiteBase.setForeground(Color.DARK_GRAY);
		WhiteBase.setBackground(new Color(255, 255, 255));
		WhiteBase.setLayout(null);
		
		WhiteBase.arcs = new Dimension(10,10);
		WhiteBase2.arcs= new Dimension(10,10);
		WhiteBase2.setBackground(new Color(255, 255, 255));
		
		frame.getContentPane().add(WhiteBase2);
		WhiteBase2.setBounds(6, 69, 1005, 576);
		WhiteBase2.setLayout(null);
		WhiteBase2.add(TaskDetail);
		WhiteBase2.add(scrollTaskPane);
		TaskDetail.setBackground(new Color(0,141,177));
		TaskDetail.setLayout(null);
		TaskDetail.setBounds(745, 11, 250, 545);
		TaskDetail.arcs = new Dimension(10,10);
		
		
		
		//desde aca para abajo van los detalles del taskdetail
		//mas comentarios para que se marque la wea
		//jiji asjidaiofuahdjasbjaks
		
		JTextArea textArea_1 = new JTextArea();
		textArea_1.setBackground(Color.white);
		textArea_1.setBounds(5, 100, 235, 210);
		TaskDetail.add(textArea_1);						/////Descrpcion [0]
		
		JTextField lblNombreTarea_1 = new JTextField("Nombre Tarea");
		lblNombreTarea_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNombreTarea_1.setForeground(new Color(255, 255, 255));
		lblNombreTarea_1.setBounds(0, 44, 240, 50);
		lblNombreTarea_1.setHorizontalAlignment(JLabel.CENTER);
		lblNombreTarea_1.setBackground(new Color(0,141,177));
		lblNombreTarea_1.setBorder(BorderFactory.createEmptyBorder());
		
		TaskDetail.add(lblNombreTarea_1);				/////Nombre [1]
		
		JComboBox ctx_1 = new JComboBox();
		ctx_1.setBounds(10, 355, 100, 20);
		
		TaskDetail.add(ctx_1);					/////Contextos [2]
		
		JComboBox impo_1 = new JComboBox();
		impo_1.setBounds(130, 15, 110, 20);
		impo_1.addItem("Normal");
		impo_1.addItem("Importante");
		impo_1.addItem("Muy Importante");
		TaskDetail.add(impo_1);					/////importancia [3]
		
		JCheckBox chckbxTareaLista = new JCheckBox("Tarea Lista");
		chckbxTareaLista.setBackground(new Color(30, 144, 255));
		chckbxTareaLista.setForeground(Color.white);
		chckbxTareaLista.setBounds(10, 457, 97, 23);
		chckbxTareaLista.setBorder(null);
		TaskDetail.add(chckbxTareaLista);			/////tarea lista [4]
		
		JLabel lblProceso = new JLabel("Progreso");
		lblProceso.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblProceso.setBounds(10, 480, 100, 23);
		lblProceso.setForeground(Color.white);
		TaskDetail.add(lblProceso);					/////Progreso [5]
		
		JSlider slider_1 = new JSlider();
		slider_1.setBackground(new Color(30, 144, 255));
		slider_1.setForeground(new Color(64, 224, 208));
		slider_1.setBounds(21, 514, 200, 20);
		slider_1.setMaximum(100);
		slider_1.setMinimum(0);
		TaskDetail.add(slider_1);					/////SliderProgreso [6]
		
		JTextField DayEdit = new JTextField();
		JTextField MonthEdit = new JTextField();
		JTextField YearEdit = new JTextField();
		
		JButton btnD = new JButton("+ D");
		btnD.setBounds(130, 355, 51, 23);
		btnD.setBackground(Color.white);
		btnD.setForeground(new Color(0,141,177));
		btnD.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(SelectedTask!=null)
				{
					SelectedTask.getDeadline().add(Calendar.DATE, 1);
					DayEdit.setText(SelectedTask.getDeadline().get(Calendar.DATE)+"");
					MonthEdit.setText((SelectedTask.getDeadline().get(Calendar.MONTH)+1)+"");
					YearEdit.setText(""+SelectedTask.getDeadline().get(Calendar.YEAR));
				}
				else
				{
					selectedProyect.getDeadline().add(Calendar.DATE, 1);
					DayEdit.setText(selectedProyect.getDeadline().get(Calendar.DATE)+"");
					MonthEdit.setText((selectedProyect.getDeadline().get(Calendar.MONTH)+1)+"");
					YearEdit.setText(""+selectedProyect.getDeadline().get(Calendar.YEAR));
				}
				
			}
		});
		TaskDetail.add(btnD);						/////Button +D [7]
		
		JButton btnW = new JButton("+ W");
		btnW.setBounds(190, 355, 51, 23);
		btnW.setBackground(Color.white);
		btnW.setForeground(new Color(0,141,177));
		btnW.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				if(SelectedTask!=null)
				{
					SelectedTask.getDeadline().add(Calendar.DATE, 7);
					DayEdit.setText(SelectedTask.getDeadline().get(Calendar.DATE)+"");
					MonthEdit.setText((SelectedTask.getDeadline().get(Calendar.MONTH)+1)+"");
					YearEdit.setText(""+SelectedTask.getDeadline().get(Calendar.YEAR));
				}
				else
				{
					selectedProyect.getDeadline().add(Calendar.DATE, 7);
					DayEdit.setText(selectedProyect.getDeadline().get(Calendar.DATE)+"");
					MonthEdit.setText((selectedProyect.getDeadline().get(Calendar.MONTH)+1)+"");
					YearEdit.setText(""+selectedProyect.getDeadline().get(Calendar.YEAR));
				}
				
			}
		});
		TaskDetail.add(btnW);						/////Button +W [8]
		
		JTextField txtHh_1 = new JTextField();		
		txtHh_1.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				if(txtHh_1.getText().equals("HH"))
					txtHh_1.setText("");
			}
			@Override
			public void focusLost(FocusEvent e) {
				if(txtHh_1.getText().equals(""))
					txtHh_1.setText("HH");				
			}
		});
		txtHh_1.setForeground(new Color(255, 255, 255));
		txtHh_1.setFont(new Font("Tahoma", Font.PLAIN, 19));
		txtHh_1.setBackground(new Color(30, 144, 255));
		txtHh_1.setText("HH");
		txtHh_1.setBounds(191, 401, 50, 34);
		TaskDetail.add(txtHh_1);					////HH [9]
		txtHh_1.setColumns(10);
		
		
		
		
		JTextField txtMm_1 = new JTextField();
		txtMm_1.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if(txtMm_1.getText().equals("MM"))
					txtMm_1.setText("");
			}
			@Override
			public void focusLost(FocusEvent e) {
				if(txtMm_1.getText().equals(""))
					txtMm_1.setText("MM");
			}
		});
		txtMm_1.setBackground(new Color(30, 144, 255));
		txtMm_1.setForeground(new Color(255, 255, 255));
		txtMm_1.setFont(new Font("Tahoma", Font.PLAIN, 19));
		txtMm_1.setText("MM");
		txtMm_1.setBounds(130, 401, 50, 34);
		TaskDetail.add(txtMm_1);
		txtMm_1.setColumns(10);						/////MM [10]
		
		JButton btnGuardar = new JButton("GUARDAR");
		btnGuardar.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnGuardar.setForeground(new Color(125,184,93));
		btnGuardar.setBackground(Color.WHITE);
		btnGuardar.setBounds(113, 476, 117, 28);
		TaskDetail.add(btnGuardar);
		btnGuardar.addActionListener(new ActionListener(){
			public void actionPerformed (ActionEvent e)
			{
				//SelectedTask.setContext(admin.ContColor.get(ctx_1.getSelectedItem().toString()));		
				if(SelectedTask!=null)
				{
					SelectedTask.setRelevance(impo_1.getSelectedIndex());
					SelectedTask.setDescription(textArea_1.getText());
					SelectedTask.setProgress(slider_1.getValue());
					if(chckbxTareaLista.isSelected())
					{
						SelectedTask.setState(State.Finished);
						SelectedTask.setProgress(100);
						slider_1.setValue(100);
						
					}
					SelectedTask.getDeadline().clear();
					SelectedTask.getDeadline().set(Integer.parseInt(YearEdit.getText()), Integer.parseInt(MonthEdit.getText())-1, Integer.parseInt(DayEdit.getText()));
					SelectedTask.CheckDate();
					SelectedTask.setName(lblNombreTarea_1.getText());
					Context context = admin.getPosibleContextColor().get(admin.getPosibleContext().indexOf(ctx_1.getSelectedItem()));
					
					if(context!=SelectedTask.getContext()){
						SelectedTask.getContext().getTasks().remove(SelectedTask);
						SelectedTask.setContext(context);
						context.AddTask(SelectedTask);
					}
					SelectedTask.setChange(true);
					WhiteBase2.revalidate();
					WhiteBase2.repaint();
					WhiteBase.revalidate();
					WhiteBase.repaint();
				}		
				else if(selectedProyect!=null)
				{
					selectedProyect.setDescription(textArea_1.getText());
					selectedProyect.setProgress(slider_1.getValue());
					if(chckbxTareaLista.isSelected())
					{
						selectedProyect.setState(State.Finished);
						selectedProyect.setProgress(100);
						slider_1.setValue(100);
						
					}
					if(selectedProyect.getDeadline()!=null)
					{
						selectedProyect.getDeadline().clear();
						selectedProyect.getDeadline().set(Integer.parseInt(YearEdit.getText()), Integer.parseInt(MonthEdit.getText())-1, Integer.parseInt(DayEdit.getText()));
					}
					selectedProyect.setName(lblNombreTarea_1.getText());
					WhiteBase2.revalidate();
					WhiteBase2.repaint();
					WhiteBase.revalidate();
					WhiteBase.repaint();
				}
				
				//faltan las fechas tambien
				//hay que hacer un try and catcha para las fechas y para las horas
				//de que se pueda parsear lo que hay en los textfields
			}
		});

		TaskDetail.add(btnGuardar);					/////Guardar [11]
		frame.getRootPane().setDefaultButton(btnGuardar);
		
		DayEdit.setHorizontalAlignment(JLabel.CENTER);
		DayEdit.setBounds(130, 325, 20, 30);
		DayEdit.setBorder(BorderFactory.createEmptyBorder());
		DayEdit.setFont(new Font("Tahoma", Font.PLAIN, 19));
		DayEdit.setBackground(new Color(0,141,177));
		DayEdit.setForeground(Color.white);
		TaskDetail.add(DayEdit);					//////DateEdit [12]
		
		MonthEdit.setHorizontalAlignment(JLabel.CENTER);
		MonthEdit.setBounds(160, 325, 20, 30);
		MonthEdit.setBorder(BorderFactory.createEmptyBorder());
		MonthEdit.setFont(new Font("Tahoma", Font.PLAIN, 19));
		MonthEdit.setBackground(new Color(0,141,177));
		MonthEdit.setForeground(Color.white);
		TaskDetail.add(MonthEdit);
		
		YearEdit.setHorizontalAlignment(JLabel.CENTER);
		YearEdit.setBounds(190, 325, 50, 30);
		YearEdit.setBorder(BorderFactory.createEmptyBorder());
		YearEdit.setFont(new Font("Tahoma", Font.PLAIN, 19));
		YearEdit.setBackground(new Color(0,141,177));
		YearEdit.setForeground(Color.white);
		TaskDetail.add(YearEdit);
		
		JLabel slash1 = new JLabel("/");
		slash1.setBounds(150, 325, 10, 30);
		slash1.setBorder(BorderFactory.createEmptyBorder());
		slash1.setFont(new Font("Tahoma", Font.PLAIN, 19));
		slash1.setBackground(new Color(0,141,177));
		slash1.setForeground(Color.white);
		slash1.setHorizontalAlignment(JLabel.CENTER);
		TaskDetail.add(slash1);
		JLabel slash2 = new JLabel("/");
		slash2.setHorizontalAlignment(JLabel.CENTER);
		slash2.setBounds(180, 325, 10, 30);
		slash2.setBorder(BorderFactory.createEmptyBorder());
		slash2.setFont(new Font("Tahoma", Font.PLAIN, 19));
		slash2.setBackground(new Color(0,141,177));
		slash2.setForeground(Color.white);
		TaskDetail.add(slash2);
		//hasta aca llegan los componentes del taskdetails
////////////////////////		////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		scrollTime.setBounds(6, 69, 998, 568);
		frame.getContentPane().add(scrollTime);
		scrollTime.setOpaque(false);
		scrollTime.getViewport().setOpaque(false);
		scrollTime.setBorder(BorderFactory.createEmptyBorder());
		scrollTime.setViewportBorder(BorderFactory.createEmptyBorder());										
		if(!recovered)
			TimeLinePanel = new TimeLinePanel(admin);
		TimeLinePanel.setBackground(new Color(255, 255, 255));
		scrollTime.setViewportView(TimeLinePanel);
		TimeLinePanel.setLayout(null);
		TimeLinePanel.setVisible(true);
		
		Titulo = new JLabel("Proyect Administrator");
		Titulo.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 33));
		Titulo.setForeground(Color.WHITE);
		Titulo.setBounds(276, 17, 422, 40);
		frame.getContentPane().add(Titulo);
		
		
		System.out.println(System.getProperty("user.dir"));
		
		scrollTime.setVisible(false);
		
		
		
	}
		
		private void DetallarTarea(Task t)
	    {
	    	//al seleccionar una tarea muestra sus detalles en el panel de TaskDetail
			JComboBox impo =(JComboBox)TaskDetail.getComponent(3);
			JComboBox ctx =(JComboBox)TaskDetail.getComponent(2);
			JTextField lblNombreTarea = (JTextField)TaskDetail.getComponent(1);
			JTextArea Description = (JTextArea)TaskDetail.getComponent(0);
			JSlider slider = (JSlider)TaskDetail.getComponent(6);
			JTextField DayEdit = (JTextField)TaskDetail.getComponent(12);
			JTextField MonthEdit = (JTextField)TaskDetail.getComponent(13);
			JTextField YearEdit = (JTextField)TaskDetail.getComponent(14);
			
			impo.setSelectedIndex(t.getRelevance());
			ArrayList<String> Listac=admin.getPosibleContext();
			String[] opcionesc = new String[Listac.size()+1];
			for(int i=0; i<Listac.size();i++)
			{
				opcionesc[i]=Listac.get(i);
			}
			ctx.setModel(new DefaultComboBoxModel(opcionesc));
			int i = admin.getPosibleContext().indexOf(t.getContext().getName());
	    	ctx.setSelectedItem(Listac.get(i));
	    	//diccionario para pasar del color del contexto a un string reconocible por el combobox
	    	Description.setText(t.getDescription());
	    	if(t.getName().equals(""))
	    	{
	    		lblNombreTarea.setText("Agregar Nombre");
	    		lblNombreTarea.getCaret().setVisible(true);
	    	}
	    	else
	    		lblNombreTarea.setText(t.getName());
	    	//poner las fechas y horas en orden tambien
	    	slider.setValue(t.getProgress());
	    	DayEdit.setText(t.getDeadline().get(Calendar.DATE)+"");
	    	MonthEdit.setText((t.getDeadline().get(Calendar.MONTH)+1)+"");
	    	YearEdit.setText(""+t.getDeadline().get(Calendar.YEAR));
	    	
	    }

		private void DetallarProyecto(Proyect p)
	    {
	    	//al seleccionar una tarea muestra sus detalles en el panel de TaskDetail
			JComboBox impo =(JComboBox)TaskDetail.getComponent(3);
			JComboBox ctx =(JComboBox)TaskDetail.getComponent(2);
			JTextField lblNombreTarea = (JTextField)TaskDetail.getComponent(1);
			JTextArea Description = (JTextArea)TaskDetail.getComponent(0);
			JSlider slider = (JSlider)TaskDetail.getComponent(6);
			JTextField DayEdit = (JTextField)TaskDetail.getComponent(12);
			JTextField MonthEdit = (JTextField)TaskDetail.getComponent(13);
			JTextField YearEdit = (JTextField)TaskDetail.getComponent(14);
			
			
			ArrayList<String> Listac=admin.getPosibleContext();
			String[] opcionesc = new String[Listac.size()+1];
			for(int i=0; i<Listac.size();i++)
			{
				opcionesc[i]=Listac.get(i);
			}
			
	    	//diccionario para pasar del color del contexto a un string reconocible por el combobox
	    	Description.setText(p.getDescription());
	    	if(p.getName().equals(""))
	    	{
	    		lblNombreTarea.setText("Agregar Nombre");
	    		lblNombreTarea.getCaret().setVisible(true);
	    	}
	    	else
	    		lblNombreTarea.setText(p.getName());
	    	//poner las fechas y horas en orden tambien
	    	slider.setValue(p.getProgress());
	    	if(p.getDeadline()!=null)
	    	{
		    	DayEdit.setText(p.getDeadline().get(Calendar.DATE)+"");
		    	MonthEdit.setText((p.getDeadline().get(Calendar.MONTH)+1)+"");
		    	YearEdit.setText(""+p.getDeadline().get(Calendar.YEAR));
	    	}
	    	
	    }
		
		public void Serialize(Object o,String name) throws IOException
		{
			File directory = new File("Data");
			File objectData = new File("Data/"+name+".data");
			if (!directory.exists()) 
			{
				    try
				    {
				        directory.mkdir();
				    } 
				    catch(SecurityException se)
				    {}        
			}
			
			if (!objectData.exists()) 
			{
				    try
				    {
				       objectData.createNewFile(); 
				    } 
				    catch(SecurityException se)
				    { 
				    	
				    }        
			}
			FileOutputStream oStream = new FileOutputStream(objectData);
			ObjectOutputStream adminOut = new ObjectOutputStream(oStream);
			adminOut.writeObject(o);
			oStream.close();
		}

		public Object deSerialize(String name) throws IOException, ClassNotFoundException
		{
			File directory = new File("Data");
			File objectData = new File("Data/"+name+".data");
			
			
			if (!directory.exists()) 
			{
				    try
				    {
				        directory.mkdir();
				    } 
				    catch(SecurityException se)
				    {}   
				    return null;
			}
			
			if (!objectData.exists()) 
			{

				    return null;       
			}
			FileInputStream oStream = new FileInputStream(objectData);
			ObjectInputStream adminIn = new ObjectInputStream(oStream);
			return adminIn.readObject();
			
		}

}


