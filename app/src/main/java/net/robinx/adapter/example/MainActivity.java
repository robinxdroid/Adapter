package net.robinx.adapter.example;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;

public class MainActivity extends AppCompatActivity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		init();
	}

	private void init() {
		findViewById(R.id.btn_lv).setOnClickListener(this);
		findViewById(R.id.btn_rcv).setOnClickListener(this);
		findViewById(R.id.btn_rcv_wrapper).setOnClickListener(this);
		findViewById(R.id.btn_view_pager).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_lv:
			startActivity(new Intent(this, ABSActivity.class));
			break;

		case R.id.btn_rcv:
			startActivity(new Intent(this, RecyclerActivity.class));
			break;
		case R.id.btn_rcv_wrapper:
			startActivity(new Intent(this, RecyclerHeaderAndFooterActivity.class));
			break;
		case R.id.btn_view_pager:
			startActivity(new Intent(this, ViewPagerActivity.class));
			break;
		}
	}

}
