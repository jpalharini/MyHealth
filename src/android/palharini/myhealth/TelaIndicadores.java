package android.palharini.myhealth;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.palharini.myhealth.abas.AdaptadorAbasPeriodos;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

public class TelaIndicadores extends FragmentActivity implements ActionBar.TabListener {
		
	private ViewPager viewPager;
	private ActionBar actionBar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tela_indicadores);

		viewPager = (ViewPager) findViewById(R.id.pager);
		actionBar = getActionBar();
		final AdaptadorAbasPeriodos adapter = new AdaptadorAbasPeriodos(getSupportFragmentManager());
		
		viewPager.setAdapter(adapter);
		actionBar.setHomeButtonEnabled(false);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
	 
        actionBar.addTab(actionBar.newTab().setText(R.string.abaSemana).setTabListener(this));
        actionBar.addTab(actionBar.newTab().setText(R.string.abaMes).setTabListener(this));
        actionBar.addTab(actionBar.newTab().setText(R.string.abaAno).setTabListener(this));
	
		viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			 
		    @Override
		    public void onPageSelected(int position) {
		        // on changing the page
		        // make respected tab selected
		        actionBar.setSelectedNavigationItem(position);
		    }
		 
		    @Override
		    public void onPageScrolled(int arg0, float arg1, int arg2) {
		    }
		 
		    @Override
		    public void onPageScrollStateChanged(int arg0) {
		    }
		});
	}
	
		@Override
		public void onTabReselected(Tab tab, FragmentTransaction ft) {
			// TODO Auto-generated method stub
	
		}

		@Override
		public void onTabSelected(Tab tab, FragmentTransaction ft) {
			// TODO Auto-generated method stub
			viewPager.setCurrentItem(tab.getPosition());	
		}

		@Override
		public void onTabUnselected(Tab tab, FragmentTransaction ft) {
			// TODO Auto-generated method stub
		}
		
}