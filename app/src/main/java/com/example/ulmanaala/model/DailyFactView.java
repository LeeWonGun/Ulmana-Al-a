package com.example.ulmanaala.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.ulmanaala.response.DailyFactItem;

import java.util.List;

public class DailyFactView extends ViewModel {
    private final MutableLiveData<List<DailyFactItem>> factList = new MutableLiveData<>();

    public LiveData<List<DailyFactItem>> getFactList() {
        return factList;
    }

    public void setFactList(List<DailyFactItem> facts) {
        factList.setValue(facts);
    }

    public boolean hasData() {
        return factList.getValue() != null && !factList.getValue().isEmpty();
    }
}
