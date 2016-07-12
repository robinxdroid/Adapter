package net.robinx.lib.adapter.recycler.wrapper;

import android.support.v7.widget.RecyclerView;


import java.util.ArrayList;
import java.util.List;

/**
 * RecyclerViewAdapterBase default implementation,as an common adapter
 * @author Robin
 * @since 2015-07-15 11:33:46
 * @param <ItemDataType>
 */
public class HeaderAndFooterAdapter<ItemDataType> extends HeaderAndFooterAdapterBase<ItemDataType> {


	public HeaderAndFooterAdapter(RecyclerView.Adapter adapter) {
		super(adapter);
	}

	/**
	 * Dynamically add a data
	 * @param itemDataType entity class object
	 */
	public void appendHeaderData(ItemDataType itemDataType) {
		if (itemDataType != null) {
			mHeaderDataList.add(itemDataType);
			notifyDataSetChanged();
		}
	}

    public void appendFooterData(ItemDataType itemDataType) {
        if (itemDataType != null) {
            mFooterDataList.add(itemDataType);
            notifyDataSetChanged();
        }
    }

	/**
	 * 	Dynamically add a data
	 * 
	 * @param itemDataType entity class object
	 * @param withAnimate Whether show animation
	 * @param position Should be displayed animation insertion position
	 */
	/*public void appendHeaderData(ItemDataType itemDataType, boolean withAnimate, int position) {
		if (itemDataType != null) {
			if (withAnimate) {
				mHeaderDataList.add(position,itemDataType);
				notifyItemInserted(position);
				//fix data disorder
				if (position != mHeaderDataList.size()) {
					notifyItemRangeChanged(position, mHeaderDataList.size() - position);
				}
			}else {
				mHeaderDataList.add(position,itemDataType);
				notifyDataSetChanged();
			}
		}
	}

    public void appendFooterData(ItemDataType itemDataType, boolean withAnimate, int position) {
        if (itemDataType != null) {
            if (withAnimate) {
                mFooterDataList.add(position,itemDataType);
                notifyItemInserted(position);
                //fix data disorder
                if (position != mFooterDataList.size()) {
                    notifyItemRangeChanged(position, mFooterDataList.size() - position);
                }
            }else {
                mFooterDataList.add(position,itemDataType);
                notifyDataSetChanged();
            }
        }
    }*/

	/**
	 * Dynamically add a set of data collection
	 * 
	 * @param itemDataTypes Entity class collection
	 */
	public void appendHeaderList(List<ItemDataType> itemDataTypes) {
		if (itemDataTypes.size() > 0) {
			for (ItemDataType itemDataType : itemDataTypes) {
				mHeaderDataList.add(itemDataType);
			}
			notifyDataSetChanged();
		}
	}

    public void appendFooterList(List<ItemDataType> itemDataTypes) {
        if (itemDataTypes.size() > 0) {
            for (ItemDataType itemDataType : itemDataTypes) {
                mFooterDataList.add(itemDataType);
            }
            notifyDataSetChanged();
        }
    }

	/**
	 * Replace all data
	 * 
	 * @param itemDataTypes Entity class collection
	 */
	public void replaceHeaderList(List<ItemDataType> itemDataTypes) {
		mHeaderDataList.clear();
		if (itemDataTypes.size() > 0) {
			mHeaderDataList.addAll(itemDataTypes);
			notifyDataSetChanged();
		}
	}

    public void replaceFooterList(List<ItemDataType> itemDataTypes) {
        mHeaderDataList.clear();
        if (itemDataTypes.size() > 0) {
            mHeaderDataList.addAll(itemDataTypes);
            notifyDataSetChanged();
        }
    }


	/**
	 * To remove a data set
	 * 
	 * @param position 
	 * @param withAnimate Whether show animation
	 */
	public void removeHeaderData(int position, boolean withAnimate) {
		mHeaderDataList.remove(position);
        mHeaderViews.removeAt(position);
        mHeaderEnclosingInstances.removeAt(position);
        mHeaderArgs.removeAt(position);
        mHeaderHolderClasses.removeAt(position);
		if (withAnimate) {
			notifyItemRemoved(position);
			//fix data disorder
			/*if(position != mHeaderViews.size()){
				notifyItemRangeChanged(position, mHeaderViews.size() - position);
			}*/
		}else {
			notifyDataSetChanged();
		}
	}

    public void removeFooterData(int position, boolean withAnimate) {
        mFooterDataList.remove(position);
        mFooterViews.removeAt(position);
        mFooterEnclosingInstances.removeAt(position);
        mFooterArgs.removeAt(position);
        mFooterHolderClasses.removeAt(position);
        if (withAnimate) {
            notifyItemRemoved(position+getHeadersCount()+getRealItemCount());
            //fix data disorder
            /*if(position != mFooterViews.size()){
                notifyItemRangeChanged(position, mFooterViews.size()-position);
            }*/
        }else {
            notifyDataSetChanged();
        }
    }



	/**
	 * Remove all data
	 */
	public void removeAllHeader() {
		mHeaderDataList.clear();
        mHeaderViews.clear();
        mHeaderEnclosingInstances.clear();
        mHeaderArgs.clear();
        mHeaderHolderClasses.clear();
		notifyDataSetChanged();
	}

    public void removeAllFooter() {
        mFooterDataList.clear();
        mFooterViews.clear();
        mFooterEnclosingInstances.clear();
        mFooterArgs.clear();
        mFooterHolderClasses.clear();
        notifyDataSetChanged();
    }

	public List<ItemDataType> getHeaderDataList() {
		return mHeaderDataList;
	}
    public List<ItemDataType> getFooterDataList() {
        return mFooterDataList;
    }
}
