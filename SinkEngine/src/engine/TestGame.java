package engine;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class TestGame extends Game{
	public static void main(String[] args){
		new TestGame().start();
	}
	@Override
	public void update() {
		
		// TODO Auto-generated method stub
		
	}
	@Override
	public void render(BufferedImage target) {
		// TODO Auto-generated method stub
		Graphics g = target.getGraphics();
		g.clearRect(0, 0, getWidth(), getHeight());
		g.setColor(Color.WHITE);
		g.fillRect((int)(Math.random()*100), (int)(Math.random()*100), 100, 100);
	}

}
