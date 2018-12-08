package com.testtarget.ui.repo

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel

import com.testtarget.network.model.TopWeekly

/**
 * Created by kuljeetsingh on 12/6/18.
 */

class TopRepoListViewModel : ViewModel() {
    var repoListData = MutableLiveData<List<TopWeekly>>()
    var errorMessageVisibility = MutableLiveData<Int>()
    var loadingVisibility = MutableLiveData<Int>()
    var repoListVisibility = MutableLiveData<Int>()
    var errorMessage = MutableLiveData<String>()
}
