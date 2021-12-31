package akash.sarkar.clickquick.models;

public class LevelSelectorModel {
    public int level,required_points;
    public boolean  is_locked;

    public LevelSelectorModel(int level, int required_points, boolean is_locked) {
        this.level = level;
        this.required_points = required_points;
        this.is_locked = is_locked;
    }
}
