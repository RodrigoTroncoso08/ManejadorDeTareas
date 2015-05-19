import java.awt.BasicStroke;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;
import backend.Task;


public class TaskPanel extends JPanel{
	
	Task t;
	int position;
	MigLayout layout;
	public TaskPanel(Task t,int Position) {
		// TODO Auto-generated constructor stub
		super();
		layout = new MigLayout("", "[20]", "[50]");
		position=Position;
		String aux= (String)layout.getColumnConstraints();
		for(int i=1; i<position;i++)
		{
			aux=aux+"10[20]";
		}
		layout.setColumnConstraints(aux);
		setLayout(layout);
		
		setOpaque(false);
		NodeButton node = new NodeButton(t.getName(),t);
		node.setBounds(20, 0, t.getRelevance()*15,t.getRelevance()*15);
		node.setBackground(t.getColor());
		node.setForeground(t.getContext());
		node.setPreferredSize(new Dimension(15*(t.getRelevance()+1), 15*(t.getRelevance()+1)));
		node.shady=false;
		setBounds(0, 0, 600, 50);
		add(node,"cell "+position+" 0,grow");
		
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D graphics = (Graphics2D) g;
		
		/////////////////////////////////// BlueBase
		graphics.setColor(getBackground());
	    graphics.fillRoundRect(0, 0, getWidth()-3, getHeight()-5, 5, 5);
	    graphics.setColor(getForeground());
	    graphics.setStroke(new BasicStroke((float)1));
	    graphics.drawRoundRect(0, 0, getWidth()-3, getHeight()-5, 5, 5);
	    
	    /////////////////////////////////// estela
	    /*
	    graphics.setColor(t.getColor().brighter());
	    graphics.fillRoundRect(10*(position-t.getWorkingDays()),5, 15*t.getRelevance(), 15*t.getRelevance(), 5, 5);
	    graphics.setColor(t.getContext());
	    graphics.setStroke(new BasicStroke((float)1));
	    graphics.drawRoundRect(10*(position-t.getWorkingDays()), 5, 10*t.getWorkingDays(), 15*t.getRelevance()+5, 25, 5);
	    */
	    
	    /////////////////////////////////// node
	    /*
	    graphics.setColor(t.getColor());
	    graphics.fillRoundRect(10*position, 0, 15*t.getRelevance(), 15*t.getRelevance(), 5, 5);
	    graphics.setColor(t.getContext());
	    graphics.setStroke(new BasicStroke((float)1));
	    graphics.drawRoundRect(10*position, 0, 15*t.getRelevance(), 15*t.getRelevance(), 5, 5);
	    */
	    
	    
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
