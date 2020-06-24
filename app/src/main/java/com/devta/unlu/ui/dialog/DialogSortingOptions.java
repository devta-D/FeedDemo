package com.devta.unlu.ui.dialog;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.devta.unlu.R;
import com.devta.unlu.databinding.DialogSortingOptionBinding;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

/**
 *  Created on : Jun, 25, 2020 at 00:54
 * Author     : Divyanshu Tayal
 * Name       : devta-D
 * GitHub     : https://github.com/devta-D/
 * LinkedIn   : https://www.linkedin.com/in/divyanshu-tayal-4a95b2aa/
 */

public class DialogSortingOptions extends BottomSheetDialogFragment
    implements RadioGroup.OnCheckedChangeListener {

    private Callbacks callbacks;
    private int selectedSortOption;
    private DialogSortingOptionBinding binding;

    public static DialogSortingOptions newInstance(Callbacks callbacks,
                                                   int selectedSortOption) {
        DialogSortingOptions dialogSortingOptions = new DialogSortingOptions();
        dialogSortingOptions.setCallbacks(callbacks);
        dialogSortingOptions.setSelectedSortOption(selectedSortOption);
        return dialogSortingOptions;
    }

    private void setSelectedSortOption(int selectedSortOption) {
        if(selectedSortOption < 0)
            selectedSortOption = 0;
        this.selectedSortOption = selectedSortOption;
    }

    private void setCallbacks(Callbacks callbacks) {
        this.callbacks = callbacks;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_sorting_option,
                container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(getDialog() != null) {
            BottomSheetBehavior behavior = ((BottomSheetDialog) getDialog()).getBehavior();
            behavior.setHideable(true);
            int orientation = getResources().getConfiguration().orientation;
            if(orientation == Configuration.ORIENTATION_LANDSCAPE) {
                behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                ((BottomSheetDialog) getDialog()).getBehavior().addBottomSheetCallback(callback);
            }
        }
        RadioButton radioButton = (RadioButton) binding.radioGroup.getChildAt(selectedSortOption);
        radioButton.setChecked(true);
        binding.radioGroup.setOnCheckedChangeListener(this);
    }

    private BottomSheetBehavior.BottomSheetCallback callback = new BottomSheetBehavior.BottomSheetCallback() {
        @Override
        public void onStateChanged(@NonNull View bottomSheet, int newState) {
            if(newState == BottomSheetBehavior.STATE_COLLAPSED && getDialog() != null) {
                ((BottomSheetDialog) getDialog()).getBehavior().setState(BottomSheetBehavior.STATE_HIDDEN);
            }
        }

        @Override
        public void onSlide(@NonNull View bottomSheet, float slideOffset) {

        }
    };

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int id) {
        if(id == R.id.radio_newest) {
            if(callbacks != null) callbacks.sortByNewest(0);
        }else if(id == R.id.radio_oldest) {
            if(callbacks != null) callbacks.sortByOldest(1);
        }else if(id == R.id.radio_views_high_to_low) {
            if(callbacks != null) callbacks.sortByViewsHighToLow(2);
        }else if(id == R.id.radio_views_low_to_high) {
            if(callbacks != null) callbacks.sortByViewsLowToHigh(3);
        }else if(id == R.id.radio_likes_high_to_low) {
            if(callbacks != null) callbacks.sortByLikesHighToLow(4);
        }else if(id == R.id.radio_likes_low_to_high) {
            if(callbacks != null) callbacks.sortByLikesLowToHigh(5);
        }else if(id == R.id.radio_shares_high_to_low) {
            if(callbacks != null) callbacks.sortBySharesHighToLow(6);
        }else if(id == R.id.radio_shares_low_to_high) {
            if(callbacks != null) callbacks.sortBySharesLowToHigh(7);
        }
        dismiss();
    }

    public interface Callbacks {
        //date
        void sortByNewest(int optionPosition);
        void sortByOldest(int optionPosition);

        //views
        void sortByViewsHighToLow(int optionPosition);
        void sortByViewsLowToHigh(int optionPosition);

        //likes
        void sortByLikesHighToLow(int optionPosition);
        void sortByLikesLowToHigh(int optionPosition);

        //shares
        void sortBySharesHighToLow(int optionPosition);
        void sortBySharesLowToHigh(int optionPosition);
    }

}
