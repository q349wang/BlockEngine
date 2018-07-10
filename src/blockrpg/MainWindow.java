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
	private int zoom;

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
			
			if(zoom == 0) {
				zoom = Integer.parseInt(document.getElementsByTagName("zoom").item(0).getTextContent());
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
		
		Thread thread = new Thread(new Runnable() {

		    @Override
		    public void run() {
		    	MainWindow window = new MainWindow();
				window.setVisible(true);
				window.testFunc();  
		    }
		    
		});

		thread.start();
		
//		java.awt.EventQueue.invokeLater(() -> {
//			MainWindow window = new MainWindow();
//			window.setVisible(true);
//			window.testFunc();
//		});
	}

	private void testFunc(){
		Perspective pov = new Perspective(new double[] {-8000,0,8000}, new double[] {1, 0,-1}, new double[] {0,-1,0});
		Position2D[] points1 = {new Position2D(-1000,-500), new Position2D(-1000, 500), new Position2D(0, 500), new Position2D(0, -500)};
		Position2D[] points2 = {new Position2D(0,-500), new Position2D(0, 500), new Position2D(1000, 500), new Position2D(1000, -500)};
		Plane plane = new Plane(new Vector3D(0,1,0), new Vector3D(0,0,1), new Position3D());
		pov.setZoom(zoom);
		VisualFace face1 = new VisualFace(points1, points1.length, plane, pov);
		VisualFace face2 = new VisualFace(points2, points2.length, plane, pov);
		gamePanel.addShape(face1);
		gamePanel.addShape(face2);
		gamePanel.repaint();
		
//		try {
//			Thread.sleep(1000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		face.rotate(Math.PI, new Vector3D(0,0,1));
//		gamePanel.faces.clear();
//		gamePanel.faces.add(face);
//		gamePanel.repaint();
		int xSign = -1;
		int ySign = -1;
		int zSign = -1;
		while (true) {
			Vector3D axis = new Vector3D(1, 0, 0);
			face1 = face1.rotate(0.01, axis);
			face2 = face2.rotate(0.01, axis);

			
			if (face1.getPlane().getPos().getCoord()[0] <= -2000) {
				xSign = 1;
			} else if (face1.getPlane().getPos().getCoord()[0] >= 2000){
				xSign = -1;
			}
			if (face1.getPlane().getPos().getCoord()[1] <= -2000) {
				ySign = 1;
			} else if (face1.getPlane().getPos().getCoord()[1] >= 2000){
				ySign = -1;
			}
			if (face1.getPlane().getPos().getCoord()[1] <= -2000) {
				zSign = 1;
			} else if (face1.getPlane().getPos().getCoord()[1] >= 2000){
				zSign = -1;
			}
			face1.addX(xSign * 10);
			face2.addX(xSign * 10);
			face1.addY(ySign * 10);
			face2.addY(ySign * 10);
			face1.addZ(zSign * 10);
			face2.addZ(zSign * 10);
			
			gamePanel.faces.clear();
			gamePanel.addShape(face1);
			gamePanel.addShape(face2);
			gamePanel.repaint();
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	private Drawer gamePanel;
}
