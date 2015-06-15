import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.Timer;

import net.miginfocom.swing.MigLayout;
import backend.Task;


public class TaskPanel extends JPanel implements ActionListener{
	
	Task t;
	int position;
	int total;
	Graphics2D graphics;
	NodeButton node;
	public TaskPanel(Task t,int Position, int total) {
		// TODO Auto-generated constructor stub
		super();
		
		setLayout(new MigLayout());
		setOpaque(false);
		Timer clock = new Timer(5000,this); //cada 5 segundos verifica si algun task cambio de estado
		clock.setRepeats(true);
		clock.setInitialDelay(1000);
		clock.start();
		node = new NodeButton(t.getName(),t);
		node.setBackground(t.getColor());
		node.setForeground(t.getContext().getColor());
		node.shady=false;
		add(node, "pos "+Position*60+" 0"+", w 50!,h 50!");
		this.t=t;
		this.total = total;
		if(t.getWorkingDays()==0)
			t.setWorkingDays(5);
		position =Position;
		TaskPanel aux= this;
		node.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JDialog ask = new JDialog();
				JPanel base = new JPanel();
				base.setBackground(new Color(213,227,254));
				base.setLayout(null);
				ask.setContentPane(base);
				ask.setBounds(500, 300, 200, 150);
				
				JLabel workingLabel = new JLabel("Working Days");
				workingLabel.setForeground(new Color(0,110,141));
				workingLabel.setBounds(20, 20, 130, 30);
				base.add(workingLabel);
				
				JTextField workingD = new JTextField();
				workingD.setText(""+t.getWorkingDays());
				workingD.setForeground(new Color(0,110,141));
				workingD.setBorder(BorderFactory.createLineBorder(new Color(0,110,141)));
				workingD.setBounds(100, 20, 80, 30);
				base.add(workingD);
				
				JButton guardar = new JButton("guardar");
				guardar.setBackground(Color.white);
				guardar.setForeground(new Color(0,110,141));
				guardar.setBounds(100, 80, 80, 20);
				base.add(guardar);
				guardar.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						try
						{
							t.setWorkingDays(Integer.parseInt(workingD.getText()));
							aux.revalidate();
							aux.repaint();
						}
						catch(NumberFormatException nfe)
						{
							JOptionPane.showMessageDialog(ask, "No es entero");
						}
						ask.setVisible(false);
						ask.dispose();
					}
				});
				ask.getRootPane().setDefaultButton(guardar);
				ask.setResizable(false);
				ask.setVisible(true);
				
				
			}
		});
		
		
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		graphics = (Graphics2D) g.create();
		
		/////////////////////////////////// BlueBase
		graphics.setColor(getBackground());
	    graphics.fillRoundRect(0, 0, getWidth()-3, getHeight()-3, 10, 10);
	    graphics.setColor(getForeground());
	    /*
	    graphics.setStroke(new BasicStroke((float)1));
	    graphics.drawRoundRect(0, 0, getWidth()-3, getHeight()-3, 5, 5);
	    */
	    
	    /////////////////////////////////// estela
	    if(t!=null)
	    {
		    graphics.setColor(t.getColor().brighter());
		    graphics.fillRoundRect(60*(position-t.getWorkingDays()+1),25-(10+12*(t.getRelevance()+1))/2,
		    		55*t.getWorkingDays()-5, 10+12*(t.getRelevance()+1), 10*(t.getRelevance()+1), 10*(t.getRelevance()+1));
		    graphics.setColor(t.getContext().getColor());
		    graphics.setStroke(new BasicStroke((float)3));
		    graphics.drawRoundRect(60*(position-t.getWorkingDays()+1), 25-(10+12*(t.getRelevance()+1))/2,
		    		55*t.getWorkingDays()-5, 10+12*(t.getRelevance()+1), 10*(t.getRelevance()+1), 10*(t.getRelevance()+1));
	    }
	    
	    graphics.setStroke(new BasicStroke((float)1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
	    graphics.setColor(new Color(40, 140, 180));
	    for(int i=0; i<total+1; i++ ){
	    	graphics.drawLine(55+60*i, 0, 55+60*i, 50);
	    }
	    
	}
	
	public void setTotal(int t)
	{
		total=t;
	}
	
	public void setPosition(int p)
	{
		position=p;
	}
	
	public Task getTask()
	{
		return t;
	}
	
	@Override
	public void repaint() {
		
		
		if(graphics!=null)
		{
			graphics.setStroke(new BasicStroke((float)1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
		    graphics.setColor(new Color(40, 140, 180));
		    for(int i=0; i<total+1; i++ )
		    {
		    	graphics.drawLine(60+60*i, 0, 60+60*i, 50);
		    }
		
		
			if(t!=null)
		    {
			    graphics.setColor(t.getColor().brighter());
			    graphics.fillRoundRect(60*(position-t.getWorkingDays()+1),25-(10+12*(t.getRelevance()+1))/2,
			    		55*t.getWorkingDays()-5, 10+12*(t.getRelevance()+1), 10*(t.getRelevance()+1), 10*(t.getRelevance()+1));
			    graphics.setColor(t.getContext().getColor());
			    graphics.setStroke(new BasicStroke((float)3));
			    graphics.drawRoundRect(60*(position-t.getWorkingDays()+1), 25-(10+12*(t.getRelevance()+1))/2,
			    		55*t.getWorkingDays()-5, 10+12*(t.getRelevance()+1), 10*(t.getRelevance()+1), 10*(t.getRelevance()+1));
		    }
			
			if(node!=null)
			{
				remove(node);
				add(node, "pos "+position*60+" 0"+", w 50!,h 50!");
			}
		}
		super.repaint();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		this.repaint();
	}
	
	
	
	
	
	
	
	
	
	
	
}
