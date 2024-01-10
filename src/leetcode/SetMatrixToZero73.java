package leetcode;

import java.util.HashSet;
import java.util.Set;

public class SetMatrixToZero73 {

	public static void main(String[] args) {
		int givenMatrix[][] = { { 1, 1, 1 }, { 1, 0, 1 }, { 1, 1, 1 } };
		int givenMatrix2[][] = { { 0, 1, 2, 0 }, { 3, 4, 5, 2 }, { 1, 3, 1, 5 } };

//		print(givenMatrix2);
		new SetMatrixToZero73().getLengthOfOptimalCompression(givenMatrix2);
		print(givenMatrix2);

		int givenMatrix3[][] = { { 0, 1, 2, 0 }, { 3, 4, 5, 2 }, { 1, 3, 1, 5 } };
//		print(givenMatrix3);
		new SetMatrixToZero73().setZeroes(givenMatrix3);
		print(givenMatrix3);

	}

	private static void print(int[][] givenMatrix) {
		for (int i = 0; i < givenMatrix.length; i++) {
			for (int j = 0; j < givenMatrix[i].length; j++) {
				System.out.print(givenMatrix[i][j] + "\t");
			}
			System.out.println();
		}
	}

	private void getLengthOfOptimalCompression(int[][] givenMatrix) {
		Set<Integer> rows = new HashSet<>(givenMatrix.length);
		Set<Integer> cols = new HashSet<>(givenMatrix.length);

		for (int i = 0; i < givenMatrix.length; i++) {
			for (int j = 0; j < givenMatrix[i].length; j++) {
				if (givenMatrix[i][j] == 0) {
					rows.add(i);
					cols.add(j);
				}
			}
		}

		for (int curRow : rows) {
			for (int i = 0; i < givenMatrix[curRow].length; i++) {
				givenMatrix[curRow][i] = 0;
			}
		}
		for (int cuurCols : cols) {
			for (int i = 0; i < givenMatrix.length; i++) {
				givenMatrix[i][cuurCols] = 0;
			}
		}

//		for (int i = 0; i < givenMatrix.length; i++) {
//			for (int j = 0; j < givenMatrix.length; j++) {
//				if (rows.contains(i) || cols.contains(j)) {
//					givenMatrix[i][j] = 0;
//				}
//			}
//		}
	}

	public void setZeroes(int[][] givenMatrix) {

		int colContainsZero = 0;

		for (int i = 1; i < givenMatrix.length; i++) {

			if (givenMatrix[i-1][0] == 0)
				colContainsZero = 1;
			if (givenMatrix[i][0] == 0)
				colContainsZero = 1;

			for (int j = 1; j < givenMatrix[i].length; j++) {
				System.out.println(givenMatrix[i][j]);
				if (givenMatrix[i][j] == 0) {
					System.out.println("now setting 0" + i + ", j=" + j);
					givenMatrix[i][0] = 0;
					givenMatrix[0][j] = 0;
				}
			}
		}

		for (int i = 1; i < givenMatrix.length; i++) {
			for (int j = 1; j < givenMatrix[i].length; j++) {
				if (givenMatrix[i][0] == 0 || givenMatrix[0][j] == 0) {
					System.out.println("now setting" + i + ", j=" + j);
					givenMatrix[i][j] = 0;
				}
			}
		}

		if (givenMatrix[0][0] == 0)
			for (int i = 1; i < givenMatrix[0].length; i++) {
				givenMatrix[0][i] = 0;
			}
		if(colContainsZero==1) {
			for (int i = 1; i < givenMatrix.length; i++) {
				givenMatrix[i][0] = 0;
			}
		}
	}
}
