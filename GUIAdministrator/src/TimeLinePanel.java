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
import javax.swing.plaf.ScrollBarUI;

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
	JPanel TPanel = new JPanel();
	JPanel taskNames = new JPanel();
	JPanel fechas = new JPanel();
	JScrollPane scrollTask;
	Administrator Admin;
	public TimeLinePanel(Administrator admin)
	{
		super();
		Admin = admin;
		setSize(this.getPreferredSize().width, this.getPreferredSize().height);
		setOpaque(false);
		JSeparator separator_1 = new JSeparator();
		separator_1.setBorder(BorderFactory.createLineBorder(new Color(40, 140, 180)));
		separator_1.setBackground(new Color(0, 0, 0));
		separator_1.setBounds(370, 45, 620, 2);
		this.add(separator_1);
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setBorder(BorderFactory.createLineBorder(new Color(40, 140, 180)));
		separator_2.setForeground(new Color(40, 180, 255));
		separator_2.setBackground(Color.BLACK);
		separator_2.setBounds(370, 20, 620, 2);
		this.add(separator_2);
		
		JSeparator separator_3 = new JSeparator();
		separator_3.setBorder(BorderFactory.createLineBorder(new Color(40, 140, 180)));
		separator_3.setBackground(new Color(0, 0, 204));
		separator_3.setOrientation(SwingConstants.VERTICAL);
		separator_3.setBounds(355, 60, 2, 460);
		this.add(separator_3);
		
		scrollTask = new JScrollPane();
		scrollTask.getHorizontalScrollBar().setUI(new myScrollBarUI());
		scrollTask.getVerticalScrollBar().setUI(new myScrollBarUI());
		scrollTask.setBorder(BorderFactory.createEmptyBorder());
		scrollTask.setBounds(360, 50, 628, 470);
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
		TPanel.setBorder(BorderFactory.createEmptyBorder());
		scrollTask.setViewportView(TPanel);
		
		scrollTask.setViewportBorder(BorderFactory.createEmptyBorder());
		TPanel.setBounds(250, 0, 620, 532);
		TPanel.setBackground(new Color(255, 255, 255));
		MigLayout mig = new MigLayout("fillx", "[0][grow]", "[0]0[50]");
		TPanel.setLayout(mig);
		TPanel.setVisible(true);
		setComponentZOrder(scrollTask, 1);
		
		JLabel lblTareas = new JLabel("Tareas");
		lblTareas.setForeground(new Color(0, 102, 102));
		lblTareas.setHorizontalAlignment(SwingConstants.CENTER);
		lblTareas.setFont(new Font("Berlin Sans FB Demi", Font.PLAIN, 30));
		lblTareas.setBounds(250, 11, 100, 27);
		this.add(lblTareas);
		TPanel.setVisible(true);
		
		
		JPanel vacio = new JPanel();
		vacio.setBackground(new Color(255, 255, 255));
		vacio.setBounds(0, 0, 800, 50);
		TPanel.add(vacio, "cell 1 0,grow");
		MigLayout dateMig = new MigLayout("fillx", "[30]10", "[50]");
		vacio.setLayout(dateMig);
		
		taskNames.setBackground(new Color(255, 255, 255));
		taskNames.setBounds(0, 0, 90, 470);
		MigLayout nameMig = new MigLayout("","[100]","");
		taskNames.setLayout(nameMig);
		
		JScrollPane nameScroll = new JScrollPane(taskNames);
		nameScroll.setBounds(250, 50, 100, 470);
		this.add(nameScroll);
		nameScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		nameScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		nameScroll.setBorder(BorderFactory.createEmptyBorder());
		nameScroll.setViewportBorder(BorderFactory.createEmptyBorder());
		
		
		fechas.setBounds(0, 0, 620, 50);
		fechas.setBackground(new Color(255,255,255));
		MigLayout fMig = new MigLayout("","","[50]");
		fechas.setLayout(fMig);
		
		JScrollPane fScroll = new JScrollPane(fechas);
		fScroll.setBounds(360, 5, 620, 55);
		this.add(fScroll);
		fScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		fScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		fScroll.setBorder(BorderFactory.createEmptyBorder());
		fScroll.setViewportBorder(BorderFactory.createEmptyBorder());
		
		//se conectan los scrollbars
		scrollTask.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {
			
			@Override
			public void adjustmentValueChanged(AdjustmentEvent e) {
				// TODO Auto-generated method stub
				JScrollBar nVScroll = nameScroll.getVerticalScrollBar();
				int v1 = nVScroll.getValue();
				int v2 = scrollTask.getVerticalScrollBar().getValue();
				nVScroll.setValue(scrollTask.getVerticalScrollBar().getValue());
			}
		});
		scrollTask.getHorizontalScrollBar().addAdjustmentListener(new AdjustmentListener() {
			
			@Override
			public void adjustmentValueChanged(AdjustmentEvent arg0) {
				// TODO Auto-generated method stub
				JScrollBar fHScroll = fScroll.getHorizontalScrollBar();
				fHScroll.setValue(scrollTask.getHorizontalScrollBar().getValue());
			}
		});
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
	int total;
	if(tasks.size()==0)
		return;
	initial =  tasks.get(0).getDeadline();
	int k = tasks.size()-1;
	end = tasks.get(k).getDeadline();
	int year = end.get(Calendar.YEAR);
	//reviso si hay tareas con fecha. creo que podria corregirse verificando solo initial
	while(year==9999&&k>=0) //9999 -> sin fecha
	{
		
		end = tasks.get(k).getDeadline();
		year = end.get(Calendar.YEAR);
		k--;
	}
	if(year==9999)
		total=0;
	else
		total = ((end.get(Calendar.YEAR)-initial.get(Calendar.YEAR))*365)+
			(end.get(Calendar.DAY_OF_YEAR)-initial.get(Calendar.DAY_OF_YEAR));
	////////////////////////////////
	//empieza la inclusion de t
	int index =tasks.indexOf(t)+1;  //+1 pq los tasks parten desde la segunda row
	//hace el espacio
	for(int i = tasks.indexOf(t)+1; i<(TPanel.getComponentCount());i++)
	{
		TaskPanel task = (TaskPanel)TPanel.getComponent(index);
		TPanel.remove(TPanel.getComponent(index));
		String constr = "cell 1 "+(i+1)+",grow, h 55!";   /// lo mueve a la siguiente posicion
		TPanel.add(task, constr);	
		
		JLabel tName = (JLabel)taskNames.getComponent(index-1);
		taskNames.remove(taskNames.getComponent(index-1));
		constr = "cell 0 "+(i)+",grow, h 55!, w 100!";
		taskNames.add(tName,constr);
		
		
	}
	for(int i=1; i<TPanel.getComponentCount();i++)
	{
		TaskPanel task = (TaskPanel)TPanel.getComponent(i);
		task.setTotal(total);
		task.setPosition(((task.getTask().getDeadline().get(Calendar.YEAR)-initial.get(Calendar.YEAR))*365)+
			(task.getTask().getDeadline().get(Calendar.DAY_OF_YEAR)-initial.get(Calendar.DAY_OF_YEAR)));
		task.repaint();
	}
	
	TaskPanel task = new TaskPanel(t,((t.getDeadline().get(Calendar.YEAR)-initial.get(Calendar.YEAR))*365)+
			(t.getDeadline().get(Calendar.DAY_OF_YEAR)-initial.get(Calendar.DAY_OF_YEAR)),total);  ////////calcula la posicion del nodo de acuerdo a la distancia en fecha
	
	String constr = "cell 1 "+index+",grow, h 55!";
	TPanel.add(task, constr,Math.min(index, TPanel.getComponentCount()));
	MigLayout m =(MigLayout)TPanel.getLayout();
	task.setBackground(new Color(212, 227, 252));
	String constrain = m.getRowConstraints()+"10[55]";
	m.setRowConstraints(constrain);
	
	JLabel tName = new JLabel(t.getName());
	tName.setHorizontalAlignment(JLabel.CENTER);
	tName.setFont(new Font("Berlin Sans FB Demi", Font.PLAIN, 18));
	tName.setForeground(t.getColor());
	constr = "cell 0 "+(index-1)+",grow, h 55!, w 100!";
	taskNames.add(tName,constr,Math.min(index, taskNames.getComponentCount()));
	MigLayout nMig =(MigLayout)taskNames.getLayout();
	constrain = nMig.getRowConstraints()+"10[55]";
	nMig.setRowConstraints(constrain);
	
	
	TPanel.setPreferredSize(new Dimension(Math.max(65*(total+5), 628), 100+tasks.size()*60));
	taskNames.setPreferredSize(new Dimension(100,100+tasks.size()*60));
	MigLayout MigFechas = (MigLayout)fechas.getLayout();
	fechas.removeAll();
	for(int i = 0; i <=total&&i<2000; i++ )
	{
		
		Calendar c = Calendar.getInstance();
		c.setTime(initial.getTime()); 
		c.add(Calendar.DAY_OF_MONTH, i);
		JLabel d = new JLabel(c.get(Calendar.DAY_OF_MONTH)+"/"+(c.get(Calendar.MONTH)+1));
		d.setForeground(new Color(40, 180, 255));
		d.setHorizontalAlignment(SwingConstants.CENTER);
		d.setSize(30, 50);
		fechas.add(d,"cell "+i+" 0 , grow , w 50!");
		MigFechas.setColumnConstraints(MigFechas.getColumnConstraints()+"10[50]");	
	}
	
	fechas.setPreferredSize(new Dimension(Math.max(65*(total+5), 628),50));
	this.revalidate();
	this.repaint();
}






}
