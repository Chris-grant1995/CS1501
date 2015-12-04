/*
Chris Grant

DLB.java
Created on 9/19/15
For Pitt's CS 1501 Project 1
2015 Fall Semester
Prof. Bill Garrison
 */
public class DLB
{
    private int LLCount;
    public boolean empty;
    protected LinkedList root;
    int size;
    final char sent ='&';
    private Node iterator;
    private LinkedList iteratorList;


    public DLB(String s)
    {
        LLCount = 0;
        empty=true;
        add(s);
        size = 1;
    }

    public DLB (char c)
    {
        empty=true;
        String s = new String(new Character(c).toString());
        add(s);
        size = 1;
    }

    public DLB()
    {
        empty=true;
        LLCount = 0;
        size = 0;
    }
    public DLB(StringBuilder s)
    {
        size = 1;
        LLCount = 0;
        empty=true;
        add(s.toString());
    }

    public int size() {
        return size;
    }




    public boolean add(String s)
    {
        size+=1;
        int pos=-1;
        if (empty==true)
        {
            LinkedList prevList = new LinkedList(s.charAt(0));
            root = prevList;
            for (int i = 1; i < s.length(); i++)
            {
                LinkedList curList = new LinkedList(s.charAt(i));
                prevList.head.childNode = curList.head;
                prevList = curList;
            }
            LinkedList sentinelList = new LinkedList(sent);
            prevList.head.childNode=sentinelList.head;
            empty=false;
            return true;
        }
        else
        {
            int letterFound = 0;
            int temp=0;
            Node curNode = root.head;
            for (int i = 0; i<=s.length()-1; i++)
            {

                if ((i==s.length()-1) && curNode.data==s.charAt(i))
                {

                    if (curNode.childNode.data!=sent)
                    {
                        curNode = curNode.childNode;
                        if (curNode.siblingNode==null)
                        {
                            curNode.siblingNode = new Node(sent);

                            return true;
                        }
                        else
                        {
                            Node prevNode=curNode;
                            while (curNode.siblingNode!=null)
                            {

                                if (curNode.data==sent)
                                {

                                    size--;
                                    return false;
                                }

                                curNode = curNode.siblingNode;
                            }
                            curNode.siblingNode=new Node(sent);
                            curNode=curNode.siblingNode;

                            return true;
                        }
                    }
                    else
                    {

                        size--;
                        return false;
                    }
                }
                if (curNode.data!=s.charAt(i))
                {
                    if (curNode.siblingNode!=null)
                    {
                        while (curNode.siblingNode!=null)
                        {

                            curNode = curNode.siblingNode;
                            if (curNode.data==s.charAt(i))
                            {

                                letterFound=1;
                                break;	//break out of whileloop
                            }

                        }
                        if (letterFound==0)
                        {

                            curNode.siblingNode = new Node(s.charAt(i));
                            curNode = curNode.siblingNode;
                            temp = i+1;
                            break;
                        }
                        else
                        {
                            letterFound=0;
                        }
                    }
                    else
                    {

                        curNode.siblingNode = new Node(s.charAt(i));
                        curNode=curNode.siblingNode;
                        temp = i+1;
                        break;
                    }
                }

                curNode = curNode.childNode;
            }

            if (temp>s.length()-1)
            {
                LinkedList endList = new LinkedList(sent);
                curNode.childNode=endList.head;
                return true;
            }
            else if (temp==s.length()-1)
            {
                LinkedList newList = new LinkedList(s.charAt(temp));
                curNode.childNode = newList.head;
                curNode = curNode.childNode;
                LinkedList endList = new LinkedList(sent);
                curNode.childNode=endList.head;
                return true;
            }
            else
            {

                for (int i = temp; i<=s.length()-1; i++) //for each remaining letter that needs built
                {
                    LinkedList newList = new LinkedList(s.charAt(i));
                    curNode.childNode=newList.head;
                    curNode=curNode.childNode;
                }
                LinkedList endList = new LinkedList(sent);
                curNode.childNode=endList.head;
                return true;
            }
        }
    }


    public int searchPrefix(String s) {
        return searchPrefix(new StringBuilder(s));
    }

    public int searchPrefix(StringBuilder s)
    {
        iteratorList = root;
        iterator = root.head;
        //System.out.println(iteratorNode.data);
        boolean isPrefix = false;
        boolean isWord = false;
        for (int i = 0; i<=s.length()-1; i++) //for loop iterates over entirety of word...once it exits simply analyze results
        {
            if ((i==s.length()-1) && (searchList(iteratorList, s.charAt(i))==true)) //if we're at the end of the word AND the letter is found
            {
                isPrefix=true;
                //System.out.println(s.charAt(i));
                //System.out.println(iteratorNode.childNode.data);
                iterator = iterator.childNode;
                iteratorList= iterator.thisList;
                int iteratorCount=0;
                while (iterator.siblingNode!=null)
                {
                    iteratorCount++;
                    //System.out.println("# of additional nodes in the list holding the sentinel of " + s + " = " + iteratorCount);
                    iterator = iterator.siblingNode;
                }
                if(findSentinel(iteratorList)==true)
                {
                    isWord=true;
                    if(iteratorCount<1)
                        isPrefix=false;
                    break;
                }
            }
            else if (searchList(iteratorList, s.charAt(i))==true) //else we're not at the end and we still need to iterate through the list
            {
                iterator = iterator.childNode;
                iteratorList = iterator.thisList;
            }
        }
        if (isWord==true && isPrefix==true)
        {
            return 3;
        }
        else if (isWord==true && isPrefix==false)
        {
            return 2;
        }
        else if (isWord==false && isPrefix==true)
        {
            return 1;
        }
        else
        {
            return 0;
        }
    }



    public boolean contains(String target) {
        boolean ret = false;
        LinkedList curList = root;
        Node iter = curList.head;
        int targetIndex = 0;
        while (iter != null) {
            if (iter.data == target.toCharArray()[targetIndex]) {
                if (targetIndex == target.length()-1 && iter.data == target.toCharArray()[target.length()-1] && isLastLetterInWord(iter)) {
                    ret = true;
                    break;
                }
                else if (targetIndex == target.length()-1 &&
                        iter.data == target.toCharArray()[target.length()-1]) {
                    break;
                }
                else {

                    iter = iter.childNode;
                    curList = iter.getCurrentList();
                    targetIndex++;
                }
            }
            else {
                iter = iter.siblingNode;
            }

        }
        return ret;
    }

    private boolean isLastLetterInWord(Node node) {
        Node iter = new Node(node.childNode);
        boolean ret = false;
        while (iter != null) {
            if (iter.data == sent) {
                ret = true;
                break;
            }
            else {
                iter = iter.siblingNode;
            }
        }
        return ret;
    }

    protected boolean findSentinel(LinkedList curList)
    {
        iteratorList = curList;
        iterator = curList.head;
        if (searchList(curList, sent)!=true)
        {
            return false;
        }
        else
        {
            return true;
        }

    }
    protected boolean searchList(LinkedList curList, char c)
    {
        if(iteratorList.head.data==c)
        {
            return true;
        }
        else
        {
            while (iterator !=null)
            {
                if(iterator.data==c)
                {
                    return true;
                }
                iterator = iterator.siblingNode;
            }
            return false;
        }
    }



}
	