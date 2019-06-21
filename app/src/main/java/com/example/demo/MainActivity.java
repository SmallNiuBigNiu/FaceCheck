package com.example.demo;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.json.Location;

public class MainActivity extends AppCompatActivity {
    private static final int GET_TOKEN = 1001;
    private static final int GET_FACE_LOCATION = 1002;
    private Button btnGetToekn;
    private TextView tvToken;
    private Handler handler;
    private String token;
    private final  String TOKEN = "24.aa1d6eb3fd50c89eaa264928a6f7b44d.2592000.1563496349.282335-9533086";
    private Button btnDetect;
    private Location location;
    private MyImageView ivImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ivImage = findViewById(R.id.iv_image);

        btnGetToekn = findViewById(R.id.btn_get_token);
        tvToken = findViewById(R.id.tv_token);
        btnDetect = findViewById(R.id.btn_detect);
        btnDetect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String yangchaoyueFilePath = "/mnt/shell/emulated/0/Images/xuezhiqian.png";
                        byte[] yangchaoyuebytearr = Base64Util.image2byte(yangchaoyueFilePath);
                        String yangchaoyueBase64 = Base64Util.encode(yangchaoyuebytearr);
                        Log.e("BASE64", yangchaoyueBase64);
                        location = FaceDetect.detect(yangchaoyueBase64);
                        handler.sendEmptyMessage(GET_FACE_LOCATION);
                    }
                }).start();
            }
        });

        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if(msg.what == GET_TOKEN){
                    tvToken.setText(token);
                    return true;
                }
                if(msg.what ==  GET_FACE_LOCATION){
                    ivImage.showFace(location);
                    return true;
                }
                return false;
            }
        });

        btnGetToekn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        token = AuthService.getAuth();
                        handler.sendEmptyMessage(GET_TOKEN);
                    }
                }).start();
            }
        });
    }
}
