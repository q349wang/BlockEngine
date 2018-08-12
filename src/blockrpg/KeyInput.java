package blockrpg;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyInput implements KeyListener {

	public double test = 0;
	
	public double test2 = 0;
	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			System.exit(0);
		}

		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			test = 0.01;
		} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			test = -0.01;
		}
		
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			test2 = 10;
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			test2 =-10;
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_RIGHT) {
			test = 0;
		} 

		if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN) {
			test2 = 0;
		} 
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

}
