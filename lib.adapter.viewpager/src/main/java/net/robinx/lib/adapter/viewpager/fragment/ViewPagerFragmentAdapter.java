package net.robinx.lib.adapter.viewpager.fragment;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * ViewPager adapter for fragment
 * @author Robin
 * @since 2015-09-13 12:17:10
 *
 */
public class ViewPagerFragmentAdapter extends FragmentPagerAdapter {

	private List<Fragment> mFragments;
	
	public ViewPagerFragmentAdapter(FragmentActivity activity,List<Fragment> fragments){
		this(activity.getSupportFragmentManager());
		this.mFragments = fragments;
	}
	
	public ViewPagerFragmentAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int position) {
		return mFragments.get(position);
	}

	@Override
	public int getCount() {
		return mFragments.size();
	}

}
