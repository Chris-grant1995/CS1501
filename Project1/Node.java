/*
Chris Grant

Node.java
Created on 9/19/15
For Pitt's CS 1501 Project 1
2015 Fall Semester
Prof. Bill Garrison
 */

public class Node
{
    
    public Node siblingNode=null; 
    public Node childNode=null;
    public char data; 
    public LinkedList thisList;

    
    public Node(char c) 
    {
        data = c;
    }
    public Node(Node copy) {
        siblingNode = copy.siblingNode;
        childNode = copy.childNode;
        data = copy.data;
        thisList = copy.thisList;
    }

    public LinkedList getCurrentList() {
        return thisList;
    }
    public char getData()
    {
        return data;
    }
    public void setData(char c)
    {
        data = c;
    }
}