package com.account.management.customer.utils;

import java.util.*;

class Test {
	Test other;

	public static void main(String[] args) {
		
		name(Set.of(1,2,3,4,5));
		name(List.of(6,7,8,9));
		// int[] L = {2,2,2,2,2,3,4,4,4,6};
		fun();

	}
	
	
	
	static void fun() {
		Test a = new Test();
		Test b = new Test();
		a.other = b;
		b.other = a;
	}
	
	public static <T> void name(Collection<T> e) {
		for (T er: e ) {
			System.out.print(er);
			
		}
	}

	public int solution(int[] arr) {
		// write your code in Java SE 8
		int arrCnt = arr.length;
		int res = 1;
		boolean found = false;
		Set<Integer> set = new HashSet<>();
		for (int a : arr) {
			if (a > 0) {
				set.add(a);
			}
		}
		System.out.println("set=========" + set);
		for (int i = 1; i <= arrCnt + 1 && !found; i++) {
			System.out.println("i=========" + i);
			if (!set.contains(i)) {
				System.out.println("res not contain=========" + i);
				res = i;
				found = true;
			}
		}
		return res;

	}

	int solution2(int[] A) {
		int n = A.length;
		int[] L = new int[n + 1];
		L[0] = -1;
		for (int i = 0; i < n; i++) {
			L[i + 1] = A[i];
		}
		int count = 0;
		int pos = (n + 1) / 2;
		int candidate = L[pos];
		for (int i = 1; i <= n; i++) {
			if (L[i] == candidate)
				count = count + 1;
		}
//       if (count > pos)
		if (2 * count > n)
			return candidate;
		return (-1);
	}
}
