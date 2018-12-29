import java.util.*;
import java.io.*;

public class Main{
    public static BufferedReader getInput(String arg[])throws IOException{
        if(arg.length==0){
            return new BufferedReader(new InputStreamReader(System.in));
        }else{
            return new BufferedReader(new FileReader(arg[0]));
        }        
    }

    public static void main(String arg[])throws IOException{
        BufferedReader br = getInput(arg);
        PrintWriter pr = new PrintWriter(System.out);
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int size = (int)Math.pow(2,n);
        int arr[] = new int[size];
        st = new StringTokenizer(br.readLine());
        for(int i=0;i<size;i++){
            arr[i] = Integer.parseInt(st.nextToken());
        }
        RMQ rmq = new RMQ();
        rmq.build(arr,size);
        for(int i=0;i<m;i++){
            st = new StringTokenizer(br.readLine());
            int p = Integer.parseInt(st.nextToken())-1;
            int b = Integer.parseInt(st.nextToken());
            rmq.update(size, p, b);
            System.out.println(rmq.tree[0]);
        }
        pr.close();
    }
}

class RMQ{
    int tree[];
    boolean or;
    int buildUtil(int arr[],int ss,int se,int i){
        if(ss==se){
            tree[i] = arr[ss];
            return tree[i];
        }
        int mid = ss+(se-ss)/2;
        boolean hold = or;
        int left = buildUtil(arr,ss,mid,2*i+1);
        or = hold;
        int right = buildUtil(arr,mid+1,se,2*i+2);
        if(or){
            tree[i] = left|right;
        }else{
            tree[i] = left^right;
        }
        or = !or;
        return tree[i];
    }
    void build(int arr[],int n){
        int x = (int)Math.ceil(Math.log(n)/Math.log(2));
        int max = 2*(int)Math.pow(2,x)-1;
        tree = new int[max];
        or = true;
        buildUtil(arr,0,n-1,0);
    }
    void updateUtil(int ss,int se,int p,int b,int i){
        if(ss==se){
            tree[i] = b;
            return;
        }
        int mid = ss+(se-ss)/2;
        if(p>=ss && p<=mid){
            updateUtil(ss,mid,p,b,2*i+1);
        }else{
            updateUtil(mid+1,se,p,b,2*i+2);
        }
        if(or){
            tree[i] = tree[2*i+1]|tree[2*i+2];
        }else{
            tree[i] = tree[2*i+1]^tree[2*i+2];
        }
        or = !or;
    }
    void update(int n,int p,int b){
        or = true;
        updateUtil(0,n-1,p,b,0);
    }
}