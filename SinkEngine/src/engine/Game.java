package engine;

import java.awt.DisplayMode;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public abstract class Game implements MouseListener, KeyListener{
	private int width, height;
	private double scale;
	private int fps;
	private int time;
	int targetFPS = 60;
	private BufferedImage screen;
	private DisplayMode old;
	private JFrame frame;
	private JPanel panel;
	private int mouseX, mouseY;
	boolean fullScreen;
	
	private boolean[] keys;
	private boolean[] mouseButtons;
	
	public Game(){
		this(320, 180);
	}
	public int getMouseX(){
		return mouseX;
	}
	public int getMouseY(){
		return mouseY;
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
		
		keys = new boolean[128];
		mouseButtons = new boolean[4];
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
		frame.addMouseListener(this);
		frame.addKeyListener(this);
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
        	Point mouse = MouseInfo.getPointerInfo().getLocation();
        	SwingUtilities.convertPointFromScreen(mouse, panel);
        	mouseX = (int)(mouse.x/scale);
        	mouseY = (int)(mouse.y/scale);
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
	public boolean mouseDown(int button){
		return mouseButtons[button];
	}
	public boolean keyDown(int button){
		return keys[button];
	}
	@Override
	public void mouseClicked(MouseEvent e){
		
	}
	@Override
	public void mousePressed(MouseEvent e){
		if(e.getButton() < mouseButtons.length){
			mouseButtons[e.getButton()] = true;
		}
	}
	@Override
	public void mouseReleased(MouseEvent e){
		if(e.getButton() < mouseButtons.length){
			mouseButtons[e.getButton()] = false;
		}
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		
		
	}
	@Override
	public void keyTyped(KeyEvent e){
		
	}
	@Override
	public void keyPressed(KeyEvent e){
		if(e.getKeyCode() < keys.length){
			keys[e.getKeyCode()] = true;
		}
	}
	@Override
	public void keyReleased(KeyEvent e){
		if(e.getKeyCode() < keys.length){
			keys[e.getKeyCode()] = false;
		}
	}
}
