package HarkonenProject.Services.Calculator;

public class Complex {
	
	
	
	
		private Double free;
		private Double i;
		private Character operation;
		private Integer priority;
		private Complex next;

		// constractors

		public Complex(Double free, Double i, Character operation) {
			this.free = free;
			this.i = i;
			this.operation = operation;
			this.priority = this.priority(operation);
			this.next = null;
		}

		public Complex(Complex complex) {
			this.free = complex.free;
			this.i = complex.i;
			this.operation = complex.operation;
			this.priority = complex.priority;
			this.next = null;
		}

		public Complex(Double free, Double i) {
			this.free = free;
			this.i = i;
			this.operation = null;
			this.next = null;
		}

		public Complex() {
			this.free = 0.0;
			this.i = 0.0;
			this.operation = null;
			this.next = null;
		}

		// methods

		private int priority(Character ch) {
			switch (ch) {
			case '+':
				return (1);
			case '-':
				return (1);
			case '*':
				return (2);
			case '/':
				return (2);
			default:
				return (0);
			}
		}

		public Complex addComplex(Complex complex) {
			Double sumFree = this.free + complex.free;
			Double sumI = this.i + complex.i;
			Complex result = new Complex(sumFree, sumI);
			return (result);
		}

		public Complex substractComplex(Complex complex) {
			Double sumFree = this.free - complex.free;
			Double sumI = this.i - complex.i;
			Complex result = new Complex(sumFree, sumI);
			return (result);
		}

		public Complex multiplyComplex(Complex complex) {
			Double sumFree = this.free * complex.free - this.i * complex.i;
			Double sumI = this.i * complex.free + this.free * complex.i;
			Complex result = new Complex(sumFree, sumI);
			return (result);
		}

		public Complex divideComplex(Complex complex) {
			Double sumFree = (this.free * complex.free + this.i * complex.i)
					/ (complex.free * complex.free + complex.i * complex.i);
			Double sumI = (this.i * complex.free - this.free * complex.i)
					/ (complex.free * complex.free + complex.i * complex.i);
			Complex result = new Complex(sumFree, sumI);
			return (result);
		}

		public void printComplex() {
			System.out.println(this.toString());
		}
		
		public String toString(){
			String result = "";
			if (i >= 0) {
				result += "(" + this.free + " + " + this.i + "i)";
			}
			else{
				result += "(" + this.free + " - " + (this.i * (-1)) + "i)";
			}
			return (result);
		}
		
		public void setNumber(Complex number){
			this.free = number.free;
			this.i = number.i;
		}
		
		public Complex getNumber(){
			Complex result = new Complex(this.free, this.i);
			return (result);
		}

		// getters & setters

		public Double getFree() {
			return free;
		}

		public void setFree(Double free) {
			this.free = free;
		}

		public Double getI() {
			return i;
		}

		public void setI(Double i) {
			this.i = i;
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

		public Complex getNext() {
			return this.next;
		}

		public void setNext(Complex next) {
			this.next = next;
		}
	}


