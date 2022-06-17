import java.util.*;
import java.lang.*;
import java.io.*;

 class Main
{
	public static void main (String[] args) throws java.lang.Exception
	{
		//your code here
      Scanner sc=new Scanner(System.in);
      int n=sc.nextInt();
      int[] coins={1, 2, 5, 10, 20, 50, 100, 200, 500,2000};
      int len=coins.length;
      System.out.print(CoinsExchang(coins,len,n));
	}
  public static int CoinsExchang(int[] coins, int len,int n ){
    if(n==0)
      return 0;
    int res=Integer.MAX_VALUE;
    for(int i=0;i<len;i++){
      if(coins[i]<=n){
        int subres=CoinsExchang(coins,len,n-coins[i]);
        if(subres!=Integer.MAX_VALUE && subres+1<res)
          res=subres+1;
      }
    }
    return res;
  }
}