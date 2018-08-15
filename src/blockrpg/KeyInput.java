package blockrpg;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyInput implements KeyListener {

	public double test = 0;

	public double test2 = 0;

	public double test3 = 0;

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			System.exit(0);
		}

		if (e.getKeyCode() == KeyEvent.VK_E) {
			test = 0.01;
		} else if (e.getKeyCode() == KeyEvent.VK_Q) {
			test = -0.01;
		}

		if (e.getKeyCode() == KeyEvent.VK_A) {
			test2 = 12;
		} else if (e.getKeyCode() == KeyEvent.VK_D) {
			test2 = -12;
		}

		if (e.getKeyCode() == KeyEvent.VK_W) {
			test3 = -12;
		} else if (e.getKeyCode() == KeyEvent.VK_S) {
			test3 = 12;
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_Q || e.getKeyCode() == KeyEvent.VK_E) {
			test = 0;
		}

		if (e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_A) {
			test2 = 0;
		}

		if (e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_S) {
			test3 = 0;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

}
