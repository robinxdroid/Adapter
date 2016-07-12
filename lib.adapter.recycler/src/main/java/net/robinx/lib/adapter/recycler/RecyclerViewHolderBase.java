package net.robinx.lib.adapter.recycler;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;

/**
 * RecyclerViewHolder基类
 * @author Robin
 * @since 2015-05-24 11:12:17
 */
public abstract class RecyclerViewHolderBase<ItemDataType> extends ViewHolder {
	
	public RecyclerViewHolderBase(View itemView) {

		super(itemView);
		createView(itemView);
	}

    public abstract void createView(View itemView);



    /**
     * 显示数据
     * @param adapter
     * @param position 位置
     * @param itemData item数据
     */
    public abstract void showData(RecyclerView.Adapter<RecyclerViewHolderBase<ItemDataType>> adapter, int position, ItemDataType itemData);

}
