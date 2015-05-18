import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.ScrollPane;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import backend.Administrator;
import backend.State;
import backend.Task;
import net.miginfocom.swing.MigLayout;

import java.awt.BasicStroke;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.util.ArrayList;
import java.util.Calendar;


public class TimeLinePanel extends JPanel {

	
	Calendar initial;
	Calendar end;
	JPanel TaskPanel = new JPanel();
	JScrollPane scrollTask;
	Administrator Admin;
	public TimeLinePanel(Administrator admin)
	{
		super();
		Admin = admin;
		setSize(this.getPreferredSize().width, this.getPreferredSize().height);
		setOpaque(false);
		JSeparator separator_1 = new JSeparator();
		separator_1.setBackground(new Color(0, 0, 0));
		separator_1.setBounds(120, 45, 620, 2);
		this.add(separator_1);
		separator_1.setForeground(new Color(0, 102, 255));
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setForeground(new Color(0, 102, 255));
		separator_2.setBackground(Color.BLACK);
		separator_2.setBounds(120, 20, 620, 2);
		this.add(separator_2);
		
		JSeparator separator_3 = new JSeparator();
		separator_3.setBackground(new Color(0, 0, 204));
		separator_3.setForeground(new Color(0, 0, 204));
		separator_3.setOrientation(SwingConstants.VERTICAL);
		separator_3.setBounds(100, 20, 10, 490);
		this.add(separator_3);
		
		scrollTask = new JScrollPane();
		scrollTask.setBorder(BorderFactory.createEmptyBorder());
		scrollTask.setBounds(110, 10, 628, 521);
		this.add(scrollTask);
		TimeLinePanel aux = this;
		scrollTask.getViewport().addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent arg0) {
				// TODO Auto-generated method stub
				aux.revalidate();
				aux.repaint();
				
			}
		});
		
		
		TaskPanel.setBorder(BorderFactory.createEmptyBorder());
		scrollTask.setViewportView(TaskPanel);
		scrollTask.setViewportBorder(BorderFactory.createEmptyBorder());
		TaskPanel.setBounds(0, 0, 620, 532);
		TaskPanel.setPreferredSize(new Dimension(1000, 500)); 
		TaskPanel.setBackground(new Color(255, 255, 255));
		MigLayout mig = new MigLayout("fillx", "[1][grow]", "[50]40[50]10[50]10[50]");
		
		TaskPanel.setLayout(mig);
		
		TaskPanel.setVisible(true);
		
		
		JLabel lblTareas = new JLabel("Tareas");
		lblTareas.setForeground(new Color(0, 102, 102));
		lblTareas.setHorizontalAlignment(SwingConstants.CENTER);
		lblTareas.setFont(new Font("Berlin Sans FB Demi", Font.PLAIN, 25));
		lblTareas.setBounds(10, 11, 75, 27);
		this.add(lblTareas);
		TaskPanel.setVisible(true);
		
		
		
		JPanel Fechas = new JPanel();
		Fechas.setBackground(new Color(255, 255, 255));
		Fechas.setBounds(0, 0, 800, 50);
		TaskPanel.add(Fechas, "cell 1 0,grow");
		MigLayout dateMig = new MigLayout("fillx", "[30]15", "[50]");
		Fechas.setLayout(dateMig);
		
		
		
		
	}


@Override
protected void paintComponent(Graphics g) {

    super.paintComponent(g);
    ////////////////////////////////////////whiteBase
    Graphics2D graphics = (Graphics2D) g;
    graphics.setColor(getBackground());
    graphics.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
    graphics.setColor(getForeground());
    graphics.setStroke(new BasicStroke((float)1));
    graphics.drawRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
    
	}
public void AddTasks(Task t){
	
	
	ArrayList<Task> tasks = Admin.AllTasks();
	if(tasks.size()==0)
		return;
	initial =  tasks.get(0).getDeadline();
	end = tasks.get(tasks.size()-1).getDeadline();
	int index =tasks.indexOf(t)+1;
	for(int i = tasks.indexOf(t)+1; i<tasks.size();i++)
	{
		
		TaskPanel task = (TaskPanel)TaskPanel.getComponent(index);
		TaskPanel.remove(TaskPanel.getComponent(index));
		String constr = "cell 1 "+(i+1)+",grow, h 50!";   /// lo mueve a la siguiente posicion
		TaskPanel.add(task, constr);
		
	}
	TaskPanel task = new TaskPanel(t,((t.getDeadline().get(Calendar.YEAR)-initial.get(Calendar.YEAR))*365)+
			(t.getDeadline().get(Calendar.DAY_OF_YEAR)-initial.get(Calendar.DAY_OF_YEAR)) );  ////////calcula la posicion del nodo de acuerdo a la distancia en fecha
	
	String constr = "cell 1 "+index+",grow, h 50!";
	TaskPanel.add(task, constr,index);
	MigLayout m =(MigLayout)TaskPanel.getLayout();
	task.setBackground(new Color(212, 227, 252));
	String constrain = m.getRowConstraints()+"10[50]";
	m.setRowConstraints(constrain);
	
	
	int total = ((end.get(Calendar.YEAR)-initial.get(Calendar.YEAR))*365)+
			(end.get(Calendar.DAY_OF_YEAR)-initial.get(Calendar.DAY_OF_YEAR));
	TaskPanel.setPreferredSize(new Dimension(30*total, 100+tasks.size()*60));
	JPanel fechas = (JPanel)TaskPanel.getComponent(0);
	MigLayout MigFechas = (MigLayout)fechas.getLayout();
	fechas.removeAll();
	for(int i = 0; i <total; i++ )
	{
		
		Calendar c = Calendar.getInstance();
		c.setTime(initial.getTime()); ///esto esta mal
		c.add(Calendar.DAY_OF_MONTH, i);
		//JLabel d = new JLabel(c.get(Calendar.DAY_OF_MONTH)+"/"+c.get(Calendar.MONTH));
		
		JLabel fecha1 = new JLabel("17/"+i);
		fecha1.setHorizontalAlignment(SwingConstants.CENTER);
		fecha1.setSize(30, 50);
		fechas.setPreferredSize(new Dimension(30*i,50));
		fechas.add(fecha1,"cell "+i+" 0 , grow , w 30!");
		MigFechas.setColumnConstraints(MigFechas.getColumnConstraints()+"10[30]");
		
	}
	this.revalidate();
	this.repaint();
}






}
