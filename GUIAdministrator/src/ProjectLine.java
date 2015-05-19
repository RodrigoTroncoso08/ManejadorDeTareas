import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import java.awt.*;

import backend.*;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Calendar;
import java.util.Collections;
import java.util.ArrayList;

public class ProjectLine extends JPanel {

	protected Proyect pro;
	protected int countTask=0;
	public JPanel panel = new JPanel();
	protected int Current;
	
	
	public ProjectLine(Proyect p) {
		setLayout(null);
		
		pro = p;
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0,0, 440, 545);
		scrollPane.setBorder(BorderFactory.createEmptyBorder());
		scrollPane.setViewportBorder(BorderFactory.createEmptyBorder());
		add(scrollPane);
		panel.setBackground(new Color(255, 255, 255));
		
		scrollPane.setViewportView(panel);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{200,120,120};
		gbl_panel.rowHeights = new int[]{90,90,90,90,90,90,90,90,90,90,90,90,90,90};
		gbl_panel.columnWeights = new double[]{Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{Double.MIN_VALUE};
		panel.setLayout(gbl_panel);

	}
	
	public void AddTask(NodeButton nodeButton_5){
		//es una adaptacion del codigo del ProyectPanel.AddTask ya que hacen lo mismo pero el otro en horizontal y este en vertical
		
		
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
			
			
			NodeButton node= (NodeButton)panel.getComponent(mark*3);  ///error aqui
			GridBagConstraints NodeConst= g.getConstraints(node);
			NodeConst.gridy=i+1;
			panel.remove(mark*3);
			panel.add(node, NodeConst);
			
			Component TaskLabel= panel.getComponent(mark*3);
			GridBagConstraints TaskConst= g.getConstraints(TaskLabel);
			TaskConst.gridy=i+1;
			panel.remove(mark*3);
			panel.add(TaskLabel, TaskConst);
			
			
		}
		
		Calendar c= t.getDeadline();
		int month = c.get(Calendar.MONTH)+1;
		int year = c.get(Calendar.YEAR);
		JLabel lblNewLabel_2 = new JLabel(c.get(Calendar.DAY_OF_MONTH)+"/"+(month+1)+"/"+year); //se le resta 1 a year para que funcione..
		lblNewLabel_2.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 12));
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
		pan.setBackground(new Color(255,255,255)); //para las tareas no seleccionadas
		nodeButton_5.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 30));
		nodeButton_5.setForeground(t.getContext());
		pan.add(nodeButton_5, BorderLayout.CENTER);
		GridBagConstraints gbc_nodeButton_5 = new GridBagConstraints();
		gbc_nodeButton_5.insets = new Insets(0, 0, 0, 0);
		gbc_nodeButton_5.gridy = pro.getTasks().indexOf(t);
		gbc_nodeButton_5.gridx = 1;
		gbc_nodeButton_5.weightx=1; //puede que haya que cambiarlo tambien
		
		
		nodeButton_5.setBackground(pro.getColor());
		nodeButton_5.setPreferredSize(new Dimension(15*(t.getRelevance()+1), 15*(t.getRelevance()+1)));  
		
		nodeButton_5.setAlignmentX(0.5f);
		
		JLabel TaskLabel = new JLabel(t.getName());
		TaskLabel.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 12));
		GridBagConstraints gbc_TaskLabel = new GridBagConstraints();
		TaskLabel.setForeground(new Color(112, 150, 252));
		gbc_TaskLabel.insets = new Insets(0, 0, 0, 5);
		gbc_TaskLabel.gridy = pro.getTasks().indexOf(t);
		gbc_TaskLabel.gridx = 0;
		gbc_TaskLabel.ipady = 4;
		gbc_TaskLabel.anchor = gbc_TaskLabel.CENTER;
		
		
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
		}
	
}


