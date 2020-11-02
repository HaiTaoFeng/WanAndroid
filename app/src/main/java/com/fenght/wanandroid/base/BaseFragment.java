package com.fenght.wanandroid.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fenght.wanandroid.inject.InjectPresenter;
import com.fenght.wanandroid.weight.ProgressDialog;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import me.jessyan.autosize.internal.CustomAdapt;

public abstract class BaseFragment extends Fragment implements IBaseView, CustomAdapt {
    private List<BasePresenter> mInjectPresenters;
    private View view;
    protected abstract @LayoutRes int setLayout();

    protected abstract void initViews(@Nullable Bundle savedInstanceState);
    protected abstract void initData();
    protected <T extends View> T $(@IdRes int viewId){
        return this.getView().findViewById(viewId);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //避免重复创建View
        if (view == null) {
            view = inflater.inflate(setLayout(), container, false);
        }
        mInjectPresenters = new ArrayList<>();

        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field:fields) {
            InjectPresenter injectPresenter = field.getAnnotation(InjectPresenter.class);
            if (injectPresenter != null) {
                try{
                    Class<? extends BasePresenter> type = (Class<? extends BasePresenter>) field.getType();
                    BasePresenter mInjectPresenter = type.newInstance();
                    mInjectPresenter.attach(this);
                    field.setAccessible(true);
                    field.set(this,mInjectPresenter);
                    mInjectPresenters.add(mInjectPresenter);
                }catch (IllegalAccessException | java.lang.InstantiationException e) {
                    e.printStackTrace();
                }catch (ClassCastException e){
                    e.printStackTrace();
                    throw new RuntimeException("SubClass must extends Class:BasePresenter");
                }
            }
        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(savedInstanceState);
        initData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        for (BasePresenter basePresenter:mInjectPresenters) {
            basePresenter.detach();
        }
        mInjectPresenters.clear();
        mInjectPresenters = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (view != null && view.getParent() != null) {
            //移除view，防止重复加载
            ((ViewGroup) view.getParent()).removeView(view);
        }
    }

    public View getView(){
        return view;
    }

    @Override
    public void showDialog(String s) {
        ProgressDialog.showDialog(getContext());
    }

    @Override
    public void dissDialog() {
        ProgressDialog.cancleDialog();
    }

    @Override
    public boolean isBaseOnWidth() {
        return true;
    }

    @Override
    public float getSizeInDp() {
        return 360;
    }
}
