import java.awt.BasicStroke;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;
import backend.Task;


public class TaskPanel extends JPanel{
	
	Task t;
	int position;
	public TaskPanel(Task t,int Position, int total) {
		// TODO Auto-generated constructor stub
		super();
		setLayout(new MigLayout());
		setOpaque(false);
		NodeButton node = new NodeButton(t.getName(),t);
		node.setBackground(t.getColor());
		node.setForeground(t.getContext());
		node.shady=false;
		add(node, "pos "+Position*60+" 0"+", w 50!,h 50!");
		
		
		
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D graphics = (Graphics2D) g;
		
		/////////////////////////////////// BlueBase
		graphics.setColor(getBackground());
	    graphics.fillRoundRect(0, 0, getWidth()-3, getHeight()-3, 5, 5);
	    graphics.setColor(getForeground());
	    graphics.setStroke(new BasicStroke((float)1));
	    graphics.drawRoundRect(0, 0, getWidth()-3, getHeight()-3, 5, 5);
	    
	    /////////////////////////////////// estela
	    if(t!=null)
	    {
		    graphics.setColor(t.getColor().brighter());
		    graphics.fillRoundRect(10*(position-t.getWorkingDays()),5, 15*t.getRelevance(), 15*t.getRelevance(), 5, 5);
		    graphics.setColor(t.getContext());
		    graphics.setStroke(new BasicStroke((float)1));
		    graphics.drawRoundRect(10*(position-t.getWorkingDays()), 5, 10*t.getWorkingDays(), 15*t.getRelevance()+5, 25, 5);
	    }
	    
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
