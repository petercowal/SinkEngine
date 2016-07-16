package engine;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

public abstract class Game extends JPanel{
	String name;
	int width, height;
	long targetFPS = 60;
	BufferedImage screen;
	
	JFrame frame;
	public Game(){
		this(320, 180);
	}
	public Game(int width, int height){
		this.width = width;
		this.height = height;
	}
	public void start(){
		frame = new JFrame();
		
		screen = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		//frame.setUndecorated(true);
        frame.setResizable(false);
        Toolkit tk=Toolkit.getDefaultToolkit();
        frame.setSize(tk.getScreenSize());
        this.setSize(tk.getScreenSize());
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(this);
        frame.setVisible(true);
        
        while(true){
        	long startTime = System.nanoTime();
        	update();
        	render(screen);
        	repaint();
        	long endTime = System.nanoTime();
        	long sleepTime = 1000000000/60 - endTime + startTime;
        	if (sleepTime < 1) sleepTime = 1;
        	try {
				Thread.sleep(sleepTime/1000000, (int)(sleepTime%1000000));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
        }
	}
	public abstract void render(BufferedImage target);
	public abstract void update();
	
	public void paint(Graphics g){
		Graphics2D g2d = (Graphics2D)g;
		AffineTransform at = AffineTransform.getScaleInstance(frame.getWidth()/width,frame.getHeight()/height);
		g2d.drawImage(screen, at, this);
		//g2d.drawImage(screen, 0, 0, frame.getWidth(), frame.getHeight(), null);
	}
}
