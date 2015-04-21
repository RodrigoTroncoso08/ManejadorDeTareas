
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;

import javax.swing.JButton;


public class NodeButton extends JButton {
	protected int strokeSize = 1;
    /** Color of shadow */
    protected Color shadowColor = Color.black;
    /** Sets if it drops shadow */
    protected boolean shady = true;
    /** Sets if it has an High Quality view */
    protected boolean highQuality = true;
    /** Double values for Horizontal and Vertical radius of corner arcs */
    protected Dimension arcs = new Dimension(getPreferredSize().width, getPreferredSize().width);
    /** Distance between shadow border and opaque panel border */
    protected int shadowGap = 3;
    /** The offset of shadow.  */
    protected int shadowOffset =4;
    
    protected int nodeOffset =getPreferredSize().width/4;
    /** The transparency value of shadow. ( 0 - 255) */
    protected int shadowAlpha = 150;
	  public NodeButton(String label) {
	    super(label);

	    
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
	    	 int width = getPreferredSize().width;
	         int height = getPreferredSize().width;
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
	                     height - strokeSize - shadowOffset, // height
	                     arcs.width, arcs.height);// arc Dimension
	         } else {
	             shadowGap = 1;
	         }

	         //Draws the rounded opaque panel with borders.
	         graphics.setColor(getBackground());
	         graphics.fillRoundRect(0, 0, width - shadowGap, 
	        height - shadowGap, arcs.width, arcs.height);
	         graphics.setColor(getForeground());
	         graphics.setStroke(new BasicStroke(strokeSize));
	         graphics.drawRoundRect(0, 0, width - shadowGap, 
	 		height - shadowGap, arcs.width, arcs.height);

	         super.paintComponent(g);
	    }
	  }

	
}
