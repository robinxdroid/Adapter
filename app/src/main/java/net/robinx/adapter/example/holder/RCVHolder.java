package net.robinx.adapter.example.holder;

import java.lang.ref.WeakReference;


import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import net.robinx.adapter.example.R;
import net.robinx.adapter.example.RecyclerActivity;
import net.robinx.adapter.example.bean.TestBean;
import net.robinx.lib.adapter.recycler.RecyclerListDataAdapter;
import net.robinx.lib.adapter.recycler.RecyclerViewAdapterBase;
import net.robinx.lib.adapter.recycler.RecyclerViewHolderBase;

public class RCVHolder extends RecyclerViewHolderBase<TestBean> {
	private TextView textView;

	private WeakReference<RecyclerActivity> mActivityRefrence;
	
	public RCVHolder(View itemView) {
		super(itemView);
	}
	
	public RCVHolder(View itemView,RecyclerActivity activity) {
		super(itemView);
		mActivityRefrence = new WeakReference<RecyclerActivity>(activity);
	}


	@Override
	public void createView(View itemView) {
		textView=(TextView) itemView.findViewById(R.id.tv);
	}

	@Override
	public void showData(final RecyclerView.Adapter<RecyclerViewHolderBase<TestBean>> adapter, final int position, final TestBean itemData) {
		textView.setText(itemData.getName());
	    textView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (mActivityRefrence != null && mActivityRefrence.get() != null) {
					mActivityRefrence.get().callActivityMethod(position);
				} else {
					Toast.makeText(v.getContext(),"Position:"+position,Toast.LENGTH_SHORT).show();
				}
			}
		});
	    textView.setOnLongClickListener(new View.OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View v) {
				Log.i("robin", "position:"+position);
				Log.i("robin", "holderLayoutPosition:"+getLayoutPosition());
				
				((RecyclerListDataAdapter<TestBean>)adapter).remove(position, true);
				
				return false;
			}
		});
	}
}
