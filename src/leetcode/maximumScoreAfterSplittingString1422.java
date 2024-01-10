package leetcode;

public class maximumScoreAfterSplittingString1422 {

	public static void main(String[] args) {
		
		System.out.println(new Solution().maxScore("00"));
	}
	
}

//All possible ways of splitting s into two non-empty substrings are:
//left = "0" and right = "11101", score = 1 + 4 = 5 
//left = "01" and right = "1101", score = 1 + 3 = 4 
//left = "011" and right = "101", score = 1 + 2 = 3 
//left = "0111" and right = "01", score = 1 + 1 = 2 
//left = "01110" and right = "1", score = 2 + 1 = 3

class Solution {
    public int maxScore(String s) {
        int leftArr[]=new int[s.length()];
        int rightArr[]=new int[s.length()];
        
        int leftSum =0;
        int rightSum =0;
        int j=s.length()-1;
        
        if(s.length()<=2){
            if(s=="00"||s=="11")
                return 1;
            else 
            return 0;
        }

        
        char arr[]=s.toCharArray();
        
        for(int i=0;i<arr.length;i++,j--){
            char lChar=arr[i];
            
            char rChar = arr[j];
            if(lChar=='0') {
            	leftSum++;
            }
            if(rChar=='1') {
            	rightSum++;
            }
            leftArr[i]=leftSum;
            rightArr[j]=rightSum;
        }
        
        int maxSum = Integer.MIN_VALUE;
        for(int i=1;i<leftArr.length-1;i++) {
        	int tempSum=leftArr[i]+rightArr[i+1];
        	if(tempSum>maxSum) {
        		maxSum=tempSum;
        	}
//        	tempSum=leftArr[i+1]+rightArr[i];
//        	if(tempSum>maxSum) {
//        		maxSum=tempSum;
//        	}
        }
//        System.out.println(maxSum);
        
        
        return maxSum;
    }
}