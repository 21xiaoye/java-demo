package com.example.datastructure.Tree;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * 最小堆：
 * */
public class MinHeap {
    private HuffumanNode[] HeapArray = new HuffumanNode[1];
    private int size;
    public MinHeap() {
        this.size=0;
    }

    public MinHeap(int[] array){
        insert(array);
    }
    public MinHeap(char[] array){
        insert(array);
    }
  /*  public <T> MinHeap(T[] array){
        insert(array);
    }*/
    private int parent(int positoin) {
         if(positoin == 0) return 0;
         return (positoin+1)/2-1;
    }
    private int leftChild(int position){
        return 2*position+1;
    }
    private int rightChild(int position){
        return 2*position+2;
    }
    /**
     * 判断节点位置是否为叶子节点
     * 完全二叉树叶子节点个数为size/2
    * */
    private boolean isLeaf(int position){
        return position >= (size / 2) && position <= size;
    }

    private void swap(int fpos, int spos){
        HuffumanNode temp=HeapArray[fpos];
        HeapArray[fpos]=HeapArray[spos];
        HeapArray[spos]=temp;
    }
    private void convertToMinHeap(int position){
        if(!isLeaf(position)){
            /*
            *(rightChild(position) < size)
            * 当根节点大于左节点时，因为堆是一个完全二叉树，所以只有当右节点不为空时才比较
            * rightChild(position) < size:
            * 因为从下标为0开始，所以右节点位置为（2*i)+2，(2*i)+2一定小于size,当(2*i)+2不小于size,及右节点为空：
            * 例如：根节点为1时，左为3，右为4,最多可以有5个元素，当右节点为null,size=4,(2*1)+2 >= size就可以得知，
            * 右节点为空；
            *
            * " 根节点编号为0"
                 (1)如果i>0,则序号为i的结点的双亲结点的序号是：（i-1）/2,如果i=0，则序号为i的结点是根结点，无双亲结点。
                （2）如果2i+1<n,则序号为i的结点的左孩子结点的序号是2i+1，2i+1>n,则序号为i的结点无左孩子。
                （3）如果2i+2<n,则序号为i的结点的右孩子结点的序号是2i+2，2i+2>n,则序号为i的结点无右孩子。

                "根节点编号为1"
                （1）如果2i<n,则序号为i的结点的左孩子结点的序号是2i，2i>n,则序号为i的结点无左孩子。
                （2）如果2i+1<n,则序号为i的结点的右孩子结点的序号是2i+1，2i+1>n,则序号为i的结点无右孩子。
            * */
            if((HeapArray[position].getKey() > HeapArray[leftChild(position)].getKey())){
                // 插入到节点小的一端，插入到左节点
                if(rightChild(position) <size && HeapArray[leftChild(position)].getKey() < HeapArray[rightChild(position)].getKey()){
                    swap(position,leftChild(position));
                    convertToMinHeap(leftChild(position));
                }else if(rightChild(position) >= size){ //没有右节点，直接和左节点交换
                    swap(position,leftChild(position));
                    convertToMinHeap(leftChild(position));
                }else{//右节点小于左节点，和右节点进行交换
                    swap(position,rightChild(position));
                    convertToMinHeap(rightChild(position));
                }
            }
            // 不小于左节点，小于右节点
            if((rightChild(position) < size) && (HeapArray[position].getKey() > HeapArray[rightChild(position)].getKey())){
                swap(position,rightChild(position));
                convertToMinHeap(position);
            }
        }
    }

    public void insert(int[] array){
        for (int num : array)
            insert(num);
    }
    public void insert(int element){
        if(size>=HeapArray.length) expansion();
        int current=size;
        HeapArray[size++]=new HuffumanNode(element,null,null,null);

        if(HeapArray[parent(current)]!=null){
            while(HeapArray[current].getKey() < HeapArray[parent(current)].getKey()){
                swap(current,parent(current));
                current = parent(current);
                if(HeapArray[parent(current)]==null) break;
            }
        }
    }

    public void insert(char[] array){
        for(int i=0; i<array.length; i++)
            insert(array[i],i+1);
    }
    public void insert(char element, int weights){
        if(size >= HeapArray.length) expansion();
        int current = size;
        HeapArray[size++] = new HuffumanNode(weights,element,null,null,null);

        if(HeapArray[parent(current)]!=null){
            while(HeapArray[current].getKey() < HeapArray[parent(current)].getKey()){
                swap(current,parent(current));
                current = parent(current);
                if(HeapArray[parent(current)]==null) break;
            }
        }
    }
    /**
     * 往堆中插入一个元素
     * */
    public void insert(HuffumanNode node){
        if(size>=HeapArray.length-1) expansion();
        int curent = size;
        HeapArray[size++] = node;

        if(HeapArray[parent(curent)]!=null){
            while (HeapArray[curent].getKey() < HeapArray[parent(curent)].getKey()){
                swap(curent,parent(curent));
                curent = parent(curent);
                if(HeapArray[parent(curent)]==null) break;
            }
        }
    }
    /**
     * 扩容
     * */
    private void expansion(){ HeapArray =  Arrays.copyOf(HeapArray, HeapArray.length+1); }
    /**
     * 删除堆中最后一个元素
     * */
    private void removeLastElement(){ HeapArray= Arrays.copyOf(HeapArray,HeapArray.length-1); }
    /**
     * 获取堆中最小元素
     * */
    public HuffumanNode getMinHeapMinElement(){ return HeapArray[0];}
    /**
     * 获取堆中最小元素并删除该元素
     * */
    public HuffumanNode getMinHepMinElementAndRemove(){
        if(HeapArray.length==0) throw new RuntimeException("array is empty");
        HuffumanNode huffumanTreeNode = HeapArray[0];
        swap(0,HeapArray.length-1);
        removeLastElement();
        this.size--;
        convertToMinHeap(0);
        return huffumanTreeNode;
    }
    /**
     * 获取堆中最小元素的值，不删除该元素
     * */
    public int getMinHeapMinNum(){ return HeapArray[0].getKey(); }
    /**
     * 获取堆中最小元素的值并删除该元素
    * */
    public int getMinHeapMinNumAndRemove(){
        if(HeapArray.length==0) throw new RuntimeException("array is empty");
        int number = HeapArray[0].getKey();
        swap(0,HeapArray.length-1);
        removeLastElement();
        this.size--;
        convertToMinHeap(0);
        return number;
    }
    public void print(){
        for (HuffumanNode huffumanNode : HeapArray){
            System.out.println(huffumanNode.getValue());
        }
    }
    /**
     * 最小堆排序
     * */
    public static List<Integer> sort(MinHeap minHeap){
        ArrayList<Integer> list = new ArrayList<>();
        int size = minHeap.size;
        while (size!=0){
            int minHeapMinNumAndRemove = minHeap.getMinHeapMinNumAndRemove();
            list.add(minHeapMinNumAndRemove);
            size--;
        }
        return list;
    }
}
