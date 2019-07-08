package com.demo.intandabs;

public class LinkedList<E> {
    // 头结点
    Node head = null;
    // 尾结点
    Node tail = null;

    public int index = 0;

    public void add(E e) {
        Node node = new Node(e);
        // 头为空，头尾都是自己
        if (head == null) {
            head = node;
            tail = node;
            index++;
        }
        // 连接尾结点并且把自己变成尾结点
        tail.next = node;
        tail = node;
        index++;
    }

    public int size() {
        return index;
    }
}
