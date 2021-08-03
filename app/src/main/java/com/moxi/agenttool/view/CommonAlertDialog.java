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
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.moxi.agenttool.R;


public class CommonAlertDialog extends Dialog {

	public interface AlertDialogUser {
		void onResult(boolean confirmed, Bundle bundle);
	}

	private String title;
	private String msg;
	private AlertDialogUser user;
	private Bundle bundle;
	private boolean showCancel = false;




	public CommonAlertDialog(Context context, String title, AlertDialogUser user) {
		super(context);
		this.title = title;
		this.user=user;
		this.setCanceledOnTouchOutside(true);
	}



	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.layout_dialog_hint);

		TextView cancel = (TextView)findViewById(R.id.btn_cancel);
		final TextView tvHint = (TextView) findViewById(R.id.tv_hint);
		final PasswordToggleEditText tvPwd = (PasswordToggleEditText)findViewById(R.id.tv_pwd);
		final PasswordToggleEditText tvPwdTwo = (PasswordToggleEditText)findViewById(R.id.tv_pwd_two);
		TextView ok = (TextView)findViewById(R.id.btn_ok);
		TextView titleView = (TextView) findViewById(R.id.title);
		setTitle(title);
		
		View.OnClickListener listener = new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (view.getId() == R.id.btn_ok) {
					if(tvPwd.getText().toString().equals(tvPwdTwo.getText().toString())){
						onOk(view);
					}else {
						tvHint.setText("两次输入的密钥不一致，请重新输入");
						tvHint.setTextColor(Color.parseColor("#FF3B30"));
					}
				} else if (view.getId() == R.id.btn_cancel) {
					onCancel(view);
				}
			}
		};
		cancel.setOnClickListener(listener);
		ok.setOnClickListener(listener);

		if (title != null)
		    titleView.setText(title);
		
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
