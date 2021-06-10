package com.akshansh.stockskhaata.screens.common.toolbar;

import com.akshansh.stockskhaata.screens.common.views.ObservableViewMvc;

public interface ToolbarViewMvc extends ObservableViewMvc<ToolbarViewMvc.Listener> {
    interface Listener{
        void OnAddOptionItemSelected();
        void OnFilterOptionItemSelected();
        void OnSearchEventStart();
        void OnSearchEventClosed();
        void OnSearchTextChanged(String s);
    }
    void setTitle(String title);
    void clearBinding();
    void enableAddButton();
    void enableFilterButton();
    void enableSearchButton();
    void disableAddButton();
    void disableSearchButton();
    void disableFilterButton();
}
