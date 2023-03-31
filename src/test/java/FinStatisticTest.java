import junit.framework.TestCase;
import org.json.simple.JSONObject;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.util.HashMap;
import java.util.Map;

public class FinStatisticTest extends TestCase {

    protected FinStatistic finStatistic = new FinStatistic();
    protected Purchase purchase = new Purchase("булка", "2023.03.31", 500L);

    protected Map<String, Long> testMap = new HashMap<>();

    @Test
    public void testCheckProductCategory() {
        final String product1 = "булка";
        final String product2 = "штаны";

        String result1 = finStatistic.checkProductCategory(product1, Main.listProducts);
        String result2 = finStatistic.checkProductCategory(product2, Main.listProducts);

        Assertions.assertEquals("еда", result1);
        Assertions.assertEquals("Другое", result2);
    }

    @Test
    public void testAddPurchase() {

        Map<String, Long> testMap = finStatistic.addPurchase(finStatistic.getCountCategory(), purchase);

        Assertions.assertTrue(testMap.containsKey("еда"));
        Assertions.assertTrue(testMap.containsValue(500L));
    }

    @Test
    public void testCountMaxCategory() {
        testMap.put("еда", 2000L);
        testMap.put("одежда", 500L);
        testMap.put("быт", 1500L);
        testMap.put("финансы", 100L);
        testMap.put("другое", 1000L);

        Map<String, Long> result = finStatistic.countMaxCategory(testMap);

        Assertions.assertTrue(result.containsKey("еда"));
        Assertions.assertTrue(result.containsValue(2000L));
    }

    @Test
    public void testAnswerToClient() {
        testMap.put("еда", 2500L);
        testMap.put("одежда", 500L);
        testMap.put("быт", 1500L);
        testMap.put("финансы", 100L);
        testMap.put("другое", 1000L);

        String maxCategoryName = "еда";
        Long maxCategorySum = 2500L;

        JSONObject jo = finStatistic.answerToClient(maxCategoryName, maxCategorySum);
        String result = jo.toString();

        Assertions.assertTrue(result.contains("еда"));
        Assertions.assertTrue(result.contains("2500"));
    }
}