import java.util.Arrays;

public class DisjointSet {
    int[] s;
    int[] size;
    public DisjointSet(int size){
        s = new int[size];
        this.size = new int[size];
        for (int i = 0; i < size; i++){
            s[i] = i;
            this.size[i] = 1;
        }
    }

    // find 是找到老大
    public int find(int x){
        if (x == s[x]){
            return x;
        }
        return s[x] = find(s[x]);
    }

    // union 是让两个集合“相交”，即选出新老大，x、y 是原老大索引
    public void union(int x, int y){
//        if (size[x] > size[y]){
//            s[y] = x;
//            size[x] = size[x] + size[y];
//        }else {
//            s[x] = y;
//            size[y] = size[x] + size[y];
//        }

        // 简短代码
        if (size[x] < size[y]){
            int t = x;
            x = y;
            y = t;
        }
        s[y] = x;
        size[x] = size[x] + size[y];
    }

    @Override
    public String toString() {
        return Arrays.toString(s);
    }
}
