package net.robinx.adapter.example.holder;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import net.robinx.adapter.example.R;
import net.robinx.adapter.example.RecyclerHeaderAndFooterActivity;
import net.robinx.adapter.example.bean.TestBean;
import net.robinx.lib.adapter.recycler.RecyclerListDataAdapter;
import net.robinx.lib.adapter.recycler.RecyclerViewHolderBase;
import net.robinx.lib.adapter.recycler.wrapper.HeaderAndFooterAdapter;

import java.lang.ref.WeakReference;

public class RCVFooterHolder extends RecyclerViewHolderBase<TestBean> {
	private TextView textView;

	private WeakReference<RecyclerHeaderAndFooterActivity> mActivityReference;

	public RCVFooterHolder(View itemView) {
		super(itemView);
	}

	public RCVFooterHolder(View itemView, RecyclerHeaderAndFooterActivity activity) {
		super(itemView);
		mActivityReference = new WeakReference<RecyclerHeaderAndFooterActivity>(activity);
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
				if (mActivityReference != null && mActivityReference.get() != null) {
					mActivityReference.get().callActivityMethod("footer"+position,position);
				} else {
					Toast.makeText(v.getContext(),"footer"+position+",Position:"+position+",内容："+itemData.getName(),Toast.LENGTH_SHORT).show();
				}
			}
		});
	    textView.setOnLongClickListener(new View.OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {
				Log.i("robin", "position:"+position);
				Log.i("robin", "holderLayoutPosition:"+getLayoutPosition());

				((HeaderAndFooterAdapter<TestBean>)adapter).removeFooterData(position, false);

				return false;
			}
		});
	}
}
