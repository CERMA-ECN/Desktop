package fr.ecn.common.desktop;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import fr.ecn.common.desktop.actions.AboutAction;
import fr.ecn.common.desktop.actions.OpenAction;
import fr.ecn.common.desktop.actions.QuitAction;


public class MainWindow extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JPanel imagePanel;
	private JPanel optionsPanel;
	
	private MainController controller;

	public MainWindow(MainController controller){
		super();
		
		this.controller = controller;
		
		build();
	}
	
	private void build(){
		setTitle(this.controller.getApplication().getTitle());
		setSize(1200,800);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(buildContentPane());
		setJMenuBar(buildJMenuBar());
	}

	private Container buildContentPane() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		
		this.imagePanel = new JPanel();
		this.imagePanel.setLayout(new BorderLayout());
		panel.add(this.imagePanel, BorderLayout.CENTER);

		this.optionsPanel = new JPanel();
		this.optionsPanel.setLayout(new BorderLayout());
		panel.add(this.optionsPanel, BorderLayout.EAST);
		
		return panel;
	}

	private JMenuBar buildJMenuBar() {
		JMenuBar menuBar = new JMenuBar();

		JMenu menu1 = new JMenu("File");

		menu1.add(new JMenuItem(new OpenAction(this.controller)));
		
		menu1.add(new JMenuItem(new QuitAction(this.controller)));

		menuBar.add(menu1);

		JMenu menu2 = new JMenu("Help");
		
		menu2.add(new JMenuItem(new AboutAction(this.controller)));
		
		menuBar.add(menu2);

		return menuBar;
	}
	
	public void setImagePanel(Component comp) {
		this.imagePanel.removeAll();
		this.imagePanel.add(comp);
		this.validate();
	}
	
	public void setOptionsPanel(Component comp) {
		this.optionsPanel.removeAll();
		this.optionsPanel.add(comp);
		this.validate();
	}
}
