package com.fenght.wanandroid.weight;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.fenght.wanandroid.R;
import com.fenght.wanandroid.adapter.CommonRAdapter;

import java.util.List;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class PopupDialog {
    private Context context;
    private PopupWindow window;
    private AppCompatTextView tv_title;
    private RecyclerView rv_list;
    private PopupAdapter adapter;
    private AdapterListener adapterListener;

    //默认宽度与屏幕宽度一样，高度自适应
    public PopupDialog(Context context,View contentView) {
        this.context = context;
        // 创建PopupWindow对象，其中：
        // 第一个参数是用于PopupWindow中的View，第二个参数是PopupWindow的宽度，
        // 第三个参数是PopupWindow的高度，第四个参数指定PopupWindow能否获得焦点
        if (window == null) {
            window =new PopupWindow(contentView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        }
        //设置动画
        window.setAnimationStyle(R.style.pop_Anim);
        // 设置PopupWindow的背景
        window.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        // 设置PopupWindow是否能响应外部点击事件
        window.setOutsideTouchable(true);
        // 设置PopupWindow是否能响应点击事件
        window.setTouchable(true);
    }


    /**
     * 新建一个带默认列表的popupWindow
     * 默认宽度与屏幕宽度一样，高度自适应
     * @param context
     */
    public <T>PopupDialog(Context context, List<String> list) {
        this.context = context;
        // 用于PopupWindow的View
        View contentView= LayoutInflater.from(context).inflate(R.layout.popuwindow_default_list, null, false);
        tv_title = contentView.findViewById(R.id.tv_title); //标题
        rv_list = contentView.findViewById(R.id.rv_list); //列表
        rv_list.setLayoutManager(new LinearLayoutManager(context));
        if (adapter == null) {
            adapter = new PopupAdapter(context,list,R.layout.adapter_popup_dialog);
            rv_list.setAdapter(adapter);
        }else{
            adapter.refresh(list);
        }
        // 创建PopupWindow对象，其中：
        // 第一个参数是用于PopupWindow中的View，第二个参数是PopupWindow的宽度，
        // 第三个参数是PopupWindow的高度，第四个参数指定PopupWindow能否获得焦点
        if (window == null) {
            window =new PopupWindow(contentView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        }
        //设置动画
        window.setAnimationStyle(R.style.pop_Anim);
        // 设置PopupWindow的背景
        window.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        // 设置PopupWindow是否能响应外部点击事件
        window.setOutsideTouchable(true);
        // 设置PopupWindow是否能响应点击事件
        window.setTouchable(true);
    }


    /**
     * 弹出弹窗，默认从底部弹出
     * @param view 点击弹出dialog的控件
     * @param gravity 方位
     */
    public void showPopupDialog(View view,int gravity){
        // 第一个参数是PopupWindow的锚点，第二和第三个参数分别是PopupWindow相对锚点的x、y偏移
//                window.showAsDropDown(fab, 0, 0);
        // 第一个参数是PopupWindow的父View，第二个参数是PopupWindow相对父View的位置，
        // 第三和第四个参数分别是PopupWindow相对父View的x、y偏移
        window.showAtLocation(view,gravity, 0, 0);
    }

    public void dismiss(){
        window.dismiss();
    }


    //设置自定义布局
    public void setContentView(View view){
        window.setContentView(view);
    }

    /**
     * 设置弹窗长宽
     * @param width
     * @param height
     */
    public void setWidthHeight(int width,int height){
        window.setWidth(width);
        window.setHeight(height);
    }
    /**
     * 设置背景
     * @param colorid 颜色的 Rid
     */
    public void setBackgroundDrawable(int colorid){
        // 设置PopupWindow的背景
        window.setBackgroundDrawable(new ColorDrawable(colorid));
    }

    /**
     * 设置动画
     * @param id R.id
     */
    public void setAnimationStyle(int id){
        window.setAnimationStyle(id);
    }

    /**
     * 设置背景透明度
     * @param alpha 透明值0~1。 当值为1时，背景透明
     */
    private void setBackgroundAlpha(Activity activity,float alpha){
        WindowManager.LayoutParams lp=activity.getWindow().getAttributes();
        lp.alpha = alpha;
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        activity.getWindow().setAttributes(lp);
    }

    /**
     * 设置item监听
     * @param itemClickListener
     */
    public void setAdapterListener(CommonRAdapter.OnItemClickListener itemClickListener){
        adapter.setOnItemClickListener(itemClickListener);
    }

    public interface AdapterListener{
        void itemListener(String data);
    }


    static class PopupAdapter extends CommonRAdapter<String>{
        private setItemListener itemListener;

        public PopupAdapter(Context context, List<String> list,int id) {
            super(context,list,id);
        }

        @Override
        public void convert(CommonViewHolder holder, final String itemData, final int postion) {
            holder.setText(R.id.tv_textview,itemData);
//            holder.itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    itemListener.setitemListener(itemData,postion);
//                }
//            });
        }

        public void setItemListener(setItemListener itemListener){
            this.itemListener = itemListener;
        }

        @Override
        public void setOnItemClickListener(OnItemClickListener itemClickListener) {
            super.setOnItemClickListener(itemClickListener);
        }

        @Override
        public int getItemCount() {
            return super.getItemCount();
        }

        //自定义回调方法
        private interface setItemListener{
            void setitemListener(String itemData,int postion);
        }
    }
}
