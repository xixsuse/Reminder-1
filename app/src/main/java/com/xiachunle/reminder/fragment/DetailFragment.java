package com.xiachunle.reminder.fragment;

import android.app.AlarmManager;
import android.content.Intent;

import android.database.Cursor;
import android.graphics.Bitmap;


import android.media.Image;
import android.net.Uri;

import android.os.Bundle;

import android.provider.MediaStore;

import android.support.v4.app.Fragment;


import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;


import android.text.TextWatcher;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.xiachunle.reminder.R;
import com.xiachunle.reminder.activity.ContentActivity;
import com.xiachunle.reminder.adapter.DBAdapter;
import com.xiachunle.reminder.bean.MemoReminders;
import com.xiachunle.reminder.util.FileUtil;

import java.io.File;


import java.util.ArrayList;


import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;




import static android.app.Activity.RESULT_OK;
import static android.content.Context.ALARM_SERVICE;

/**
 * Created by xiachunle on 2016/12/1.
 * 每个备忘录详细内容
 */

public class DetailFragment extends Fragment implements View.OnClickListener {
    private EditText result;
    private TextView timeTitle;
    private ImageView saveText, cancelText;
    private ImageView importText ,delText;
    private ImageView timeLock;
    private ImageView  getPicture, startCamera;

    private DBAdapter adapter;

    private int CAMERA_FLAG = 0x10;
    private int PICTURE_FLAG = 0x11;
    private final static int CAMERA_PREMISSION = 0x12;


    private HashMap<String, byte[]> hashMap;//应用中进行数据处理的形式
    private List<String> tempPathsList;//每张身份标识
    private List<byte[]> tempDatasList;//每张图片的数据

    private boolean isContainImage = false;
    private ImageSpan imageSpan;
    private SpannableString ss;
    private File cameraTempFile;

    private String resultMsg;
    private MemoReminders memoReminders;
    private String positionId;

    private AlarmManager alarmManager;

    public static DetailFragment getInstance(String id) {
        DetailFragment detailFragment = new DetailFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("position", id);
        detailFragment.setArguments(bundle);
        return detailFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_details, container, false);
        initSQLData();
        initView(v);

        return v;

    }

    /**
     * 初始化sqlite连接
     */
    private void initSQLData() {
        adapter = new DBAdapter(getActivity().getApplicationContext());
        adapter.open();
    }

    /**
     * 初始化控件机变量
     *
     * @param v
     */
    private void initView(View v) {
        result = (EditText) v.findViewById(R.id.selectResult);
        result.setSelection(result.getText().toString().length());
        result.setCursorVisible(false);
        result.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    result.setCursorVisible(true);
                }
                return false;
            }
        });
        timeTitle = (TextView) v.findViewById(R.id.timeTitle);
        importText = (ImageView) v.findViewById(R.id.importFlag);
        saveText = (ImageView) v.findViewById(R.id.save);
        cancelText = (ImageView) v.findViewById(R.id.cancel);
        delText = (ImageView) v.findViewById(R.id.delete);
        positionId = getArguments().getString("position");
        getPicture = (ImageView) v.findViewById(R.id.picture);
        startCamera = (ImageView) v.findViewById(R.id.startCamera);
        timeLock=(ImageView) v.findViewById(R.id.lock);
        hashMap = new HashMap<String, byte[]>();
        tempPathsList = new ArrayList<String>();
        tempDatasList = new ArrayList<byte[]>();
        alarmManager=(AlarmManager)getActivity().getSystemService(ALARM_SERVICE);
        initShow();
        importText.setOnClickListener(this);
        saveText.setOnClickListener(this);
        cancelText.setOnClickListener(this);
        getPicture.setOnClickListener(this);
        startCamera.setOnClickListener(this);
        timeLock.setOnClickListener(this);

    }

    /**
     * 显示备忘录具体信息或者新建备忘录
     */
    private void initShow() {
        if (positionId != null) {
            memoReminders = adapter.fetchReminderById(Integer.parseInt(positionId));
            timeTitle.setText(memoReminders.getCreateTime() == null ? "" : memoReminders.getCreateTime());
//            importText.setB(memoReminders.getmFlag() == 1);
            String content = memoReminders.getmContent();
            hashMap = FileUtil.deserialize(memoReminders.getImageDatas());

            if (hashMap != null) {
                Log.e("test", "before:" + hashMap.size());
                isContainImage = true;
                ss = new SpannableString(content);
                for (Iterator iterator = hashMap.entrySet().iterator(); iterator.hasNext(); ) {
                    Map.Entry<String, byte[]> elemnet = (Map.Entry<String, byte[]>) iterator.next();
                    String path = elemnet.getKey();
                    byte[] data = elemnet.getValue();
                    tempPathsList.add(path);
                    tempDatasList.add(data);
                    int start = content.indexOf(path);
                    int end = path.length() + start;
                    Bitmap bm = FileUtil.byteToBitmap(data);
                    imageSpan = new ImageSpan(getActivity(), bm);
                    ss.setSpan(imageSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    result.addTextChangedListener(new MyTextWatcher(path));
                }
                result.setGravity(Gravity.CENTER);

                result.setText(ss);
                result.append("\n");
                result.setGravity(Gravity.LEFT);

            } else {
                isContainImage = false;
                result.setGravity(Gravity.LEFT);
                result.setText(content);
            }
            delText.setVisibility(View.VISIBLE);
            delText.setOnClickListener(this);
        } else {
            result.setGravity(Gravity.LEFT);
            timeTitle.setText(FileUtil.getTime());
            result.setText("");
        }

        result.setCursorVisible(false);
        result.setSelection(result.getText().toString().length());
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.save:
                Log.e("test", "se:" + tempPathsList.size());
                if (tempPathsList.size() != 0) {
                    if (hashMap != null) {
                        hashMap.clear();
                    } else {
                        hashMap = new HashMap<String, byte[]>();
                    }
                    for (int i = 0; i < tempPathsList.size(); i++) {
                        hashMap.put(tempPathsList.get(i), tempDatasList.get(i));
                    }
                    Log.e("test", "hm:" + hashMap.size());
                } else {
                    hashMap = null;
                }
                resultMsg = result.getText().toString().trim();
                result.setCursorVisible(false);
                if (positionId != null) {

                    MemoReminders reminders = new MemoReminders(memoReminders.getmId(), resultMsg,
                           1, timeTitle.getText().toString(), FileUtil.serialize(hashMap));
                    adapter.updatReminder(reminders);
                } else {
                    adapter.addReminder(resultMsg, FileUtil.serialize(hashMap),
                            true, timeTitle.getText().toString());
                }
                intent = new Intent(getActivity(), ContentActivity.class);
                startActivity(intent);
                getActivity().finish();
                break;
            case R.id.cancel:
                result.setCursorVisible(false);
                intent = new Intent(getActivity(), ContentActivity.class);
                startActivity(intent);
                getActivity().finish();
                break;
            case R.id.picture:
                isContainImage = true;
                intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, PICTURE_FLAG);
                break;
            case R.id.selectResult:
                if (result.getText().toString().length() > 2048) {
                    result.setFocusable(false);
                    result.setFocusableInTouchMode(false);
                    Toast.makeText(getActivity(), "这个只是个备忘录内容太多快记不住了", Toast.LENGTH_SHORT).show();
                }
                result.setClickable(false);
                break;
            case R.id.startCamera:
                isContainImage = true;
                intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                try {
                    cameraTempFile = new File(FileUtil.getSDPath() +
                            File.separator + "camera.jpg");
                    if (cameraTempFile.exists()) {
                        cameraTempFile.delete();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(cameraTempFile));
                startActivityForResult(intent, CAMERA_FLAG);
                break;
            case R.id.delete:
                adapter.deleteReminderById(memoReminders.getmId());
                long begin = System.currentTimeMillis();
                adapter.updataRealId();
                intent = new Intent(getActivity(), ContentActivity.class);
                startActivity(intent);
                getActivity().finish();
                break;
            case R.id.lock:
                Calendar c=Calendar.getInstance();
//                TimePickerDialog dialog=new TimePickerDialog(getActivity(), 1,
//                        new TimePickerDialog.OnTimeSetListener() {
//
//                            PendingIntent pendingIntent=
//                    @Override
//                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
//
//                    }
//                });


//                dialog.show();
                break;
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {
            if (requestCode == PICTURE_FLAG) {
                Uri uri = data.getData();
                try {
                    String[] proj = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getActivity().getContentResolver().query(uri, proj, null, null, null);
                    int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    cursor.moveToFirst();
                    String path = cursor.getString(column_index);
                    showImage(path);
                } catch (Exception e) {
                    e.printStackTrace();

                }
            } else if (requestCode == CAMERA_FLAG) {
                showImage(cameraTempFile.getPath());
                cameraTempFile.delete();
            }
            result.setGravity(Gravity.LEFT);

        }
    }

    private void showImage(final String path) {
        if (tempPathsList.size() > 0) {
            isContainImage = true;
        } else {
            isContainImage = false;
        }
        Bitmap bm = FileUtil.smallBimap(result.getWidth(), result.getHeight(), path);
        ImageSpan imageSpan = new ImageSpan(getActivity(), bm);

        ss = new SpannableString(path);
        ss.setSpan(imageSpan, 0, path.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        result.setGravity(Gravity.CENTER);

        if (tempPathsList.size() >= 6) {
            Toast.makeText(getActivity(), "图片达到最大！！", Toast.LENGTH_SHORT).show();
            result.append("");
        } else {
            tempPathsList.add(path);
            tempDatasList.add(FileUtil.bitmapToByte(bm));
            result.addTextChangedListener(new MyTextWatcher(path));
            int index = result.getSelectionStart();
            Editable edit_text = result.getEditableText();
            if (index < 0 || index > edit_text.length()) {
                edit_text.append(ss);
            } else {
                edit_text.insert(index, ss);
            }
            result.append("\n");
        }
        Log.e("test", "size01:" + tempDatasList.size() + "====" + tempPathsList.size());
        result.setSelection(result.getText().toString().length());
    }

    private class MyTextWatcher implements TextWatcher {

        private String path;

        public MyTextWatcher(String path) {
            this.path = path;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (!result.getText().toString().contains(path) && isContainImage) {
                tempDatasList.remove(tempPathsList.indexOf(path));
                tempPathsList.remove(path);
                Log.e("Test", "back" + tempPathsList.size() + "====" + tempDatasList.size());
                if (tempPathsList.size() <= 0) {
                    isContainImage = false;
                }
            }
        }
    }
}
