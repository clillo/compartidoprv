package com.previred.utils.algoritmos;

public class StringSimilarity {

	  /**
	   * Calculates the similarity (a number within 0 and 1) between two strings.
	   */
	  public static double similarity(char s1[], char s2[]) {
		int longerLength =0;
		int s1l = s1.length;
		int s2l = s2.length;
		if (s1l==0 || s2l==0)
			return 1.0;
		
	    if (s1l< s2l)
		    return (longerLength - editDistance(s1, s2, s1l, s2l)) / (double) longerLength;
	    else 
		    return (longerLength - editDistance(s2, s1, s2l, s1l)) / (double) longerLength;
	    
	  }

	  // Example implementation of the Levenshtein Edit Distance
	  // See http://rosettacode.org/wiki/Levenshtein_distance#Java
	  public static int editDistance(char s1[], char s2[], 	int s1l, int s2l) {
	  //  s1 = s1.toLowerCase();
	   // s2 = s2.toLowerCase();

		int lastValue = 0;
		int newValue = 0;
	    int[] costs = new int[s2l + 1];
	    for (int i = 0; i <= s1l; i++) {
	      lastValue = i;
	      for (int j = 0; j <= s2l; j++) {
	        if (i == 0)
	          costs[j] = j;
	        else {
	          if (j > 0) {
	            newValue = costs[j - 1];
	            if (s1[i - 1] != s2[j - 1])
	              newValue = Math.min(Math.min(newValue, lastValue),  costs[j]) + 1;
	            costs[j - 1] = lastValue;
	            lastValue = newValue;
	          }
	        }
	      }
	      if (i > 0)
	        costs[s2l] = lastValue;
	    }
	    return costs[s2l];
	  }

//	  public static void printSimilarity(String s, String t) {
//	    System.out.println(String.format("%.3f is the similarity between \"%s\" and \"%s\"", similarity(s, t), s, t));
//	  }

	  public static void main(String[] args) {
//	    printSimilarity("", "");
//	    printSimilarity("1234567890", "1");
//	    printSimilarity("1234567890", "123");
//	    printSimilarity("1234567890", "1234567");
//	    printSimilarity("1234567890", "1234567890");
//	    printSimilarity("1234567890", "1234567980");
//	    printSimilarity("47/2010", "472010");
//	    printSimilarity("47/2010", "472011");
//	    printSimilarity("47/2010", "AB.CDEF");
//	    printSimilarity("47/2010", "4B.CDEFG");
//	    printSimilarity("47/2010", "AB.CDEFG");
//	    printSimilarity("The quick fox jumped", "The fox jumped");
//	    printSimilarity("The quick fox jumped", "The fox");
//	    printSimilarity("kitten", "sitting");
	    long tiempo = System.currentTimeMillis();
	    char c1[] = { '1','1','9','7','5','1','7','8'};
	    char c2[] = { '1','2','9','1','0','8','6','5'};
	    
	    double d=0;
		for (int i=0; i<2_000_000; i++)
			for (int j=0; j<100; j++){
				d=similarity(c1, c2);
		}
		System.out.println(d);
	    
	    System.out.println(System.currentTimeMillis() - tiempo);
	  }
}
