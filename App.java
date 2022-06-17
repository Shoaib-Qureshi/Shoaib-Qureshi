import java.util.*;


class Node {
    Node next;
    int data;

    Node(int data) {
        this.data = data;
    }
}

class list {
    Node head;

    void inser(int data) {
        if (head == null) {
            head = new Node(data);
            return;
        }
        Node temp = head;
        while (temp.next != null) {
            temp = temp.next;
        }
        temp.next = new Node(data);
    }

    void print() {
        Node temp = head;
        while (temp != null) {
            System.out.print(temp.data + " ");
            temp = temp.next;
        }
        System.out.println();
    }

    public void BinarySort() {
        Node temp = head;
        if (temp == null || temp.next == null) {
            return;
        }
        int count0 = 0, count1 = 0;
        while (temp != null) {
            if (temp.data == 0) {
                count0++;
            } else {
                count1++;
            }
            temp = temp.next;
        }
        temp = head;
        while (count0 > 0) {
            temp.data = 0;
            temp = temp.next;
            count0--;
        }
        while (count1 > 0) {
            temp.data = 1;
            temp = temp.next;
            count1--;
        }
        print();
    }

    void swap() {

    }
}

class App {
    public static void main(String[] args) {
    
    System.out.print(5%2.5);
        
    }

public static int findNumbers(int[] nums) {
    int count=0;
    
    for(int i=0;i<nums.length;i++){
       int sum=0;
        int temp=0;
        
        temp=nums[i];
        while(temp!=0){
            temp=temp/10;
            sum++;
         }
        if(sum%2==0){
            count++;
        }
    }
    return count;
}

   public static int[] twoSum(int[] nums, int target) {
     HashMap<Integer,Integer>hm=new HashMap<>();
       
        
        for(int i=0;i<nums.length;i++){
            int k=target-nums[i];
            if(hm.containsKey(k)){
               int[] res={hm.get(k),i};
               return res;
            }else hm.put(nums[i],i);
      
        }
        return null;
    }

    
static int MinSubArray(int arr[],int k){
    int start=0;
    int end=arr.length;
    int sum=0;
    int i=0;
    int ans=0;
    while(end--==0){
        sum+=arr[i];
        while(sum>=k){
            ans=Math.min(ans,i+1-start);
            sum-=arr[start++];
        }
        i++;
    }
    return  ans;
}
public static int BinarySearch(int[] arr,int k){
    int low=0,high=arr.length;
    while(low<=high){
        int mid=low+(high-low)/2;
        if(arr[mid]==k)
        return arr[mid];
        else if(arr[mid]<k)
      low=mid+1;
        else
        high=mid-1;
    }
    return -1;
}

    public static void Swap2Arrays(int[] arr1,int arr2[]){ /// Mearge of two arrays using arraycopy 
      int alen=arr1.length;
      int blen=arr2.length;
        int ans[]=new int[alen+blen];
         System.arraycopy(arr1, 0, ans,0, alen); 
         System.arraycopy(arr2, 0, ans, alen,blen);
            Arrays.sort(ans);
           System.arraycopy(ans, 0, arr1, 0, alen); 
           System.arraycopy(ans, alen, arr2, 0, blen);
            for(int i=0;i<alen;i++){
              System.out.print(arr1[i]+" ");
           }
           System.out.println();
           for(int i=0;i<blen;i++){
            System.out.print(arr2[i]+" ");
        }
    }

    public static int Coins(int arr[],int len,int n){
        int table[]=new int[n+1];
        table[0]=1;
        for(int i=0;i<len;i++){
            for(int j=arr[i];j<=n;j++){
                table[j]+=table[j-arr[i]];
                System.out.print(j+" ");
            }
        }
        System.out.println();
        return (int)table[n];
    }
    public static String repl(String str){
    str.replace(".", "xyz" );
    return str;
    }
        
    public static String reverseWords(String str){
        String rev=new String();
        String word[]=str.split(" ");
        for(int i=word.length-1;i>=0;i--){
            if(word[i].trim().length()!=0){
                rev+=word[i]+" ";
            }

        }
        rev=rev.trim();
        return rev;
    } 
    public static int FirstOccr(int arr[],int k){
     int start=0;
     int last=arr.length-1;
     int index=-1;
     while(start<=last){
        int mid=(start+last)/2;
        if(arr[mid]==k){
            index=mid;
            last=mid-1;
        }else if(arr[mid]>k){
            last=mid-1;
        }else {
            start=mid+1;
        }
     }
     return index;
    }
    public static int LastOcc(int arr[],int k){
        int start=0;
        int last=arr.length-1;
        int index=-1;
        while(start<=last){
            int mid=(start+last)/2;
            if(arr[mid]==k){
                index=mid;
                start=mid+1;
            }else if(arr[mid]>k){
                last=mid-1;
            }else{
                start=mid+1;
            }
           
        }
        return index;
    }



    static int reverseNum(int n){
        int rev=0;
        while(n!=0){
            rev=rev*10+n%10;
            n=n/10;
        }
        return rev;
    }
    static int convertStringToInteger(String str){
        int a=0;
        for(int i=0;i<str.length();i++){
            a=a*10+((int)str.charAt(i)-49);
        }
        return a;
    }

    static int firstNonRepating(String str){
        int[] ch=new int[256];
        for(int i=0;i<str.length();i++){
           ch[str.charAt(i)]++;
        }
        int index=-1;
        for(int i=0;i<str.length();i++){
            if(ch[str.charAt(i)]==1){
                index=1;
                break;
            }
           
        }
        return index;
    }
    static boolean isPalindromString(String str){
        int i=0,j=str.length()-1;
        while(i<j){
            if(str.charAt(i)!=str.charAt(j))
                return false;
                i++;
                j--;
        }
        return true;
    }
    

    public static int maxdiff(int[] arr) {
        int msx_D = arr[1] - arr[0];
        for (int i = 0; i < arr.length; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j] - arr[i] > msx_D)
                    msx_D = arr[j] - arr[i];
            }
        }
        return msx_D;
    }

    public static boolean isPrime(int n) {
            for(int i=2;i<=(n/2);i++){
                if(n%i==0)
                return false;
            }
            return true;
    }

    public static void printChar(String str) {
        int n = str.length();
        int freq[] = new int[26];
        for (int i = 0; i < n; i++) {
            freq[str.charAt(i) - 'a']++;
        }
        for (int i = 0; i < n; i++) {
            if (freq[str.charAt(i) - 'a'] != 0) {
                System.out.print(str.charAt(i));
                System.out.print(freq[str.charAt(i) - 'a'] + " ");
                freq[str.charAt(i) - 'a'] = 0;
            }
        }
    }

    public static int n(int[] arr) {
        int f = arr.length / 2;
        for (int n : arr) {
            int c = 0;
            for (int nn : arr) {
                if (nn == n) {
                    c = c + 1;
                }
            }
            if (c > f) {
                return n;
            }
        }
        return -1;
    }

    public static void patter(int n) {
        if (n == 0 || n <= 0) {
            System.out.print(n + " ");
            return;
        }
        System.out.print(n + " ");
        patter(n - 5);
        System.out.print(n + " ");
    }

    public static int lengthOfLongestSubstring(String s) {
        if (s.length() == 0)
            return 0;
        HashMap<Character, Integer> map = new HashMap<Character, Integer>();
        int max = 0;
        for (int i = 0, j = 0; i < s.length(); ++i) {
            if (map.containsKey(s.charAt(i))) {
                j = Math.max(j, map.get(s.charAt(i)) + 1);
            }
            map.put(s.charAt(i), i);
            max = Math.max(max, i - j + 1);
        }
        return max;
    }

}
