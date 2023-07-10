package com.kavinoo.kavinoo;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.ColorRes;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.content.ContextCompat;
import androidx.transition.ChangeBounds;
import androidx.transition.TransitionManager;
import androidx.transition.TransitionSet;
import androidx.viewpager.widget.ViewPager.SimpleOnPageChangeListener;
import com.cuberto.flashytabbarandroid.R.anim;
import com.cuberto.flashytabbarandroid.R.id;
import com.cuberto.flashytabbarandroid.R.layout;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayout.Tab;
import java.util.ArrayList;
import java.util.List;
public class FlashyTabLayoutsecond extends SimpleOnPageChangeListener{


    private final List<String> mFragmentTitleList = new ArrayList();
    private final List<Integer> mFragmentIconList = new ArrayList();
    private final List<Integer> mFragmentColorList = new ArrayList();
    private final List<Float> mFragmentSizeList = new ArrayList();
    private TabLayout tabLayout;
    private int previousPosition = -1;

    public FlashyTabLayoutsecond(TabLayout tabLayout) {
        this.tabLayout = tabLayout;
    }

    public void addTabItem(String title, int tabIcon) {
        this.addTabItem(title, tabIcon, (Integer)null, (Float)null);
    }

    public void addTabItem(String title, int tabIcon, int color) {
        this.addTabItem(title, tabIcon, color, (Float)null);
    }

    public void addTabItem(String title, int tabIcon, float size) {
        this.addTabItem(title, tabIcon, (Integer)null, size);
    }

    public void addTabItem(String title, Integer tabIcon, Integer color, Float size) {
        this.mFragmentTitleList.add(title);
        this.mFragmentIconList.add(tabIcon);
        this.mFragmentColorList.add(color);
        this.mFragmentSizeList.add(size);
    }

    private void getTabView(int position, Tab tab, boolean isSelected) {
        View view = tab.getCustomView() == null ? LayoutInflater.from(this.tabLayout.getContext()).inflate(layout.custom_tab, (ViewGroup)null) : tab.getCustomView();
        if (tab.getCustomView() == null) {
            tab.setCustomView(view);
        }

        ImageView tabImageView = (ImageView)view.findViewById(id.tab_image);
        tabImageView.setImageResource((Integer)this.mFragmentIconList.get(position));
        tabImageView.setScaleX(1.3f);
        tabImageView.setScaleY(1.3f);
        ConstraintLayout layout = (ConstraintLayout)view.findViewById(id.root);
        ConstraintSet set = new ConstraintSet();
        ImageView foreground = (ImageView)view.findViewById(id.image_foreground);

        foreground.setScaleX(1.5f);
        foreground.setRotationX(50f);
        foreground.setScaleY(1.6f);

        ImageView textForeground = (ImageView)view.findViewById(id.text_foreground);
        ImageView dot = (ImageView)view.findViewById(id.dot);
        dot.setColorFilter(Color.argb(255, 17, 125, 179));
        TextView title = (TextView)view.findViewById(id.tab_title);
        title.setText((CharSequence)this.mFragmentTitleList.get(position));
        title.setTextColor(this.mFragmentColorList.get(position) == null ? Color.argb(255, 17, 125, 179) : this.getColor((Integer)this.mFragmentColorList.get(position)));
        if (this.mFragmentSizeList.get(position) != null) {
            title.setTextSize(0, (Float)this.mFragmentSizeList.get(position));
        }

        set.clone(layout);
        set.connect(textForeground.getId(), 3, isSelected ? title.getId() : tabImageView.getId(), 4);
        if (isSelected) {
            set.clear(tabImageView.getId(), 4);
            set.connect(title.getId(), 3, 0, 3);
            set.connect(title.getId(), 4, 0, 4);
            dot.startAnimation(AnimationUtils.loadAnimation(this.tabLayout.getContext(), anim.show));
        } else {
            set.connect(tabImageView.getId(), 4, 0, 4);
            set.clear(title.getId(), 4);
            set.connect(title.getId(), 3, 0, 4);
            if (position == this.previousPosition || this.previousPosition == -1) {
                dot.startAnimation(AnimationUtils.loadAnimation(this.tabLayout.getContext(), anim.hide));
            }
        }

        set.clear(foreground.getId(), isSelected ? 3 : 4);
        set.connect(foreground.getId(), isSelected ? 4 : 3, tabImageView.getId(), 4);
        set.applyTo(layout);
        tabImageView.setColorFilter(Color.argb(255, 62, 80, 100));
    }

    public void highLightTab(int position) {
        if (this.tabLayout != null) {
            TransitionManager.beginDelayedTransition(this.tabLayout, this.getTransitionSet());

            for(int i = 0; i < this.tabLayout.getTabCount(); ++i) {
                Tab tab = this.tabLayout.getTabAt(i);

                assert tab != null;

                this.getTabView(i, tab, i == position);
                LinearLayout layout = (LinearLayout)((LinearLayout)this.tabLayout.getChildAt(0)).getChildAt(i);
                layout.setBackground((Drawable)null);
                layout.setPaddingRelative(0, 0, 0, 0);
            }

            this.previousPosition = position;
        }

    }

    private TransitionSet getTransitionSet() {
        TransitionSet set = new TransitionSet();
        set.addTransition((new ChangeBounds()).setDuration(250L));
        set.setOrdering(0);
        return set;
    }

    public void onStart(TabLayout tabLayout) {
        this.tabLayout = tabLayout;
    }

    public void onStop() {
        this.tabLayout = null;
    }

    public void onPageSelected(int position) {
        this.highLightTab(position);
    }

    public int getColor(@ColorRes int colorRes) {
        return ContextCompat.getColor(this.tabLayout.getContext(), colorRes);
    }

}
