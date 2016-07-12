package net.robinx.adapter.example;

import java.util.ArrayList;
import java.util.List;


import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.Toast;

import net.robinx.adapter.example.holder.ABSHolder;
import net.robinx.adapter.example.holder.ABSHolder2;
import net.robinx.lib.adapter.abs.AbsViewTypeManager;
import net.robinx.lib.adapter.abs.ListDataAdapter;
import net.robinx.lib.adapter.abs.ListDataAdapterBase;
import net.robinx.lib.adapter.abs.anim.AnimationAdapter;
import net.robinx.lib.adapter.abs.anim.extra.ScaleInAnimationAdapter;
import net.robinx.lib.adapter.abs.anim.extra.SwingBottomInAnimationAdapter;

public class ABSActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_abs);

		init();
	}

	private void init() {
		initAbsListView();
		initMultiViewTypeListView();
	}

	private void initAbsListView() {
		ListView listView = (ListView) findViewById(R.id.lv);

		final ListDataAdapter<String> adapter = new ListDataAdapter<String>();
		
		//单个View
		adapter.setViewHolderClass(this, ABSHolder.class, this);
		
		AnimationAdapter animationAdapter = new SwingBottomInAnimationAdapter(adapter);
		animationAdapter.setAbsListView(listView);
		
		listView.setAdapter(animationAdapter);
		adapter.setOnEventListener(new ListDataAdapterBase.OnEventListener<String>() {

			@Override
			public String onEvent(Object... params) {
				String itemData = (String) params[0];
				Toast.makeText(ABSActivity.this, itemData, Toast.LENGTH_SHORT).show();
				return itemData;
			}
		});

		for (int i = 0; i < 20; i++) {
			adapter.append(i + "");
		}
		
	}
	
	private void initMultiViewTypeListView() {
		ListView listView = (ListView) findViewById(R.id.lv_multi_view_type);
		final ListDataAdapter<String> adapter = new ListDataAdapter<String>();
		
		//多view type
		AbsViewTypeManager<String> viewTypeManager = new AbsViewTypeManager<String>();
		viewTypeManager.viewTypes(ABSHolder.VIEW_TYPE_1,ABSHolder.VIEW_TYPE_2)  //对应Type
		                  .viewHolderClasses(ABSHolder.class,ABSHolder2.class)  //Type对应的Holder class
		                  .argsArray(new Object[]{this})  //Holder构造函数的参数，顺序一一对应
		                  .itemViewTypeLogic(new AbsViewTypeManager.OnItemViewTypeLogic<String>() {  //Adapter获取ViewType的逻辑

							@Override
							public int getItemViewType(int position, String itemData) {
								if (position == 0 || position == 2 || position == 3|| position == 5) {
									return ABSHolder.VIEW_TYPE_2;
								}
								return ABSHolder.VIEW_TYPE_1;
							}
						});
		adapter.setViewTypeManager(viewTypeManager);
		
		AnimationAdapter animationAdapter = new ScaleInAnimationAdapter(adapter);
		animationAdapter.setAbsListView(listView);
		
		listView.setAdapter(animationAdapter);
		adapter.setOnEventListener(new ListDataAdapterBase.OnEventListener<String>() {

			@Override
			public String onEvent(Object... params) {
				String itemData = (String) params[0];
				Toast.makeText(ABSActivity.this, itemData, Toast.LENGTH_SHORT).show();
				return itemData;
			}
		});

		List<String> strings = new ArrayList<String>();
		for (int i = 0; i < 20; i++) {
			strings.add(i+"");
			
		}
		adapter.append(strings);		
	
	}
	
	public void callActivityMethod(int position) {
		Toast.makeText(this, "Item:"+position+"  callActivityMethod", Toast.LENGTH_SHORT).show();
	}
}
