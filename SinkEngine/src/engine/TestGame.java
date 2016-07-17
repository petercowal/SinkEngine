package engine;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class TestGame extends Game{
	public static void main(String[] args){
		TestGame t = new TestGame();
		t.setSize(320, 180);
		t.setFullScreen();
		t.setTargetFPS(60);
		t.start();
		
	}
	@Override
	public void update() {
		
		// TODO Auto-generated method stub
		
	}
	@Override
	public void render(BufferedImage target) {
		// TODO Auto-generated method stub
		Graphics2D g = (Graphics2D)target.getGraphics();
		g.clearRect(0, 0, this.getWidth(), this.getHeight());
		g.setColor(new Color(255,255,255,120));
		g.drawRect(0, 0, this.getWidth()-1, this.getHeight()-1);
		for(int i = 0; i < 1; i++){
			g.fillRect(-100+this.getTime()%500, 10, 100, 100);
		}
		g.drawString("FPS: "+this.getFPS(), 20, 20);
		//setFullScreen();
	}

}
