package com.skinexam.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.skinexam.myapplication.R;
import com.skinexam.myapplication.model.PatientState;

import java.util.List;

/**
 * Created by webwerks1 on 25/4/15.
 */
public class PatientStatusAdapter extends ArrayAdapter<PatientState> implements CompoundButton.OnCheckedChangeListener
{
    Context context;
    List<PatientState> stateList = null;
    List<PatientState> selectedList = null;

    public PatientStatusAdapter(Context context, List<PatientState> stateList, List<PatientState> selectedList) {
        super(context, 0, stateList);
        this.context = context;
        this.stateList = stateList;
        this.selectedList=selectedList;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.row_list, parent, false);
        CheckBox cbStatus=(CheckBox)convertView.findViewById(R.id.chkItem);
        cbStatus.setText(stateList.get(position).getStatus());
        cbStatus.setOnCheckedChangeListener(this);
        cbStatus.setTag(getItem(position));

        if(selectedList.size()>0){
           String status=((PatientState)cbStatus.getTag()).getStatus();
            for(PatientState state:selectedList){
                if(state.getStatus().equalsIgnoreCase(status)){
                    cbStatus.setChecked(true);
                    getItem(position).setSelected(true);
                }
            }
        }
        return convertView;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(isChecked)
            ((PatientState)buttonView.getTag()).setSelected(true);
        else
            ((PatientState)buttonView.getTag()).setSelected(false);
    }
}
