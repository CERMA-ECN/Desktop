package fr.ecn.ombre.desktop;

import java.awt.GridLayout;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import fr.ecn.common.desktop.utils.BackNextPanel;

public class OptionsOptionsPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected OptionsController controller;
	
	protected BackNextPanel backNextPanel;

	protected JTextField dateField;
	protected JTextField timeField;
	protected JCheckBox shadowsOnWallsField;
	protected JCheckBox expendToStreetField;
	
	protected Calendar date;
	
	/**
	 * @param controller
	 * @param imagePanel 
	 */
	public OptionsOptionsPanel(OptionsController controller) {
		super();
		this.controller = controller;
		
		this.build();
	}
	
	private void build() {
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		
		JPanel form = new JPanel();
		form.setLayout(new GridLayout(4, 2, 5, 5));
		
		this.dateField = new JTextField();
		this.timeField = new JTextField();
		this.shadowsOnWallsField = new JCheckBox();
		this.expendToStreetField = new JCheckBox();
		
		this.dateField.setText("__/__/____");
		this.timeField.setText("__:__");
		
		form.add(new JLabel("Date"));
		form.add(this.dateField);
		form.add(new JLabel("Time"));
		form.add(this.timeField);
		form.add(new JLabel("Shadows on walls"));
		form.add(this.shadowsOnWallsField);
		form.add(new JLabel("Expend to street"));
		form.add(this.expendToStreetField);
		
		this.add(form);
		
		this.add(Box.createVerticalGlue());
		
		this.backNextPanel = new BackNextPanel(this.controller);
		this.add(this.backNextPanel);
	}
	
	public void valid() throws ParseException {
		//Validate infos
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		Date date = dateFormat.parse(this.dateField.getText() + " " + this.timeField.getText());
		
		this.date = Calendar.getInstance();
		this.date.setTime(date);
	}

	/**
	 * @return the date
	 */
	public Calendar getDate() {
		return date;
	}
	
	public boolean getShadowsOnWalls() {
		return this.shadowsOnWallsField.isSelected();
	}
	
	public boolean getExpendToStreet() {
		return this.expendToStreetField.isSelected();
	}
	
}
