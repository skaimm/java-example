package service;

import model.Order;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class OrderService {

    public double calculateTotalSum(List<Order> orders) {
        return orders.stream()
                .mapToDouble(order -> order.getQuantity() * order.getUnitPrice())
                .sum();
    }

    public double calculateAveragePrice(List<Order> orders) {
        return orders.stream()
                .collect(Collectors.teeing(
                        Collectors.summingDouble(o -> o.getUnitPrice() * o.getQuantity()),
                        Collectors.summingInt(Order::getQuantity),
                        (totalSum, totalQuantity) -> totalSum / totalQuantity
                ));
    }

    public Map<Integer, Double> calculateAveragePricePerProduct(List<Order> orders) {
        return orders.stream()
                .collect(Collectors.groupingBy(Order::getProductId, Collectors.teeing(
                        Collectors.summingDouble(o -> o.getUnitPrice() * o.getQuantity()),
                        Collectors.summingInt(Order::getQuantity),
                        (totalSum, totalQuantity) -> totalSum / totalQuantity
                )));
    }

    public Map<Integer, Map<Integer, Long>> calculateProductCountsPerOrder(List<Order> orders) {
        return orders.stream()
                .collect(Collectors.groupingBy(Order::getProductId,
                        Collectors.groupingBy(Order::getOrderId,
                                Collectors.summingLong(Order::getQuantity))));
    }
}
