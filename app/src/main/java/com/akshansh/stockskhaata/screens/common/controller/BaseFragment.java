package com.akshansh.stockskhaata.screens.common.controller;

import androidx.fragment.app.Fragment;

import com.akshansh.stockskhaata.common.dependencyinjection.controller.ControllerComponent;


public class BaseFragment extends Fragment {
    private ControllerComponent injector;

    protected ControllerComponent getInjector(){
        if(injector == null){
            injector = ((BaseActivity)requireActivity()).getActivityComponent()
                    .newControllerComponent();
        }
        return injector;
    }
}
