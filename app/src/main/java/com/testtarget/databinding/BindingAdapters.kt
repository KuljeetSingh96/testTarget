package com.testtarget.databinding

import android.databinding.BindingAdapter
import android.text.TextUtils
import android.widget.ImageView
import com.squareup.picasso.Picasso
import com.testtarget.utils.CircleTransformPurple

object BindingAdapters {

    @BindingAdapter("profileAvatarUrl")
    fun setProfileAvatarUrl(imageView: ImageView, profileAvatarUrl: String) {
        val context = imageView.context
        if (!TextUtils.isEmpty(profileAvatarUrl)) {
            // TODO: Determine best method for loading this image
            Picasso.with(context)
                    .load(profileAvatarUrl)
                    .transform(CircleTransformPurple())
                    .centerCrop()
                    .fit()
                    .into(imageView)
        }
    }

}
