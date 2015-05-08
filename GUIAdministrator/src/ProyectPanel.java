import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.RenderingHints;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import backend.*;

public class ProyectPanel extends JPanel{
	
		
		public JLabel ProyectLabel = new JLabel("Proyect");
		public JPanel NodeGrid = new JPanel();
		
		protected int strokeSize = 1;
	    /** Color of shadow */
	    protected Color shadowColor = new Color(0,110,140);
	    /** Sets if it drops shadow */
	    protected boolean shady = true;
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
	    
	    static int ProyectCount=0;
	    
	public ProyectPanel(String Name) {
		// TODO Auto-generated constructor stub
		super();
		setOpaque(false);
		this.setBounds(230, 20+130*ProyectCount, 748, 119);
		this.setBackground(new Color(212, 227, 252));
		this.setLayout(null);
		ProyectCount++;
		
		
		ProyectLabel.setText(Name);
		ProyectLabel.setForeground(new Color(0, 128, 128));
		ProyectLabel.setHorizontalAlignment(SwingConstants.CENTER);
		ProyectLabel.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 18));
		ProyectLabel.setBounds(6, 45, 101, 31);
		this.add(ProyectLabel);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(140, 6, 602, 100);
		scrollPane_1.setBackground(new Color(212, 227, 252));
		this.add(scrollPane_1);
		scrollPane_1.setBorder(BorderFactory.createEmptyBorder());
		scrollPane_1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.BLACK);
		scrollPane_1.setViewportView(panel_1);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.X_AXIS));
		
		JScrollPane scrollPane_3 = new JScrollPane();
		panel_1.add(scrollPane_3);
		scrollPane_3.setBorder(BorderFactory.createEmptyBorder());
		
		scrollPane_3.setViewportView(NodeGrid);
		NodeGrid.setBackground(new Color(212, 227, 252));
		GridBagLayout gbl_NodeGrid = new GridBagLayout();
		gbl_NodeGrid.columnWidths = new int[] {79,79,79,79,79,77,79,79,79,79,79,79};
		gbl_NodeGrid.rowHeights = new int[] {10, 40, 10};
		gbl_NodeGrid.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_NodeGrid.rowWeights = new double[]{0.0, 0.0, 0.0};
		NodeGrid.setLayout(gbl_NodeGrid);
		
		this.setBorder(BorderFactory.createEmptyBorder());
		
		
	}
public void AddTask(Task t)
	{
		if(NodeGrid.getSize().getHeight()<countTask) //verifica que el grid tenga el espacio indicado
		{
			NodeGrid.setSize(countTask+1,3);
		}
		
		if(t.getDeadline()!=null)
		{
			JLabel lblNewLabel_2 = new JLabel(t.getDeadline().DAY_OF_MONTH + "/"+t.getDeadline().DECEMBER+"/"+t.getDeadline().YEAR);  //Date
			lblNewLabel_2.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 12));
			lblNewLabel_2.setForeground(new Color(0, 128, 128));
			lblNewLabel_2.setBackground(new Color(255, 250, 250));
			GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
			gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
			gbc_lblNewLabel_2.gridx = countTask;
			gbc_lblNewLabel_2.gridy = 0;
			NodeGrid.add(lblNewLabel_2, gbc_lblNewLabel_2);
		}
		NodeButton nodeButton_5 = new NodeButton(t.getName().substring(0, 1));  //node
		nodeButton_5.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 30));
		nodeButton_5.setForeground(t.getContext());
		GridBagConstraints gbc_nodeButton_5 = new GridBagConstraints();
		gbc_nodeButton_5.insets = new Insets(0, 0, 5, 5);
		gbc_nodeButton_5.gridx = countTask;
		gbc_nodeButton_5.gridy = 1;
		NodeGrid.add(nodeButton_5, gbc_nodeButton_5);
		nodeButton_5.setBackground(t.getColor());
		nodeButton_5.setPreferredSize(new Dimension(25, 25));  //maximo 50 para que quepan, minimo 10 para que se vea
		nodeButton_5.setAlignmentX(0.5f);
		
		JLabel TaskLabel = new JLabel(t.getName());  //name
		TaskLabel.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 12));
		GridBagConstraints gbc_TaskLabel = new GridBagConstraints();
		TaskLabel.setForeground(new Color(112, 150, 252));
		gbc_TaskLabel.insets = new Insets(0, 0, 0, 5);
		gbc_TaskLabel.gridx = countTask;
		gbc_TaskLabel.gridy = 2;
		NodeGrid.add(TaskLabel, gbc_TaskLabel);
		this.revalidate();
		this.repaint();
		countTask++;
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

    //Sets strokes to default, is better.
    graphics.setStroke(new BasicStroke());
	}

public String GetName()
{
	return ProyectLabel.getText();
}
public void setColorName(Color c)
{
	ProyectLabel.setForeground(c);
}
}

	
