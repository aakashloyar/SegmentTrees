package com.aakash.Count_Inversion;

import java.util.*;

public class main {
    public static void main(String[] args) {
        int n=6;
        int[] arr=new int[]{4,3,6,1,5,2};
        CI seg1=new CI(n);
        int[][] temp=new int[n][2];
        for(int i=0;i<n;i++) {
            temp[i][1]=arr[i];
            temp[i][0]=i;
        }
        sort2darray(temp);
        int[] ind=new int[n];
        for(int i=0;i<n;i++) {
            ind[temp[i][0]]=i;
        }
        int[] cnt=new int[n];
        Arrays.fill(cnt,1);
        seg1.build(cnt,0,0,n-1);
        int ans=0;
        for(int i=0;i<arr.length;i++) {
            seg1.update(0,0,n-1,ind[i],0);
            ans+=seg1.query(0,0,n-1,0,ind[i]);
        }
        System.out.println(ans);
    }
    static void sort2darray(int[][] arr) {
        Arrays.sort(arr,(int[] a, int[] b)->{
            if(a[1]!=b[1])
                return a[1]-b[1];
            else return a[0]-b[0];

        });
    }
}
class CI {
    //here we are counting how many of the number less than given number that are not visited
    int[] seg;
    int n;
    CI(int n) {
        this.n=n;
        this.seg=new int[4*n];
    }
    void build(int[] arr,int ind,int low,int high) {
        //left ind=2*ind+1
        //right ind=2*ind+2
        //mid=(low+high)/2
        if(high-low==0) {
            seg[ind]=arr[low];
            return;
        }
        int mid=low+(high-low)/2;
        int leftind=(2*ind)+1;
        int rightind=(2*ind)+2;
        build(arr,leftind,low,mid);
        build(arr,rightind,mid+1,high);
        seg[ind]=push(seg[leftind],seg[rightind]);
    }
    int query(int ind,int low,int high,int s,int e) {
        //no overlapping
        if(high<s ||e<low) return 0;
        //complete merge
        if(s<=low &&high<=e) return seg[ind];
        int mid=low+(high-low)/2;
        int leftind=2*ind+1;
        int rightind=2*ind+2;
        int a=query(leftind,low,mid,s,e);
        int b=query(rightind,mid+1,high,s,e);
        return push(a,b);
    }//return minimum of given range in array
    int push(int a,int b) {
        int ans=a+b;
        return ans;
    }
    void update(int ind,int low,int high,int pivot,int val) {
        if(low==high) {
            seg[ind]=val;
            return;
        }
        int mid=low+(high-low)/2;
        int leftind=2*ind+1;
        int rightind=2*ind+2;
        if(pivot<=mid) update(leftind,low,mid,pivot,val);
        else update(rightind,mid+1,high,pivot,val);
        seg[ind]=push(seg[rightind],seg[leftind]);
    }//update the array on given index

}
