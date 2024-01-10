package leetcode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class BestMeetingPoint286 {

	public static void main(String[] args) {
		SolutionV2 s = new SolutionV2();
//		[[1,0,0,0,1],[0,0,0,0,0],[0,0,1,0,0]]
		int[][] arr = { { 1, 0, 0, 0, 1 }, { 0, 0, 0, 0, 0 }, { 0, 0, 1, 0, 0 } };
//		int[][] arr = { { 1, 1 } };
//		int[][] arr = { { 1 }, { 0 }, { 1 } };
//		int[][] arr = { { 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 1, 0, 0, 1, 0 }, { 1, 1, 0, 0, 0, 0, 1, 0, 0 },
//				{ 0, 0, 0, 1, 1, 1, 0, 0, 0 } };
		int ans = s.minTotalDistance(arr);
		System.out.println(ans);

	}

}

class SolutionV2 {
	public int minTotalDistance(int[][] grid) {
		handlePlain hp = new handlePlain(grid.length, grid[0].length);

		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				if (grid[i][j] == 1) {
					hp.addPoint(i, j);
				}
			}
		}
		return hp.getSumOfDist();
	}

	class handlePlain {
		class Point {
			int r, c;

			Point(int r, int c) {
				this.r = r;
				this.c = c;
			}

			@Override
			public String toString() {
				return "[r=" + r + ", c=" + c + "]";
			}
		}

		Set<Integer> rows;
		Set<Integer> cols;
		Point centroid;
		List<Point> points;

		public handlePlain(int length, int length2) {
			points = new ArrayList<>();
			this.rows = new LinkedHashSet<>(length);
			this.cols = new LinkedHashSet<>(length2);
		}

		public void addPoint(int r, int c) {
			points.add(new Point(r, c));
			rows.add(r);
			cols.add(c);
		}

		private void calculateCentroid() {
			List<Integer> acols = new ArrayList<>(cols);
			List<Integer> aRows = new ArrayList<>(rows);
			Collections.sort(acols);
			System.out.println(acols);
			System.out.println(aRows);
			int medianR = aRows.get((aRows.size()-1) / 2);
			int medianC = acols.get((acols.size()-1) / 2 );
			this.centroid = new Point(medianR, medianC);
		}

		private int calculateDistance(Point point) {
			return Math.abs(point.c - this.centroid.c) + Math.abs(point.r - this.centroid.r);
		}

		public int getSumOfDist() {
			calculateCentroid();
			System.out.println(this.centroid);

			int sum = 0;
			for (Point now : points) {
				sum += calculateDistance(now);
			}

			return sum;
		}
	}

}

class SolutionV1 {
	public int minTotalDistance(int[][] grid) {
		handlePlain hp = new handlePlain();
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				if (grid[i][j] == 1) {
					hp.addPoint(i, j);
				}
			}
		}
		return hp.getSumOfDist();
	}

	class handlePlain {
		class Point implements Comparable<Point> {
			int r, c;

			Point(int r, int c) {
				this.r = r;
				this.c = c;
			}

			@Override
			public String toString() {
				return "[r=" + r + ", c=" + c + "]";
			}

			@Override
			public int compareTo(Point o) {
				if (o.c < this.c) {
					return 1;
				}
				return -1;
			}
		}

		private List<Point> pnt;
		private Point centroid;
		private int rsum, csum;

		private handlePlain() {
			this.pnt = new LinkedList<>();
		}

		public void addPoint(int r, int c) {
			Point newOne = new Point(r, c);
			pnt.add(newOne);
//			rsum += newOne.r;
//			csum += newOne.c;
		}

		private void calculateCentroid() {
			this.centroid = new Point(Math.round(rsum / pnt.size()), Math.round(csum / pnt.size()));
		}

		private int calculateDistance(Point point) {
			return Math.abs(point.c - this.centroid.c) + Math.abs(point.r - this.centroid.r);
		}

		public int getSumOfDist() {
			Collections.sort(pnt);
			Point median = pnt.get(pnt.size() / 2);

			calculateCentroid();
			System.out.println(this.centroid);

			int sum = 0;
			for (Point now : pnt) {
				sum += calculateDistance(now);
			}

			return sum;
		}
	}

}
