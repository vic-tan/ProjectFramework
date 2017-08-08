package com.ytd.framework.equipment.ui.fragment;


import android.support.v4.app.Fragment;
import android.widget.TextView;

import com.ytd.framework.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;


/**
 * A simple {@link Fragment} subclass.
 */
@EFragment(R.layout.add_equipment_fragment)
public class AddEquipmentFragment extends Fragment {
    public static final String TAG = AddEquipmentFragment.class.getSimpleName();


    @ViewById
    TextView name;

    @AfterViews
    void init() {

    }


}
