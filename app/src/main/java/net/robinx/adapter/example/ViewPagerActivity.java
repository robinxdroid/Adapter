package net.robinx.adapter.example;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import net.robinx.adapter.example.holder.ViewPagerHolder;
import net.robinx.lib.adapter.viewpager.ViewPagerAdapter;
import net.robinx.lib.adapter.viewpager.ViewPagerAdapterBase;

public class ViewPagerActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_pager);
		
		init();
	}

	private void init() {
		initViewPager();
	}


	private void initViewPager() {
		ViewPager viewPager=(ViewPager) findViewById(R.id.vp);
		
		ViewPagerAdapter<String> adapter = new ViewPagerAdapter<String>();
		adapter.setViewHolderClass(this, ViewPagerHolder.class);
		viewPager.setAdapter(adapter);
		//设置是否循环
		adapter.setInfinite(true);
	
		
		adapter.setOnEventListener(new ViewPagerAdapterBase.OnEventListener<String>() {

			@Override
			public String onEvent(Object... params) {
				String itemData = (String) params[0];
				Toast.makeText(ViewPagerActivity.this, itemData, Toast.LENGTH_SHORT).show();
				return itemData;
			}
		});
		
		for (int i = 0; i < 6; i++) {
			adapter.append(i+"");
		}
		
		//设置无限循环的话，需要设置此项，为了开始可以向左滑动
		Log.i("robin", adapter.getInfinitePosition()+"");
		viewPager.setCurrentItem(adapter.getInfinitePosition());

	}
}
