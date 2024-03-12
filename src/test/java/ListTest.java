import java.util.ArrayList;
import java.util.List;

/**
 * @Author: yaoheng5
 * @CreateTime: 2024-02-29  14:19:22
 * @Description:
 * @Version: 1.0
 */
public class ListTest {
    public static void main(String[] args) {
        List<String> list1 = new ArrayList<>();
        list1.add("apple");
        list1.add("banana");

        List<String> list2 = new ArrayList<>();
        list2.add("orange");
        list2.add("grape");

        list1.addAll(list2);

        List<String> list3 = new ArrayList<>();


        list1.addAll(list3);

        System.out.println(list1); // Output: [apple, banana, orange, grape]


        System.out.println(list1.get(0));
    }
}
