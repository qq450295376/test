/**
 * Copyright (C) 2013-2014 EaseMob Technologies. All rights reserved.
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
package cn.ucai.fulicenter.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.easemob.chat.EMGroup;
import com.easemob.chat.EMGroupManager;
import cn.ucai.fulicenter.R;
import com.easemob.exceptions.EaseMobException;

import java.io.File;

import cn.ucai.fulicenter.FuliCenterApplication;
import cn.ucai.fulicenter.bean.GroupAvatar;
import cn.ucai.fulicenter.bean.Result;
import cn.ucai.fulicenter.data.OkHttpUtils2;
import cn.ucai.fulicenter.listener.OnSetAvatarListener;
import cn.ucai.fulicenter.utils.I;
import cn.ucai.fulicenter.utils.Utils;

public class NewGroupActivity extends BaseActivity {
	private EditText groupNameEditText;
	private ProgressDialog progressDialog;
	private EditText introductionEditText;
	private CheckBox checkBox;
	private CheckBox memberCheckbox;
	private LinearLayout openInviteContainer;
	private String AvatartName=String.valueOf(System.currentTimeMillis());

	private static int CREATE_GROUP=100;
	private EditText avatar;
	OnSetAvatarListener mOnSetAvatarListener;
	private ImageView ivAvatar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_group);
		groupNameEditText = (EditText) findViewById(R.id.edit_group_name);
		introductionEditText = (EditText) findViewById(R.id.edit_group_introduction);
		checkBox = (CheckBox) findViewById(R.id.cb_public);
		memberCheckbox = (CheckBox) findViewById(R.id.cb_member_inviter);
		openInviteContainer = (LinearLayout) findViewById(R.id.ll_open_invite);
		avatar= (EditText) findViewById(R.id.avatar);
		ivAvatar= (ImageView) findViewById(R.id.iv_avatar);

		checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked){
					openInviteContainer.setVisibility(View.INVISIBLE);
				}else{
					openInviteContainer.setVisibility(View.VISIBLE);
				}
			}
		});
		setListener();
	}

	private void setListener() {
		findViewById(R.id.avatar).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				mOnSetAvatarListener=new OnSetAvatarListener(NewGroupActivity.this,R.id.layout_group,AvatartName, I.AVATAR_TYPE_GROUP_PATH);
			}
		});
	}

	/**
	 * @param v
	 */
	public void save(View v) {
		String str6 = getResources().getString(R.string.Group_name_cannot_be_empty);
		String name = groupNameEditText.getText().toString();
		if (TextUtils.isEmpty(name)) {
			Intent intent = new Intent(this, AlertDialog.class);
			intent.putExtra("msg", str6);
			startActivity(intent);
		} else {
			// 进通讯录选人
			startActivityForResult(new Intent(this, GroupPickContactsActivity.class).putExtra("groupName", name), CREATE_GROUP);
		}
	}
	String st1;
	String st2;
	@Override
	protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		st1 = getResources().getString(R.string.Is_to_create_a_group_chat);
		st2 = getResources().getString(R.string.Failed_to_create_groups);
		if (resultCode!=RESULT_OK){
			return;
		}
		mOnSetAvatarListener.setAvatar(requestCode,data,ivAvatar);
		if (requestCode==OnSetAvatarListener.REQUEST_CROP_PHOTO){

		}
		if (requestCode == CREATE_GROUP) {
			//新建群组
			setProgressDialog();

			new Thread(new Runnable() {
				@Override
				public void run() {
					// 调用sdk创建群组方法
					String groupName = groupNameEditText.getText().toString().trim();
					String desc = introductionEditText.getText().toString();
					String[] members = data.getStringArrayExtra("newmembers");
					EMGroup group;
					try {
						if(checkBox.isChecked()){
							//创建公开群，此种方式创建的群，可以自由加入
							//创建公开群，此种方式创建的群，用户需要申请，等群主同意后才能加入此群
						    group=EMGroupManager.getInstance().createPublicGroup(groupName, desc, members, true,200);
						}else{
							//创建不公开群
						    group=EMGroupManager.getInstance().createPrivateGroup(groupName, desc, members, memberCheckbox.isChecked(),200);
						}
						createAppGroup(group.getGroupId(),groupName,desc,members);
						runOnUiThread(new Runnable() {
							public void run() {
								progressDialog.dismiss();
								setResult(RESULT_OK);
								finish();
							}
						});
					} catch (final EaseMobException e) {
						runOnUiThread(new Runnable() {
							public void run() {
								progressDialog.dismiss();
								Toast.makeText(NewGroupActivity.this, st2 + e.getLocalizedMessage(), 1).show();
							}
						});
					}
					
				}
			}).start();
		}
	}

	private void setProgressDialog() {
		progressDialog = new ProgressDialog(this);
		progressDialog.setMessage(st1);
		progressDialog.setCanceledOnTouchOutside(false);
		progressDialog.show();
	}

	private void createAppGroup(final String groupId, String groupName, String desc, final String[] members) {
		boolean ispublic =checkBox.isChecked();
		boolean invites = !ispublic;
		File file=new File(OnSetAvatarListener.getAvatarPath(NewGroupActivity.this,I.AVATAR_TYPE_GROUP_PATH),AvatartName+I.AVATAR_SUFFIX_JPG);
		String own = FuliCenterApplication.getInstance().getUserName();
		final OkHttpUtils2<String> utils= new OkHttpUtils2<String>();
		utils.setRequestUrl(I.REQUEST_CREATE_GROUP)
				.addParam(I.Group.HX_ID,groupId)
				.addParam(I.Group.NAME,groupName)
				.addParam(I.Group.OWNER,own)
				.addParam(I.Group.DESCRIPTION,desc)
				.addParam(I.Group.IS_PUBLIC,String.valueOf(ispublic))
				.addParam(I.Group.ALLOW_INVITES,String.valueOf(invites))
				.targetClass(String.class)
				.addFile(file)
				.execute(new OkHttpUtils2.OnCompleteListener<String>() {
					@Override
					public void onSuccess(String s) {
						Result result = Utils.getResultFromJson(s, GroupAvatar.class);
						if (result!=null && result.isRetMsg()){
							if (members!=null && members.length>0){
								addGroupMembers(groupId,members);
							}else {
								runOnUiThread(new Runnable() {
									public void run() {
										progressDialog.dismiss();
										setResult(RESULT_OK);
										finish();
									}
								});
							}
						}
					}

					@Override
					public void onError(String error) {

					}
				});
	}

	private void addGroupMembers(String hxid, String[] members) {
		String memberArr="";
		for (String m : members){
			memberArr+=m+",";
		}
		memberArr.substring(0,memberArr.length()-1);
		final OkHttpUtils2<String> utils=new OkHttpUtils2<String>();
		utils.setRequestUrl(I.REQUEST_ADD_GROUP_MEMBERS)
				.addParam(I.Member.GROUP_HX_ID,hxid)
				.addParam(I.Member.USER_NAME,memberArr)
				.targetClass(String.class)
				.execute(new OkHttpUtils2.OnCompleteListener<String>() {
					@Override
					public void onSuccess(String s) {
						Result result = Utils.getResultFromJson(s, GroupAvatar.class);
						GroupAvatar groupAvatar = (GroupAvatar) result.getRetData();
						if (result!=null&&result.isRetMsg()){
							runOnUiThread(new Runnable() {
								public void run() {
									progressDialog.dismiss();
									setResult(RESULT_OK);
									finish();
								}
							});
						}else {
							progressDialog.dismiss();
							Toast.makeText(NewGroupActivity.this,st2,Toast.LENGTH_LONG).show();

						}
					}

					@Override
					public void onError(String error) {

					}
				});
	}

	public void back(View view) {
		finish();
	}
}
