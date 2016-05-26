package com.gaoshuai.ui.Utils;

/**
 * Created by yanfa on 2016/5/17.
 * 显示列表信息的名字以及其拼音首字母 GrouMemberBean.java
 */
public class GroupMemberBean {
    private String name;   //显示的数据
    private String sortLetters;  //显示数据拼音的首字母

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSortLetters() {
        return sortLetters;
    }

    public void setSortLetters(String sortLetters) {
        this.sortLetters = sortLetters;
    }
}
