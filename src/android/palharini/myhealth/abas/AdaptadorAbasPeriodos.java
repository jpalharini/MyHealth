package android.palharini.myhealth.abas;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
 
public class AdaptadorAbasPeriodos extends FragmentPagerAdapter {
 
    public AdaptadorAbasPeriodos(FragmentManager fm) {
        super(fm);
    }

	@Override
	public Fragment getItem(int index) {
		// TODO Auto-generated method stub
		switch (index) {
        case 0:
            // Top Rated fragment activity
            return new AbaSemana();
        case 1:
            // Games fragment activity
            return new AbaMes();
        case 2:
            // Movies fragment activity
            return new AbaAno();
        }
		
		return null;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 3;
	}
}
