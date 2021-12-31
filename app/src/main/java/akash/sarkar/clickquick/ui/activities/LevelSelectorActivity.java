package akash.sarkar.clickquick.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import akash.sarkar.clickquick.R;
import akash.sarkar.clickquick.databinding.ActivityLevelSelectorBinding;
import akash.sarkar.clickquick.models.LevelSelectorModel;
import akash.sarkar.clickquick.ui.adapters.LevelSelectorAdapter;

public class LevelSelectorActivity extends AppCompatActivity {

    private ActivityLevelSelectorBinding binder;
    private List<LevelSelectorModel> levelSelectorModelList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binder = DataBindingUtil.setContentView(this, R.layout.activity_level_selector, null);
        init();
    }

    private void init() {
        initElements();
    }

    private void initElements() {
        binder.levSelecRecy.setLayoutManager(new GridLayoutManager(this, 2));
        levelSelectorModelList.add(new LevelSelectorModel(1,200,false));
        levelSelectorModelList.add(new LevelSelectorModel(1,200,false));
        levelSelectorModelList.add(new LevelSelectorModel(1,200,false));
        levelSelectorModelList.add(new LevelSelectorModel(1,200,false));
        levelSelectorModelList.add(new LevelSelectorModel(1,200,false));
        levelSelectorModelList.add(new LevelSelectorModel(2,500,true));
        levelSelectorModelList.add(new LevelSelectorModel(2,500,true));
        levelSelectorModelList.add(new LevelSelectorModel(2,500,true));
        levelSelectorModelList.add(new LevelSelectorModel(2,500,true));
        levelSelectorModelList.add(new LevelSelectorModel(2,500,true));
        levelSelectorModelList.add(new LevelSelectorModel(2,500,true));
        binder.levSelecRecy.setAdapter(new LevelSelectorAdapter(levelSelectorModelList,this));
    }
}