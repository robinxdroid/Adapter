package net.robinx.lib.adapter.abs;

/**
 * ViewHolder constructor
 */
public interface ViewHolderCreator<ItemDataType> {
	
    public ViewHolderBase<ItemDataType> createViewHolder();
    
}