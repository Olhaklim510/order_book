package com.company;

import java.util.Iterator;
import java.util.TreeSet;

public class Test {
    public static void main(String[] args) {

        TreeSet<Integer> set = new TreeSet<>();
        set.add(1);
        set.add(6);
        set.add(7);
        set.add(5);
        set.add(2);
        set.add(4);
        set.add(0);
        set.add(8);
        System.out.println(set);

        Iterator<Integer> iterator = set.descendingIterator();

        while (iterator.hasNext())
            System.out.println(iterator.next());
    }
}
