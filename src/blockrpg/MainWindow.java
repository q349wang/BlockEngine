/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blockrpg;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 *
 * @author L
 */
public class MainWindow extends javax.swing.JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9112470272791066228L;

	private int _width;
	private int _height;

	/**
	 * Creates new form MainWindow
	 */
	public MainWindow() {
		
		buildConfig();
		initComponents();
	}
	
	/**
	 * Reads in values from xml config file and updates accordingly
	 */
	private void buildConfig() {
		File file = new File("src\\blockrpg\\MainWindow.form");
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder;
		try {
			documentBuilder = documentBuilderFactory.newDocumentBuilder();
			Document document = documentBuilder.parse(file);

			_width = Integer.parseInt(document.getElementsByTagName("width").item(0).getTextContent());
			_height = Integer.parseInt(document.getElementsByTagName("height").item(0).getTextContent());

			if (_width != Toolkit.getDefaultToolkit().getScreenSize().getWidth()
					|| _height != Toolkit.getDefaultToolkit().getScreenSize().getHeight()) {

				_width = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
				_height = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
				document.getElementsByTagName("width").item(0).setTextContent(String.valueOf(_width));
				document.getElementsByTagName("height").item(0).setTextContent(String.valueOf(_height));
			}

			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(document);
			StreamResult result = new StreamResult(file);
			transformer.transform(source, result);
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void initComponents() {

		setExtendedState(MainWindow.MAXIMIZED_BOTH);
		setUndecorated(true);
		setVisible(true);
		setSize(_width, _height);

		gamePanel = new Drawer();

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setTitle("BlockRPG");
		setResizable(false);
		gamePanel.setBackground(new java.awt.Color(242, 242, 242));
		gamePanel.setSize(_width, _height);
		gamePanel.setPreferredSize(new Dimension(_width, _height));
		;
		gamePanel.setVisible(true);
		add(gamePanel);
		// javax.swing.GroupLayout gamePanelLayout = new
		// javax.swing.GroupLayout(gamePanel);
		// gamePanel.setLayout(gamePanelLayout);
		// gamePanelLayout.setHorizontalGroup(gamePanelLayout
		// .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0,
		// 868, Short.MAX_VALUE));
		// gamePanelLayout.setVerticalGroup(gamePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		// .addGap(0, 442, Short.MAX_VALUE));
		//
		// javax.swing.GroupLayout layout = new
		// javax.swing.GroupLayout(getContentPane());
		// getContentPane().setLayout(layout);
		// layout.setHorizontalGroup(
		// layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(gamePanel,
		// javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
		// Short.MAX_VALUE));
		// layout.setVerticalGroup(
		// layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(gamePanel,
		// javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
		// Short.MAX_VALUE));
		gamePanel.addKeyListener(new KeyInput());
		gamePanel.requestFocus();
		
		
		Perspective pov = new Perspective(new double[] {10, 0,0}, new double[] {-1, 0,0}, new double[] {0,-1,0});
		Position2D[] points = {new Position2D(0,0), new Position2D(0, 10), new Position2D(10, 10), new Position2D(10, 0)};
		Plane plane = new Plane(new Vector3D(0,1,0), new Vector3D(0,0,1), new Position3D());
		pov.setZoom(10);
		VisualFace face = new VisualFace(points, 4, plane, pov);
		gamePanel.addShape(face);
		gamePanel.repaint();
		pack();
	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(String args[]) {

		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException ex) {
			java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null,
					ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null,
					ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null,
					ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null,
					ex);
		}

		/* Create and display the form */
		java.awt.EventQueue.invokeLater(() -> {
			new MainWindow().setVisible(true);
		});
	}

	private Drawer gamePanel;
}
