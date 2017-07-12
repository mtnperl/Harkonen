package HarkonenProject.Services.Calculator.Link;

import HarkonenProject.Services.Calculator.Impl.Matrix;

public class LinkedMatrix {

	private Matrix head;

	// constractors

	public LinkedMatrix() {
		this.head = null;
	}

	public LinkedMatrix(Matrix head) {
		this.head = head;
	}

	// methods

	public void addLinkToIndex(Matrix number, Integer index) {
		if (this.head == null) {
			this.head = number;
		} else {
			Matrix temp = this.head;

			while (index > 0 && temp.getNext() != null) {
				temp = temp.getNext();
				index--;
			}
			number.setNext(temp.getNext());
			temp.setNext(number);
		}
	}

	public void addLinkToHead(Matrix number) {
		number.setNext(this.head);
		this.head = number;
	}

	public void addLinkToTail(Matrix number) {
		if (this.head == null) {
			this.head = number;
		} else {
			Matrix temp = this.head;

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
			Matrix linkIndex = this.head;

			for (Integer i = 0; i < index && linkIndex.getNext() != null; i++) {
				linkIndex = linkIndex.getNext();
			}
		}
	}

	public void delTail() {
		if (this.head != null && this.head.getNext() != null) {
			Matrix linkIndex = this.head;

			while (linkIndex.getNext().getNext() != null) {
				linkIndex = linkIndex.getNext();
			}
			linkIndex.setNext(linkIndex.getNext().getNext());
		}
	}

	public static boolean isCharOperator(Character ch) {
		return ((ch == '+') || (ch == '-') || (ch == '*') || (ch == '/') || (ch == '(') || (ch == ')'));
	}

	// getters & setters

	public Matrix getHead() {
		return head;
	}

	public void setHead(Matrix head) {
		this.head = head;
	}

}
