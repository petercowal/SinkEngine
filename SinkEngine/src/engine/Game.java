package engine;

import java.awt.DisplayMode;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

public abstract class Game{
	private int width, height;
	private double scale;
	private int fps;
	private int time;
	int targetFPS = 60;
	private BufferedImage screen;
	private DisplayMode old;
	private JFrame frame;
	private JPanel panel;
	boolean fullScreen;
	public Game(){
		this(320, 180);
	}
	public int getTime(){
		return time;
	}
	public int getWidth(){
		return width;
	}
	public double getScale(){
		return scale;
	}
	public int getHeight(){
		return height;
	}
	public int getFPS(){
		return fps;
	}
	public BufferedImage getScreen(){
		return screen;
	}
	public void setSize(int width, int height){
		this.width = width;
		this.height = height;
		screen = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        panel.setSize((int)(width*scale), (int)(height*scale));
        panel.setPreferredSize(panel.getSize());
        frame.pack();
	}
	public Game(int width, int height){
		
		this.scale = 2;
		panel = new JPanel();
		panel.setIgnoreRepaint(true);
		//panel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		//panel.setAlignmentY(JComponent.CENTER_ALIGNMENT);
		makeFrame();
		setSize(width, height);
		frame.setVisible(true);
		fullScreen = false;
		
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment()
				.getDefaultScreenDevice();
		gd.setFullScreenWindow(null);
	}
	private void makeFrame(){
		frame = new JFrame();
		frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(panel);
	}
	public void setTargetFPS(int fps){
		this.targetFPS = fps;
	}
	public void setFullScreen(){
		if(!fullScreen){
			frame.removeAll();
			frame.dispose();
			frame.setVisible(false);
			makeFrame();
			GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment()
					.getDefaultScreenDevice();
	
	    	if (gd.isFullScreenSupported()) {
	    		frame.setUndecorated(true);
	    		gd.setFullScreenWindow(frame);
	    	} else {
	    		System.out.println("Fullscreen not supported");
	    	}
	    	fullScreen = true;
		}
	}
	public void setWindowed(){
		setWindowed(2);
	}
	public void setWindowed(int newScale){
		if(fullScreen || scale != newScale){
			GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment()
					.getDefaultScreenDevice();
			gd.setFullScreenWindow(null);
			frame.removeAll();
			frame.dispose();
			scale = newScale;
			makeFrame();
			frame.pack();
			frame.setVisible(true);
			fullScreen = false;
		}
	}
	public void start(){
		
		long fpsTimer = System.currentTimeMillis();
		int frameCounter = 0;
		time = 0;
        while(true){
        	long startTime = System.nanoTime();
        	update();
        	((Graphics2D)screen.getGraphics()).setTransform(new AffineTransform());
        	render(screen);
        	//repaint();
        	paint(panel.getGraphics());
        	
        	long endTime = System.nanoTime();
        	long sleepTime = 1000000000/targetFPS - endTime + startTime;
        	if (sleepTime < 1) sleepTime = 1;
        	try {
				Thread.sleep(sleepTime/1000000, (int)(sleepTime%1000000));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
        	frameCounter++;
        	while (System.currentTimeMillis() > fpsTimer + 1000) {
        		fpsTimer += 1000;
        		fps = frameCounter;
        		frameCounter = 0;
        	}
        	time++;
        }
	}
	public abstract void render(BufferedImage target);
	public abstract void update();
	
	private void paint(Graphics g){
		Graphics2D g2d = (Graphics2D)g;
		//double minScale = scale;
		if(fullScreen){
			scale = Math.min((double)frame.getWidth()/width,
									(double)frame.getHeight()/height);
		}
		AffineTransform at = AffineTransform.getScaleInstance(scale,scale);
		g2d.drawImage(screen, at, panel);
		//g2d.drawImage(screen, 0, 0, frame.getWidth(), frame.getHeight(), null);
	}
}
