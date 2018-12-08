package com.testtarget.ui.repodetail

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel

class RepoDetailViewModel : ViewModel() {
    var avatar: String? = null
    var repoDescription = MutableLiveData<String>()
}
