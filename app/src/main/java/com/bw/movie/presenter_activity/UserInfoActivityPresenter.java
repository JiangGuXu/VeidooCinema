package com.bw.movie.presenter_activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.activity.ReSetpasswordActivity;
import com.bw.movie.activity.UserInfoActivity;
import com.bw.movie.activity.UserReSertInfoActivity;
import com.bw.movie.bean.LoginBean;
import com.bw.movie.mvp.view.AppDelage;
import com.bw.movie.utils.encrypt.Base64EncryptUtil;
import com.bw.movie.utils.net.HttpUtil;
import com.bw.movie.utils.net.SharedPreferencesUtils;
import com.bw.movie.wxapi.WXEntryActivity;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;

import static android.app.Activity.RESULT_OK;
import static android.support.v4.app.ActivityCompat.shouldShowRequestPermissionRationale;


/**
 * 2018年12月1日 09:35:22
 * 焦浩康
 * 个人信息activity
 * 注解加的有点晚  其实这个activity在几天前就已经创建了
 * <p>
 * 2018年12月1日 09:36:21
 * 焦浩康
 * 正在添加修改个人信息的功能
 * <p>
 * 2018年12月6日 15:57:43
 * 焦浩康
 * 添加了绑定微信帐号的功能
 */
public class UserInfoActivityPresenter extends AppDelage implements View.OnClickListener {

    private Context context;
    private ImageView user_info_back;
    private SimpleDraweeView user_info_head;
    private TextView user_info_sex;
    private TextView user_info_birthday;
    private TextView user_info_phone;
    private TextView user_info_emil;
    private TextView user_info_name;
    private Boolean isLogin;
    private Button user_info_logout;
    private LinearLayout user_info_layout_4;
    private RelativeLayout user_info_head_layout;
    private PopupWindow popupWindow;
    private LinearLayout user_info_selecte_camera;
    private LinearLayout user_info_selecte_photo;
    private static String path;//sd路径
    private static Bitmap head;//头像Bitmap
    private Bitmap bitmap1;
    private LinearLayout user_info_layout_3;
    private LinearLayout user_info_layout_5;
    private TextView user_info_is_bind_wx_text;
    private ImageView user_info_goto_bind_wx_img;
    private boolean isBindWx = false;
    // APP_ID 替换为你的应用从官方网站申请到的合法appID
    private static final String APP_ID = "wxb3852e6a6b7d9516";

    // IWXAPI 是第三方app和微信通信的openApi接口
    private IWXAPI api;

    @Override
    public int getLayoutId() {
        return R.layout.activity_user_info;
    }

    @Override
    public void getContext(Context context) {
        this.context = context;
    }

    @Override
    public void initData() {
        super.initData();


        regToWx();
        path = Environment.getExternalStorageDirectory().getAbsolutePath();
        //点击头像布局
        user_info_head_layout = get(R.id.user_info_head_layout);
        user_info_head_layout.setOnClickListener(this);

        //页面返回图片
        user_info_back = get(R.id.user_info_back);
        user_info_back.setOnClickListener(this);


        user_info_head = get(R.id.user_info_head);
        user_info_name = get(R.id.user_info_name);
        user_info_sex = get(R.id.user_info_sex);
        user_info_birthday = get(R.id.user_info_birthday);
        user_info_phone = get(R.id.user_info_phone);
        user_info_emil = get(R.id.user_info_emil);

        //重置密码
        user_info_layout_3 = get(R.id.user_info_layout_3);
        user_info_layout_3.setOnClickListener(this);


        //退出登录
        user_info_logout = get(R.id.user_info_logout);
        user_info_logout.setOnClickListener(this);


        //修改个人信息
        user_info_layout_4 = get(R.id.user_info_layout_4);
        user_info_layout_4.setOnClickListener(this);


        //绑定微信帐号
        user_info_layout_5 = get(R.id.user_info_layout_5);
        user_info_layout_5.setOnClickListener(this);


        //如果登录状态 给页面控件复制用户信息   其实若没有登录是进不来这个页面的
        isLogin = SharedPreferencesUtils.getBoolean(context, "isLogin");
        if (isLogin) {
            String headpic = SharedPreferencesUtils.getString(context, "headpic");
            String nickname = SharedPreferencesUtils.getString(context, "nickname");
            String sex = SharedPreferencesUtils.getString(context, "sex");
            String birthday = SharedPreferencesUtils.getString(context, "birthday");
            String phone = SharedPreferencesUtils.getString(context, "phone");
            user_info_head.setImageURI(headpic.trim());
            user_info_name.setText(nickname);
            user_info_sex.setText(sex.trim());
            user_info_phone.setText(phone);
            user_info_birthday.setText(birthday);
        }


        //点击头像布局的时候  弹出popupwindow
        View user_info_selecte_head_popupwindow_layout = View.inflate(context, R.layout.user_info_selecte_head_popupwindow_layout, null);
        popupWindow = new PopupWindow(user_info_selecte_head_popupwindow_layout, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.setOutsideTouchable(true);
        popupWindow.setTouchable(true);
        popupWindow.setOutsideTouchable(true);


        //popupwindow 的 相机
        user_info_selecte_camera = user_info_selecte_head_popupwindow_layout.findViewById(R.id.user_info_selecte_camera);
        user_info_selecte_camera.setOnClickListener(this);
        //popupwindow 的 相册
        user_info_selecte_photo = user_info_selecte_head_popupwindow_layout.findViewById(R.id.user_info_selecte_photo);
        user_info_selecte_photo.setOnClickListener(this);


        //绑定微信和是否绑定微信相关
        user_info_is_bind_wx_text = get(R.id.user_info_is_bind_wx_text);
        user_info_goto_bind_wx_img = get(R.id.user_info_goto_bind_wx_img);

        //点击绑定微信号回来的回调
        WXEntryActivity wxEntryActivity = new WXEntryActivity();
        wxEntryActivity.setWxEntryBindListener(new WXEntryActivity.WXEntryBindListener() {
            @Override
            public void onisSucceed(boolean flag) {
                if (flag) {
                    isBindWx = true;
                    Log.i("jhktest2", "onisSucceed: 成功");
                    Toast.makeText(context, "绑定成功", Toast.LENGTH_SHORT).show();
                    user_info_is_bind_wx_text.setText("已绑定");
                } else {
                    Log.i("jhktest2", "onisSucceed: 失败");
                    isBindWx = false;
                    Toast.makeText(context, "绑定失败", Toast.LENGTH_SHORT).show();
                    user_info_is_bind_wx_text.setText("未绑定");
                }
            }
        });

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //点击返回按钮的时退出该activity返回到主activity
            case R.id.user_info_back:
                ((UserInfoActivity) context).finish();
                break;

            //点击退出登录
            case R.id.user_info_logout:
                SharedPreferencesUtils.putBoolean(context, "isLogin", false);
                Boolean isLogin = SharedPreferencesUtils.getBoolean(context, "isLogin");
                if (!isLogin) {
                    Toast.makeText(context, "退出登录成功", Toast.LENGTH_SHORT).show();
                    ((UserInfoActivity) context).finish();
                } else {
                    Toast.makeText(context, "退出成功失败", Toast.LENGTH_SHORT).show();
                }

                //如果微信登录  取消存值状态
                Boolean isWXlogin = SharedPreferencesUtils.getBoolean(context, "isWXlogin");
                if (isWXlogin) {
                    SharedPreferencesUtils.putBoolean(context, "isWXlogin", false);
                }
                break;

            //点击重置密码
            case R.id.user_info_layout_3:
                Intent intent = new Intent(context, ReSetpasswordActivity.class);
                ((UserInfoActivity) context).startActivity(intent);
                ((UserInfoActivity) context).finish();
                break;


            //点击修改个人信息
            case R.id.user_info_layout_4:
                if (SharedPreferencesUtils.getBoolean(context, "isLogin")) {
                    Intent intent1 = new Intent(context, UserReSertInfoActivity.class);
                    ((UserInfoActivity) context).startActivity(intent1);
                } else {
                    Toast.makeText(context, "您还没有登录哦~", Toast.LENGTH_SHORT).show();
                }
                break;


            //点击头像布局的时候 弹出popupwindow
            case R.id.user_info_head_layout:
                popupWindow.showAtLocation(((UserInfoActivity) context).getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
                break;


            //点击popupwindow 的相机
            case R.id.user_info_selecte_camera:

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    //当前系统大于等于6.0
                    if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager
                            .PERMISSION_GRANTED) {
                        //具有拍照权限，直接调用相机
                        //具体调用代码
                        // 设置调用系统摄像头的意图(隐式意图)
                        Intent intent1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        //设置照片的输出路径和文件名
                        //设置图片的名称
                        intent1.putExtra(MediaStore.EXTRA_OUTPUT, Uri
                                .fromFile(new File(Environment.getExternalStorageDirectory(),
                                        "head.png")));
                        //开启摄像头
                        ((UserInfoActivity) context).startActivityForResult(intent1, 1);
                        popupWindow.dismiss();

                    } else {
                        //不具有拍照权限，需要进行权限申请
                        ActivityCompat.requestPermissions(((UserInfoActivity) context), new String[]{Manifest.permission.CAMERA}, 100);
                    }
                } else {
                    //当前系统小于6.0，直接调用拍照
                    // 设置调用系统摄像头的意图(隐式意图)
                    Log.i("jhktest", "onClick: " + "不用动态权限");
                    Intent intent1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    //设置照片的输出路径和文件名
                    //设置图片的名称
                    intent1.putExtra(MediaStore.EXTRA_OUTPUT, Uri
                            .fromFile(new File(Environment.getExternalStorageDirectory(),
                                    "head.png")));
                    //开启摄像头
                    ((UserInfoActivity) context).startActivityForResult(intent1, 1);
                    popupWindow.dismiss();
                }
                break;
            //点击popupwindow 的相册
            case R.id.user_info_selecte_photo:
                //具体调用代码
                //当前系统小于6.0，直接调用
                // 设置调用系统相册的意图(隐式意图)
                Intent intent1 = new Intent();
                //设置值活动//android.intent.action.PICK
                intent1.setAction(Intent.ACTION_PICK);
                //设置类型和数据
                intent1.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                // 开启系统的相册
                ((UserInfoActivity) context).startActivityForResult(intent1, 2);
                popupWindow.dismiss();

                break;


            //点击去绑定微信帐号
            case R.id.user_info_layout_5:
                if (!isBindWx) {
                    Boolean isBindWX = SharedPreferencesUtils.getBoolean(context, "isBindWX");
                    Boolean isLogin1 = SharedPreferencesUtils.getBoolean(context, "isLogin");
                    if (isLogin1 && isBindWX == false) {
                        SendAuth.Req req = new SendAuth.Req();
                        req.scope = "snsapi_userinfo";//
//                req.scope = "snsapi_login";//提示 scope参数错误，或者没有scope权限
                        req.state = "wechat_sdk_微信绑定";
                        api.sendReq(req);

                    }

                }

                break;

        }
    }


    private void regToWx() {
        // 通过WXAPIFactory工厂，获取IWXAPI的实例
        api = WXAPIFactory.createWXAPI(context, APP_ID, true);

        // 将应用的appId注册到微信
        api.registerApp(APP_ID);
    }

    @Override
    public void resume() {
        super.resume();
        //如果登录状态 给页面控件复制用户信息   其实若没有登录是进不来这个页面的
        isLogin = SharedPreferencesUtils.getBoolean(context, "isLogin");
        if (isLogin) {
            String headpic = SharedPreferencesUtils.getString(context, "headpic");
            String nickname = SharedPreferencesUtils.getString(context, "nickname");
            String sex = SharedPreferencesUtils.getString(context, "sex");
            String birthday = SharedPreferencesUtils.getString(context, "birthday");
            String phone = SharedPreferencesUtils.getString(context, "phone");
            user_info_head.setImageURI(headpic.trim());
            user_info_name.setText(nickname);
            user_info_sex.setText(sex.trim());
            user_info_phone.setText(phone);
            user_info_birthday.setText(birthday);


            //判断是否绑定微信
            if (isBindWx == false) {
                int userId = SharedPreferencesUtils.getInt(context, "userId");
                HashMap<String, String> parmMap = new HashMap<>();
                HashMap<String, String> headMap = new HashMap<>();

                headMap.put("userId", userId + "");
                new HttpUtil().postHead("/movieApi/user/v1/verify/whetherToBindWeChat", parmMap, headMap).result(new HttpUtil.HttpListener() {
                    @Override
                    public void success(String data) {
                        if (data.contains("成功")) {
                            isBindWx = true;
                            user_info_is_bind_wx_text.setText("已绑定");
                            SharedPreferencesUtils sharedPreferencesUtils = new SharedPreferencesUtils();
                            sharedPreferencesUtils.putBoolean(context, "isBindWX", false);
                            user_info_goto_bind_wx_img.setVisibility(View.GONE);
                            //这里的未绑定  不知道data 里面有没有包含 自己先写上   因为接口  访问失败
                        } else if (data.contains("未绑定")) {
                            isBindWx = false;
                            user_info_is_bind_wx_text.setText("未绑定");
                            user_info_goto_bind_wx_img.setVisibility(View.VISIBLE);
                            Toast.makeText(context, "未绑定", Toast.LENGTH_SHORT).show();
                        } else {
                            isBindWx = false;
                            Toast.makeText(context, "查询是否绑定微信失败", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void fail(String data) {
                        Toast.makeText(context, data, Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                user_info_is_bind_wx_text.setText("已绑定");
                user_info_goto_bind_wx_img.setVisibility(View.GONE);
                SharedPreferencesUtils sharedPreferencesUtils = new SharedPreferencesUtils();
                sharedPreferencesUtils.putBoolean(context, "isBindWX", false);
            }
        }


    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                //相机
                case 1:

                    File temp = new File(Environment.getExternalStorageDirectory()
                            + "/head.png");
                    //裁剪图片
                    startPhotoZoom(Uri.fromFile(temp));
                    break;

                //相册
                case 2:

                    //裁剪图片
                    startPhotoZoom(data.getData());
                    break;

                //剪裁
                case 3:
                    Bundle extras = data.getExtras();
                    if (extras == null) {
                        return;
                    }
                    head = extras.getParcelable("data");
                    Log.i("jhktest", "onActivityResult: " + "dadasd");
                    if (head != null) {

                        String fileName = path + "/head.png";//图片名字
                        setPicToView(head);//保存在SD卡中
                        File file1 = new File(fileName);
                        Uri parse = Uri.parse("file://com.bw.movie/" + file1.getAbsolutePath());
                        Log.i("jhktest", "head: " + parse.toString());
                        user_info_head.setImageURI(parse);
                        uploadImage(file1);


                    }
                    break;
            }
        }

    }

    private void uploadImage(File file) {


        HashMap<String, String> headMap = new HashMap<>();
        int userId = SharedPreferencesUtils.getInt(context, "userId");
        String sessionId = SharedPreferencesUtils.getString(context, "sessionId");
        headMap.put("userId", userId + "");
        headMap.put("sessionId", sessionId);


        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part image = MultipartBody.Part.createFormData("image", "head.png", requestBody);
        new HttpUtil().part("/movieApi/user/v1/verify/uploadHeadPic", headMap, image).result(new HttpUtil.HttpListener() {
            @Override
            public void success(String data) {
                if (data.contains("成功")) {
                    Toast.makeText(context, "上传头像成功", Toast.LENGTH_SHORT).show();

                    String pwd = SharedPreferencesUtils.getString(context, "pwd");

                    String phone = SharedPreferencesUtils.getString(context, "phone");
                    //使用非对称加密密码
                    String encrypt_pwd = Base64EncryptUtil.encrypt(pwd);
                    Log.i("password", encrypt_pwd);
                    Map<String, String> map = new HashMap<>();
                    //拼接参数
                    map.put("phone", phone);
                    map.put("pwd", encrypt_pwd);
                    //请求接口
                    new HttpUtil().post("/movieApi/user/v1/login", map).result(new HttpUtil.HttpListener() {
                        @Override
                        public void success(String data) {
                            if (data.contains("成功")) {
                                //解析数据
                                Gson gson = new Gson();
                                LoginBean loginBean = gson.fromJson(data, LoginBean.class);
                                LoginBean.ResultBean resultBean = loginBean.getResult();

                                //储存userid
                                SharedPreferencesUtils.putInt(context, "userId", resultBean.getUserId());
                                //储存sessionId
                                SharedPreferencesUtils.putString(context, "sessionId", resultBean.getSessionId());

                                LoginBean.ResultBean.UserInfoBean userInfo = resultBean.getUserInfo();

                                //存储用户头像信息
                                SharedPreferencesUtils.putString(context, "headpic", userInfo.getHeadPic().trim());


                                user_info_head.setImageURI(userInfo.getHeadPic().trim());

                            } else {
                                Toast.makeText(context, "上传成功但是头像刷新失败", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void fail(String data) {
                            Toast.makeText(context, "上传成功但是头像刷新失败", Toast.LENGTH_SHORT).show();
                        }
                    });

                } else {
                    Toast.makeText(context, "上传头像失败", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void fail(String data) {
                Log.i("jhktest", "fail: " + data);
                Toast.makeText(context, data, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /***
     * 剪裁的方法
     */
    //剪裁的方法
    public void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 64);
        intent.putExtra("outputY", 64);
        intent.putExtra("return-data", true);
        ((UserInfoActivity) context).startActivityForResult(intent, 3);
    }


    /***
     * 保存到sd卡中
     */

    private void setPicToView(Bitmap mBitmap) {
        bitmap1 = mBitmap;
        String sdStatus = Environment.getExternalStorageState();
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
            return;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //当前系统大于等于6.0
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager
                    .PERMISSION_GRANTED) {
                //请求过  直接用
                FileOutputStream b = null;
                File file = new File(path);
                file.mkdirs();// 创建文件夹
                String fileName = path + "/head.png";//图片名字

                try {
                    b = new FileOutputStream(fileName);
                    bitmap1.compress(Bitmap.CompressFormat.PNG, 100, b);// 把数据写入文件
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (b != null) {
                            //关闭流
                            b.flush();
                            b.close();
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            } else {
                //没有去请求
                ActivityCompat.requestPermissions(((UserInfoActivity) context), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 111);

            }
        } else {
            //不大于6.0直接用
            FileOutputStream b = null;
            File file = new File(path);
            file.mkdirs();// 创建文件夹
            String fileName = path + "/head.png";//图片名字

            try {
                b = new FileOutputStream(fileName);
                bitmap1.compress(Bitmap.CompressFormat.PNG, 100, b);// 把数据写入文件
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (b != null) {
                        //关闭流
                        b.flush();
                        b.close();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100) {
            if (grantResults.length >= 1) {
                int cameraResult = grantResults[0];//相机权限
                boolean cameraGranted = cameraResult == PackageManager.PERMISSION_GRANTED;//拍照权限
                if (cameraGranted) {
                    //具有拍照权限，调用相机
                    // 设置调用系统摄像头的意图(隐式意图)
                    Intent intent1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    //设置照片的输出路径和文件名
                    //设置图片的名称
                    intent1.putExtra(MediaStore.EXTRA_OUTPUT, Uri
                            .fromFile(new File(Environment.getExternalStorageDirectory(),
                                    "head.png")));
                    //开启摄像头
                    ((UserInfoActivity) context).startActivityForResult(intent1, 1);
                    popupWindow.dismiss();
                } else {
                    //不具有相关权限，给予用户提醒，比如Toast或者对话框，让用户去系统设置-应用管理里把相关权限开启
                    Toast.makeText(context, "您拒接了该权限入想开启请去系统设置-应用管理里把相关权限开启", Toast.LENGTH_SHORT).show();
                    if (!shouldShowRequestPermissionRationale(((UserInfoActivity) context), Manifest.permission.CAMERA)) {
                        //如果用户勾选了不再提醒，则返回false
                        //给予用户提醒，比如Toast或者对话框，让用户去系统设置-应用管理里把相关权限打开
                        Toast.makeText(context, "您拒接了该权限入想开启请去系统设置-应用管理里把相关权限开启", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        }


        if (requestCode == 111) {
            int cameraResult = grantResults[0];//读写权限
            boolean cameraGranted = cameraResult == PackageManager.PERMISSION_GRANTED;//权限
            if (cameraGranted) {
                FileOutputStream b = null;
                File file = new File(path);
                file.mkdirs();// 创建文件夹
                String fileName = path + "/head.png";//图片名字

                try {
                    b = new FileOutputStream(fileName);
                    bitmap1.compress(Bitmap.CompressFormat.PNG, 100, b);// 把数据写入文件
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (b != null) {
                            //关闭流
                            b.flush();
                            b.close();
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                //不具有相关权限，给予用户提醒，比如Toast或者对话框，让用户去系统设置-应用管理里把相关权限开启
                Toast.makeText(context, "您拒接了该权限入想开启请去系统设置-应用管理里把相关权限开启", Toast.LENGTH_SHORT).show();
                if (!shouldShowRequestPermissionRationale(((UserInfoActivity) context), Manifest.permission.CAMERA)) {
                    //如果用户勾选了不再提醒，则返回false
                    //给予用户提醒，比如Toast或者对话框，让用户去系统设置-应用管理里把相关权限打开
                    Toast.makeText(context, "您拒接了该权限入想开启请去系统设置-应用管理里把相关权限开启", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }


}
