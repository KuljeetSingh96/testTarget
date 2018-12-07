package com.testtarget.network.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TopWeekly(
        var username: String?,
        var name: String?,
        var url: String?,
        var avatar: String?,
        var repo: RepoEntity?
) : Parcelable {
    constructor() : this("", "", "", "", null)

    @Parcelize
    data class RepoEntity(var name: String?,
                     var description: String?,
                     var url: String?) : Parcelable {
        constructor() : this("", "", "")
    }
}
