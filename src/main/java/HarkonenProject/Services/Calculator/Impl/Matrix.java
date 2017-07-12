package HarkonenProject.Services.Calculator.Impl;

public class Matrix {

	private Integer rows;
	private Integer columns;
	private Double[][] array;
	private Character operation;
	private Integer priority;
	private Matrix next;

	// constractors

	public Matrix(Integer rows, Integer columns, Character operation) {
		this.rows = rows;
		this.columns = columns;
		this.array = new Double[this.rows][this.columns];
		this.operation = operation;
		this.priority = this.priority(operation);
		this.next = null;
	}

	public Matrix(Integer size, Character operation) {
		this.rows = size;
		this.columns = size;
		this.array = new Double[this.rows][this.columns];
		this.operation = operation;
		this.priority = this.priority(operation);
		this.next = null;
	}

	public Matrix(Matrix matrix) {
		this.rows = matrix.rows;
		this.columns = matrix.columns;
		this.copyValues(matrix);
		this.operation = matrix.operation;
		this.priority = matrix.priority;
		this.next = null;
	}

	public Matrix(Integer rows, Integer columns) {
		this.rows = rows;
		this.columns = columns;
		this.array = new Double[this.rows][this.columns];
		this.next = null;
	}

	public Matrix(Integer size) {
		this.rows = size;
		this.columns = size;
		this.array = new Double[this.rows][this.columns];
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

	public Matrix combineMatrixToLeft(Matrix matrix) {
		Matrix result = new Matrix(this.rows, this.columns + matrix.columns);
		for (Integer i = 0; i < result.rows; i++) {
			for (Integer j = 0; j < matrix.columns; j++) {
				result.array[i][j] = matrix.array[i][j];
			}
			for (Integer j = matrix.columns; j < result.columns; j++) {
				result.array[i][j] = this.array[i][j - matrix.columns];
			}
		}
		return (result);
	}

	public Matrix combineMatrixToRight(Matrix matrix) {
		Matrix result = new Matrix(this.rows, this.columns + matrix.columns);
		for (Integer i = 0; i < result.rows; i++) {
			for (Integer j = 0; j < this.columns; j++) {
				result.array[i][j] = this.array[i][j];
			}
			for (Integer j = this.columns; j < result.columns; j++) {
				result.array[i][j] = matrix.array[i][j - matrix.columns];
			}
		}
		return (result);
	}

	public Matrix combineMatrixToTop(Matrix matrix) {
		Matrix result = new Matrix(this.rows + matrix.rows, this.columns);
		for (Integer i = 0; i < matrix.rows; i++) {
			for (Integer j = 0; j < this.columns; j++) {
				result.array[i][j] = matrix.array[i][j];
			}
		}
		for (Integer i = matrix.rows; i < result.rows; i++) {
			for (Integer j = 0; j < this.columns; j++) {
				result.array[i][j] = this.array[i - matrix.rows][j];
			}
		}
		return (result);
	}

	public Matrix combineMatrixToBottom(Matrix matrix) {
		Matrix result = new Matrix(this.rows + matrix.rows, this.columns);
		for (Integer i = 0; i < this.rows; i++) {
			for (Integer j = 0; j < this.columns; j++) {
				result.array[i][j] = this.array[i][j];
			}
		}
		for (Integer i = this.rows; i < result.rows; i++) {
			for (Integer j = 0; j < this.columns; j++) {
				result.array[i][j] = matrix.array[i - this.rows][j];
			}
		}
		return (result);
	}

	public Matrix subMatrix(Integer startX, Integer startY, Integer endX, Integer endY) {
		Matrix result = new Matrix(endX - startX, endY - startY);

		for (Integer i = startX; i < endX; i++) {
			for (Integer j = startY; j < endY; j++) {
				result.array[i - startX][j - startY] = this.array[i][j];
			}
		}

		return (result);
	}

	public Matrix subMatrix(Integer startX, Integer startY) {
		return (subMatrix(startX, startY, this.rows - 1, this.columns - 1));
	}

	public Double trace() {
		if (this.rows != this.columns) {
			System.err.println("You tried to calculate the trace of a non-even matrix.");
			return (0.0);
		} else {
			Double sum = 0.0;

			for (Integer i = 0; i < this.rows; i++) {
				sum += this.array[i][i];
			}

			return (sum);
		}
	}

	public Matrix transpose() {
		Matrix result = new Matrix(this.columns, this.rows);

		for (Integer i = 0; i < this.rows; i++) {
			for (Integer j = 0; j < this.columns; j++) {
				result.array[j][i] = this.array[i][j];
			}
		}

		return (result);
	}

	public Matrix addMatrix(Matrix matrix) {
		Matrix result = new Matrix(this.rows, this.columns);

		for (Integer i = 0; i < this.rows; i++) {
			for (Integer j = 0; j < this.columns; j++) {
				result.array[i][j] = this.array[i][j] + matrix.array[i][j];
			}
		}
		return (result);
	}

	public Matrix substractMatrix(Matrix matrix) {
		Matrix result = new Matrix(this.rows, this.columns);

		for (Integer i = 0; i < this.rows; i++) {
			for (Integer j = 0; j < this.columns; j++) {
				result.array[i][j] = this.array[i][j] - matrix.array[i][j];
			}
		}
		return (result);
	}

	public Matrix multiplyMatrix(Matrix matrix) {
		Matrix result = new Matrix(this.rows);

		for (Integer i = 0; i < result.rows; i++) {
			for (Integer j = 0; j < result.columns; j++) {
				Integer index = 0;
				Double cellResult = (double) 0;
				while (index < this.columns && index < matrix.rows) {
					cellResult += this.array[i][index] * matrix.array[index][j];
					index++;
				}
				result.array[i][j] = cellResult;
			}
		}

		return (result);
	}

	public void printMatrix() {
		System.out.println(this.toString());
	}
	
	public String toString(){
		String result = "";
		for (Integer i = 0; i < this.rows; i++) {
			for (Integer j = 0; j < this.columns; j++) {
				Double value = this.array[i][j];
				if (this.array[i][j] % 1 == 0){
					result += value.intValue() + " ";
				}
				else{
					result += value + " ";
				}
			}
			result += "\n";
		}
		return (result);
	}

	public void setValueOf(Integer x, Integer y, Double value) {
		this.array[x][y] = value;
	}

	public Double getValueOf(Integer x, Integer y) {
		return this.array[x][y];
	}

	public void copyValues(Double[][] original) {
		for (Integer i = 0; i < this.rows; i++) {
			for (Integer j = 0; j < this.columns; j++) {
				this.array[i][j] = original[i][j];
			}
		}
	}

	public void copyValues(Matrix original) {
		this.copyValues(original.array);
	}

	// getters & setters

	public Integer getRows() {
		return rows;
	}

	public Integer getColumns() {
		return columns;
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

	public Matrix getNext() {
		return this.next;
	}

	public void setNext(Matrix next) {
		this.next = next;
	}
}

