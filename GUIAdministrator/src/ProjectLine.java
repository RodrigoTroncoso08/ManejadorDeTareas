import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.StrokeBorder;

import java.awt.*;

import backend.*;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.util.Calendar;
import java.util.Collections;
import java.util.ArrayList;

public class ProjectLine extends JPanel {

	protected Proyect pro;
	protected int countTask=0;
	public JPanel panel = new JPanel();
	protected int Current;
	protected int strokeSize=2;
	protected Dimension arcs = new Dimension(10, 10);
	
	public ProjectLine(Proyect p) {
		
		
		pro = p;
		this.setOpaque(false);
		panel.setBounds(500, 0, 440, 545);
		panel.setBackground(new Color(255, 255, 255));
		panel.setOpaque(false);
		this.add(panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{140,140,140};
		gbl_panel.rowHeights = new int[]{60,60,60,60,60,60,60,60,60,60,60,60,90};
		gbl_panel.columnWeights = new double[]{Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{Double.MIN_VALUE};
		panel.setLayout(gbl_panel);

	}
	
	public void AddTask(NodeButton nodeButton_5){
		//es una adaptacion del codigo del proPanel.AddTask ya que hacen lo mismo pero el otro en horizontal y este en vertical
		
		
		Task t = nodeButton_5.getTask();
		Collections.sort(pro.getTasks());
		if(panel.getSize().getHeight()<countTask) 
		{
			panel.setSize(3,countTask+1);
		}
		int mark = pro.getTasks().indexOf(t);
		if(countTask>0)
		for(int i = mark ;i<pro.getTasks().size()-1;i++)
		{
			Component label= panel.getComponent(mark*3);
			GridBagLayout g = (GridBagLayout)panel.getLayout();
			GridBagConstraints labelCons= g.getConstraints(label);
			labelCons.gridy=i+1;
			panel.remove(3*mark);
			panel.add(label, labelCons);
			
			
			JPanel Panelnode= (JPanel)panel.getComponent(mark*3);
			GridBagConstraints NodeConst= g.getConstraints(Panelnode);
			NodeConst.gridy=i+1;
			panel.remove(mark*3);
			panel.add(Panelnode, NodeConst);
			
			Component TaskLabel= panel.getComponent(mark*3);
			GridBagConstraints TaskConst= g.getConstraints(TaskLabel);
			TaskConst.gridy=i+1;
			panel.remove(mark*3);
			panel.add(TaskLabel, TaskConst);
			
			
		}
		
		Calendar c= t.getDeadline();
		int month = c.get(Calendar.MONTH)+1;
		int year = c.get(Calendar.YEAR);
		JLabel lblNewLabel_2;
		if(year!=9999)
			lblNewLabel_2 = new JLabel(c.get(Calendar.DAY_OF_MONTH)+"/"+(month)+"/"+year); //se le resta 1 a year para que funcione..
		else
			lblNewLabel_2 = new JLabel("No Especificada");
		lblNewLabel_2.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN,16));
		lblNewLabel_2.setForeground(new Color(112, 150, 252));
		lblNewLabel_2.setBackground(new Color(255, 250, 250));
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 0, 0);
		gbc_lblNewLabel_2.gridy = pro.getTasks().indexOf(t);	
		gbc_lblNewLabel_2.gridx = 2;
		gbc_lblNewLabel_2.weighty=1; //puede que haya que cambiarlo
		gbc_lblNewLabel_2.anchor= gbc_lblNewLabel_2.CENTER;
		
		
		JPanel pan = new JPanel();
		pan.setLayout(new BorderLayout());
		pan.setOpaque(false);
		pan.setBackground(new Color(255,255,255)); //para las tareas no seleccionadas
		nodeButton_5.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 30));
		nodeButton_5.setForeground(t.getContext());
		pan.add(nodeButton_5, BorderLayout.CENTER);
		GridBagConstraints gbc_nodeButton_5 = new GridBagConstraints();
		gbc_nodeButton_5.insets = new Insets(0, 0, 0, 0);
		gbc_nodeButton_5.gridy = pro.getTasks().indexOf(t);
		gbc_nodeButton_5.gridx = 1;
		gbc_nodeButton_5.weightx=1; //puede que haya que cambiarlo tambien
		gbc_nodeButton_5.anchor=GridBagConstraints.CENTER;
		gbc_nodeButton_5.fill=GridBagConstraints.BOTH;
		
		nodeButton_5.setBackground(pro.getColor());
		
		
		nodeButton_5.setAlignmentX(0.5f);
		
		JTextArea TaskLabel = new JTextArea(t.getName());
		TaskLabel.setBounds(0, 0, 130, 50);
		TaskLabel.setEditable(false);
		TaskLabel.setBorder(BorderFactory.createEmptyBorder());
		TaskLabel.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 16));
		TaskLabel.setLineWrap(true);
		TaskLabel.setWrapStyleWord(true);
		GridBagConstraints gbc_TaskLabel = new GridBagConstraints();
		TaskLabel.setForeground(new Color(112, 150, 252));
		gbc_TaskLabel.insets = new Insets(0, 0, 0, 5);
		gbc_TaskLabel.gridy = pro.getTasks().indexOf(t);
		gbc_TaskLabel.gridx = 0;
		gbc_TaskLabel.ipady = 4;
		gbc_TaskLabel.anchor=GridBagConstraints.CENTER;
		
		
		
		panel.add(TaskLabel, gbc_TaskLabel,pro.getTasks().indexOf(t)*3); 
		panel.add(pan, gbc_nodeButton_5,pro.getTasks().indexOf(t)*3);
		panel.add(lblNewLabel_2, gbc_lblNewLabel_2,pro.getTasks().indexOf(t)*3);
		this.revalidate();
		this.repaint();
		countTask++;
		
	}

	public void SelectTask(NodeButton n){ 
		Task t = n.getTask();
		int num = pro.getTasks().indexOf(t);
		panel.getComponent(num*3+1).setBackground(new Color (173,216,230));
		//se marca la tarea seleccionada	
		
		panel.getComponent((Current*3)+1).setBackground(new Color(220,220,220)); 
		//se desmarca la tarea previamente seleccionada	
		
		Current=num;
		//se selecciona la nueva tarea
		panel.getComponent(Current*3+1); 
		//aca quiero hacerle un focus a la tarea que se acaba de marcar como seleccionada
		//pintar 
		this.repaint();
		}

	@Override
	protected void paintComponent(Graphics g) {

	    super.paintComponent(g);
	    int height = getHeight();
	    Graphics2D graphics = (Graphics2D) g;

	    graphics.setColor(new Color(212, 227, 252));
	    
	    graphics.fillRoundRect(140, 0, 140, 
		height, arcs.width, arcs.height);
	    graphics.setColor(new Color(0,141,177));
	    if(pro.getState()==State.Delayed){
	    	 graphics.setColor(Color.RED);strokeSize=2;}
	    else if(pro.getState()==State.Pause){
	   	 graphics.setColor(Color.YELLOW);strokeSize=2;}
	    graphics.setStroke(new BasicStroke(strokeSize));
	    
	    graphics.drawRoundRect(140,0, 140, 
	    		height, arcs.width, arcs.height);
	    
	    graphics.setColor(new Color(188,211,250));
	    
	    graphics.fillRoundRect(140+strokeSize, strokeSize+Current*60, 140-strokeSize, 
		60, 5-2*strokeSize,5);
	    
	    graphics.setColor(new Color(112, 127, 252));
	    float dash1[] = {10.0f};
	    graphics.setStroke(new BasicStroke((float)1, BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND,10.0f, dash1, 0.0f));
	    for(int i = 0; i<countTask-1;i++)
	    {
	    	graphics.drawLine(5, 60*(1+i), this.getWidth()-10,60*(1+i) );
	    }

	    //Sets strokes to default, is better.
	    graphics.setStroke(new BasicStroke()); 
		}
}

