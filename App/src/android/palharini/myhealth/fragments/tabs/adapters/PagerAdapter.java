package android.palharini.myhealth.fragments.tabs.adapters;

import android.palharini.myhealth.fragments.tabs.MonthTab;
import android.palharini.myhealth.fragments.tabs.WeekTab;
import android.palharini.myhealth.fragments.tabs.YearTab;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
 
public class PagerAdapter extends FragmentPagerAdapter {
 
    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

	@Override
	public Fragment getItem(int index) {
		// TODO Auto-generated method stub
		switch (index) {
        case 0:
            // Top Rated fragment activity
            return new WeekTab();
        case 1:
            // Games fragment activity
            return new MonthTab();
        case 2:
            // Movies fragment activity
            return new YearTab();
        }
		
		return null;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 3;
	}
}
