import model.Order;
import service.OrderService;
import util.HttpClientUtil;

import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        // Create orders manually
        List<Order> orders = Arrays.asList(
                new Order(1000, 2000, 12, 100.51),
                new Order(1000, 2001, 31, 200),
                new Order(1000, 2002, 22, 150.86),
                new Order(1000, 2003, 41, 250),
                new Order(1000, 2004, 55, 244),
                new Order(1001, 2001, 88, 44.531),
                new Order(1001, 2002, 121, 88.11),
                new Order(1001, 2004, 74, 211),
                new Order(1001, 2002, 14, 88.11),
                new Order(1002, 2003, 2, 12.1),
                new Order(1002, 2004, 3, 22.3),
                new Order(1002, 2003, 8, 12.1),
                new Order(1002, 2002, 16, 94),
                new Order(1002, 2005, 9, 44.1),
                new Order(1002, 2006, 19, 90)
        );

        // init order service
        OrderService orderService = new OrderService();

        // 1a - calculate and print total sum of orders
        double totalSum = orderService.calculateTotalSum(orders);
        System.out.println("Toplam Tutar: " + totalSum);

        // 1b - calculate and print average of products total sum
        double averagePrice = orderService.calculateAveragePrice(orders);
        System.out.println("Ortalama Fiyat: " + averagePrice);

        // 1c - calculate and print average price per product product
        orderService.calculateAveragePricePerProduct(orders).forEach((productId, avgPrice) ->
                System.out.println("Mal No: " + productId + ", Ortalama Fiyat: " + avgPrice));

        // 1d - calculate and print products count per order
        orderService.calculateProductCountsPerOrder(orders).forEach((productId, orderMap) -> {
            System.out.println("Mal No: " + productId);
            orderMap.forEach((orderId, count) ->
                    System.out.println("\tSipariş No: " + orderId + ", Adet: " + count));
        });


        // 2 - http client service and call get&post request

        // init http client
        String baseUrl = "https://jsonplaceholder.typicode.com"; // fake server
        HttpClientUtil httpClientUtil = new HttpClientUtil(baseUrl);

        // POST request
        String postEndpoint = "/posts";
        String jsonInputString = "{\"title\": \"This is a title\", \"body\": \"This is a body\", \"userId\": 1}";
        HttpResponse<String> postResponse = httpClientUtil.sendPostRequest(postEndpoint, jsonInputString);
        System.out.println("POST Response Code : " + postResponse.statusCode());
        System.out.println(postResponse.body());

        // GET request
        String getEndpoint = "/posts/1";
        HttpResponse<String> getResponse = httpClientUtil.sendGetRequest(getEndpoint);
        System.out.println("GET Response Code : " + getResponse.statusCode());
        System.out.println(getResponse.body());


    }
}