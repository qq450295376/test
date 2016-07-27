package cn.ucai.chatuidemo.task;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.List;
import java.util.Map;

import cn.ucai.chatuidemo.SuperWeChatApplication;
import cn.ucai.chatuidemo.bean.GroupAvatar;
import cn.ucai.chatuidemo.bean.Result;
import cn.ucai.chatuidemo.bean.UserAvatar;
import cn.ucai.chatuidemo.data.OkHttpUtils2;
import cn.ucai.chatuidemo.utils.I;
import cn.ucai.chatuidemo.utils.Utils;

/**
 * Created by Administrator on 2016/7/20.
 */
public class DownloadGroupListTask {
    private final static String TAG=DownloadGroupListTask.class.getSimpleName();
    String username;
    Context mcontext;

    public DownloadGroupListTask(String username, Context mcontext) {
        this.username = username;
        this.mcontext = mcontext;
    }
    public void execute(){
        final OkHttpUtils2<String> utils=new OkHttpUtils2<String>();
        utils.setRequestUrl(I.REQUEST_FIND_GROUP_BY_USER_NAME)
                .addParam(I.User.USER_NAME,username)
                .targetClass(String.class)
                .execute(new OkHttpUtils2.OnCompleteListener<String>() {
                    @Override
                    public void onSuccess(String s) {
                        Log.e(TAG,"s="+s);
                        Result result = Utils.getListResultFromJson(s, GroupAvatar.class);
                        Log.e(TAG,"result="+result);
                        List<GroupAvatar> list= (List<GroupAvatar>) result.getRetData();
                        if (list!=null && list.size()>0){
                            Log.e(TAG,"list.size="+list);
                            SuperWeChatApplication.getInstance().setGroupList(list);
                            mcontext.sendStickyBroadcast(new Intent("update_group_list"));
                            for (GroupAvatar g : list){
                                SuperWeChatApplication.getInstance().getGroupMap().put(g.getMGroupHxid(), g);
                            }

                        }
                    }

                    @Override
                    public void onError(String error) {

                    }
                });
    }
}
