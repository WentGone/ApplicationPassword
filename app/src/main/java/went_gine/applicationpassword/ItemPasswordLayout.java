package went_gine.applicationpassword;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

/**
 *describe:  密码输入框的控件布局
 *author: Went_Gone
 *create on: 2016/10/24
 */
public class ItemPasswordLayout extends RelativeLayout{
    private EditText editText;
    private PasswordFrameView[] PasswordFrameViews;//使用一个数组存储密码框
    private StringBuffer stringBuffer = new StringBuffer();//存储密码字符
    private int count = 6;
    private String strPassword;//密码字符串

    public ItemPasswordLayout(Context context) {
        this(context,null);
    }

    public ItemPasswordLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ItemPasswordLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        PasswordFrameViews = new PasswordFrameView[6];
        View view = View.inflate(context, R.layout.item_password,this);

        editText = (EditText) findViewById(R.id.item_edittext);
        PasswordFrameViews[0] = (PasswordFrameView) findViewById(R.id.passwordframeView0);
        PasswordFrameViews[1] = (PasswordFrameView) findViewById(R.id.passwordframeView1);
        PasswordFrameViews[2] = (PasswordFrameView) findViewById(R.id.passwordframeView2);
        PasswordFrameViews[3] = (PasswordFrameView) findViewById(R.id.passwordframeView3);
        PasswordFrameViews[4] = (PasswordFrameView) findViewById(R.id.passwordframeView4);
        PasswordFrameViews[5] = (PasswordFrameView) findViewById(R.id.passwordframeView5);

        editText.setCursorVisible(false);//将光标隐藏
        setListener();
    }

    private void setListener() {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                //重点   如果字符不为""时才进行操作
                if (!editable.toString().equals("")) {
                    if (stringBuffer.length()>5){
                        //当密码长度大于5位时edittext置空
                        editText.setText("");
                        return;
                    }else {
                        //将文字添加到StringBuffer中
                        stringBuffer.append(editable);
                        editText.setText("");//添加后将EditText置空  造成没有文字输入的错局
                        Log.e("TAG", "afterTextChanged: stringBuffer is "+stringBuffer);
                        count = stringBuffer.length();//记录stringbuffer的长度
                        strPassword = stringBuffer.toString();
                        if (stringBuffer.length()==6){
                            //文字长度位6   则调用完成输入的监听
                            if (inputCompleteListener!=null){
                                inputCompleteListener.inputComplete();
                            }
                        }
                    }

                    for (int i =0;i<stringBuffer.length();i++){
                        PasswordFrameViews[i].invalidatePassword(true);
                    }
                }
            }
        });
        editText.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_DEL
                        && event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (onKeyDelete()) return true;
                    return true;
                }
                return false;
            }
        });
    }

    public boolean onKeyDelete() {
        if (count==0){
            count = 6;
            return true;
        }
        if (stringBuffer.length()>0){
            //删除相应位置的字符
            stringBuffer.delete((count-1),count);
            count--;
            Log.e("TAG", "afterTextChanged: stringBuffer is "+stringBuffer);
            strPassword = stringBuffer.toString();
            PasswordFrameViews[stringBuffer.length()].invalidatePassword(false);

        }
        return false;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }

    private InputCompleteListener inputCompleteListener;

    public void setInputCompleteListener(InputCompleteListener inputCompleteListener) {
        this.inputCompleteListener = inputCompleteListener;
    }

    public interface InputCompleteListener{
        void inputComplete();
    }

    public EditText getEditText() {
        return editText;
    }

    /**
     * 获取密码
     * @return
     */
    public String getStrPassword() {
        return strPassword;
    }

    public void setContent(String content){
        editText.setText(content);
    }
}
