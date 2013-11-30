
import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

public class MyFrame extends JFrame implements Runnable, KeyListener {
	private boolean isDown;
	private Canvas canvas;
	private Ball ball;
	public MyFrame() {
		setSize(550,380);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		ball = new Ball();
		canvas = new MyCanvas(ball);
		add(canvas);
		addKeyListener(this);
		
		Thread t = new Thread(this);
		while(true) {
			t.run();			
		}
	}
	@Override
	public void keyPressed(KeyEvent arg0) {
		int key = arg0.getKeyCode();
		if(key == KeyEvent.VK_LEFT)
			ball.force(-1);
		else if(key == KeyEvent.VK_RIGHT)
			ball.force(1);
	}
	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		int key = arg0.getKeyCode();
		if(key == KeyEvent.VK_LEFT)
			ball.force(0);
		else if(key == KeyEvent.VK_RIGHT)
			ball.force(0);		
	}
	@Override
	public void run() {
		ball.increaseTime();
		ball.downUp();

		canvas.update(canvas.getGraphics());
		try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private class MyCanvas extends Canvas {
		private Ball ball;
		public MyCanvas() {
			ball = new Ball();
		}
		public MyCanvas(Ball ball) {
			this.ball = ball;
		}
		@Override
		public void paint(Graphics g) {
			ball.draw(g);
			g.drawLine(290, 260, 290+300, 260);
			g.drawLine(290, 260, 290, 260+200);
			g.drawLine(0, 150, 600, 150);
		}
	}
	public static void main(String[] args) {
		new MyFrame();
	}
	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
}
