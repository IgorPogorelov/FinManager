import org.json.simple.JSONObject;

import java.io.*;
import java.util.*;

public class FinStatistic implements Serializable {

    private String maxCategory;
    private Long maxCategoryValue;
    private Map<String, Long> countCategory = new HashMap<>();

    public String getMaxCategory() {
        return maxCategory;
    }

    public void setMaxCategory(String maxCategory) {
        this.maxCategory = maxCategory;
    }

    public Long getMaxCategoryValue() {
        return maxCategoryValue;
    }

    public void setMaxCategoryValue(Long maxCategoryValue) {
        this.maxCategoryValue = maxCategoryValue;
    }

    public Map<String, Long> getCountCategory() {
        return countCategory;
    }

    public void setCountCategory(Map<String, Long> countCategory) {
        this.countCategory = countCategory;
    }

    public String checkProductCategory(String product, Map<String, String> listProducts){
        if (listProducts.containsKey(product)) {
            return listProducts.get(product);
        }
        return "Другое";
    }

    public Map<String, Long> addPurchase(Map<String, Long> countCategory, Purchase purchase) {

        if (!countCategory.containsKey(checkProductCategory(purchase.getTitle(), Main.listProducts))){
            countCategory.put(checkProductCategory(purchase.getTitle(), Main.listProducts), purchase.getSum());
        } else {
            countCategory.put(checkProductCategory(purchase.getTitle(), Main.listProducts), countCategory.get(checkProductCategory(purchase.getTitle(), Main.listProducts)) + purchase.getSum());
        }

        return  countCategory;
    }

    public Map<String, Long> countMaxCategory(Map<String, Long> countCategory, Purchase purchase) {
        Map<String, Long> maxCategoryValue = new HashMap<>();

        Long tmpMaxValue = Collections.max(countCategory.values());
        String tmpKeyOfMaxValue = null;
        Optional<String> tmp = countCategory.entrySet()
                .stream()
                .filter(entry -> tmpMaxValue.equals(entry.getValue()))
                .map(Map.Entry::getKey)
                .findFirst();
        if (tmp.isPresent()) {
            tmpKeyOfMaxValue = tmp.get();
        }

        setMaxCategory(tmpKeyOfMaxValue);
        setMaxCategoryValue(tmpMaxValue);

        maxCategoryValue.put(tmpKeyOfMaxValue, tmpMaxValue);
        return maxCategoryValue;
    }

    public JSONObject answerToClient(Map<String, Long> map) {
        JSONObject maxCategory = new JSONObject();
        JSONObject resultToClient = new JSONObject();

        maxCategory.put("sum", getMaxCategoryValue());
        maxCategory.put("category", getMaxCategory());
        resultToClient.put("maxCategory", maxCategory);

        return resultToClient;
    }
}