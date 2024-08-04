package com.example.datastructure.Tree;

public class HuffumanNode implements Cloneable{
    private int key;//权重
    private char value;
    private HuffumanNode leftNode;
    private HuffumanNode rightNode;
    private HuffumanNode parent;//父节点

    public HuffumanNode(){};

    public HuffumanNode(int key, HuffumanNode leftNode, HuffumanNode rightNode, HuffumanNode parent) {
        this.key = key;
        this.leftNode = leftNode;
        this.rightNode = rightNode;
        this.parent = parent;

    }

    public HuffumanNode(int key, char value, HuffumanNode leftNode, HuffumanNode rightNode, HuffumanNode parent) {
        this.value = value;
        this.key = key;
        this.leftNode = leftNode;
        this.rightNode = rightNode;
        this.parent = parent;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public HuffumanNode getLeftNode() {
        return leftNode;
    }

    public void setLeftNode(HuffumanNode leftNode) {
        this.leftNode = leftNode;
    }

    public HuffumanNode getRightNode() {
        return rightNode;
    }

    public void setRightNode(HuffumanNode rightNode) {
        this.rightNode = rightNode;
    }

    public HuffumanNode getParent() {
        return parent;
    }

    public char getValue(){
        return this.value;
    }

    public void setValue(char value){
        this.value=value;
    }

    public void setParent(HuffumanNode parent) {
        this.parent = parent;
    }
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
