package net.robinx.adapter.example;


import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import net.robinx.adapter.example.bean.TestBean;
import net.robinx.adapter.example.holder.RCVHolder;
import net.robinx.adapter.example.holder.RCVHolder2;
import net.robinx.lib.adapter.recycler.RecyclerListDataAdapter;
import net.robinx.lib.adapter.recycler.RecyclerViewAdapterBase;
import net.robinx.lib.adapter.recycler.RecyclerViewTypeManager;
import net.robinx.lib.adapter.recycler.anim.AnimatorAdapter;
import net.robinx.lib.adapter.recycler.anim.extra.ScaleInAnimatorAdapter;
import net.robinx.lib.adapter.recycler.anim.extra.SlideInBottomAnimatorAdapter;

public class RecyclerActivity extends AppCompatActivity {
	
	private RecyclerListDataAdapter<TestBean> adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recycler);
		
		init();
	}

	private void init() {
		initRecyclerView();
		initView();
		initMultiTypeRecycleView();
	}

	private void initView() {
		findViewById(R.id.btn_add).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (adapter.getItemDataList().size()<3) {
					return;
				}
				int name = adapter.getItemDataList().size()+1;
				TestBean itemData = new TestBean(name+"", TestBean.VIEW_TYPE_1);
				adapter.append(itemData, true, 2);
			}
		});
	findViewById(R.id.btn_delete).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (adapter.getItemDataList().size()<3) {
					return;
				}
				adapter.remove(2, true);
			}
		});
	}

	private void initRecyclerView() {
		RecyclerView recyclerView=(RecyclerView) findViewById(R.id.rcv);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        
        adapter=new RecyclerListDataAdapter<TestBean>();
        
        //单个View
        //adapter.setViewHolderClass(this, RCVHolder.class, R.layout.item_rcv);  //Holder构造函数无其他参数时
        adapter.setViewHolderClass(this, RCVHolder.class, R.layout.item_rcv, this); //Holder构造函数有参数时，在后面依次追加
        
        AnimatorAdapter adapterWrapper = new SlideInBottomAnimatorAdapter(adapter, recyclerView);
		recyclerView.setAdapter(adapterWrapper);

		for (int i = 0; i < 20; i++) {
			TestBean testBean  = new TestBean(i+"", TestBean.VIEW_TYPE_1);
			adapter.append(testBean);
		}
	}
	

	private void initMultiTypeRecycleView() {
		RecyclerView recyclerView=(RecyclerView) findViewById(R.id.rcv_multi_type);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        
        //多view type
        RecyclerListDataAdapter<TestBean> adapter = new RecyclerListDataAdapter<TestBean>();
        RecyclerViewTypeManager<TestBean> viewTypeManager = new RecyclerViewTypeManager<TestBean>();
        viewTypeManager.viewTypes(TestBean.VIEW_TYPE_1, TestBean.VIEW_TYPE_2)  //多Type
                                    .itemViewIds(R.layout.item_rcv,R.layout.item_rcv2)  //对应子View
                                    .viewHolderClasses(RCVHolder.class,RCVHolder2.class)  //对应Holder
                                    .argsArray(new Object[]{this})
                                    .itemViewTypeLogic(new RecyclerViewTypeManager.OnItemViewTypeLogic<TestBean>() {  //设置getItemViewType逻辑，不设置 默认取viewTypes第0个元素
                                    	@Override
                            			public int getItemViewType(int position, TestBean itemData) {
                            				return itemData.getType();
                            			}
                            		});
			
        adapter.setViewTypeManager(viewTypeManager);
        
        AnimatorAdapter adapterWrapper = new ScaleInAnimatorAdapter(adapter, recyclerView,0.5f);
		recyclerView.setAdapter(adapterWrapper);
		
		adapter.setOnEventListener(new RecyclerViewAdapterBase.OnEventListener<String>() {

			@Override
			public String onEvent(Object... params) {
				TestBean itemData = (TestBean) params[0];
				return itemData.getName();
			}
		});
		
		for (int i = 0; i < 20; i++) {
			if (i==0) {
				TestBean testBean = new TestBean(i+"", TestBean.VIEW_TYPE_1);
				adapter.append(testBean);
			}else if (i==3) {
				TestBean testBean = new TestBean(i+"", TestBean.VIEW_TYPE_1);
				adapter.append(testBean);
			}else {
				TestBean testBean = new TestBean(i+"", TestBean.VIEW_TYPE_2);
				adapter.append(testBean);
			}
		
		}
		
	}
	
	public void callActivityMethod(int position) {
		Toast.makeText(RecyclerActivity.this, "Item:"+position+"  callActivityMethod", Toast.LENGTH_SHORT).show();
	}
	
}
