/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blockrpg;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JLabel;
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

	public static boolean FPS;
	public static boolean DEBUG;
	public static boolean WIRE;
	
	private ArrayList<Face> faces;

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

			FPS = Boolean.parseBoolean(document.getElementsByTagName("fps").item(0).getTextContent());
			DEBUG = Boolean.parseBoolean(document.getElementsByTagName("debug").item(0).getTextContent());
			WIRE = Boolean.parseBoolean(document.getElementsByTagName("wire").item(0).getTextContent());
			if (_width != Toolkit.getDefaultToolkit().getScreenSize().getWidth()
					|| _height != Toolkit.getDefaultToolkit().getScreenSize().getHeight()) {

				_width = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
				_height = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
				document.getElementsByTagName("width").item(0).setTextContent(String.valueOf(_width));
				document.getElementsByTagName("height").item(0).setTextContent(String.valueOf(_height));
			}

			if (zoom == 0) {
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
		
		faces = new ArrayList<Face>();

		setExtendedState(MainWindow.MAXIMIZED_BOTH);
		setUndecorated(true);
		setVisible(true);
		setSize(_width, _height);

		gamePanel = new Drawer();
		fps = new JLabel();

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

		fps.setForeground(Color.BLACK);
		fps.setFont(new Font("Arial", Font.BOLD, 11));
		fps.setBounds(20, 10, 50, 40);

		gamePanel.addKeyListener(new KeyInput());
		if (FPS) {
			gamePanel.add(fps);
		}
		gamePanel.setLayout(null);

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
		MainWindow window = new MainWindow();


		java.awt.EventQueue.invokeLater(() -> {
			window.setVisible(true);
			
			
		});
		
		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				window.testFunc();
			}

		});

		thread.start();
	}

	private void testFunc() {
		Color col1 = new Color(45, 84, 38);
		Color col2 = new Color(72, 41, 124);
		Color col3 = new Color(56, 84, 200);
		Perspective pov = new Perspective(new double[] { -8000, 0, 0 }, new double[] { 1, 0, 0 },
				new double[] { 0, 1, 0 });
		pov.setZoom(zoom);
		Position2D[] points1 = { new Position2D(-1000, -500), new Position2D(-1000, 500), new Position2D(750, 500),
				new Position2D(750, -500) };
		Position2D[] points2 = { new Position2D(-750, -500), new Position2D(-750, 500), new Position2D(1000, 500),
				new Position2D(1000, -500) };
		Plane plane1 = new Plane(new Vector3D(0, 1, 0), new Vector3D(0, 0, 1), new Position3D(0, 0, 0));
		Plane plane2 = new Plane(new Vector3D(0, 1, 0), new Vector3D(0, 0, 1), new Position3D(0, 0, 0));
		Position2D[] points3 = { new Position2D(-1000, -1000), new Position2D(-1000, 1000),
				new Position2D(1000, 1000), new Position2D(1000, -1000) };
		Plane plane3 = new Plane(new Vector3D(0, 1, 0), new Vector3D(0, 0, 1), new Position3D(1500, 0, 0));
		Face face1 = new Face(points1, points1.length, plane1, pov, col1);
		Face face2 = new Face(points2, points2.length, plane2, pov, col2);
		Face face3 = new Face(points3, points3.length, plane3, pov, col3);
		faces.add(face1);
		faces.add(face2);
		faces.add(face3);
	//	gamePanel.addShape(face1);
		//gamePanel.addShape(face2);
	//	gamePanel.addShape(face3);
		Collections.sort(faces);
		gamePanel.setList(faces);
		java.awt.EventQueue.invokeLater(() -> {
			gamePanel.repaint();
			
		});
		//Thread.sleep(1000);
		face1.setX(1000);
		face2.setX(-1000);
		Collections.sort(faces);
		gamePanel.setList(faces);
		java.awt.EventQueue.invokeLater(() -> {
			gamePanel.repaint();
			
		});
		//Thread.sleep(1000);

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
		int ySign = 1;
		int zSign = -1;
		
		Long prevTick = System.nanoTime();
		Long currTick = System.nanoTime();
		while (true) {

			prevTick = System.nanoTime();

			Vector3D axis = new Vector3D(0, 1, 1);
			Vector3D axis2 = new Vector3D(1, 0, 0);
			face1.setTo(face1.rotate(0.01, axis));
			// face2 = face2.rotate(0.01, axis);

			// face1 = face1.rotate(0.01, axis2);

			if (face1.getPlane().getPos().getCoord()[0] <= -1000) {
				xSign = 1;
			} else if (face1.getPlane().getPos().getCoord()[0] >= 1000) {
				xSign = -1;
			}
			if (face2.getPlane().getPos().getCoord()[0] <= -1000) {
				ySign = 1;
			} else if (face2.getPlane().getPos().getCoord()[0] >= 1000) {
				ySign = -1;
			}
			if (face1.getPlane().getPos().getCoord()[2] <= -1000) {
				zSign = 1;
			} else if (face1.getPlane().getPos().getCoord()[2] >= 1000) {
				zSign = -1;
			}
			face1.addX(xSign * 1);
			face2.addX(ySign * 1);
			// face1.addY(ySign * 10);
			// face2.addY(ySign * 10);
			// face1.addZ(zSign * 10);
			// face2.addZ(zSign * 10);

			// gamePanel.faces.clear();
			// gamePanel.addShape(face1);
			// gamePanel.addShape(face2);
			Collections.sort(faces);
			gamePanel.setList(faces);
			java.awt.EventQueue.invokeLater(() -> {
				gamePanel.repaint();
				
			});
			//Thread.sleep(1);

			currTick = System.nanoTime();
			fps.setText("FPS: " + Long.toString(1000000000 / (currTick - prevTick)));
		}
	}
	

	private Drawer gamePanel;
	private JLabel fps;
}
