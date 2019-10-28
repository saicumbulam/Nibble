package com.example.nimble.ui.dashboard;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.nimble.R;
import com.example.nimble.SuggestedHotelActivity;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class DashboardFragment extends Fragment {

    private Button btn_suggested_txt, btn_nearme_txt;
    private SearchView dashboard_searchView;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_dashboard,
                container, false);

        btn_suggested_txt = root.findViewById(R.id.dashboard_suggestedtext);
        btn_nearme_txt = root.findViewById(R.id.dashboard_nearMetext);
        dashboard_searchView = root.findViewById(R.id.dashboard_searchView);

        btn_suggested_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SuggestedHotelActivity.class);
                startActivity(intent);
            }
        });

        btn_nearme_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Feature is not implemented Yet!!", Toast.LENGTH_SHORT).show();
            }
        });

        dashboard_searchView.setQueryHint("Hotel Name|Food|Location");
        dashboard_searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    showInputMethod(view.findFocus());
                }
            }
        });

        dashboard_searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dashboard_searchView.setIconified(false);
            }
        });

        return root;

    }

    private void showInputMethod(View view) {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.showSoftInput(view, 0);
        }
    }


}