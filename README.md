
## Adapter ##

一个Adapter库，很早就写了，一直自用，主要实现一个Adapter适应所有列表View,不用再继承Adapter,只需要写对应的Holder即可，所有的逻辑部分都在Holder中 

### Support ###
    
    1 AbsListView 
    2 RecyclerView
    3 ViewPager
    

### Example ###

[Download demo.apk](https://github.com/robinxdroid/Adapter/blob/master/app-debug.apk?raw=true)


### Usage ###

**AbsListView:**

1.创建一个Holder:

```java
public class ABSHolder extends ViewHolderBase<String> {
	
	private TextView textView;
	

    /**
	 * 在此创建Item View并进行findView操作
     */
	@Override
	public View createView(LayoutInflater layoutInflater, int viewType) {
		View itemView=layoutInflater.inflate(R.layout.item_abs,null);
		textView=(TextView) itemView.findViewById(R.id.tv);
		return itemView;
	}

    /**
	 * 在此进行相关赋值逻辑操作
	 * @adapter 
	 * @position item位置
	 * @itemData item对应实体类对象  
     */
	@Override
	public void showData(final ListDataAdapterBase<String> adapter, final int position, final String itemData) {
		textView.setText(itemData);
		
	}
	
}
```   
2.代码中使用: 
```java
        ListDataAdapter<String> adapter = new ListDataAdapter<String>();
		adapter.setViewHolderClass(ABSHolder.class);
		listView.setAdapter(adapter);

        //追加数据
        for (int i = 0; i < 20; i++) {
			adapter.append(i + "");
		}
```      
3.多Type:
```java
        final ListDataAdapter<String> adapter = new ListDataAdapter<String>();
	
		AbsViewTypeManager<String> viewTypeManager = new AbsViewTypeManager<String>();
		viewTypeManager.viewTypes(ABSHolder.VIEW_TYPE_1,ABSHolder.VIEW_TYPE_2)  //对应Type
		                  .viewHolderClasses(ABSHolder.class,ABSHolder2.class)  //Type对应的Holder class
		                  .itemViewTypeLogic(new AbsViewTypeManager.OnItemViewTypeLogic<String>() {  //Adapter获取ViewType的逻辑

							@Override
							public int getItemViewType(int position, String itemData) {
								if (position == 0) {
									return ABSHolder.VIEW_TYPE_2;
								}
								return ABSHolder.VIEW_TYPE_1;
							}
						});
		adapter.setViewTypeManager(viewTypeManager);
		listView.setAdapter(adapter);
``` 

**RecyclerView**


1.创建一个Holder:

```java
public class RCVHolder extends RecyclerViewHolderBase<TestBean> {
	private TextView textView;

	
	public RCVHolder(View itemView) {
		super(itemView);
	}
	
    /**
	 * 在此进行findView操作
     */
	@Override
	public void createView(View itemView) {
		textView=(TextView) itemView.findViewById(R.id.tv);
	}

    /**
	 * 在此进行相关赋值逻辑操作
	 * @adapter 
	 * @position item位置
	 * @itemData item对应实体类对象  
     */
	@Override
	public void showData(final RecyclerView.Adapter<RecyclerViewHolderBase<TestBean>> adapter, final int position, final TestBean itemData) {
		textView.setText(itemData.getName());
	    
	}
}
```   
2.代码中使用: 
```java
        RecyclerListDataAdapter adapter=new RecyclerListDataAdapter<TestBean>();
        adapter.setViewHolderClass(RCVHolder.class, R.layout.item_rcv);  
     
		recyclerView.setAdapter(adapter);

        //追加数据
        for (int i = 0; i < 20; i++) {
			adapter.append(i + "");
		}
```    

3.多Type:
```java
        RecyclerListDataAdapter<TestBean> adapter = new RecyclerListDataAdapter<TestBean>();
        RecyclerViewTypeManager<TestBean> viewTypeManager = new RecyclerViewTypeManager<TestBean>();
        viewTypeManager.viewTypes(TestBean.VIEW_TYPE_1, TestBean.VIEW_TYPE_2)  //多Type
                       .itemViewIds(R.layout.item_rcv,R.layout.item_rcv2)  //对应子View
                       .viewHolderClasses(RCVHolder.class,RCVHolder2.class)  //对应Holder
                       .itemViewTypeLogic(new RecyclerViewTypeManager.OnItemViewTypeLogic<TestBean>() {  //设置etItemViewType逻辑，不设置 默认取viewTypes第0个元素
                              	@Override
                        			public int getItemViewType(int position, TestBean itemData) {
                           				return itemData.getType();
                           			}
                          		});
			
        adapter.setViewTypeManager(viewTypeManager);
		recyclerView.setAdapter(adapter);

```
4.添加Header 与 Footer(装饰者模式):

```java
        HeaderAndFooterAdapter headerAndFooterAdapterWrapper = new HeaderAndFooterAdapter<>(adapter);
        View headerView1 = View.inflate(this,R.layout.item_header,null);
        View footerView1 = View.inflate(this,R.layout.item_footer,null);
        headerAndFooterAdapterWrapper.addHeaderView(this, RCVHeaderHolder.class,headerView1,this);
        headerAndFooterAdapterWrapper.addFootView(this, RCVFooterHolder.class,footerView1)
        recyclerView.setAdapter(headerAndFooterAdapterWrapper);
```

**ViewPager**：

 1.创建一个Holder:

```java
public class ViewPagerHolder extends ViewPagerHolderBase<String> {
	
	private Context context;
	private TextView textView;
	
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
		
	}

}
```   
2.代码中使用: 
```java
        ViewPager viewPager=(ViewPager) findViewById(R.id.vp);
		ViewPagerAdapter<String> adapter = new ViewPagerAdapter<String>();
		adapter.setViewHolderClass(ViewPagerHolder.class);
		viewPager.setAdapter(adapter);

        //追加数据
        for (int i = 0; i < 20; i++) {
			adapter.append(i + "");
		}
``` 

3.无限循环:

```java
        adapter.setInfinite(true);

        //使开始可以向左滑动，必须在加载数据之后调用
		viewPager.setCurrentItem(adapter.getInfinitePosition());
``` 
 

**动画（装饰者模式）**：

1.ListView:
```java
        AnimationAdapter animationAdapter = new ScaleInAnimationAdapter(adapter);
		animationAdapter.setAbsListView(listView);
		
		listView.setAdapter(animationAdapter);
```
2.RecyclerView(支持到23.1.1)

```java
        AnimatorAdapter adapterWrapper = new SlideInBottomAnimatorAdapter(adapter, recyclerView);
		recyclerView.setAdapter(adapterWrapper);
```


#About me
Email:735506404@robinx.net<br>
Blog:[www.robinx.net](http://www.robinx.net)


