package Concepts;

import java.util.ArrayList;

public class Practice {
    public static void main(String[] args) {
        ArrayList<int[]> list = new ArrayList<>();
        int[] element = {1, 2};
        list.add(element);
        System.out.println(list.get(0)[0] + " " + list.get(0)[1]);
        Integer[][] intArray = (Integer[][]) list.toArray();
    }
}
