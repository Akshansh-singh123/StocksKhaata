package com.akshansh.stockskhaata.screens.common.dialogs;

import com.akshansh.stockskhaata.common.dependencyinjection.controller.ControllerComponent;
import com.akshansh.stockskhaata.screens.common.controller.BaseActivity;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class BaseBottomSheetDialog extends BottomSheetDialogFragment {
    private ControllerComponent injector;

    protected ControllerComponent getInjector(){
        if(injector == null){
            injector = ((BaseActivity)requireActivity()).getActivityComponent()
                    .newControllerComponent();
        }
        return injector;
    }
}
