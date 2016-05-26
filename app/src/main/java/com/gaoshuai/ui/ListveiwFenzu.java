package com.gaoshuai.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.gaoshuai.ui.Utils.CharacterParser;
import com.gaoshuai.ui.Utils.ClearEditText;
import com.gaoshuai.ui.Utils.GroupMemberBean;
import com.gaoshuai.ui.Utils.PinyinComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListveiwFenzu extends AppCompatActivity {

    private Button myBtn;
    private ExpandableListView myExpandLv;
    private ClearEditText myClearEt;
    private TextView noSearchResultTv;
    private MyExpandableAdapter myAdapter;

    private List<GroupMemberBean> groupBeanList;
    private List<List<GroupMemberBean>> childBeanList = new ArrayList<>();
    /**
     * 汉字转换成拼音的类
     */
    private CharacterParser characterParser;
    /**
     * 根据拼音来排列ListView里面的数据类
     */
    private PinyinComparator pinyinComparator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listveiw_fenzu);

        init();
    }

    private void init() {
        noSearchResultTv = (TextView) this.findViewById(R.id.no_search_result_tv);
        myExpandLv = (ExpandableListView) this.findViewById(R.id.my_expand_lv);

        // 实例化汉字转拼音类
        characterParser = CharacterParser.getInstance();

        pinyinComparator = new PinyinComparator();

        String [] group = {"软件","安卓","Ios","行政","人事"};
        String [] child = {"gaoshuai","高帅","Ios","杨建坤","0000","a李嘉俊"};
        String [] child2 = {"小名","pipi","树立","444","你好","c李嘉俊"};

        groupBeanList = filledData(group);
        List<GroupMemberBean> tempOne = filledData(child);
        List<GroupMemberBean> tempTwo = filledData(child2);


        // 根据a-z进行排序源数据
        Collections.sort(groupBeanList, pinyinComparator);

        Collections.sort(tempOne, pinyinComparator);
        Collections.sort(tempTwo, pinyinComparator);



        //用于生成列表信息
        myBtn = (Button) this.findViewById(R.id.my_btn);
        myBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                myAdapter = new MyExpandableAdapter(ListveiwFenzu.this,
                        groupBeanList, childBeanList);
                myExpandLv.setAdapter(myAdapter);
                myExpandLv.expandGroup(0);
            }

        });

        myClearEt = (ClearEditText) this.findViewById(R.id.filter_edit);

        myClearEt.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                // TODO Auto-generated method stub
                filterData(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub

            }
        });
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//        if (id == R.id.action_settings) {
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }

    /**
     * 为ListView填充数据
     *
     * @param date
     * @return
     */
    private List<GroupMemberBean> filledData(String[] date) {
        List<GroupMemberBean> mSortList = new ArrayList<GroupMemberBean>();

        for (int i = 0; i < date.length; i++) {
            GroupMemberBean sortModel = new GroupMemberBean();
            sortModel.setName(date[i]);
            // 汉字转换成拼音
            String pinyin = characterParser.getSelling(date[i]);
            String sortString = pinyin.substring(0, 1).toUpperCase();

            // 正则表达式，判断首字母是否是英文字母
            if (sortString.matches("[A-Z]")) {
                sortModel.setSortLetters(sortString.toUpperCase());
            } else {
                sortModel.setSortLetters("#");
            }

            mSortList.add(sortModel);
        }
        return mSortList;

    }

    /**
     * 根据输入框中的值来过滤数据并更新ListView
     *
     * @param filterStr
     */
    private void filterData(String filterStr) {
        List<GroupMemberBean> groupFilterList = new ArrayList<GroupMemberBean>();
        List<GroupMemberBean> tempFilterList;
        List<List<GroupMemberBean>> childFilterList = new ArrayList<List<GroupMemberBean>>();

        if (TextUtils.isEmpty(filterStr)) {
            groupFilterList = groupBeanList;
            childFilterList = childBeanList;
            noSearchResultTv.setVisibility(View.GONE);
        } else {
            groupFilterList.clear();
            childFilterList.clear();
            for (int i = 0; i < groupBeanList.size(); i++) {
                //标记departGroup是否加入元素
                boolean isAddGroup = false;
                tempFilterList = new ArrayList<GroupMemberBean>();
                GroupMemberBean sortModel = groupBeanList.get(i);
                String name = sortModel.getName();
                // depart有字符直接加入
                if (name.indexOf(filterStr.toString()) != -1
                        || characterParser.getSelling(name).startsWith(
                        filterStr.toString())) {
                    if (!groupFilterList.contains(sortModel)) {
                        groupFilterList.add(sortModel);
                        isAddGroup = true;
                    }
                }

                for (int j = 0; j < childBeanList.get(i).size(); j++) {
                    GroupMemberBean sortChildModel = childBeanList.get(i)
                            .get(j);
                    String childName = sortChildModel.getName();
                    // child有字符直接加入，其父也加入
                    if (childName.indexOf(filterStr.toString()) != -1
                            || characterParser.getSelling(childName)
                            .startsWith(filterStr.toString())) {
                        tempFilterList.add(sortChildModel);
                        if (!groupFilterList.contains(groupBeanList.get(i))) {
                            groupFilterList.add(groupBeanList.get(i));
                            isAddGroup = true;
                        }
                    }

                }
                Collections.sort(tempFilterList, pinyinComparator);
                if (isAddGroup) {
                    childFilterList.add(tempFilterList);
                }
            }

            // 根据a-z进行排序
            Collections.sort(groupBeanList, pinyinComparator);
        }

        if (myAdapter != null) {
            myAdapter.updateListView(groupFilterList, childFilterList);

            if (TextUtils.isEmpty(filterStr)) {
                for (int i = 0; i < groupFilterList.size(); i++) {
                    if (i == 0) {
                        myExpandLv.expandGroup(i);
                        continue;
                    }
                    myExpandLv.collapseGroup(i);
                }
            } else {
                //搜索的结果全部展开
                for (int i = 0; i < groupFilterList.size(); i++) {
                    myExpandLv.expandGroup(i);
                }
            }
        }

        //如果查询的结果为0时，显示为搜索到结果的提示
        if (groupFilterList.size() == 0) {
            noSearchResultTv.setVisibility(View.VISIBLE);
        } else {
            noSearchResultTv.setVisibility(View.GONE);
        }
    }

}
