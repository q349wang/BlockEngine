package blockrpg;

import javax.swing.JPanel;
import java.awt.*;

public class Drawer extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2810583553938140766L;

	public Drawer() {

	}

	@Override
	public void paintComponent(Graphics g) {

		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D) g; // Casts the Graphics to Graphics2D

		// Turns on Anti-Aliasing
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		g2.setRenderingHints(rh);


	}
}
