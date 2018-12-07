package com.testtarget.ui.repodetail;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

public class RepoDetailViewModel extends ViewModel{
    public String avatar;
    public MutableLiveData<String> repoDescription = new MutableLiveData<>();
}
