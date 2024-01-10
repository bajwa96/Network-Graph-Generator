package leetcode;

public class RotateMatrix {

	public static void main(String[] args) {
//		[],[4,5,6],[7,8,9]]
		int[][] arr = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 } };
		new RotateMatrix().rotate(arr);
		print(arr);
	}

	private static void print(int[][] givenMatrix) {
		for (int i = 0; i < givenMatrix.length; i++) {
			for (int j = 0; j < givenMatrix[i].length; j++) {
				System.out.print(givenMatrix[i][j] + "\t");
			}
			System.out.println();
		}
	}

	public void rotate(int[][] matrix) {
		int startIndex = 0;
		int endIndex = matrix.length - 1;
		int temp = matrix[startIndex][startIndex];

	}

	public void performRotatation(int[][] matrix, int startRow, int endRow, int startCol, int endCol) {
		int temp = matrix[startRow+1][startCol];
		for(int i=startCol+1;i<=endCol;i++) {
			matrix[i][startRow]=matrix[i-1][startRow];
		}
	}
}
