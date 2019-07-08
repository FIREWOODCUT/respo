package com.demo.intandabs;

public class ArrayList<E> {

    // index记录个数
    public int index = 0;

    /**
     * 新增元素
     *
     * @param element
     */
    public void add(E element) {
        // 初始化长度为10
        Object[] o = new Object[10];
        if (index == o.length) {
            // 空间不足扩容
            Object[] newob = new Object[o.length + o.length];
            // cocy
            System.arraycopy(o, 0, newob, 0, o.length);
            o = newob;
        }
        o[index] = element;
        index++;
    }

    /**
     * 返回size
     *
     * @return
     */
    public int size() {
        return index;
    }
}
