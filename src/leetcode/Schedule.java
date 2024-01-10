package leetcode;

import java.util.Arrays;
import java.util.Comparator;

public class Schedule {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	// Function to find the maximum profit and the number of jobs done.
	int[] JobScheduling(Job arr[], int n) {
		Arrays.sort(arr, new Comparator<Job>() {
			@Override
			public int compare(Job a, Job b) {
				if (a.deadline == b.deadline) {
					return Integer.compare(a.profit, b.profit);
				} else {
					return Integer.compare(a.deadline, b.deadline);
				}
			}
		});
		 Arrays.sort(arr, (a,b)->a.deadline==b.deadline?(Integer.compare(a.profit,b.profit)):Integer.compare(a.deadline,b.deadline));
		int maxProfit = arr[0].profit;
		Job last = arr[0];
		int jobs = 1;
		for (int i = 1; i < arr.length; i++) {
			if (arr[i].deadline > last.deadline) {
				maxProfit += arr[i].profit;
				last = arr[i];
				jobs++;
			}
		}
		return new int[]{maxProfit,jobs};
	}

	class Job {
		int id, profit, deadline;

		Job(int x, int y, int z) {
			this.id = x;
			this.deadline = y;
			this.profit = z;
		}
	}

}
