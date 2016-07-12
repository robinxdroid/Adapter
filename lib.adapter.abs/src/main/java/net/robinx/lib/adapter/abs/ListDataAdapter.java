package net.robinx.lib.adapter.abs;

import java.util.List;

/**
 * ListDataAdapterBase default implementation
 */
public class ListDataAdapter<ItemDataType> extends ListDataAdapterBase<ItemDataType> {

    public ListDataAdapter() {
    }
    
    public ListDataAdapter(List<ItemDataType> itemDataTypes) {
    	super.mItemDataList = itemDataTypes;
    }

    public List<ItemDataType> getDataList() {
        return mItemDataList;
    }
    
    public void append(ItemDataType itemDataType){
    	if (itemDataType!=null) {
			mItemDataList.add(itemDataType);
			notifyDataSetChanged();
		}
    }
    
    public void append(List<ItemDataType> itemDataTypes){
    	if (itemDataTypes.size()>0) {
    		for (ItemDataType itemDataType : itemDataTypes) {
    			mItemDataList.add(itemDataType);
			}
    		notifyDataSetChanged();
		}
    }
    
    public void replace(List<ItemDataType> itemDataTypes){
    	mItemDataList.clear();
    	if (itemDataTypes.size()>0) {
    		mItemDataList.addAll(itemDataTypes);
    		notifyDataSetChanged();
		}
    }
    
    public void remove(List<ItemDataType> itemDataList){
    	if (itemDataList.size()>0) {
    		for (ItemDataType itemDataType : itemDataList) {
    			if (mItemDataList.contains(itemDataType)) {
    				mItemDataList.remove(itemDataType);
    			}
    		}
    		notifyDataSetChanged();
		}
    }
    
    public void remove(ItemDataType itemDataType){
    	if (mItemDataList.contains(itemDataType)) {
			mItemDataList.remove(itemDataType);
			notifyDataSetChanged();
		}
    }
    
    public void remove(int position){
    	mItemDataList.remove(position);
    	notifyDataSetChanged();
    }
    
    public void removeAll(){
    	mItemDataList.clear();
    	notifyDataSetChanged();
    }

}
