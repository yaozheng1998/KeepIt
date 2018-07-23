package y84107258.demo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class NewScheduleActivity extends Activity implements ScheduleDialogFragment.Callback {
    private static final int PHOTO_REQUEST_CAMERA=1;
    private static final int PHOTO_REQUEST_GALLERY=2;
    private static final int PHOTO_REQUEST_CUT=3;
    private ImageView image;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_schedule);
        this.image = (ImageView) this.findViewById(R.id.image);

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(NewScheduleActivity.this, TodoListActivity.class);
                intent.putExtra("id",0);
                startActivity(intent);
            }
        });

        findViewById(R.id.confirm_new).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(NewScheduleActivity.this, TodoListActivity.class);
                startActivity(intent);
            }
        });
    }
    @Override
    public void onClick(String scheduleName, String startTime, String endTime) {

    }

    public void showScheduleDialogFragment(View view){
        ScheduleDialogFragment scheduleDialogFragment=new ScheduleDialogFragment();
        scheduleDialogFragment.show(getFragmentManager());
    }

    /**
     * 从相册选取图片
     * @param view
     */
    public void viewGallery(View view) {
        Intent intent=new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,PHOTO_REQUEST_GALLERY);
    }

    private void crop(Uri uri){
        Intent intent=new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop","true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);

        intent.putExtra("outputX", 250);
        intent.putExtra("outputY", 250);

        intent.putExtra("outputFormat", "JPEG");
        intent.putExtra("noFaceDetection", true);
        intent.putExtra("return-data", true);

        startActivityForResult(intent, PHOTO_REQUEST_CUT);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode==PHOTO_REQUEST_GALLERY){
            if (data!=null){
                Uri uri=data.getData();
                crop(uri);
            }
        }else if(requestCode==PHOTO_REQUEST_CUT){
            if(data!=null){
                Bitmap bitmap=data.getParcelableExtra("data");
                this.image.setImageBitmap(bitmap);
            }
        }
        super.onActivityResult(requestCode,resultCode,data);
    }


}
