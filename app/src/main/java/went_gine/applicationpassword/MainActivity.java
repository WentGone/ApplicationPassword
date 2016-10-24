package went_gine.applicationpassword;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
/**
 *describe: 程序界面
 *author: Went_Gone
 *create on: 2016/10/24
 */
public class MainActivity extends AppCompatActivity {
    private PasswordDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FloatingActionButton actionButton = (FloatingActionButton) findViewById(R.id.main_floationgActionButton);
        dialog = new PasswordDialog(MainActivity.this);
        dialog.setViewClickListener(new PasswordDialog.ViewClickListener() {
            @Override
            public void click() {
                Toast.makeText(MainActivity.this, "password is："+dialog.getPassword(), Toast.LENGTH_SHORT).show();
            }
        });
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!dialog.isShowing()) {
                    dialog.show();
                }
            }
        });
    }
}
