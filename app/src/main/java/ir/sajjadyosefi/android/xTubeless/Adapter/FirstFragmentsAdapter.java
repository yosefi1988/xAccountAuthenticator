package ir.sajjadyosefi.android.xTubeless.Adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;
import ir.sajjadyosefi.android.xTubeless.Fragment.tab1Fragment;
import ir.sajjadyosefi.android.xTubeless.Fragment.tab2Fragment;
import ir.sajjadyosefi.android.xTubeless.Fragment.tab3Fragment;

/**
 * Created by sajjad on 10/18/2016.
 */
public class FirstFragmentsAdapter extends FragmentStatePagerAdapter  {
    Context context;

    int PAGE_COUNT;
    private String mTabTitles[] = new String[] {
            "\uE802",
            "\uE801",
            "\uE804",
            "\uE800",
            "\uE803"};

    public FirstFragmentsAdapter(Context context,final AppCompatActivity activity, int count) {
        super(activity.getSupportFragmentManager());
        this.context = context;
        setCount();
    }

    private void setCount() {
        PAGE_COUNT = 3;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }



    Fragment fragmentx1;
    Fragment fragmentx2;
    Fragment fragmentx3;

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                fragmentx1 = (new tab1Fragment()).newInstance(context);
                return fragmentx1;
            case 1:
                fragmentx2 = (new tab2Fragment()).newInstance(context);
                return fragmentx2;
            case 2:
                fragmentx3 = (new tab3Fragment()).newInstance(context);
                return fragmentx3;
        }
        return null;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate text based on item position
        return mTabTitles[position];
    }
}