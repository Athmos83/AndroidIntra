package com.epitech.intra.intranetepitech;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by dedick on 04/02/2015.
 */

public class PlanningAdapter extends ArrayAdapter<BeanPlanning> {
    private List<BeanPlanning> contracts;

    public PlanningAdapter(Context context, int view, List<BeanPlanning> passedContracts) {
        super(context, view, passedContracts);
        contracts = passedContracts;
    }

    @Override
    public int getCount() {
        return contracts.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View currentView = convertView;
        LayoutInflater currentViewInflater = (LayoutInflater)getContext().getSystemService(getContext().LAYOUT_INFLATER_SERVICE);
        currentView = currentViewInflater.inflate(R.layout.display_module, null);
        BeanPlanning currentContract = contracts.get(position);
        TextView date = (TextView) currentView.findViewById(R.id.plan_date);
        TextView title = (TextView) currentView.findViewById(R.id.plan_titre);
        TextView duree = (TextView) currentView.findViewById(R.id.plan_horaire);
        date.setText(currentContract.get_date());
        title.setText(currentContract.get_titleModule());
        duree.setText(currentContract.get_time());
        return currentView;
    }
}

