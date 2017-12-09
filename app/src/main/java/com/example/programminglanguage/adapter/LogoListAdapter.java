package com.example.programminglanguage.adapter;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.programminglanguage.R;
import com.example.programminglanguage.model.LogoItem;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by GUNNER on 9/12/2560.
 */

public class LogoListAdapter extends ArrayAdapter<LogoItem> {
    private Context mContext;
    private int mLayoutResId;
    private ArrayList<LogoItem> mPhoneItemList;

    public LogoListAdapter(@NonNull Context context, int layoutResId, @NonNull ArrayList<LogoItem> phoneItemList) {
        super(context, layoutResId, phoneItemList);

        this.mContext = context;
        this.mLayoutResId = layoutResId;
        this.mPhoneItemList = phoneItemList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View itemLayout = inflater.inflate(mLayoutResId, null);

        LogoItem item = mPhoneItemList.get(position);

        ImageView logoImageView = itemLayout.findViewById(R.id.imageView_logo);
        TextView logoTitleTextView = itemLayout.findViewById(R.id.textView_logo);

        logoTitleTextView.setText(item.title);

        String pictureFileName = item.picture;

        AssetManager am = mContext.getAssets();
        try {
            InputStream stream = am.open(pictureFileName);
            Drawable drawable = Drawable.createFromStream(stream, null);
            logoImageView.setImageDrawable(drawable);

        } catch (IOException e) {
            e.printStackTrace();

            File pictureFile = new File(mContext.getFilesDir(), pictureFileName);
            Drawable drawable = Drawable.createFromPath(pictureFile.getAbsolutePath());
            logoImageView.setImageDrawable(drawable);
        }

        return itemLayout;
    }
}
