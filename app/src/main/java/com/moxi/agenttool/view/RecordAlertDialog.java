/**
 * Copyright (C) 2016 Hyphenate Inc. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.moxi.agenttool.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.moxi.agenttool.R;


public class RecordAlertDialog extends Dialog {

	private TextView tvHint;

	public interface AlertDialogUser {
		void onResult(boolean confirmed, Bundle bundle);
	}

	private String title;
	private String msg;
	private AlertDialogUser user;
	private Bundle bundle;
	private boolean showCancel = false;



	public void setTvHint(String s) {
		tvHint.setText(s);
	}

	public RecordAlertDialog(Context context, AlertDialogUser user) {
		super(context);
		this.user=user;
		this.setCanceledOnTouchOutside(true);
	}



	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.layout_record_dialog);
		Window dialogWindow = getWindow();
		dialogWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		TextView cancel = (TextView)findViewById(R.id.btn_cancel);
		 tvHint = (TextView) findViewById(R.id.tv_hint);
		TextView ok = (TextView)findViewById(R.id.btn_ok);
		TextView titleView = (TextView) findViewById(R.id.title);

		View.OnClickListener listener = new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (view.getId() == R.id.btn_ok) {
						onOk(view);
				} else if (view.getId() == R.id.btn_cancel) {
					onCancel(view);
				}
			}
		};
		cancel.setOnClickListener(listener);
		ok.setOnClickListener(listener);


		if (showCancel) {
			cancel.setVisibility(View.VISIBLE);
		}

	}





	
	public void onOk(View view){
		this.dismiss();
		if (this.user != null) {
			this.user.onResult(true, this.bundle);
		}
	}
	
	public void onCancel(View view) {
		this.dismiss();
	}
}
