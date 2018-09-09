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
import java.util.Timer;
import java.util.TimerTask;

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

	public static int _width;
	public static int _height;
	private int zoom;

	public static boolean FPS;
	public static boolean DEBUG;
	public static boolean WIRE;
	public static boolean SHOWCENT;

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
			SHOWCENT = Boolean.parseBoolean(document.getElementsByTagName("center").item(0).getTextContent());
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

		setSize(_width, _height);
		setExtendedState(MainWindow.MAXIMIZED_BOTH);
		setUndecorated(true);
		setVisible(true);

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

		input = new KeyInput();
		gamePanel.addKeyListener(input);
		if (FPS) {
			gamePanel.add(fps);
		}
		gamePanel.setLayout(null);
		gamePanel.setList(faces);
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
				window.testFunc3();
			}

		});

		Timer paintingThread = new Timer();
		paintingThread.scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {

				// prism.addY(-3000);
				// prism.rotate(Math.PI, axis);

				// prevTick = System.nanoTime();

				window.gamePanel.repaint();

				// currTick = System.nanoTime();
				// window.fps.setText("FPS: " + Long.toString(1000000000 / (currTick -
				// prevTick)));
			}

		}, 0, 7);

		thread.start();
	}

	private void paint() {
		if (faces != null) {
			java.awt.EventQueue.invokeLater(() -> {
				gamePanel.repaint();

			});
		}
	}

	private void testFunc2() {
		Color col1 = new Color(45, 84, 38);
		Perspective pov = new Perspective(new double[] { -8000, 0, 8000 }, new double[] { 1, 0, -1 },
				new double[] { 0, 1, 0 });
		pov.setZoom(zoom);
		Position2D[] points1 = { new Position2D(-1000, -500), new Position2D(-1000, 500), new Position2D(1000, 500),
				new Position2D(1000, -500) };
		Plane plane1 = new Plane(new Vector3D(0, 1, 0), new Vector3D(0, 0, 1), new Position3D(0, 0, 0));
		Face face1 = new Face(points1, points1.length, plane1, pov, col1);

		Prism prism = new Prism(1000.0, new Position3D(0, 0, 0), pov, face1);
		Prism prisms[] = new Prism[5];
		for (int i = 0; i < 5; i++) {
			prisms[i] = new Prism(prism);
			prisms[i].setZ(-250 * (i - 2));
			prisms[i].setY(-1250 * (i - 2));
		}

		for (int i = 0; i < 6; i++) {
			prism.setCol(new Color(50 * i, 200 - 15 * i, 210 - 30 * i), i);
			faces.add(prism.getFaces()[i]);
			for (int j = 0; j < 5; j++) {
				faces.add(prisms[j].getFaces()[i]);
				prisms[j].setCol(new Color(50 * i, 15 * j + 10 * i, 12 * j + 15 * i), i);
			}

		}
		Vector3D axis = new Vector3D(0, 1, 0);

		// prism.addY(-3000);
		// prism.rotate(Math.PI, axis);
		Collections.sort(faces, new FirstFaceCompare());

		Long prevTick = System.nanoTime();
		Long currTick = System.nanoTime();
		while (true) {
			prevTick = System.nanoTime();
			prism.addY(input.test2);
			prism.addX(input.test3);
			// pov.orbit(input.test, axis, prism.getCenter());
			prism.rotate(input.test, axis);
//			for (int i = 0; i < faces.size(); i++) {
//				for (int j = 0; j < faces.size(); j++) {
//					if ((faces.get(i).checkMoved() || faces.get(j).checkMoved()) && i != j
//							&& (faces.get(i).getCenter2D().totDistanceFrom(faces.get(j).getCenter2D())
//									- faces.get(i).getBound2D() - faces.get(j).getBound2D() < 0)) {
//						int coverCheck = faces.get(i).checkCover(faces.get(j));
//						if (coverCheck != 2) {
//							if (coverCheck < 0) {
//								faces.get(i).setVisible(false);
//								faces.get(index)
//								break;
//							} else {
//								faces.get(i).setVisible(true);
//							}
//						} else {
//							faces.get(i).setVisible(true);
//						}
//					}
//				}
//			}
			Collections.sort(faces);
			for (Face face : faces) {
				face.setMoved(false);
			}
			java.awt.EventQueue.invokeLater(() -> {

				gamePanel.repaint();

			});

			currTick = System.nanoTime();
			fps.setText("FPS: " + Long.toString(1000000000 / (currTick - prevTick)));
		}
	}

	private void testFunc3() {
		Color col1 = new Color(45, 84, 38);
		Color col2 = new Color(72, 41, 124);
		Color col3 = new Color(120, 41, 124);
		Color col4 = new Color(84, 30, 224);
		Color col5 = new Color(102, 41, 14);
		Color col6 = new Color(62, 91, 124);
		Perspective pov = new Perspective(new double[] { -8000, 0, 8000 }, new double[] { 1, 0, -1 },
				new double[] { 0, 1, 0 });
		pov.setZoom(zoom);
//
		Position2D[] points1 = { new Position2D(-500, -1000), new Position2D(-500, 1000), new Position2D(500, 1000),
				new Position2D(500, -1000) };
		Position2D[] points2 = { new Position2D(-500, -500), new Position2D(-500, 500), new Position2D(500, 500),
				new Position2D(500, -500) };
		Position2D[] points3 = { new Position2D(-500, -1000), new Position2D(-500, 1000), new Position2D(500, 1000),
				new Position2D(500, -1000) };
		Position2D[] points4 = { new Position2D(-500, -1000), new Position2D(-500, 1000), new Position2D(500, 1000),
				new Position2D(500, -1000) };
		Position2D[] points5 = { new Position2D(-500, -1000), new Position2D(-500, 1000), new Position2D(500, 1000),
				new Position2D(500, -1000) };
		Position2D[] points6 = { new Position2D(-500, -500), new Position2D(-500, 500), new Position2D(500, 500),
				new Position2D(500, -500) };
		Plane plane1 = new Plane(new Vector3D(-1, 0, 0), new Vector3D(0, 0, 1), new Position3D(400, 900, 1000));
		Plane plane2 = new Plane(new Vector3D(1, 0, 0), new Vector3D(0, -1, 0), new Position3D(400, 400, 0));
		Plane plane3 = new Plane(new Vector3D(0, 1, 0), new Vector3D(0, 0, 1), new Position3D(900, 400, 1000));
		Plane plane4 = new Plane(new Vector3D(0, -1, 0), new Vector3D(0, 0, 1), new Position3D(-100, 400, 1000));
		Plane plane5 = new Plane(new Vector3D(-1, 0, 0), new Vector3D(0, 0, 1), new Position3D(400, -100, 1000));
		Plane plane6 = new Plane(new Vector3D(1, 0, 0), new Vector3D(0, 1, 0), new Position3D(400, 400, 2000));
		Face face1 = new Face(points1, points1.length, plane1, pov, col1);
		//Face face2 = new Face(points2, points2.length, plane2, pov, col2);
		//Face face3 = new Face(points3, points3.length, plane3, pov, col3);
		//Face face4 = new Face(points4, points4.length, plane4, pov, col4);
		//Face face5 = new Face(points5, points5.length, plane5, pov, col5);
		//Face face6 = new Face(points6, points6.length, plane6, pov, col6);
		faces.add(face1);
		//faces.add(face2);
		//faces.add(face3);
		//faces.add(face4);
		//faces.add(face5);
		//faces.add(face6);

		Position3D pos = new Position3D(400, 400, 1000);
		Vector3D axis = new Vector3D(0, 0, 1);

		Long prevTick = System.nanoTime();
		Long currTick = System.nanoTime();

		while (true) {
			prevTick = System.nanoTime();
			double num = input.test;
			face1.orbit(num, axis, pos);
			//face2.orbit(num, axis, pos);
			//face3.orbit(num, axis, pos);
			//face4.orbit(num, axis, pos);
			//face5.orbit(num, axis, pos);
			//face6.orbit(num, axis, pos);
			//Collections.sort(faces);
			gamePanel.setList(faces);
			java.awt.EventQueue.invokeLater(() -> {
				gamePanel.repaint();

			});

			currTick = System.nanoTime();
			fps.setText("FPS: " + Long.toString(1000000000 / (currTick - prevTick)));
		}
		
//		Position2D test = new Position2D();
//		Position3D testResult = pov.getRealPoint(test, plane1);
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
		Position2D[] points3 = { new Position2D(-1000, -1000), new Position2D(-1000, 1000), new Position2D(1000, 1000),
				new Position2D(1000, -1000) };
		Plane plane3 = new Plane(new Vector3D(0, 1, 0), new Vector3D(0, 0, 1), new Position3D(1500, 0, 0));
		Face face1 = new Face(points1, points1.length, plane1, pov, col1);
		Face face2 = new Face(points2, points2.length, plane2, pov, col2);
		Face face3 = new Face(points3, points3.length, plane3, pov, col3);
		faces.add(face1);
		faces.add(face2);
		faces.add(face3);
		// gamePanel.addShape(face1);
		// gamePanel.addShape(face2);
		// gamePanel.addShape(face3);
		Collections.sort(faces);
		gamePanel.setList(faces);
		java.awt.EventQueue.invokeLater(() -> {
			gamePanel.repaint();

		});
		// Thread.sleep(1000);
		face1.setX(1000);
		face2.setX(-1000);
		Collections.sort(faces);
		gamePanel.setList(faces);
		java.awt.EventQueue.invokeLater(() -> {
			gamePanel.repaint();

		});
		// Thread.sleep(1000);

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
		// int zSign = -1;

		Long prevTick = System.nanoTime();
		Long currTick = System.nanoTime();
		while (true) {

			prevTick = System.nanoTime();

			Vector3D axis = new Vector3D(0, 1, 0);
			// Vector3D axis2 = new Vector3D(1, 0, 0);
			face1.rotate(0.01, axis);
			// face2 = face2.rotate(0.01, axis);

			// face1 = face1.rotate(0.01, axis2);

			if (face1.getPlane().getPos().getCoord()[0] <= -2000) {
				xSign = 1;
			} else if (face1.getPlane().getPos().getCoord()[0] >= 2000) {
				xSign = -1;
			}
			if (face2.getPlane().getPos().getCoord()[0] <= -2000) {
				ySign = 1;
			} else if (face2.getPlane().getPos().getCoord()[0] >= 2000) {
				ySign = -1;
			}
			if (face1.getPlane().getPos().getCoord()[2] <= -1000) {
				// zSign = 1;
			} else if (face1.getPlane().getPos().getCoord()[2] >= 1000) {
				// zSign = -1;
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
			// Thread.sleep(1);

			currTick = System.nanoTime();
			fps.setText("FPS: " + Long.toString(1000000000 / (currTick - prevTick)));
		}
	}

	private Drawer gamePanel;
	private JLabel fps;
	private KeyInput input;
}
