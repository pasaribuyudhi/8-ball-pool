
public class Character {
	String name; //nama player
	boolean turn;//giliran tiap player 
	int points;	//points atau tiap bola yang berhasil dimasukan player
	boolean striped; //menentukan player menggunakan bola solid atau strips
	int betAmount; 
	
	public Character(String name, boolean turn, int points, boolean striped, int betAmount) {
		super();
		this.name = name;
		this.turn = turn;
		this.points = points;
		this.striped=striped;
		this.betAmount = betAmount;
	}

	public void incrementScore()
	{
		System.out.println("!");
		points += 1;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isTurn() {
		return turn;
	}

	public void setTurn(boolean turn) {
		this.turn = turn;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public boolean isStriped() {
		return striped;
	}

	public void setStriped(boolean striped) {
		this.striped = striped;
	}
	
	
}
