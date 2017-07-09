package HarkonenProject.Services.Calculator.Link;

import HarkonenProject.Services.Calculator.Complex;

public class LinkedComplex {

	private Complex head;

	// constractors

	public void resolveInstantOperation(Complex index, Integer position) {
		switch (index.getOperation()) {
		case '+': {
			index.getNext().setNumber(index.addComplex(index.getNext()));
			this.delIndex(position);
		}
			break;
		case '-': {
			index.getNext().setNumber(index.substractComplex(index.getNext()));
			this.delIndex(position);
		}
			break;
		case '*': {
			index.getNext().setNumber(index.multiplyComplex(index.getNext()));
			this.delIndex(position);
		}
			break;
		case '/': {
			index.getNext().setNumber(index.divideComplex(index.getNext()));
			this.delIndex(position);
		}
			break;
		}
	}

	public Complex resolve() {
		if (this.head != null) {
			while (this.head.getNext() != null) {
				this.resolveHighestPriority(this.head, 0);
			}
		}
		return (this.head);
	}

	public void resolveHighestPriority(Complex index, Integer position) {
		if (index.getPriority() < index.getNext().getPriority()) {
			this.resolveHighestPriority(index.getNext(), position + 1);
		} else {
			this.resolveInstantOperation(index, position);
		}
	}

	public void addLinkToIndex(Complex number, Integer index) {
		if (this.head == null) {
			this.head = number;
		} else {
			Complex temp = this.head;

			while (index > 0 && temp.getNext() != null) {
				temp = temp.getNext();
				index--;
			}
			number.setNext(temp.getNext());
			temp.setNext(number);
		}
	}

	public void addLinkToHead(Complex number) {
		number.setNext(this.head);
		this.head = number;
	}

	public void addLinkToTail(Complex number) {
		if (this.head == null) {
			this.head = number;
		} else {
			Complex temp = this.head;

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
			Complex linkIndex = this.head;

			for (Integer i = 0; i < index && linkIndex.getNext() != null; i++) {
				linkIndex = linkIndex.getNext();
			}
		}
	}

	public void delTail() {
		if (this.head != null && this.head.getNext() != null) {
			Complex linkIndex = this.head;

			while (linkIndex.getNext().getNext() != null) {
				linkIndex = linkIndex.getNext();
			}
			linkIndex.setNext(linkIndex.getNext().getNext());
		}
	}

	public LinkedComplex() {
		this.head = null;
	}

	public LinkedComplex(Complex head) {
		this.head = head;
	}

	// methods

	public static boolean isCharOperator(Character ch) {
		return ((ch == '+') || (ch == '-') || (ch == '*') || (ch == '/') || (ch == '(') || (ch == ')'));
	}

	// getters & setters

	public Complex getHead() {
		return head;
	}

	public void setHead(Complex head) {
		this.head = head;
	}

}
