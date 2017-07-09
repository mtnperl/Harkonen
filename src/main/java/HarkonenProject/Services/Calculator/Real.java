package HarkonenProject.Services.Calculator;


	public class Real {

		private Double number;
		private Character operation;
		private Integer priority;
		private Real next;

		// contractors

		public Real(Double number, Character operation) {
			this.number = number;
			this.operation = operation;
			this.priority = this.priority(operation);
			this.next = null;
		}

		public Real(Real number) {
			this.number = number.number;
			this.operation = number.operation;
			this.priority = this.priority(operation);
			this.next = null;
		}

		public Real(Double number) {
			this.number = number;
			this.priority = 0;
			this.next = null;
		}

		public Real() {
			this.number = 0.0;
			this.priority = 0;
			this.next = null;
		}

		// methods

		private Integer priority(Character ch) {
			if (ch == null){
				return (0);
			}
			else{
				switch (ch) {
				case '+':
					return (1);
				case '-':
					return (1);
				case '*':
					return (2);
				case '/':
					return (2);
				case '%':
					return (2);
				case '^':
					return (3);
				case 'R':
					return (3);
				case '!':
					return (4);
				case 'E':
					return (4);
				case 'F':
					return (4);
				default:
					return (0);
				}
			}
		}

		public Real add(Real num) {
			Real result = new Real(this.number + num.number);
			return (result);
		}

		public Real substract(Real num) {
			Real result = new Real(this.number - num.number);
			return (result);
		}

		public Real multiply(Real num) {
			Real result = new Real(this.number * num.number);
			return (result);
		}

		public Real divide(Real num) {
			Real result;
			if (num.number != 0) {
				result = new Real(this.number / num.number);
			} else {
				result = new Real(0.0);
			}
			return (result);
		}

		public Real mod(Real num) {
			Real result = new Real(this.number % num.number);
			return (result);
		}

		public Real pow(Real num) {
			Real result = new Real(Math.pow(this.number, num.number));
			return (result);
		}
		
		public Real root(Real num){
			Real oneth = new Real(1.0 / num.getNumber());
			return ((this.pow(oneth)));
		}
		
		public Real sigma() {
			Real result = new Real(0.0);
			for (Integer i = 1; i <= this.number; i++) {
				result.number += i;
			}
			return (result);
		}

		public Real factorial() {
			Real result = new Real(1.0);
			for (Integer i = 1; i <= this.number; i++) {
				result.number *= i;
			}
			return (result);
		}

		private Real fibbonachi(Long number) {
			if (number == 1 || number == 2) {
				Real result = new Real(1.0);
				return (result);
			} else {
				Real result = new Real(fibbonachi(number - 1).number + fibbonachi(number - 2).number);
				return (result);
			}
		}

		public Real fibbonachi() {
			return (fibbonachi(this.number.longValue()));
		}

		public void print() {
			System.out.println(this.toString());
		}
		
		public String toString(){
			String result = "";
			if (this.number % 1 == 0){
				result += this.number.longValue();
			}
			else{
				result += this.number;
			}
			return (result);
		}

		// getters & setters

		public Double getNumber() {
			return number;
		}

		public void setNumber(Double number) {
			this.number = number;
		}

		public void setNumber(Real number) {
			this.number = number.number;
		}

		public Character getOperation() {
			return this.operation;
		}

		public void setOperation(Character operation) {
			this.operation = operation;
			this.priority = this.priority(operation);
		}

		public Integer getPriority() {
			return this.priority;
		}

		public void setPriority(Integer priority) {
			this.priority = priority;
		}

		public Real getNext() {
			return this.next;
		}

		public void setNext(Real next) {
			this.next = next;
		}
	}
	
	

