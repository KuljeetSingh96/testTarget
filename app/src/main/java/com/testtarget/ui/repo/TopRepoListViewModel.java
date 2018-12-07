package com.testtarget.ui.repo;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.testtarget.network.model.TopWeekly;

import java.util.List;

/**
 * Created by kuljeetsingh on 12/6/18.
 */

public class TopRepoListViewModel extends ViewModel {
    public MutableLiveData<List<TopWeekly>> repoListData = new MutableLiveData<>();
    public MutableLiveData<Integer> errorMessageVisibility = new MutableLiveData<>();
    public MutableLiveData<Integer> loadingVisibility = new MutableLiveData<>();
    public MutableLiveData<Integer> repoListVisibility = new MutableLiveData<>();
    public MutableLiveData<String> errorMessage = new MutableLiveData<>();

}
