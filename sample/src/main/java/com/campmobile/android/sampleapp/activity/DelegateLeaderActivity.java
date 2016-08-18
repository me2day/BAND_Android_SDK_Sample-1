package com.campmobile.android.sampleapp.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.VolleyError;
import com.campmobile.android.bandsdk.BandManager;
import com.campmobile.android.bandsdk.BandManagerFactory;
import com.campmobile.android.bandsdk.api.ApiCallbacks;
import com.campmobile.android.bandsdk.constant.ApiSpecificError;
import com.campmobile.android.sampleapp.R;
import com.campmobile.android.sampleapp.SampleConstants;

public class DelegateLeaderActivity extends BaseToolbarActivity {
	private BandManager bandManager = null;
	private String bandKey;
	private String userKey;

	private EditText bandKeyEditText;
	private EditText userKeyEditText;
	private Button delegateLeaderBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_guild_delegate_leader);

		initParam();
		initUI();
		setToolbar(R.string.title_guild_delegate_leader);
	}

	private void initParam() {
		bandManager = BandManagerFactory.getSingleton();
		bandKey = getIntent().getStringExtra(SampleConstants.ParameterKey.BAND_KEY);
		userKey = getIntent().getStringExtra(SampleConstants.ParameterKey.USER_KEY);
	}

	private void initUI() {
		bandKeyEditText = (EditText) findViewById(R.id.band_key_edit_text);
		if(bandKey != null && bandKey.length() > 0){
			bandKeyEditText.setText(bandKey);
		}
		userKeyEditText = (EditText) findViewById(R.id.user_key_edit_text);
		if(userKey != null && userKey.length() > 0){
			userKeyEditText.setText(userKey);
		}
		delegateLeaderBtn = (Button) findViewById(R.id.delegate_leader_btn);

		delegateLeaderBtn.setOnClickListener(onClickListener);
	}

	private View.OnClickListener onClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			bandManager.delegateLeader(bandKeyEditText.getText().toString(), userKeyEditText.getText().toString(), new ApiCallbacks<Void>() {
				@Override
				public void onResponse(Void response) {
					showDialog(R.string.success, "success");
				}

				@Override
				public void onError(VolleyError error) {
					super.onError(error);
					showDialog(R.string.fail, "fail");
				}

				@Override
				public void onApiSpecificResponse(ApiSpecificError apiSpecificError, String message) {
					super.onApiSpecificResponse(apiSpecificError, message);
					showDialog(R.string.fail, "fail ApiSpecificError" + apiSpecificError.getErrorCode());
				}
			});
		}
	};
}
