package com.tsystems.javaschool.tasks.subsequence;

import java.rmi.UnexpectedException;
import java.util.List;

public class Subsequence {

    /**
     * Checks if it is possible to get a sequence which is equal to the first
     * one by removing some elements from the second one.
     *
     * @param x first sequence
     * @param y second sequence
     * @return <code>true</code> if possible, otherwise <code>false</code>
     */
    @SuppressWarnings("rawtypes")
    public boolean find(List x, List y) {
        // TODO: Implement the logic here
        if (x==null || y==null){
            throw new IllegalArgumentException();
        }
        int count = 0;
        int marker = 0;
        for (int i = 0; i < x.size(); i++) {
            if (count < i) {
                break;
            } else {
                for (int j = marker; j < y.size(); j++) {
                    if (x.get(i).equals(y.get(j))) {
                        count++;
                        marker = j++;
                        break; 
                    }
                }
            }
        }
        return x.size() == count ? true : false;

    }
}
