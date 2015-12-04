/*
Chris Grant

LinkedList.java
Created on 9/19/15
For Pitt's CS 1501 Project 1
2015 Fall Semester
Prof. Bill Garrison
 */
public class LinkedList
{
    public Node head;
    public int i;
    public LinkedList thisList = this;
    public int size;


    public LinkedList(char c)
    {
        Node newNode = new Node(c);
        head = newNode;
        newNode.thisList=this;
        size++;
    }

    public boolean contains(char c) {
        boolean ret = false;
        Node iter = head;
        while (iter!=null) {
            if (iter.getData()==c) {
                ret = true;
                break;
            }
            else {
                iter = iter.siblingNode;
            }
        }
        return ret;
    }

}