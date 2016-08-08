package cn.ucai.fulicenter.task;

import android.content.Context;
import android.content.Intent;
import android.os.Message;
import android.util.Log;

import java.util.List;
import java.util.Map;

import cn.ucai.fulicenter.FuliCenterApplication;
import cn.ucai.fulicenter.bean.MessageBean;
import cn.ucai.fulicenter.bean.Result;
import cn.ucai.fulicenter.bean.UserAvatar;
import cn.ucai.fulicenter.data.OkHttpUtils2;
import cn.ucai.fulicenter.utils.I;
import cn.ucai.fulicenter.utils.Utils;

/**
 * Created by Administrator on 2016/7/20.
 */
public class DownloadCollectCountTask {
    private final static String TAG=DownloadCollectCountTask.class.getSimpleName();
    String username;
    Context mcontext;

    public DownloadCollectCountTask(String username, Context mcontext) {
        this.username = username;
        this.mcontext = mcontext;
    }
    public void execute(){
        final OkHttpUtils2<MessageBean> utils=new OkHttpUtils2<MessageBean>();
        utils.setRequestUrl(I.REQUEST_FIND_COLLECT_COUNT)
                .addParam(I.Collect.USER_NAME,username)
                .targetClass(MessageBean.class)
                .execute(new OkHttpUtils2.OnCompleteListener<MessageBean>() {
                    @Override
                    public void onSuccess(MessageBean msg) {
                        if (msg!=null){
                            FuliCenterApplication.getInstance().setCollectCount(Integer.valueOf(msg.getMsg()));
                        }else {
                            FuliCenterApplication.getInstance().setCollectCount(0);
                        }
                        mcontext.sendStickyBroadcast(new Intent("update_collect"));
                    }

                    @Override
                    public void onError(String error) {

                    }
                });
    }
}
