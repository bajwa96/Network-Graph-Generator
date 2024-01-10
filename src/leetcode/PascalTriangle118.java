package leetcode;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PascalTriangle118 {

	public static void main(String[] args) {
		System.out.println(new PascalTriangle118().generate(10));
	}

	public List<List<Integer>> generate(int numRows) {
		List<List<Integer>> pascalTriangle = new ArrayList<>();
		List<Integer> sizeZero = new ArrayList<>();
		if (numRows >= 1) {
			sizeZero.add(1);
			pascalTriangle.add(sizeZero);
		}
		processNextRows(2, numRows, pascalTriangle, sizeZero);
		return pascalTriangle;

	}

	private void processNextRows(int i, int numRows, List<List<Integer>> pascalTriangle, List<Integer> lastRow) {
		if (i <= numRows) {
			List<Integer> nextRow = new ArrayList<>();

			Integer pechla = null;

			for (int now : lastRow) {
				int current = -1;
				if (pechla == null) {
					current = now;
					pechla = current;
				} else {
					current = now + pechla;
					pechla = now;
				}
				nextRow.add(current);
			}
			nextRow.add(pechla);
			pascalTriangle.add(nextRow);
			processNextRows(i + 1, numRows, pascalTriangle, nextRow);
		}
	}

}
