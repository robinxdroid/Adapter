package net.robinx.adapter.example.holder;

import java.lang.ref.WeakReference;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import net.robinx.adapter.example.ABSActivity;
import net.robinx.adapter.example.R;
import net.robinx.lib.adapter.abs.ListDataAdapterBase;
import net.robinx.lib.adapter.abs.ViewHolderBase;

public class ABSHolder extends ViewHolderBase<String> {
	public static final int VIEW_TYPE_1 = 0x00;
	public static final int VIEW_TYPE_2 = 0x01;
	
	private TextView textView;
	
	
	private WeakReference<ABSActivity> mActivityRefrence;
	
	public ABSHolder(ABSActivity activity) {
		mActivityRefrence = new WeakReference<ABSActivity>(activity);
	}
	
	@SuppressLint("InflateParams")
	@Override
	public View createView(LayoutInflater layoutInflater, int viewType) {
		View itemView=layoutInflater.inflate(R.layout.item_abs,null);
		textView=(TextView) itemView.findViewById(R.id.tv);
		return itemView;
	}

	@Override
	public void showData(final ListDataAdapterBase<String> adapter, final int position, final String itemData) {
		textView.setText(itemData);
		textView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (mActivityRefrence != null && mActivityRefrence.get() != null) {
					mActivityRefrence.get().callActivityMethod(position);
				}
			}
		});
	}
	
}
