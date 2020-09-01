package com.fenght.wanandroid.base;

import android.util.Log;

import com.fenght.wanandroid.weight.ProgressDialog;

import java.lang.ref.SoftReference;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;

public class BasePresenter<V extends IBaseView, M extends BaseModel> implements IBasePresenter{
    private SoftReference<IBaseView> mReferenceView;
    private V mProxyView;
    private M mModel;

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

    public void dissDialog() {
        ProgressDialog.cancleDialog();
    }

    @Override
    public void detach() {
        mReferenceView.clear();
        mReferenceView = null;
    }
}
