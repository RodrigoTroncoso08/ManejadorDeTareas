import backend.*;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.TimeUnit;

import javax.swing.JButton;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.xml.ws.Dispatch;

import com.sun.glass.ui.InvokeLaterDispatcher;


public class NodeButton extends JButton implements ActionListener {
	
	

	protected int strokeSize = 2;
    /** Color of shadow */
    protected Color shadowColor = Color.black;
    /** Sets if it drops shadow */
    protected boolean shady = true;
    /** Sets if it has an High Quality view */
    protected boolean highQuality = true;
    /** Double values for Horizontal and Vertical radius of corner arcs */
    protected Dimension arcs = new Dimension(getPreferredSize().width*2, getPreferredSize().width*2);
    /** Distance between shadow border and opaque panel border */
    protected int shadowGap = 3;
    /** The offset of shadow.  */
    protected int shadowOffset =4;
    
    protected Graphics2D graphics;
    
    protected int nodeOffset =getPreferredSize().width/4;
    /** The transparency value of shadow. ( 0 - 255) */
    protected Task task;
    protected int shadowAlpha = 150;
	  public NodeButton(String label, Task t) {
	    super("");
	    task=t;
	    Timer clock =new Timer(10000,this);
	    clock.setRepeats(true);
	    clock.setCoalesce(true);;
	    clock.start();
	// These statements enlarge the button so that it 
	// becomes a circle rather than an oval.
	    Dimension size = getPreferredSize();
	    size.width = size.height = Math.max(size.width, 
	      size.width);
	    setPreferredSize(size);
	// This call causes the JButton not to paint 
	   // the background.
	// This allows us to paint a round background.
	    setContentAreaFilled(false);
	    
	  }

	// Paint the round background and label.
	  protected void paintComponent(Graphics g) {
		  {
	// You might want to make the highlight color 
	   // a property of the RoundButton class.
			   //maximo 50 para que quepan, minimo 10 para que se vea\
				
	    	 int width = 10+12*(task.getRelevance()+1);
	         int height = width;
	         graphics = (Graphics2D) g;
	         
	         //Sets antialiasing if HQ.
	         if (highQuality) {
	             graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
	 			RenderingHints.VALUE_ANTIALIAS_ON);
	         }


	         //Draws the rounded opaque panel with borders.
	         if(task.getState()!=State.Finished)
	         {
		         graphics.setColor(getBackground());
		         graphics.fillRoundRect((this.getWidth()-width)/2, (this.getHeight()-height)/2, width, 
		         height, arcs.width, arcs.height);
		         setForeground(task.getContext().getColor());
		         graphics.setColor(task.getContext().getColor());
		         BasicStroke bs3 = new BasicStroke((float) 3.5);
		         graphics.setStroke(bs3);
		         graphics.drawRoundRect((this.getWidth()-width)/2, (this.getHeight()-height)/2, width , 
		 		        height, arcs.width, arcs.height);
		         graphics.setStroke(new BasicStroke(strokeSize));
	         }
	         if(task.getState()==State.Finished)
	         {
	        	 
	        	 int red=getBackground().getRed();
	        	 int blue=getBackground().getBlue();
	        	 int green=getBackground().getGreen();
	        	 setBackground(new Color(red,green,blue,99));
	        	 
	        	 int rc = getForeground().getRed();
	        	 int gc = getForeground().getGreen();
	        	 int bc = getForeground().getBlue();
	        	 setForeground(new Color(rc,gc,bc,20));
	        	 
	        	 graphics.setColor(getBackground());
	        	 graphics.fillRoundRect((this.getWidth()-width)/2, (this.getHeight()-height)/2, width, 
	      		         height, arcs.width, arcs.height);
	        	 graphics.setColor(getForeground());
	        	 graphics.setStroke(new BasicStroke(strokeSize));
	        	 graphics.drawRoundRect((this.getWidth()-width)/2, (this.getHeight()-height)/2, width , 
			 		        height, arcs.width, arcs.height);
			     
	        	 
	        	 
	         }
	         else if(task.getState()!=State.Active)
	         {
		        if(task.getState()==State.Delayed)
		        	 graphics.setColor(new Color(210,0,0));
		        else if(task.getState()==State.Pause)
		        	 graphics.setColor(Color.YELLOW);
		        
		        //graphics.drawRect(0, 0, width+10+strokeSize, height+10+strokeSize);
		        graphics.drawRoundRect((this.getWidth()-width)/2-3,(this.getHeight()-height)/2-3, width+6, 
		        		height+6, height/5
		        		, height/5);
	         }
	         setToolTipText(task.getDescription());
	         super.paintComponent(g);
	    }
	  }

	
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(this.task.getChange())
		{
			this.repaint();
			this.task.setChange(false);
			task.isCheck(0);
			setToolTipText(task.getDescription());
		}
	}

	public Task getTask(){
		return task;
	}

}
