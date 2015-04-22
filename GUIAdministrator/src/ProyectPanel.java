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
	public ProyectPanel(String Name) {
		// TODO Auto-generated constructor stub
		super();
		setOpaque(false);
		this.setBounds(230, 64, 758, 121);
		this.setBackground(new Color(212, 227, 252));
		this.setLayout(null);
		
		ProyectLabel.setText(Name);
		ProyectLabel.setForeground(new Color(0, 128, 128));
		ProyectLabel.setHorizontalAlignment(SwingConstants.CENTER);
		ProyectLabel.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 18));
		ProyectLabel.setBounds(6, 45, 101, 31);
		this.add(ProyectLabel);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(106, 6, 632, 109);
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
		
		JPanel NodeGrid = new JPanel();
		scrollPane_3.setViewportView(NodeGrid);
		NodeGrid.setBackground(new Color(212, 227, 252));
		GridBagLayout gbl_NodeGrid = new GridBagLayout();
		gbl_NodeGrid.columnWidths = new int[] {79};
		gbl_NodeGrid.rowHeights = new int[] {10, 40, 10};
		gbl_NodeGrid.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_NodeGrid.rowWeights = new double[]{0.0, 0.0, 0.0};
		NodeGrid.setLayout(gbl_NodeGrid);
		
		JLabel lblNewLabel_2 = new JLabel("CTM");
		lblNewLabel_2.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 12));
		lblNewLabel_2.setForeground(new Color(0, 128, 128));
		lblNewLabel_2.setBackground(new Color(255, 250, 250));
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_2.gridx = 0;
		gbc_lblNewLabel_2.gridy = 0;
		NodeGrid.add(lblNewLabel_2, gbc_lblNewLabel_2);
		
		NodeButton nodeButton_5 = new NodeButton("asdsa");
		nodeButton_5.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 30));
		nodeButton_5.setForeground(new Color(0, 128, 128));
		GridBagConstraints gbc_nodeButton_5 = new GridBagConstraints();
		gbc_nodeButton_5.insets = new Insets(0, 0, 5, 5);
		gbc_nodeButton_5.gridx = 0;
		gbc_nodeButton_5.gridy = 1;
		NodeGrid.add(nodeButton_5, gbc_nodeButton_5);
		nodeButton_5.setBackground(Color.blue);
		nodeButton_5.setPreferredSize(new Dimension(25, 25));  //maximo 50 para que quepan, minimo 10 para que se vea
		nodeButton_5.setAlignmentX(0.5f);
		
		JLabel TaskLavel = new JLabel("asdsad");
		TaskLavel.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 12));
		GridBagConstraints gbc_TaskLavel = new GridBagConstraints();
		TaskLavel.setForeground(new Color(112, 150, 252));
		gbc_TaskLavel.insets = new Insets(0, 0, 0, 5);
		gbc_TaskLavel.gridx = 0;
		gbc_TaskLavel.gridy = 2;
		NodeGrid.add(TaskLavel, gbc_TaskLavel);
	}
public void AddTask(int index, Task t)
	{
		if(NodeGrid.getSize().getHeight()<index) //verifica que el grid tenga el espacio indicado
		{
			NodeGrid.setSize(index+1,3);
		}
		
		JLabel lblNewLabel_2 = new JLabel("CTM");
		lblNewLabel_2.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 12));
		lblNewLabel_2.setForeground(new Color(0, 128, 128));
		lblNewLabel_2.setBackground(new Color(255, 250, 250));
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_2.gridx = index;
		gbc_lblNewLabel_2.gridy = 0;
		NodeGrid.add(lblNewLabel_2, gbc_lblNewLabel_2);
		
		NodeButton nodeButton_5 = new NodeButton(t.getName().substring(0, 1));
		nodeButton_5.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 30));
		nodeButton_5.setForeground(new Color(0, 128, 128));
		GridBagConstraints gbc_nodeButton_5 = new GridBagConstraints();
		gbc_nodeButton_5.insets = new Insets(0, 0, 5, 5);
		gbc_nodeButton_5.gridx = index;
		gbc_nodeButton_5.gridy = 1;
		NodeGrid.add(nodeButton_5, gbc_nodeButton_5);
		nodeButton_5.setBackground(t.getColor());
		nodeButton_5.setPreferredSize(new Dimension(25, 25));  //maximo 50 para que quepan, minimo 10 para que se vea
		nodeButton_5.setAlignmentX(0.5f);
		
		JLabel TaskLavel = new JLabel(t.getName());
		TaskLavel.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 12));
		GridBagConstraints gbc_TaskLavel = new GridBagConstraints();
		TaskLavel.setForeground(new Color(112, 150, 252));
		gbc_TaskLavel.insets = new Insets(0, 0, 0, 5);
		gbc_TaskLavel.gridx = index;
		gbc_TaskLavel.gridy = 2;
		NodeGrid.add(TaskLavel, gbc_TaskLavel);
		NodeGrid.repaint();
		this.repaint();
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
}
	
