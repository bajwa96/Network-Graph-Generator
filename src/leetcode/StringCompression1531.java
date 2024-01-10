package leetcode;

public class StringCompression1531 {

	public static void main(String[] args) {
		new StringCompression1531().getLengthOfOptimalCompression("aaabcccd", 1);

	}

	public int getLengthOfOptimalCompression(String s, int k) {

		char charGstr[] = new char[s.length()];
		int count[] = new int[s.length()];
		int length = 0;
		int outputLength = 0;

		char givenStr[] = s.toCharArray();
		for (int i = 0; i < givenStr.length; i++) {
			int nowCount = 0;
			for (int j = i; j < s.length() && givenStr[i] == givenStr[j]; j++) {
				nowCount++;
			}
			i += nowCount - 1;
			charGstr[length] = givenStr[i];
			count[length++] = nowCount;
			if (nowCount > 1) {
				outputLength += 2;
			} else {
				outputLength++;
			}
		}

		int minLength = optimiseThisString(charGstr, count, outputLength, k);

		for (char now : charGstr) {
			System.out.print(now + " ");
		}
		System.out.println();
		for (int now : count) {
			System.out.print(now + " ");
		}
		System.out.println("\nLength=" + length);
		System.out.println("outputLength=" + outputLength);
		System.out.println("minLength" + minLength);
		return k;

	}

	private int optimiseThisString(char[] charGstr, int[] count, int outputLength, int k) {
		if (k > 0) {
			if (charGstr.length > 3) {
				for (int i = 1; i < charGstr.length - 1; i++) {
					
					int leftCount = count[i - 1];
					int nowCount = count[i];
					int nextCount = count[i + 1];
					
					if (charGstr[i - 1] == charGstr[i + 1]) {
						
					} else {
						
					}
					
				}
			}
		}
		int minOutLength = 0;
		for (int i = 0; i < charGstr.length; i++) {
			if (count[i] > 1) {
				outputLength += 2;
			} else {
				outputLength++;
			}
		}
		return minOutLength;
	}
}
