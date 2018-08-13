package sample.p040;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;


public class Solution {
		
	static long MOD  = 1000000007;
	static long RESULT;
	static long[] DT = new long[100000+1];
	static int[][] COMPANY  = new int[100000+1][2]; //INPUT 직장
	static int[][] AGENCY   = new int[300000+1][2]; //INPUT 소개소
	static int[]   CHANGE      = new int[100000+1];    //index 변경
	static ArrayList<Integer>[] BF_COMPANY = new ArrayList[100000+1]; //이전직장리스트
	static int T, N, M, CASE;

	public static void main(String[] args) throws Exception{

		System.setIn(new FileInputStream(Solution.class.getResource("").getPath()+"sample_input.txt"));
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
		
		T = Integer.parseInt(bufferedReader.readLine());
		StringTokenizer stTokenizer;
		
		for(CASE = 1; CASE <= T ; CASE++){
			stTokenizer = new StringTokenizer(bufferedReader.readLine());
			N = Integer.parseInt(stTokenizer.nextToken());
			M = Integer.parseInt(stTokenizer.nextToken());
			
			stTokenizer = new StringTokenizer(bufferedReader.readLine());
			
			for(int i=1 ; i <=N ; i++){
				int n = Integer.parseInt(stTokenizer.nextToken());
				COMPANY[i] = new int[]{i, n};
			}
			
			for(int i=1 ; i <=M ; i++){
				stTokenizer = new StringTokenizer(bufferedReader.readLine());
				int a = Integer.parseInt(stTokenizer.nextToken());
				int b = Integer.parseInt(stTokenizer.nextToken());
				
				AGENCY[i] = new int[]{a, b};
			}
			
			solve();
			
			System.out.println("#"+CASE+" "+RESULT);
		}	
			
	}
	
	public static void solve(){
		
		//직장의 크기 순으로 정렬
		Arrays.sort(COMPANY, 1, N+1, new Comparator<int[]>() {

			@Override
			public int compare(int[] arg0, int[] arg1) {
				// TODO Auto-generated method stub
				return arg0[1] - arg1[1];
			}
		});
		
		for(int i=1 ; i <=N ; i++){
			
			//INDEX 변경
			CHANGE[COMPANY[i][0]] = i;
			
			//초기화
			DT[i] = 0;
			BF_COMPANY[i] = new ArrayList<>();
		}
		
		
		//이전직장 세팅
		for(int i=1 ; i <=M ; i++){
			
			int a = CHANGE[AGENCY[i][0]];
			int b = CHANGE[AGENCY[i][1]];
			
			if(COMPANY[a][1] > COMPANY[b][1]){
				BF_COMPANY[a].add(b);
			}else if(COMPANY[b][1] > COMPANY[a][1]){
				BF_COMPANY[b].add(a);
			}
		}
		
		
		//DP
		RESULT = 0;
		
		for(int i=1 ; i <=N ; i++){
			
			for(int j=0 ; j < BF_COMPANY[i].size() ; j++){	
				
				int m = BF_COMPANY[i].get(j);
				
			    DT[i] = (DT[i] + DT[m]+1) % MOD;
			}			
			RESULT = (RESULT + DT[i]) % MOD;
		}
	}

}
