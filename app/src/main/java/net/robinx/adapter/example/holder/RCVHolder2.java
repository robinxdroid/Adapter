package net.robinx.adapter.example.holder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import net.robinx.adapter.example.R;
import net.robinx.adapter.example.bean.TestBean;
import net.robinx.lib.adapter.recycler.RecyclerListDataAdapter;
import net.robinx.lib.adapter.recycler.RecyclerViewAdapterBase;
import net.robinx.lib.adapter.recycler.RecyclerViewHolderBase;

public class RCVHolder2 extends RecyclerViewHolderBase<TestBean> {
	private Context context;
	private TextView textView;

	public RCVHolder2(View itemView) {
		super(itemView);
	}


	@Override
	public void createView(View itemView) {
		context=itemView.getContext();
		textView=(TextView) itemView.findViewById(R.id.tv);
	}

	@Override
	public void showData(final RecyclerView.Adapter<RecyclerViewHolderBase<TestBean>> adapter, final int position, final TestBean itemData) {
		final RecyclerViewAdapterBase<TestBean> adapterBase = (RecyclerViewAdapterBase<TestBean>) adapter;
		textView.setText(itemData.getName());
	    textView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String returnData=adapterBase.onEvent(itemData); //通过onEvent(Object... params)函数传递一个或多个变量，在Activity中就可以通过setOnEventListener函数监听到传递的值， 同时可接收返回值
				Toast.makeText(context, "返回值title:"+returnData, Toast.LENGTH_SHORT).show();
			}
		});
	    textView.setOnLongClickListener(new View.OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View v) {
				Log.i("robin", "position:"+position);
				Log.i("robin", "holderLayoutPosition:"+getLayoutPosition());
				
				((RecyclerListDataAdapter<TestBean>)adapterBase).remove(position, true);
				
				return false;
			}
		});
	}
}
