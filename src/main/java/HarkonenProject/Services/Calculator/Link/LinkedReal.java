package HarkonenProject.Services.Calculator.Link;

import HarkonenProject.Services.Calculator.Real;

public class LinkedReal {

	private Real head;

	// constractors

	public LinkedReal() {
		this.head = null;
	}

	LinkedReal(Real head) {
		this.head = head;
	}

	// methods
	
	public void resolveInstantOperation(Real index, Integer position){
		switch (index.getOperation()){
		case '+': {
					index.getNext().setNumber(index.add(index.getNext()));
					this.delIndex(position);
				  }
					break;
		case '-': {
					index.getNext().setNumber(index.substract(index.getNext()));
					this.delIndex(position);
				  }
					break;
		case '*': {
					index.getNext().setNumber(index.multiply(index.getNext()));
					this.delIndex(position);
		  		  }
					break;
		case '/': {
					index.getNext().setNumber(index.divide(index.getNext()));
					this.delIndex(position);
		  		  }
					break;
		case '^': {
					index.getNext().setNumber(index.pow(index.getNext()));
					this.delIndex(position);
		  		  }
					break;
		case 'R': {
					index.getNext().setNumber(index.root(index.getNext()));
					this.delIndex(position);
				  }
					break;
		case '!': {
					index.getNext().setNumber(index.factorial());
					this.delIndex(position);
				  }
					break;
		case 'E': {
					index.getNext().setNumber(index.sigma());
					this.delIndex(position);
				  }
					break;
		case 'F': {
					index.getNext().setNumber(index.fibbonachi());
					this.delIndex(position);
				  }
					break;
		}
	}
	
	public Real resolve(){
		if (this.head != null){
			while (this.head.getNext() != null){
				this.resolveHighestPriority(this.head, 0);
			}
		}
		return (this.head);
	}
	
	public void resolveHighestPriority(Real index, Integer position){
		if (index.getPriority() < index.getNext().getPriority()){
			this.resolveHighestPriority(index.getNext(), position+1);
		}
		else{
			this.resolveInstantOperation(index, position);
		}
	}
	
	public void addLinkToIndex(Real number, Integer index) {
		if (this.head == null) {
			this.head = number;
		} else {
			Real temp = this.head;

			while (index > 0 && temp.getNext() != null) {
				temp = temp.getNext();
				index--;
			}
			number.setNext(temp.getNext());
			temp.setNext(number);
		}
	}

	public void addLinkToHead(Real number) {
		number.setNext(this.head);
		this.head = number;
	}

	public void addLinkToTail(Real number) {
		if (this.head == null) {
			this.head = number;
		} else {
			Real temp = this.head;

			while (temp.getNext() != null) {
				temp = temp.getNext();
			}
			temp.setNext(number);
		}
	}

	public void delHead() {
		if (this.head != null) {
			this.head = this.head.getNext();
		}
	}

	public void delIndex(Integer index) {
		if (this.head != null) {
			if (index == 0){
				this.delHead();
			}
			else{
				Real linkIndex = this.head;

				for (Integer i = 1; i < index && linkIndex.getNext().getNext() != null; i++) {
					linkIndex = linkIndex.getNext();
				}
				linkIndex.setNext(linkIndex.getNext().getNext());
			}
		}
	}

	public void delTail() {
		if (this.head != null && this.head.getNext() != null) {
			Real linkIndex = this.head;

			while (linkIndex.getNext().getNext() != null) {
				linkIndex = linkIndex.getNext();
			}
			linkIndex.setNext(linkIndex.getNext().getNext());
		}
	}

	public static boolean isCharOperator(Character ch) {
		return ((ch == '+') || (ch == '-') || (ch == '*') || (ch == '/') || (ch == '%') ||
				(ch == '^') || (ch == 'R') || (ch == '(') || (ch == ')'));
	}

	public static boolean isCharFunctionOperator(Character ch) {
		return ((ch == '!') || (ch == 'E') || (ch == 'F'));
	}

	// getters & setters

	public Real getHead() {
		return head;
	}

	public void setHead(Real head) {
		this.head = head;
	}
}
