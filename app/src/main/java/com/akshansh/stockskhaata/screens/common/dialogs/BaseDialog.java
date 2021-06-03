package com.akshansh.stockskhaata.screens.common.dialogs;

import androidx.fragment.app.DialogFragment;

import com.akshansh.stockskhaata.common.dependencyinjection.controller.ControllerComponent;
import com.akshansh.stockskhaata.screens.common.controller.BaseActivity;

public class BaseDialog extends DialogFragment {
    private ControllerComponent injector;

    protected ControllerComponent getInjector(){
        if(injector == null){
            injector = ((BaseActivity)requireActivity()).getActivityComponent()
                    .newControllerComponent();
        }
        return injector;
    }
}
