package net.robinx.lib.adapter.abs;

import java.util.ArrayList;
import java.util.List;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * Base adapter
 * @author Robin
 * @since 2015-05-24 12:10:43
 */
public abstract class ListDataAdapterBase<ItemDataType> extends BaseAdapter {
	
	 protected List<ItemDataType> mItemDataList = new ArrayList<ItemDataType>();

    protected ViewHolderCreator<ItemDataType> mViewHolderCreator;
    protected boolean mForceCreateView = false;
    
    private AbsViewTypeManager<ItemDataType> mAbsViewTypeManager;
    
    /*=======================================================
     * Constructor
     *=======================================================
     */

    public ListDataAdapterBase() {
    }

    public ListDataAdapterBase(final Object enclosingInstance, final Class<?> cls) {
        setViewHolderClass(enclosingInstance, cls);
    }

    /*=======================================================
     * Override BaseAdapter
     *=======================================================
     */

    @Override
    public int getViewTypeCount() {
    	if (mAbsViewTypeManager != null) {
			return mAbsViewTypeManager.getViewTypes().length;
		}
    	return super.getViewTypeCount();
    }
    
    @Override
    public int getItemViewType(int position) {
    	if (mAbsViewTypeManager != null) {
			return mAbsViewTypeManager.getItemViewType(position, mItemDataList.get(position));
		}
    	return super.getItemViewType(position);
    }
    
    @Override
    public int getCount() {
        return mItemDataList.size();
    }

    @Override
    public ItemDataType getItem(int position) {
        if (mItemDataList.size() <= position || position < 0) {
            return null;
        }
        return mItemDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ItemDataType itemData = getItem(position);
        ViewHolderBase<ItemDataType> holderBase = null;
        if (mForceCreateView || convertView == null || (!(convertView.getTag() instanceof ViewHolderBase<?>))) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            if (mAbsViewTypeManager == null) {
            	 holderBase = createViewHolder();
			}else {
			    ViewHolderCreator<ItemDataType> viewHolderCreator = mAbsViewTypeManager.getViewHolderCreator(parent.getContext(), getItemViewType(position));
                holderBase = viewHolderCreator.createViewHolder();
			}
           
            if (holderBase != null) {
                convertView = holderBase.createView(inflater, getItemViewType(position));
                if (convertView != null) {
                    if (!mForceCreateView) {
                        convertView.setTag(holderBase);
                    }
                }
            }
        } else {
            holderBase = (ViewHolderBase<ItemDataType>) convertView.getTag();
        }
        if (holderBase != null) {
            holderBase.setItemData(position, convertView);
            holderBase.showData(this,position, itemData);
        }
        return convertView;
        	
    }

    /**
     * The "ViewHolder" of use reflection to create objects
     * @return
     */
    private ViewHolderBase<ItemDataType> createViewHolder() {
        if (mViewHolderCreator == null) {
            throw new RuntimeException("view holder creator is null");
        }
        if (mViewHolderCreator != null) {
            return mViewHolderCreator.createViewHolder();
        }
        return null;
    }
    
    /*=======================================================
     * Getter And Setter
     *=======================================================
     */
    
    public void setViewHolderClass(Class<?> cls, Object... args) {
    	setViewHolderClass(null, cls, args);
    }

    public void setViewHolderClass(Object enclosingInstance, Class<?> cls, Object... args) {
        mViewHolderCreator = DefaultViewHolderCreatorImpl.create(enclosingInstance, cls, args);
    }
    
    public void setViewTypeManager(AbsViewTypeManager<ItemDataType> viewTypeManager){
    	this.mAbsViewTypeManager = viewTypeManager;
    }

    public void forceCreateView(boolean yes) {
        mForceCreateView = yes;
    }
    
    /*=======================================================
     * Event delivery mechanism
     *=======================================================
     */

	@SuppressWarnings("unchecked")
	public <ReturnDataType>ReturnDataType onEvent(Object...params){
    	if (onEventListener != null) {
			return  (ReturnDataType) onEventListener.onEvent(params);
		}
    	return null;
    }
    private OnEventListener<?> onEventListener;
    
	public void setOnEventListener(OnEventListener<?> onEventListener) {
		this.onEventListener = onEventListener;
	}

	public interface OnEventListener<ReturnDataType>{
    	ReturnDataType onEvent(Object... params);
    }
}
