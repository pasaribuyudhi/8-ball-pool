import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Color;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Ball {
	private double x;
	private double y;
	private double radius = 15;
	private double mass = 1;
	private Speed speed;
	private Color color;
	private boolean solid;
	private int ballNumber;
	Boolean pocketed = false;

	double slowDownSpeed = 0.02;// Memperlambat kecepatan bola tiap menyentuh bola

	int distance = 100;// jarak pantul bola ke dinding

	// Constructor
	public Ball(double x, double y, double radius, double mass, Speed speed, Color ballColor, boolean solid,
			int ballNumber) {
		this.x = x;
		this.y = y;
		setRadius(radius);
		setMass(mass);
		this.speed = speed;
		this.color = ballColor;
		this.solid = solid;
		this.ballNumber = ballNumber;
	}

	// Getters and Setters
	public Speed getSpeed() {
		return speed;
	}
	
	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getRadius() {
		return radius;
	}

	public void setRadius(double radius) {
		if (radius > 0)
			this.radius = radius;
	}

	public double getMass() {
		return mass;
	}

	public void setMass(double mass) {
		this.mass = mass;
	}

	public void setColor(Color ballColor) {
		this.color = ballColor;
	}

	public void setX(Double x) {
		this.x = x;
	}

	public void setY(Double y) {
		this.y = y;
	}

	public Color getColor() {
		return color;
	}

	public void setSolid(boolean solid) {
		this.solid = solid;
	}

	public boolean getSolid() {
		return solid;
	}

	public void setBallNumber(int ballNumber) {
		this.ballNumber = ballNumber;
	}

	public int getBallNumber() {
		return ballNumber;
	}

	
	
	
	
	
	public void setSpeedZero() {
		this.speed.setX(0.0);
		this.speed.setY(0.0);
	}

	//Bola masuk ke dalam lubang
	public void pocketed() {
		this.pocketed = true;
	}
	
	//mengecek pergerakan terakhir bola
	public boolean moving() {
		if (speed.getX() == 0 && speed.getY() == 0) {
			return false;
		}
		return true;
	}

	//Method pergerakan bola berdasarkan waktu
	public void move(double time) {
		move(speed.getX() * time, speed.getY() * time);
	}

	//
	public void move(double x, double y) {

		// membuat bola semakin lambat
		if (speed.getX() > 1 && speed.getY() > 1) {
			slowDownSpeed += 0.0075;
		} else {
			slowDownSpeed = 0.04;
		}

		
		// Statement cek nilai x dan y yang melambatkan bola.
		if (speed.getX() < 1 && speed.getX() > 0) {
			speed.setX(0);
		} else if (speed.getX() > -1 && speed.getX() < 0) {
			speed.setX(0);
		} else if (speed.getX() > 0) {
			speed.subtractX(slowDownSpeed);
		} else if (speed.getX() < 0) {
			speed.addX(slowDownSpeed);
		}
		if (speed.getY() < 1 && speed.getY() > 0) {
			speed.setY(0);
		} else if (speed.getY() > -1 && speed.getY() < 0) {
			speed.setY(0);
		} else if (speed.getY() > 0) {
			speed.subtractY(slowDownSpeed);
		} else if (speed.getY() < 0) {
			speed.addY(slowDownSpeed);
		}

		//Update variabel object
		this.x += x;
		this.y += y;

		// Variabel untuk area bola
		double playX = this.x - distance;
		double playY = this.y - distance;
		double playX2 = Main.WIDTH - distance;
		double playY2 = Main.HEIGHT - distance;

		// state pantulan bola pada area dan arah pantulannya
		if (playX < radius + 2) {// Left wall
			playX = 2 * radius - playX;
			speed.addX(-2 * speed.getX());
			Level.queue_collision_update();
		}

		if (this.x > playX2 - radius + 2) {// Right wall
			this.x = 2 * (playX2 - radius) - this.x;
			speed.addX(-2 * speed.getX());
			Level.queue_collision_update();
		}

		if (playY < radius + 2) {// Top wall
			playY = 2 * radius - playY;
			speed.addY(-2 * speed.getY());
			Level.queue_collision_update();
		}

		if (this.y > playY2 - radius + 2) {// Bottom wall
			this.y = 2 * (playY2 - radius) - this.y;
			speed.addY(-2 * speed.getY());
			Level.queue_collision_update();
		}

		Level.queue_collision_update();
	}

	//Menentukan object bola pertama yang terbentuk untuk setiap player.
	public void paint(Graphics2D g) {
		
		//menentukan bola solid atau stripes
			if (solid) {
				if (ballNumber == 77) {
					g.setColor(Color.BLACK);
					g.fill(new Ellipse2D.Double(x - radius, y - radius, 2 * radius, 2 * radius));
				} else {
					g.setColor(this.color);
					g.fill(new Ellipse2D.Double(x - radius, y - radius, 2 * radius, 2 * radius));
				}

			} else {
				g.setColor(Color.WHITE);
				g.fill(new Ellipse2D.Double(x - radius, y - radius, 2 * radius, 2 * radius));

				// untuk bola stripes
				g.setColor(this.color);
				g.fillRoundRect((int) (x - 6.3), (int) (y - 15), 13, 31, 15, 5);
			}

			// Setting angka tiap bola
			if (ballNumber > 0 && ballNumber < 10 && ballNumber != 77) {
				g.setColor(Color.WHITE);
				g.fillOval((int) (x - 9), (int) (y - 7.3), 15, 15);

				g.setColor(Color.BLACK);
				g.drawString(String.valueOf(this.ballNumber), (int) (x - 6.3), (int) (y + 4));

				
			//state bola putih saat masuk lubang.
			} else if (ballNumber >= 10 && ballNumber != 77) {
				g.setColor(Color.WHITE);
				g.fillOval((int) (x - 9), (int) (y - 7.3), 15, 15);

				g.setColor(Color.BLACK);
				g.drawString(String.valueOf(this.ballNumber), (int) (x - 10), (int) (y + 4));
			}
		
	}

	public double next_collision(Ball next) {
		
		// set posisi bola dan kecepatan angle 
		double d_x = getX() - next.getX();
		double d_y = getY() - next.getY();
		double d_vx = speed.getX() - next.getSpeed().getX();
		double d_vy = speed.getY() - next.getSpeed().getY();

		//set kecepatan bola
		if (d_vx == 0 && d_vy == 0)
			return Double.POSITIVE_INFINITY;

		//angle range atas
		double a = d_vx * d_vx + d_vy * d_vy;
		
		//angle range bawah
		double b_mezzi = d_vx * d_x + d_vy * d_y;
		
		//angle range pantulan
		double delta_quarti = Math.pow(radius + next.getRadius(), 2) * a - Math.pow(d_vx * d_y - d_vy * d_x, 2);

		if (delta_quarti < 0)
			return Double.POSITIVE_INFINITY;

		//kalkulasi point
		double beginning = (-b_mezzi - Math.sqrt(delta_quarti)) / a;
		double end = (-b_mezzi + Math.sqrt(delta_quarti)) / a;

		if (end < 0)
			return Double.POSITIVE_INFINITY;

		//pengembalian nilai pantulan bola
		if (beginning < (beginning - end) / 2) 
			return Double.POSITIVE_INFINITY;

		if (beginning < 0)
			return 0.0;

		return beginning;
	}

	public void collide(Ball next, double time) {

		//Set angle pantulan
		double theta = Math.atan2(next.getY() - getY(), next.getX() - getX());
		
		double v_1 = speed.getComponent(theta);
		double v_2 = next.getSpeed().getComponent(theta);

		double m_1 = getMass();
		double m_2 = next.getMass();
		
		//set angle pantulan tiap bola solid dan stripes
		double w_1 = ((m_1 - m_2) * v_1 + 2 * m_2 * v_2) / (m_1 + m_2);
		double w_2 = ((m_2 - m_1) * v_2 + 2 * m_1 * v_1) / (m_1 + m_2);
		
		//Set angle baru tiap bola
		speed.addComponent(theta, -v_1 + w_1);
		next.getSpeed().addComponent(theta, -v_2 + w_2);
		
	}
}
