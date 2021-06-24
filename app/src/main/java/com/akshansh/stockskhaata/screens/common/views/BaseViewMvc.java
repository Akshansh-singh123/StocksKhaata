package com.akshansh.stockskhaata.screens.common.views;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public abstract class BaseViewMvc implements ViewMvc {
    private View rootView;

    @Override
    public View getRootView() {
        return rootView;
    }

    public void setRootView(View rootView) {
        this.rootView = rootView;
    }

    protected Context getContext(){
        return rootView.getContext();
    }

    protected String getString(int id){
        return getContext().getString(id);
    }

    protected InputMethodManager getKeyboardManager(){
        return (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    protected void showKeyboard(View view){
        if(view.requestFocus()){
            getKeyboardManager().showSoftInput(view,InputMethodManager.SHOW_IMPLICIT);
        }
    }

    protected void hideKeyboard(View view){
        getKeyboardManager().hideSoftInputFromWindow(view.getWindowToken(),0);
    }

    protected <T extends View> T findViewById(int id){
        return rootView.findViewById(id);
    }
}
