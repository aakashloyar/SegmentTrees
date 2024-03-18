package com.aakash.Lazy_Propagation;

public class main {
    public static void main(String[] args) {
        int n=6;
        int[] arr=new int[]{2,1,0,5,4,3};
        LP seg1=new LP(n);
        seg1.build(arr,0,0,n-1);
        int min=seg1.query(0,0,n-1,0,3);
        System.out.println(min);
        seg1.update(0,0,n-1,2,4,2);
        min=seg1.query(0,0,n-1,0,3);
        System.out.println(min);
        seg1.update(0,0,n-1,2,4,3);
        min=seg1.query(0,0,n-1,0,3);
        System.out.println(min);
    }
}
class LP {
    int[] seg;
    int[] lazy;
    int n;
    LP(int n) {
        this.n=n;
        this.seg=new int[4*n];
        this.lazy=new int[4*n];
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
        int mid=low+(high-low)/2;
        int leftind=2*ind+1;
        int rightind=2*ind+2;
        propagate(ind,leftind,rightind,low,high,0);
        if(s<=low &&high<=e) return seg[ind];
        int a=query(leftind,low,mid,s,e);
        int b=query(rightind,mid+1,high,s,e);
        return push(a,b);
    }//return minimum of given range in array
    int push(int a,int b) {
        int ans=a+b;
        return ans;
    }
    void update(int ind,int low,int high,int s,int e,int val) {
        //no overlapping
        if(high<s ||e<low) return;
        int mid=low+(high-low)/2;
        int leftind=2*ind+1;
        int rightind=2*ind+2;
        propagate(ind,leftind,rightind,low,high,0);
        //complete overlapping
        if(s<=low &&high<=e) {
            propagate(ind,leftind,rightind,low,high,val);
            return;
        }
        update(leftind,low,mid,s,e,val);
        update(rightind,mid+1,high,s,e,val);
        seg[ind]=push(seg[rightind],seg[leftind]);
    }//update the array on given index
    int propagate(int ind,int leftind,int rightind,int low,int high,int val) {
        seg[ind]+=((high-low+1)*(lazy[ind]+val));
        if(low!=high) {
            lazy[leftind]+=(lazy[ind]+val);
            lazy[rightind]+=(lazy[ind]+val);
        }
        lazy[ind]=0;
        return seg[ind];
    }
}
