package went_gine.applicationpassword;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.List;

/**
 *describe: 支付密码的弹窗模式
 *author: Went_Gone
 *create on: 2016/10/24
 */
public class PasswordDialog extends AlertDialog implements View.OnClickListener{
    private ItemPasswordLayout passwordLayout;
    private GridView gridView;
    private String[] numbers = {"1","2","3","4","5","6","7","8","9",null,"0",null};
    private MyAdapter mAdapter;
    private TextView tvCancle,tvSure;

    private Context context;
    public PasswordDialog(Context context) {
        super(context, R.style.NoDialogTitle);
        this.context = context;
        setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_password_layout);
        initViews();
        setListener();

        setShowStyle();
    }

    private void initViews() {
        passwordLayout = (ItemPasswordLayout) findViewById(R.id.dialog_password_layout_IPLayout);
        gridView = (GridView) findViewById(R.id.dialog_password_layout_gridview);
        tvCancle = (TextView) findViewById(R.id.dialog_password_layout_TV_cancle);
        tvSure = (TextView) findViewById(R.id.dialog_password_layout_TV_sure);
    }

    private void setListener() {
        mAdapter = new MyAdapter();
        gridView.setAdapter(mAdapter);
        tvCancle.setOnClickListener(this);
        tvSure.setOnClickListener(this);
    }

    public void setShowStyle(){
        Window window = this.getWindow();
        // 可以在此设置显示动画
        WindowManager.LayoutParams wl = window.getAttributes();
        WindowManager wm = (WindowManager) getContext()
                .getSystemService(Context.WINDOW_SERVICE);
        wl.x = 0;
        wl.y = wm.getDefaultDisplay().getHeight()/2;
        // 以下这两句是为了保证按钮可以水平满屏
        wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
        wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        // 设置显示位置
        this.onWindowAttributesChanged(wl);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            this.dismiss();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.dialog_password_layout_TV_cancle:
                dismiss();
                break;
            case R.id.dialog_password_layout_TV_sure:
                if (viewClickListener!=null){
                    viewClickListener.click();
                }
                break;
        }
    }

    private class MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return numbers.length;
        }

        @Override
        public String getItem(int i) {
            if (i!=9 && i!=11){
                return numbers[i];
            }else {
                return null;
            }
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public int getViewTypeCount() {
            return 3;
        }

        @Override
        public int getItemViewType(int position) {
            if (position!=9 && position!=11){
                return 0;
            }else if (position == 9){
                return 1;
            }else {
                return 2;
            }
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            int itemViewType = getItemViewType(i);
            switch (itemViewType){
                case 0:
                    //类似于键盘数字键
                    view = LayoutInflater.from(context).inflate(R.layout.item_gride,null);
                    TextView tv= (TextView) view.findViewById(R.id.btn_keys);
                    tv.setText(getItem(i));
                    tv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            passwordLayout.setContent(getItem(i));
                        }
                    });
                    break;
                case 1:
                    view = LayoutInflater.from(context).inflate(R.layout.item_gride,null);
                    break;
                case 2:
                    //类似于键盘的删除键
                    view = LayoutInflater.from(context).inflate(R.layout.item_gride_imge,null);
                    ImageView imageView = (ImageView) view.findViewById(R.id.image_keys);
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            passwordLayout.onKeyDelete();
                        }
                    });
                    break;
            }
            return view;
        }
    }
    public String getPassword(){
        String password = null;
        if (passwordLayout.getStrPassword()!=null && passwordLayout.getStrPassword().length()<=6){
            password = passwordLayout.getStrPassword();
        }
        return password;
    }

    private ViewClickListener viewClickListener;

    public void setViewClickListener(ViewClickListener viewClickListener) {
        this.viewClickListener = viewClickListener;
    }

    public interface ViewClickListener{
        void click();
    }
}
