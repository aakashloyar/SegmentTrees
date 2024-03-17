package com.aakash;

public class main {
    public static void main(String[] args) {
        int n=6;
        int[] arr=new int[]{2,1,0,5,4,3};
        Segment seg1=new Segment(n,arr);
        seg1.build(arr,0,0,n-1);
        int min=seg1.query(0,0,n-1,0,n-1);
        System.out.println(min);
        seg1.update(0,0,n-1,2,4);
        seg1.update(0,0,n-1,0,4);
        seg1.update(0,0,n-1,1,4);
        min=seg1.query(0,0,n-1,0,n-1);
        System.out.println(min);
    }
}
class Segment {
    int[] seg;
    int n;
    Segment(int n,int[] seg) {
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
        if(high<s ||e<low) return Integer.MAX_VALUE;
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
        int ans=Math.min(a,b);
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
        seg[ind]=Math.min(seg[rightind],seg[leftind]);
    }//update the array on given index
}
