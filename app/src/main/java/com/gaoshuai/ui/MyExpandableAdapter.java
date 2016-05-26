package com.gaoshuai.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gaoshuai.ui.Utils.GroupMemberBean;

import java.util.List;

/**
 * Created by yanfa on 2016/5/17.
 * ExpandaleListView 适配器MyExpandableAdapter.java
 */
public class MyExpandableAdapter extends BaseExpandableListAdapter {

    private Context mContext;
    private List<GroupMemberBean> mGroup;
    private List<List<GroupMemberBean>> mChild;
    private LayoutInflater mInflater;

    public MyExpandableAdapter(Context mContext, List<GroupMemberBean> mGroup, List<List<GroupMemberBean>> mChild) {
        this.mContext = mContext;
        this.mGroup = mGroup;
        this.mChild = mChild;
        this.mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    /**
     * 当ListView数据发生变化时,调用此方法来更新ListView
     *
     * @param
     */
    public void updateListView(List<GroupMemberBean> mGroup, List<List<GroupMemberBean>> mChild) {
        this.mGroup = mGroup;
        this.mChild = mChild;
        notifyDataSetChanged();
    }

    @Override
    public int getGroupCount() {
        // TODO Auto-generated method stub
        return mGroup.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        // TODO Auto-generated method stub
        return mChild.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        // TODO Auto-generated method stub
        return mGroup.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        // TODO Auto-generated method stub
        return mChild.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        // TODO Auto-generated method stub
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        // TODO Auto-generated method stub
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        GroupHolderView groupHolderView;
        if (convertView == null) {
            groupHolderView = new GroupHolderView();
            convertView = (View) mInflater.inflate(R.layout.activity_group, null);
            groupHolderView.groupTv = (TextView) convertView.findViewById(R.id.my_group_tv);
            convertView.setTag(groupHolderView);
        } else {
            groupHolderView = (GroupHolderView) convertView.getTag();
        }
        groupHolderView.groupTv.setText(mGroup.get(groupPosition).getName());
        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        ChildHolderView childHolderView;
        if (convertView == null) {
            childHolderView = new ChildHolderView();
            convertView = (View) mInflater.inflate(R.layout.activity_child, null);
            childHolderView.childIv = (ImageView) convertView.findViewById(R.id.my_child_iv);
            childHolderView.childTv = (TextView) convertView.findViewById(R.id.my_child_tv);
            childHolderView.childLl = (LinearLayout) convertView.findViewById(R.id.my_child_ll);
            convertView.setTag(childHolderView);
        } else {
            childHolderView = (ChildHolderView) convertView.getTag();
        }
        childHolderView.childTv.setText(mChild.get(groupPosition).get(childPosition).getName());
        childHolderView.childLl.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Toast.makeText(mContext, "group:" + mGroup.get(groupPosition).getName() + "->child:" + mChild.get(groupPosition).get(childPosition).getName(), Toast.LENGTH_SHORT).show();
            }
        });

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        // TODO Auto-generated method stub
        return false;
    }

    class GroupHolderView {
        TextView groupTv;
    }

    class ChildHolderView {
        ImageView childIv;
        TextView childTv;
        LinearLayout childLl;
    }
}