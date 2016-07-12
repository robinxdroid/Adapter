package net.robinx.lib.adapter.recycler;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

import android.view.View;

/**
 * Using reflection to the incoming ViewHolder class instantiation
 * @author Robin
 * @since 2015-05-24 12:31:34
 */
public class DefaultRecyclerViewHolderCreatorImpl<T> implements RecyclerViewHolderCreator<T> {

    private final Constructor<?> mConstructor;
    private Object[] mInstanceObjects;

    private DefaultRecyclerViewHolderCreatorImpl(Constructor<?> constructor, Object[] instanceObjects) {
        mConstructor = constructor;
        mInstanceObjects = instanceObjects;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
	public static <ItemDataType> RecyclerViewHolderCreator<ItemDataType> create(final Object enclosingInstance, final Class<?> cls, final Object... args) {
        if (cls == null) {
            throw new IllegalArgumentException("ViewHolderClass is null.");
        }

        // top class
        boolean isEnclosingInstanceClass = false;
        // if top class is not null and inner class is not static
        if (cls.getEnclosingClass() != null && !Modifier.isStatic(cls.getModifiers())) {
            isEnclosingInstanceClass = true;
        }

        // inner instance class should pass enclosing class, so +1
        int argsLen = isEnclosingInstanceClass ? args.length + 1 : args.length;

        final Object[] instanceObjects = new Object[argsLen];

        int copyStart = 0;
        // if it is inner instance class, first argument should be the enclosing class instance
        if (isEnclosingInstanceClass) {
            instanceObjects[0] = enclosingInstance;
            copyStart = 1;
        }

        // has copy construction parameters
        if (args.length > 0) {
            System.arraycopy(args, 0, instanceObjects, copyStart, args.length);
        }

        // fill the types
        final Class[] parameterTypes = new Class[argsLen];
        for (int i = 0; i < instanceObjects.length; i++) {
        	Class<?> parameterClass =  instanceObjects[i].getClass();
        	if (i == 0) {  //The first element is the item view
        	    while(true){
                    parameterClass = parameterClass.getSuperclass();
                    if (parameterClass == View.class) {
                        break;
                    }
                }
			}
            parameterTypes[i] = parameterClass;
        }

        Constructor<?> constructor = null;
        try {
            constructor = cls.getDeclaredConstructor(parameterTypes);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        if (constructor == null) {
            throw new IllegalArgumentException("ViewHolderClass can not be initiated");
        }

        RecyclerViewHolderCreator recyclerViewHolderCreator = new DefaultRecyclerViewHolderCreatorImpl(constructor, instanceObjects);
        return recyclerViewHolderCreator;
    }

    @SuppressWarnings("unchecked")
	@Override
    public RecyclerViewHolderBase<T> createViewHolder() {
        Object object = null;
        try {
        	//if the inner class is private 
            boolean isAccessible = mConstructor.isAccessible();
            if (!isAccessible) {
                mConstructor.setAccessible(true);
            }
            object = mConstructor.newInstance(mInstanceObjects);
            if (!isAccessible) {
                mConstructor.setAccessible(false);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (object == null || !(object instanceof RecyclerViewHolderBase)) {
            throw new IllegalArgumentException("ViewHolderClass can not be initiated");
        }
        return (RecyclerViewHolderBase<T>) object;
    }
}
