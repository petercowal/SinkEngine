package engine;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class TestGame extends Game{
	Sprite testSprite;
	public static void main(String[] args){
		TestGame t = new TestGame();
		//t.setFullScreen();
		t.setTargetFPS(60);
		t.start();
		
	}
	public TestGame(){
		super(320,180);
		try {
			testSprite = new Sprite("src/sprite.png");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public void update() {
		// TODO Auto-generated method stub
	}
	@Override
	public void render(BufferedImage target) {
		// TODO Auto-generated method stub
		Graphics2D g = target.createGraphics();
		g.clearRect(0, 0, this.getWidth(), this.getHeight());
		g.setColor(new Color(255,255,255,120));
		g.drawRect(0, 0, this.getWidth()-1, this.getHeight()-1);
		for(int i = 0; i < 1; i++){
			g.fillRect(-100+this.getTime()%500, 10, 100, 100);
		}
		g.drawString("FPS: "+this.getFPS(), 20, 20);
		
		AffineTransform at = new AffineTransform();
		at.translate(this.getMouseX(), this.getMouseY());
		at.rotate(this.getTime()/10.);
		at.translate(-16, -16);
		g.drawImage(testSprite.getFrame(0), at, null);
	}


}
