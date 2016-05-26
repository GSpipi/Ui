package com.gaoshuai.ui.Utils;

import java.util.Comparator;

/**
 * Created by yanfa on 2016/5/17.
 * 拼音比较类：PinyinComparator.java
 */
public class PinyinComparator implements Comparator<GroupMemberBean> {

    public int compare(GroupMemberBean o1, GroupMemberBean o2) {
        if (o1.getSortLetters().equals("@")
                || o2.getSortLetters().equals("#")) {
            return -1;
        } else if (o1.getSortLetters().equals("#")
                || o2.getSortLetters().equals("@")) {
            return 1;
        } else {
            return o1.getSortLetters().compareTo(o2.getSortLetters());
        }
    }

}
