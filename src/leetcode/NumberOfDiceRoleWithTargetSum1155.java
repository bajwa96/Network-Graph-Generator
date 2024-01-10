package leetcode;

public class NumberOfDiceRoleWithTargetSum1155 {

	public static void main(String[] args) {
		new NumberOfDiceRoleWithTargetSum1155().numRollsToTarget(4, 6, 15);
	}

	public int numRollsToTarget(int n, int k, int target) {
		int postions[] = new int[n];
		filleInMaxValues(postions, k, target, 0, 0);
		for (int now : postions) {
			System.out.print(now + ", ");
		}
		return 0;
	}

	private void filleInMaxValues(int[] positions, int k, int target, int index, int currSum) {
		if (index < positions.length) {
			int remaining = (target - currSum);

			if (index == positions.length-1) {
				positions[index] = remaining;
				return;
			}

			if ((remaining - k) / (positions.length - index) > 0) {
				positions[index] = k;
				currSum += k;
			} else {
				for (int i = k - 1; i > 0; i--) {
					if (Math.round((float) (remaining - i) / (float) (positions.length - index)) > 0) {
						positions[index] = i;
						currSum += i;
						break;
					}

				}
			}
			filleInMaxValues(positions, k, target, index + 1, currSum);
		}
	}
}
