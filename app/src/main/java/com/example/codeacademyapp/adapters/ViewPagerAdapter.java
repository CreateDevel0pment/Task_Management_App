package com.example.codeacademyapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.codeacademyapp.R;


public class ViewPagerAdapter extends PagerAdapter {


    private int[] landing_images;
    private Context context;

    public ViewPagerAdapter(Context context, int[] landing_slika) {
        this.landing_images = landing_slika;
        this.context = context;
    }

    @Override
    public int getCount() {
        return landing_images.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = layoutInflater.inflate(R.layout.landing_img_item, container, false);

        ImageView landing_img = itemView.findViewById(R.id.landing_img);
        landing_img.setImageResource(landing_images[position]);
        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout) object);
    }
}
