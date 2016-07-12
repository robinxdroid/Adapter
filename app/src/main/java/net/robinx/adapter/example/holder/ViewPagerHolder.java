package net.robinx.adapter.example.holder;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import net.robinx.adapter.example.R;
import net.robinx.lib.adapter.viewpager.ViewPagerAdapterBase;
import net.robinx.lib.adapter.viewpager.ViewPagerHolderBase;

public class ViewPagerHolder extends ViewPagerHolderBase<String> {
	
	private Context context;
	private TextView textView;
	
	
	@SuppressLint("InflateParams")
	@Override
	public View createView(LayoutInflater layoutInflater) {
		context = layoutInflater.getContext();
		View itemView=layoutInflater.inflate(R.layout.item_vp,null);
		textView=(TextView) itemView.findViewById(R.id.tv);
		return itemView;
	}

	@Override
	public void showData(final ViewPagerAdapterBase<String> adapter, int position, final String itemData) {
		textView.setText(itemData);
		textView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String returnData=adapter.onEvent(itemData); //通过onEvent(Object... params)函数传递一个或多个变量，在Activity中就可以通过setOnEventListener函数监听到传递的值， 同时可接收返回值
				Toast.makeText(context, "返回值:"+returnData, Toast.LENGTH_SHORT).show();
			}
		});
	}

}
