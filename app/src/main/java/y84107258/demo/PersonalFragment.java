package y84107258.demo;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import y84107258.demo.view.CircleImageView;

public class PersonalFragment extends Fragment {
    private TextView logoutView;
    private CircleImageView circleImageView;
    private ImageView editSign;
    private TextView signature;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_personal, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        initComponents();
    }

    private void initComponents(){
        Typeface typeface=Typeface.createFromAsset(getActivity().getAssets(),"fonts/ml.ttf");
        logoutView=getActivity().findViewById(R.id.logoutButton);
        logoutView.setTypeface(typeface);
        logoutView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent=new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });
        circleImageView= getActivity().findViewById(R.id.avatar);
        signature=getActivity().findViewById(R.id.signature);
        editSign=getActivity().findViewById(R.id.editSign);
        editSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signature.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
            }
        });
    }

}
