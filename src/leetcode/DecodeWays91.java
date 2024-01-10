package leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DecodeWays91 {

	final static Map<String, String> numberVsCharacter;
	static {
		numberVsCharacter = new HashMap<>();

		for (char i = 'A'; i <= 'Z'; i++) {
			numberVsCharacter.put(String.valueOf(i - 64), (i) + "");
		}
	}
//	1201234 4 ->5
//	1212 4->5
	/**
	 * 1212 1 2 1 2 12 12 1 21 2 1 2 12 12 1 2
	 */

	static int count = 0;

	public static void main(String[] args) {

		String given = "121212121212121212121212";

		System.out.println(getCount(given));

	}

	private static int getCount(String s) {
		if (s == null || s.length() == 0 || s.charAt(0) == '0') {
			return 0;
		}

		int n = s.length();
		int[] dp = new int[n + 1];
		dp[0] = 1;
		dp[1] = 1;

		for (int i = 2; i <= n; ++i) {
			char oneDigit = s.charAt(i - 1);
			int twoDigits = Integer.parseInt(s.substring(i - 2, i));

			if (oneDigit != 0) {
				dp[i] += dp[i - 1];
			}

			if (10 <= twoDigits && twoDigits <= 26) {
				dp[i] += dp[i - 2];
			}
		}

		return dp[n];
	}

	private static String checkPattern(String str) {
		String pattern = "";
		String ans = "";
		for (int i = 0; i < str.length() / 2; i++) {
			pattern += str.charAt(i);
			if (str.length() % pattern.length() == 0 && isRepeating(str, pattern)) {
				ans = pattern;
			}
		}
		return ans;
	}

	private static boolean isRepeating(String str, String pattern) {
		String leftover = str;
		int currIndex = leftover.indexOf(pattern);
		while (currIndex == 0) {
			if (currIndex + pattern.length() == leftover.length()) {
				return true; // you have reached the last possible instance of the pattern at this point
			}
			leftover = leftover.substring(currIndex + pattern.length());
			currIndex = leftover.indexOf(pattern);
		}
		return false;
	}

	public static void numDecodingsV2(char[] s, Set<String> noOfWays, int index, String computing, boolean[] visited) {
		if (index < s.length) {
			char now = s[index];
			boolean localvisited[] = visited.clone();

			if (index == 0) {
				String key = now + "";

				numDecodingsV2(s, noOfWays, index + 1, computing, visited.clone());

				if (numberVsCharacter.containsKey(key)) {
					localvisited[index] = Boolean.TRUE;
					numDecodingsV2(s, noOfWays, index + 1, computing + numberVsCharacter.get(key), localvisited);
				}

			} else {
				numDecodingsV2(s, noOfWays, index + 1, computing, visited.clone());
				char prev = s[index - 1];
				String key = prev + "" + now;
				if (numberVsCharacter.containsKey(key) && !visited[index - 1]) {
					localvisited[index - 1] = Boolean.TRUE;
					localvisited[index] = Boolean.TRUE;
					numDecodingsV2(s, noOfWays, index + 1, computing + numberVsCharacter.get(key), localvisited);
				}
				key = now + "";
				if (numberVsCharacter.containsKey(key)) {
					localvisited = visited.clone();
					localvisited[index] = Boolean.TRUE;
					numDecodingsV2(s, noOfWays, index + 1, computing + numberVsCharacter.get(key), localvisited);
				}

			}
		} else {
			if (index == s.length && visited(visited) && computing != "") {
				count++;
//				noOfWays.add(computing);
			}
		}
	}

	public static boolean visited(boolean visited[]) {

		for (boolean now : visited) {
			if (!now)
				return false;
		}
		return true;
	}

	public static int numDecodingsV1(String s) {

		List<String> noOfWays = new LinkedList<>();

		boolean found = false;

		char ss[] = s.toCharArray();
		for (int i = 1; i < ss.length; i++) {
			char prev = ss[i - 1];
			char now = ss[i];
			found = false;

			String key = "" + prev;
			if (numberVsCharacter.containsKey(key)) {
				noOfWays.add(numberVsCharacter.get(key));
				found = true;
			}
			key += now;
			if (numberVsCharacter.containsKey(key)) {
				noOfWays.add(numberVsCharacter.get(key));
				found = true;
			}
			key = "" + now;
			if (numberVsCharacter.containsKey(key)) {
				noOfWays.add(numberVsCharacter.get(key));
				found = true;
			}

			if (found == false) {
				return 0;
			}
		}
		System.out.println(noOfWays);

		return noOfWays.size();

	}

}
