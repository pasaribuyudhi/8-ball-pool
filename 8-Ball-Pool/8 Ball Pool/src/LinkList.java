public class LinkList {
	private Node first;

	public LinkList() {
		first = null;
	}

	public void insert(Ball ball) {
		Node newNode = new Node(ball);
		newNode.next = first;
		first = newNode;
	}
	public void printList() {
		Node runner = first;
		while (runner != null) {
			System.out.println(runner.ball);
			runner = runner.next;
		}
	}
	public Ball[] getElements() {
		int count = 0; 
		Node runner; 
		Ball[] elements;
		runner = first;
		while (runner != null) {
			count++;
			runner = runner.next;
		}
		elements = new Ball[count];
		runner = first;
		count--;
		while (runner != null) {
			elements[count] = runner.ball;
			count--;
			runner = runner.next;
		}
		return elements;
	}

}