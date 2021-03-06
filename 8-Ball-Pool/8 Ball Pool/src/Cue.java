import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

public class Cue implements MouseListener {

	//ukuran cue
	private static final int CUE_WIDTH = 400;
	private static final int CUE_HEIGHT = 10;

	// posisi koordinat cue
	private int xPos;
	private int yPos;

	//nilai besarnya cue dapat ditarik
	private int drawBack_xPos;

	private boolean drawnBack;

	private boolean ballsMoving = false;

	private static boolean MOUSE_HELD_DOWN;

	// besar nilai 0-360
	private static int angle = 180;

	double power = 0;

	private Color color = Color.BLACK;

	public Cue(JPanel frame) {
		MOUSE_HELD_DOWN = false;
		frame.addMouseListener(this);
		drawnBack = false;
		drawBack_xPos = 0;
	}

	public void updatePosition(int xPos, int yPos) {
		this.xPos = xPos;
		this.yPos = yPos;
	}
	public static void updateAngle(int increment) {
		angle += increment;

		if (angle > 360) {
			int remainder = (angle - 360);
			angle = 0 + remainder;
		} else if (angle < 0) {
			int remainder = Math.abs(angle - 0);
			angle = 360 - remainder;
		}
	}

	public void drawBack() {

		if (MOUSE_HELD_DOWN) {
			drawBack_xPos += 3;
			power+=1;

			if (drawBack_xPos > 150)
			{
				drawBack_xPos = 150;
				power = 50;
			}
			drawnBack = true;
		} else {
			if (drawBack_xPos > 0) {
				if (drawBack_xPos > 16)
					drawBack_xPos -= 25;

				drawBack_xPos -= 15;
			} else
				drawBack_xPos = 0;
		}

		// kondisi saat cue dilepaskan setelah ditarik
		if (drawnBack == true && drawBack_xPos <= 0 && ballsMoving == false) {
			drawnBack = false;
			Ball cue = Main.content.getBall(0);
			double angleSp = 0;

			if (angle > 0 && angle < 90) {
				angleSp = angle;
				angleSp = Math.toRadians(angleSp);
				cue.getSpeed().setY(-Math.sin(angleSp) * power / 1.35);
				cue.getSpeed().setX(-Math.sin(Math.PI / 2 - angleSp) * power / 1.35);
			} else if (angle > 90 && angle < 180) {
				//kalkulasi nilai dan konversi dalam bentuk radian
				angleSp = angle - 90;
				angleSp = Math.toRadians(angleSp);
				//penghitungan kecepatan dengan trigonometri
				cue.getSpeed().setX(Math.sin(angleSp) * power / 1.35);
				cue.getSpeed().setY(-Math.sin(Math.PI / 2 - angleSp) * power / 1.35);
			} else if (angle > 180 && angle < 270) { 
				angleSp = angle - 180;
				angleSp = Math.toRadians(angleSp);
				cue.getSpeed().setY(Math.sin(angleSp) * power / 1.35);
				cue.getSpeed().setX(Math.sin(Math.PI / 2 - angleSp) * power / 1.35);
			} else if (angle > 270 && angle < 360) {
				angleSp = angle - 270;
				angleSp = Math.toRadians(angleSp);
				cue.getSpeed().setY(Math.sin(Math.PI / 2 - angleSp) * power / 1.35);
				cue.getSpeed().setX(-Math.sin(angleSp) * power / 1.35);
			} else if (angle == 0 || angle == 360) {
				angleSp = 0;
				cue.getSpeed().setY(Math.sin(angleSp) * power / 1.35);
				cue.getSpeed().setX(-Math.sin(Math.PI / 2 - angleSp) * power / 1.35);
			} else if (angle == 270) {
				angleSp = 0;
				cue.getSpeed().setY(Math.sin(Math.PI / 2 - angleSp) * power / 1.35);
				cue.getSpeed().setX(Math.sin(angleSp) * power / 1.35);
			} else if (angle == 180) {
				angleSp = 0;
				angleSp = Math.toRadians(angleSp);
				cue.getSpeed().setY(Math.sin(angleSp) * power/1.35);
				cue.getSpeed().setX(Math.sin(Math.PI / 2 - angleSp) * power/1.35);
			} else if (angle == 90) {
				angleSp = 0;
				angleSp = Math.toRadians(angleSp);
				cue.getSpeed().setY(-Math.sin(Math.PI / 2 - angleSp) * power/1.35);
				cue.getSpeed().setX(Math.sin(angleSp) * power/1.35);
			}
			cue.move(5);
			power=0;
		}
	}

	public void render(Graphics g, BufferedImage cue, Level level) {
		Graphics2D g2d = (Graphics2D) g.create();
		g2d.setColor(this.color);

		g2d.rotate(Math.toRadians(angle), xPos, yPos);
		int a = 0;
		for (int i = 0; i < 16; i++) {
			if (Main.content.getBall(i).moving() == false) {
				a++;
			}
		}
		//state jika bola berhenti
		if (a == 16) {
			ballsMoving = false;
		} else {
			ballsMoving = true;
		}
		if (ballsMoving == false) {
			
			g2d.drawImage(cue, xPos + 20 + drawBack_xPos, yPos, CUE_WIDTH, CUE_HEIGHT, level);
			g2d.drawLine(xPos, yPos, xPos-900, yPos);

		}
	}

	public void mouseClicked(MouseEvent e) {
		// jika klik kanan mouse
		if (e.getButton() == MouseEvent.BUTTON3)
			drawBack_xPos = 0;
	}


	public void mousePressed(MouseEvent e) {
		MOUSE_HELD_DOWN = true;
	}

	
	public void mouseReleased(MouseEvent e) {
		MOUSE_HELD_DOWN = false;
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}
}
