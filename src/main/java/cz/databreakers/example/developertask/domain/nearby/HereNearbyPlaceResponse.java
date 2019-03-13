package cz.databreakers.example.developertask.domain.nearby;

import java.math.BigDecimal;
import java.util.List;

/**
 * A simple whitelisting of attributes we want to extract from the {@code /places/v1/discover/here} response.
 *
 * @author hvizdos
 */
public class HereNearbyPlaceResponse {

    private Results results;

    public Results getResults() {
        return results;
    }

    public void setResults(Results results) {
        this.results = results;
    }

    public static class Results {

        private List<Item> items;

        public List<Item> getItems() {
            return items;
        }

        public void setItems(List<Item> items) {
            this.items = items;
        }
    }

    public static class Item {

        private BigDecimal distance;
        private String title;
        private Category category;

        public BigDecimal getDistance() {
            return distance;
        }

        public void setDistance(BigDecimal distance) {
            this.distance = distance;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public Category getCategory() {
            return category;
        }

        public void setCategory(Category category) {
            this.category = category;
        }
    }

    public static class Category {
        private String title;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
