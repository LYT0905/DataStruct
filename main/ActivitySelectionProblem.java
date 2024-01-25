import java.util.ArrayList;
import java.util.List;

public class ActivitySelectionProblem {
    static class Activity{
        int start;
        int finish;
        int index;

        public Activity(int index, int start, int finish) {
            this.index = index;
            this.start = start;
            this.finish = finish;
        }

        @Override
        public String toString() {
            return "Activity(" + index + ")";
        }
    }

    public static void select(Activity[] activities, int n){
        List<Activity> result = new ArrayList<>();
        Activity pre = activities[0];
        result.add(pre);
        for (int i = 1; i < n; i++){
            Activity curr = activities[i];
            if (curr.start >= pre.finish){
                result.add(curr);
                pre = curr;
            }
        }
    }
}
