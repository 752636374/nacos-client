import java.util.HashMap;
import java.util.Map;

/**
 * @Author: yaoheng5
 * @CreateTime: 2024-02-29  14:32:04
 * @Description:
 * @Version: 1.0
 */
public class MapTest {
    public static void main(String[] args) {
        Map<String, String> m = new HashMap<>();
        m.put("1/2/*/*/*", "122");
        Map<String, String> m2 = new HashMap<>();
        m2.put("1/2/*/*/*", "12");
        for (String key : m2.keySet()) {
            System.out.println(m.get(key));
        }
    }
}
