package com.testtarget.databinding;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.text.TextUtils;
import android.widget.ImageView;
import com.squareup.picasso.Picasso;
import com.testtarget.utils.CircleTransformPurple;

public class BindingAdapters {

    @BindingAdapter({"profileAvatarUrl"})
    public static void setprofileAvatarUrl(ImageView imageView, String profileAvatarUrl) {
        Context context = imageView.getContext();
        if (!TextUtils.isEmpty(profileAvatarUrl)) {
            // TODO: Determine best method for loading this image
            Picasso.with(context)
                    .load(profileAvatarUrl)
                    .transform(new CircleTransformPurple())
                    .centerCrop()
                    .fit()
                    .into(imageView);
        }
    }

}
