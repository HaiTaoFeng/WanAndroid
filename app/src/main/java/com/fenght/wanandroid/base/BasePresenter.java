package com.fenght.wanandroid.base;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.fenght.wanandroid.utils.LogUtil;
import com.fenght.wanandroid.weight.ProgressDialog;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;

import androidx.annotation.NonNull;

public class BasePresenter<V extends IBaseView, M extends BaseModel> implements IBasePresenter{
    private SoftReference<IBaseView> mReferenceView;
    private Message msg;
    private V mProxyView;
    private M mModel;

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            dissDialog();
            switch (msg.what){
                case 0: //接口调用失败
                    String s = (String)msg.obj;
                    LogUtil.e(s);
                    getView().error(s);
                    break;
                case 1: //转换文章数据
                    getView().succeed(msg.obj);
                    break;
            }
            return false;
        }
    });

    public V getView(){
        return mProxyView;
    }

    protected M getModel(){
        return mModel;
    }

    @Override
    public void attach(IBaseView view) {
        //使用软引用创建对象
        mReferenceView = new SoftReference<>(view);
        //使用动态代理做统一的逻辑判断app思想
        mProxyView = (V) Proxy.newProxyInstance(view.getClass().getClassLoader(), view.getClass().getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                if (mReferenceView == null || mReferenceView.get() == null) {
                    return null;
                }
                return method.invoke(mReferenceView.get(),args);
            }
        });

        //通过获得泛型类的父类，拿到泛型的接口类实例，通过反射来实例化 model
        ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
        Log.d("===========", "attach: "+this.getClass().getSimpleName());
        if (type != null) {
            Type[] types = type.getActualTypeArguments();
            try {
                mModel = (M) ((Class<?>) types[1]).newInstance();
            }catch (IllegalAccessException | InstantiationException e){
                e.printStackTrace();
            }
        }
    }

    public void showDialog() {
        ProgressDialog.showDialog(getView().getContext());
    }

    public static void dissDialog() {
        ProgressDialog.cancleDialog();
    }

    /**
     * 通过Handler处理数据
     * @param obj 数据
     * @param what 标记，0为失败，1为成功
     * @param <T>
     */
    public <T> void pushData (T obj, int what){
        msg = handler.obtainMessage(what,obj);
        handler.sendMessage(msg);
    }

    @Override
    public void detach() {
        mReferenceView.clear();
        mReferenceView = null;
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
            handler = null;
        }
    }


}
