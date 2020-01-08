package com.practise;

public class MyLinkedList {
    public Node head;
    public Node tail;

    public MyLinkedList() {
        head = new Node(0);
        tail = head;
    }

    public void addNode(int i) {
        Node a = new Node(i);
        tail.next = a;
        tail = a;
    }

    public int size() {
        int count = 0;
        Node flag = head;
        while (flag.next != null) {
            count++;
            flag = flag.next;
        }
        return count;
    }


    public static void main(String[] args) {
        MyLinkedList myLinkList = new MyLinkedList();
        System.out.println(myLinkList.head);
        System.out.println(myLinkList.tail.data);
        System.out.println(myLinkList.size());
        myLinkList.addNode(5);
        System.out.println(myLinkList.head);
        System.out.println(myLinkList.tail.data);
        System.out.println(myLinkList.size());
    }
}
class Node{
    int data;
    Node next;
    public Node(int data){
        this.data = data;
        this.next = null;
    }
}

