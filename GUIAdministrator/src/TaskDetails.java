import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.*;

import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;


public class TaskDetails extends JPanel {
	private JTextField txtHh;
	private JTextField txtMm;

	/**
	 * Create the panel.
	 */
	public TaskDetails() {
		setBackground(new Color(30, 144, 255));
		setLayout(null);
		
		JTextArea textArea = new JTextArea();
		textArea.setBackground(new Color(102, 205, 170));
		textArea.setBounds(30, 100, 290, 210);
		add(textArea);
		
		JLabel lblNombreTarea = new JLabel("Nombre Tarea");
		lblNombreTarea.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNombreTarea.setForeground(new Color(255, 255, 255));
		lblNombreTarea.setBounds(73, 44, 200, 50);
		add(lblNombreTarea);
		
		JComboBox ctx = new JComboBox();
		ctx.setBounds(30, 344, 90, 20);
		add(ctx);
		
		JComboBox impo = new JComboBox();
		impo.setBounds(230, 28, 90, 20);
		add(impo);
		
		JCheckBox chckbxTareaLista = new JCheckBox("Tarea Lista");
		chckbxTareaLista.setBackground(new Color(30, 144, 255));
		chckbxTareaLista.setBounds(30, 487, 97, 23);
		chckbxTareaLista.setBorder(null);
		add(chckbxTareaLista);
		
		JLabel lblProceso = new JLabel("Progreso");
		lblProceso.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblProceso.setBounds(29, 510, 69, 23);
		add(lblProceso);
		
		JSlider slider = new JSlider();
		slider.setBackground(new Color(30, 144, 255));
		slider.setForeground(new Color(64, 224, 208));
		slider.setBounds(61, 544, 200, 20);
		add(slider);
		
		JButton btnD = new JButton("+ D");
		btnD.setBounds(180, 385, 51, 23);
		add(btnD);
		
		JButton btnW = new JButton("+ W");
		btnW.setBounds(251, 385, 69, 23);
		add(btnW);
		
		txtHh = new JTextField();
		txtHh.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				if(txtHh.getText().equals("HH"))
					txtHh.setText("");
			}
			@Override
			public void focusLost(FocusEvent e) {
				if(txtHh.getText().equals(""))
					txtHh.setText("HH");				
			}
		});
		txtHh.setForeground(new Color(255, 255, 255));
		txtHh.setFont(new Font("Tahoma", Font.PLAIN, 19));
		txtHh.setBackground(new Color(30, 144, 255));
		txtHh.setText("HH");
		txtHh.setBounds(151, 431, 32, 34);
		add(txtHh);
		txtHh.setColumns(10);
		
		txtMm = new JTextField();
		txtMm.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if(txtMm.getText().equals("MM"))
					txtMm.setText("");
			}
			@Override
			public void focusLost(FocusEvent e) {
				if(txtMm.getText().equals(""))
					txtMm.setText("MM");
			}
		});
		txtMm.setBackground(new Color(30, 144, 255));
		txtMm.setForeground(new Color(255, 255, 255));
		txtMm.setFont(new Font("Tahoma", Font.PLAIN, 19));
		txtMm.setText("MM");
		txtMm.setBounds(193, 431, 38, 34);
		add(txtMm);
		txtMm.setColumns(10);
		
		JButton btnGuardar = new JButton("GUARDAR");
		btnGuardar.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnGuardar.setBackground(new Color(0, 255, 0));
		btnGuardar.setBounds(203, 476, 117, 38);
		add(btnGuardar);
		

	}
}
