import java.awt.BasicStroke;
import java.awt.BorderLayout;
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
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.util.Calendar;
import java.util.Collections;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import backend.*;

public class ProyectPanel extends JPanel implements ActionListener{
	
		
		public JLabel ProyectLabel = new JLabel("Proyect");
		public JLabel ProyectDeadLine = new JLabel("Proyect");
		public JPanel NodeGrid = new JPanel();
		
		protected int strokeSize = 1;
	    /** Color of shadow */
	    protected Color shadowColor = new Color(0,110,140);
	    /** Sets if it drops shadow */
	    protected boolean shady = false;
	    /** Sets if it has an High Quality view */
	    protected boolean highQuality = true;
	    /** Double values for Horizontal and Vertical radius of corner arcs */
	    protected Dimension arcs = new Dimension(20, 20);
	    /** Distance between shadow border and opaque panel border */
	    protected int shadowGap = 6;
	    /** The offset of shadow.  */
	    protected int shadowOffset = 5;
	    /** The transparency value of shadow. ( 0 - 255) */
	    protected int shadowAlpha = 150;
	    
	    protected int countTask=0;
	    
	    protected Proyect proyect;
	    
	    static int ProyectCount=0;
	    
	public ProyectPanel(String Name,Proyect p) {
		// TODO Auto-generated constructor stub
			super();
		proyect =p;
		Timer clock = new Timer(5000,this); //cada 5 segundos verifica si algun task cambio de estado
		clock.setRepeats(true);
		clock.setInitialDelay(1000);
		clock.start();
		
		NodeGrid.setOpaque(false);
		setOpaque(false);
		this.setBounds(230, 20+145*ProyectCount, 748, 139);
		this.setBackground(new Color(212, 227, 252));
		this.setLayout(null);
		this.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
		ProyectCount++;
		this.setForeground(new Color(0, 110, 142));
		
		ProyectLabel.setText(Name);
		ProyectLabel.setForeground(proyect.getColor());
		ProyectLabel.setHorizontalAlignment(SwingConstants.LEFT);
		ProyectLabel.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 25));
		ProyectLabel.setBounds(6, 0, 401, 31);
		this.add(ProyectLabel);
		
		Calendar c = proyect.getDeadline();
		if(c==null)
			c=Calendar.getInstance();
		int month = c.get(c.MONTH);
		if(month==0)
			month=12;
		ProyectDeadLine.setText(c.get(Calendar.DAY_OF_MONTH)+"/"+month+"/"+(c.get(c.YEAR)-1));
		ProyectDeadLine.setForeground(new Color(0, 18, 60));
		ProyectDeadLine.setHorizontalAlignment(SwingConstants.LEFT);
		ProyectDeadLine.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 25));
		ProyectDeadLine.setBounds(6, 60, 401, 31);
		//this.add(ProyectDeadLine);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setOpaque(false);
		scrollPane_1.getViewport().setOpaque(false);
		scrollPane_1.setBounds(40, 28, 697, 105);
		scrollPane_1.setForeground(shadowColor);
		scrollPane_1.getHorizontalScrollBar().setUI(new myScrollBarUI('H'));
		
		
		this.add(scrollPane_1);
		scrollPane_1.setBorder(BorderFactory.createEmptyBorder());
		scrollPane_1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		scrollPane_1.setViewportBorder(BorderFactory.createEmptyBorder());
		
		JPanel panel_1 = new JPanel();
		panel_1.setOpaque(false);
		scrollPane_1.setViewportView(panel_1);
		scrollPane_1.getViewport().setOpaque(false);
		panel_1.setBorder(BorderFactory.createEmptyBorder());
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.X_AXIS));
		
		JScrollPane scrollPane_3 = new JScrollPane();
		scrollPane_3.setOpaque(false);
		
		panel_1.add(scrollPane_3);
		scrollPane_3.setBorder(BorderFactory.createEmptyBorder());
		scrollPane_3.setViewportView(NodeGrid);
		scrollPane_3.getViewport().setOpaque(false);
		scrollPane_3.setViewportBorder(BorderFactory.createEmptyBorder());
		NodeGrid.setOpaque(false);
		NodeGrid.setBorder(BorderFactory.createEmptyBorder());
		GridBagLayout gbl_NodeGrid = new GridBagLayout();
		
		gbl_NodeGrid.columnWidths = new int[] {79,79,79,79,79,79,79,79,79};
		gbl_NodeGrid.rowHeights = new int[] {10, 55, 20};
		gbl_NodeGrid.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_NodeGrid.rowWeights = new double[]{0.0, 0.0, 0.0};
		NodeGrid.setLayout(gbl_NodeGrid);
		
		this.setBorder(BorderFactory.createEmptyBorder());
		
		
	}
public NodeButton AddTask(Task t)
	{
		Collections.sort(proyect.getTasks());//ordena los tasks para luego re agregarlos
		if(NodeGrid.getSize().getHeight()<countTask) //verifica que el grid tenga el espacio indicado
		{
			NodeGrid.setSize(countTask+1,3);
		}
		int mark = proyect.getTasks().indexOf(t);
		if(countTask>0)
		for(int i = proyect.getTasks().indexOf(t) ;i*3+2<NodeGrid.getComponentCount();i++)
		{
			Component label= NodeGrid.getComponent(mark*3);
			GridBagLayout g = (GridBagLayout)NodeGrid.getLayout();
			GridBagConstraints labelCons= g.getConstraints(label);
			labelCons.gridx=i+1;
			NodeGrid.remove(3*mark);
			NodeGrid.add(label, labelCons);
			
			
			NodeButton node= (NodeButton)NodeGrid.getComponent(mark*3);
			GridBagConstraints NodeConst= g.getConstraints(node);
			NodeConst.gridx=i+1;
			NodeGrid.remove(mark*3);
			NodeGrid.add(node, NodeConst);
			
			Component TaskLabel= NodeGrid.getComponent(mark*3);
			GridBagConstraints TaskConst= g.getConstraints(TaskLabel);
			TaskConst.gridx=i+1;
			NodeGrid.remove(mark*3);
			NodeGrid.add(TaskLabel, TaskConst);
			
			
		}
		
		Calendar c= t.getDeadline();
		JLabel lblNewLabel_2;
		
			int month = c.get(Calendar.MONTH)+1;
			int year = c.get(Calendar.YEAR);
			/*if(month==0)
				month=12;
			if(month== 12)
				year--;
				*/
		if(year!=9999)
		{
			 lblNewLabel_2 = new JLabel(c.get(Calendar.DAY_OF_MONTH)+"/"+month+"/"+year); //se le resta 1 a year para que funcione..
		}
		else
		{
			lblNewLabel_2 = new JLabel("No Especificada");
			
		}
		lblNewLabel_2.setFont(new Font("Bodoni MT Bold", Font.BOLD, 12));
		lblNewLabel_2.setForeground(proyect.getColor());
		lblNewLabel_2.setBackground(new Color(255, 250, 250));
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 0, 0);
		gbc_lblNewLabel_2.gridx = proyect.getTasks().indexOf(t);	
		gbc_lblNewLabel_2.gridy = 0;
		gbc_lblNewLabel_2.weighty=1;
		gbc_lblNewLabel_2.anchor= gbc_lblNewLabel_2.SOUTH;
		
		NodeButton nodeButton_5;
		if(!t.getName().equals(""))
			nodeButton_5 = new NodeButton(t.getName().substring(0, 1),t);
		else
			nodeButton_5 = new NodeButton("((?)",t);
		
		nodeButton_5.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 30));
		nodeButton_5.setForeground(t.getContext().getColor());
		GridBagConstraints gbc_nodeButton_5 = new GridBagConstraints();
		gbc_nodeButton_5.insets = new Insets(0, 0, 0, 0);
		gbc_nodeButton_5.gridx = proyect.getTasks().indexOf(t);
		gbc_nodeButton_5.gridy = 1;
		gbc_nodeButton_5.weightx=1;
		gbc_nodeButton_5.anchor=GridBagConstraints.CENTER;
		gbc_nodeButton_5.fill=GridBagConstraints.BOTH;
		nodeButton_5.setBackground(proyect.getColor());
		t.setColor(proyect.getColor());
		
		nodeButton_5.setAlignmentX(0.5f);
		
		JLabel TaskLabel;
		if(!t.getName().equals(""))
			TaskLabel = new JLabel(t.getName());
		else
			TaskLabel = new JLabel("(?)");
		TaskLabel.setFont(new Font("Bodoni MT Bold", Font.BOLD, 12));
		GridBagConstraints gbc_TaskLabel = new GridBagConstraints();
		TaskLabel.setForeground(proyect.getColor());
		gbc_TaskLabel.insets = new Insets(0, 0, 0, 5);
		gbc_TaskLabel.gridx = proyect.getTasks().indexOf(t);
		gbc_TaskLabel.gridy = 2;
		gbc_TaskLabel.anchor = gbc_TaskLabel.NORTH;
		NodeGrid.add(TaskLabel, gbc_TaskLabel,proyect.getTasks().indexOf(t)*3); 
		NodeGrid.add(nodeButton_5, gbc_nodeButton_5,proyect.getTasks().indexOf(t)*3);
		NodeGrid.add(lblNewLabel_2, gbc_lblNewLabel_2,proyect.getTasks().indexOf(t)*3);
		this.revalidate();
		this.repaint();
		countTask++;
		
		return nodeButton_5;
	}
public void ChangeName(String newName)

{
	ProyectLabel.setText(newName);
	ProyectLabel.repaint();
}
@Override
protected void paintComponent(Graphics g) {

    super.paintComponent(g);
    int width = getWidth();
    int height = getHeight();
    int shadowGap = this.shadowGap;
    Color shadowColorA = new Color(shadowColor.getRed(), 
    		shadowColor.getGreen(), shadowColor.getBlue(), shadowAlpha);
    Graphics2D graphics = (Graphics2D) g;

    //Sets antialiasing if HQ.
    if (highQuality) {
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
		RenderingHints.VALUE_ANTIALIAS_ON);
    }

    //Draws shadow borders if any.
    if (shady) {
        graphics.setColor(shadowColorA);
        graphics.fillRoundRect(
                shadowOffset,// X position
                shadowOffset,// Y position
                width - strokeSize - shadowOffset, // width
                height - strokeSize - shadowOffset-40, // height
                arcs.width, arcs.height);// arc Dimension
    } else {
        shadowGap = 1;
    }

    //Draws the rounded opaque panel with borders.
    graphics.setColor(getBackground());
    if(proyect.getState()==State.Finished)
    {
    	int rc = getBackground().getRed();
   	 int gc = getBackground().getGreen();
   	 int bc = getBackground().getBlue();
   	 setBackground(new Color(rc,gc,bc,30));
   	 graphics.setColor(getBackground());
   	 
    }
    graphics.fillRoundRect(0, 45, width - shadowGap, 
	height - shadowGap-67, arcs.width, arcs.height);
    graphics.setColor(getForeground());
    
    
    if(proyect.getState()==State.Finished)
    {
    	int rc = getForeground().getRed();
   	 int gc = getForeground().getGreen();
   	 int bc = getForeground().getBlue();
   	 setForeground(new Color(rc,gc,bc,30));
   	 graphics.setColor(getForeground());
   	 
 	int rl = ProyectLabel.getForeground().getRed();
  	 int gl = ProyectLabel.getForeground().getGreen();
  	 int bl = ProyectLabel.getForeground().getBlue();
  	 ProyectLabel.setForeground(new Color(rl,gl,bl,50));
  	 
   	 
    }
    
    else if(proyect.getState()==State.Delayed){
    	 graphics.setColor(Color.RED);strokeSize=2;}
    else if(proyect.getState()==State.Pause){
   	 graphics.setColor(Color.YELLOW);strokeSize=2;}
    graphics.setStroke(new BasicStroke(strokeSize=2));
    graphics.drawRoundRect(0, 45, width - shadowGap, 
	height - shadowGap-67, arcs.width, arcs.height);

  
    
    if(proyect.getState() ==State.Active)
    {
    	graphics.setColor(Color.GREEN);
        graphics.fillRoundRect(10, 70, 20, 
    	20, arcs.width, arcs.height);
        graphics.setColor(Color.white);
        graphics.setStroke(new BasicStroke());
        graphics.drawRoundRect(10, 70, 20, 
    	20, arcs.width, arcs.height);
    }
    else if(proyect.getState() ==State.Delayed)
    {
    	graphics.setColor(Color.RED);
        graphics.fill(new Rectangle(new Point(10, 70), new Dimension(20, 20)));
        graphics.setColor(Color.WHITE);
        graphics.draw(new Rectangle(new Point(10, 70), new Dimension(20, 20)));
    	
    }
    else if(proyect.getState() ==State.Pause)
    {
    	graphics.setColor(Color.YELLOW);
    	int[] X ={10,20,30};
    	int[] Y ={84,64,84};
    	
        graphics.fillPolygon(X, Y, 3);
        graphics.setColor(Color.WHITE);
        graphics.drawPolygon(X, Y, 3);
    	
    }
    //Sets strokes to default, is better.
    graphics.setStroke(new BasicStroke());
	}

public String GetName()
{
	return ProyectLabel.getText();
}
@Override
public void actionPerformed(ActionEvent e) {
	// TODO Auto-generated method stub
	if(proyect.getState()==State.Finished)
	{
		this.repaint();
		for(Task t : proyect.ActiveTasks())
			t.setState(State.Finished);
		return;
	}
	for(Task t:proyect.getTasks())
	{
		if(t.getState()==State.Delayed)
		{
			proyect.setState(State.Delayed);
			this.repaint();
			return;
		}
		else if(t.getState()==State.Pause)
		{
			proyect.setState(State.Pause);
		}
	}
	if(proyect.getState()==State.Pause)
	{
		this.repaint();
	}
	else
	{
		proyect.setState(State.Active);
		this.repaint();
	}
}
@Override
public void repaint() {
	// TODO Auto-generated method stub
	super.repaint();
	if(countTask>0)
	for(int i = 0 ;3*i+2<NodeGrid.getComponentCount();i++)
	{
		JLabel Fecha= (JLabel )NodeGrid.getComponent(i*3);
		NodeButton node= (NodeButton)NodeGrid.getComponent(i*3+1);
		JLabel  Nombre= (JLabel )NodeGrid.getComponent(i*3+2);
		
		Calendar c= node.task.getDeadline();
		
		int month = c.get(Calendar.MONTH)+1;
		int year = c.get(Calendar.YEAR);
			
		if(year!=9999)
		{
			 Fecha.setText(c.get(Calendar.DAY_OF_MONTH)+"/"+month+"/"+year); //se le resta 1 a year para que funcione..
		}
		else
		{
			Fecha.setText("No Especificada");
			
		}
		
		if(!node.task.getName().equals(""))
			Nombre.setText(node.task.getName());
		else
			Nombre.setText("(?)");
		
	}
	
}
}

	
