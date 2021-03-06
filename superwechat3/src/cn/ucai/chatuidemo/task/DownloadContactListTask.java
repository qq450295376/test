package cn.ucai.chatuidemo.task;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.List;
import java.util.Map;

import cn.ucai.chatuidemo.SuperWeChatApplication;
import cn.ucai.chatuidemo.bean.Result;
import cn.ucai.chatuidemo.bean.UserAvatar;
import cn.ucai.chatuidemo.data.OkHttpUtils2;
import cn.ucai.chatuidemo.utils.I;
import cn.ucai.chatuidemo.utils.Utils;

/**
 * Created by Administrator on 2016/7/20.
 */
public class DownloadContactListTask {
    private final static String TAG=DownloadContactListTask.class.getSimpleName();
    String username;
    Context mcontext;

    public DownloadContactListTask(String username, Context mcontext) {
        this.username = username;
        this.mcontext = mcontext;
    }
    public void execute(){
        final OkHttpUtils2<String> utils=new OkHttpUtils2<String>();
        utils.setRequestUrl(I.REQUEST_DOWNLOAD_CONTACT_ALL_LIST)
                .addParam(I.Contact.USER_NAME,username)
                .targetClass(String.class)
                .execute(new OkHttpUtils2.OnCompleteListener<String>() {
                    @Override
                    public void onSuccess(String s) {
                        Log.e(TAG,"s="+s);
                        Result result = Utils.getListResultFromJson(s, UserAvatar.class);
                        Log.e(TAG,"result="+result);
                        List<UserAvatar> list= (List<UserAvatar>) result.getRetData();
                        if (list!=null && list.size()>0){
                            Log.e(TAG,"list.size="+list);
                            SuperWeChatApplication.getInstance().setList(list);
                            mcontext.sendStickyBroadcast(new Intent("update_contact_list"));
                            Map<String,UserAvatar> userMap=SuperWeChatApplication.getInstance().getUserMap();
                            for (UserAvatar u : list){
                                userMap.put(u.getMUserName(),u);
                            }
                        }
                    }

                    @Override
                    public void onError(String error) {

                    }
                });
    }
}
