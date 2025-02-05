package ptithcm.tttn.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ptithcm.tttn.entity.*;
import ptithcm.tttn.function.OrderStatusDF;
import ptithcm.tttn.repository.*;
import ptithcm.tttn.request.OrderRequest;
import ptithcm.tttn.request.ProductSaleRequest;
import ptithcm.tttn.request.StatisticRequest;
import ptithcm.tttn.request.UpdateStatusRequest;
import ptithcm.tttn.response.CartResponse;
import ptithcm.tttn.response.GetAllProductCouponRsp;
import ptithcm.tttn.service.*;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private final OrderRepo ordersRepo;
    private final UserService userService;
    private final CustomerService customerService;
    private final OrderDetailRepo orderDetailRepo;
    private final CartDetailService cartDetailService;
    private final StaffService staffService;
    private final BillRepo billRepo;
    private final ProductRepo productRepo;
    private final TypeRepo typeRepo;
    private final TransactionRequestRepo requestRepo;
    private final TransactionRequestDetailRepo detailRepo;
    private final OrderStatusRepo statusRepo;

    @Override
    @Transactional
    public Orders orderBuyNow(OrderRequest rq, String jwt) throws Exception {
        User user = userService.findUserByJwt(jwt);
        Customer customer = customerService.findByUserId(user.getUser_id());
        Optional<Product> product = productRepo.findById(rq.getProduct_id());
        OrderStatus status = statusRepo.findStatusIndex(1);
        Product get = product.get();
        Orders orders = new Orders();
        orders.setAddress(rq.getAddress());
        orders.setStatus_id(status.getStatus_id());
        orders.setCreated_at(LocalDateTime.now());
        orders.setRecipient_name(rq.getRecipient_name());
        orders.setUpdated_at(LocalDateTime.now());
        orders.setCustomer_id(customer.getCustomer_id());
        orders.setNote(rq.getNote());
        orders.setRecipient_phone(rq.getRecipient_phone());
        orders.setIs_payment(false);
        orders.setIs_cancel(false);
        orders.setIs_delivery(false);
        Orders createOrders = ordersRepo.save(orders);
        if (createOrders != null) {
            Order_detail orderDetail = new Order_detail();
            orderDetail.setOrder_id(createOrders.getOrder_id());
            orderDetail.setProduct_id(rq.getProduct_id());
            orderDetail.setPrice(rq.getPrice());
            orderDetail.setQuantity(rq.getQuantity());
            orderDetail.setQuantity(rq.getQuantity());
            get.setQuantity(get.getQuantity() - rq.getQuantity());
            productRepo.save(get);
            Order_detail createDetail = orderDetailRepo.save(orderDetail);
            if (createDetail != null) {
                int totalPrice = orderDetailRepo.totalPriceByOrderId(createOrders.getOrder_id());
                int totalQuantity = orderDetailRepo.totalQuantityByOrderId(createOrders.getOrder_id());
                createOrders.setTotal_quantity(totalQuantity);
                createOrders.setTotal_price(totalPrice);
                ordersRepo.save(createOrders);
            }
        }
        return createOrders;
    }

    @Override
    public List<Orders> findByJwtCustomer(String jwt) throws Exception {
        User user = userService.findUserByJwt(jwt);
        Customer customer = customerService.findByUserId(user.getUser_id());
        return ordersRepo.findByCustomerId(customer.getCustomer_id());
    }

    @Override
    public Orders findById(Long id) throws Exception {
        Optional<Orders> orders = ordersRepo.findById(id);
        if (orders.isPresent()) {
            return orders.get();
        }
        throw new Exception("not found order by id " + id);
    }

    @Override
    @Transactional
    public Orders updateStatus(UpdateStatusRequest rq, Long id) throws Exception {
        Orders findOrder = findById(id);
        if (rq.getIs_cancel()) {
            findOrder.setIs_cancel(true);
            for (Order_detail detail : findOrder.getOrderDetails()) {
                Optional<Product> productOpt = productRepo.findById(detail.getProduct_id());

                if (productOpt.isPresent()) {
                    Product product = productOpt.get();
                    product.setQuantity(product.getQuantity() + detail.getQuantity());
                    productRepo.save(product);
                }
            }
        }
        return ordersRepo.save(findOrder);

    }

    @Override
    public Orders updateStatusPayment(String status, Long id) throws Exception {
//        Orders findOrder = findById(id);
//        if (status.equals(OrderStatusDF.Payment.getOrderStatus())) {
//            //findOrder.setStatus(status);
//            Bill bill = new Bill();
//            bill.setOrder_id(findOrder.getOrder_id());
//            bill.setCreated_at(LocalDateTime.now());
//            billRepo.save(bill);
//            return ordersRepo.save(findOrder);
//        }
        throw new Exception("Not found order by id " + id);
    }

    @Override
    public Orders orderBuyCart(OrderRequest rq, String jwt) throws Exception {
        User user = userService.findUserByJwt(jwt);
        Customer customer = customerService.findByUserId(user.getUser_id());
        OrderStatus status = statusRepo.findStatusIndex(1);
        List<Order_detail> list = new ArrayList<>();
        int totalQuantity = 0;

        // Duyệt qua từng sản phẩm trong giỏ hàng
        for (CartResponse product : rq.getCart()) {
            Order_detail orderDetail = new Order_detail();

            // Lấy thông tin sản phẩm từ DB
            Optional<Product> find = productRepo.findById(product.getProduct_id());
            Product get = find.get();
            // Kiểm tra và cập nhật số lượng sản phẩm
            if (get.getQuantity() < product.getQuantity()) {
                throw new Exception("Not enough quantity for product: " + product.getProduct_id());
            }
            get.setQuantity(get.getQuantity() - product.getQuantity());
            productRepo.save(get);

            // Thiết lập thông tin Order_detail
            orderDetail.setProduct_id(product.getProduct_id());
            orderDetail.setPrice(product.getDiscounted_price());
            orderDetail.setQuantity(product.getQuantity());
            Order_detail createDetail = orderDetailRepo.save(orderDetail);

            totalQuantity += createDetail.getQuantity();
            list.add(createDetail);
        }

        // Tạo đơn hàng
        Orders orders = new Orders();
        orders.setCreated_at(LocalDateTime.now());
        orders.setNote(rq.getNote());
        orders.setAddress(rq.getAddress());
        orders.setRecipient_name(rq.getRecipient_name());
        orders.setRecipient_phone(rq.getRecipient_phone());
        orders.setUpdated_at(LocalDateTime.now());
        orders.setCustomer_id(customer.getCustomer_id());
        orders.setStatus_id(status.getStatus_id()); // Trạng thái đơn hàng
        orders.setTotal_price(rq.getTotal_price());
        orders.setTotal_quantity(totalQuantity);
        orders.setIs_cancel(false);
        orders.setIs_payment(false);
        orders.setIs_delivery(false);
        // Lưu Orders và cập nhật Order_id trong Order_detail
        Orders createdOrders = ordersRepo.save(orders);
        for (Order_detail item : list) {
            item.setOrder_id(createdOrders.getOrder_id());
            orderDetailRepo.save(item);
        }

        // Xóa giỏ hàng sau khi đặt hàng thành công
        cartDetailService.deleteCart(jwt);
        return createdOrders;
    }

    @Override
    public Orders orderPaymentBuyCart(OrderRequest rq, String jwt) throws Exception {
        User user = userService.findUserByJwt(jwt);
        Customer customer = customerService.findByUserId(user.getUser_id());
        OrderStatus status = statusRepo.findStatusIndex(1);
        List<Order_detail> list = new ArrayList<>();
        int totalQuantity = 0;

        // Duyệt qua từng sản phẩm trong giỏ hàng
        for (CartResponse product : rq.getCart()) {
            Order_detail orderDetail = new Order_detail();

            // Lấy thông tin sản phẩm từ DB
            Optional<Product> find = productRepo.findById(product.getProduct_id());
            Product get = find.get();
            // Kiểm tra và cập nhật số lượng sản phẩm
            if (get.getQuantity() < product.getQuantity()) {
                throw new Exception("Not enough quantity for product: " + product.getProduct_id());
            }
            get.setQuantity(get.getQuantity() - product.getQuantity());
            productRepo.save(get);

            // Thiết lập thông tin Order_detail
            orderDetail.setProduct_id(product.getProduct_id());
            orderDetail.setPrice(product.getDiscounted_price());
            orderDetail.setQuantity(product.getQuantity());
            Order_detail createDetail = orderDetailRepo.save(orderDetail);

            totalQuantity += createDetail.getQuantity();
            list.add(createDetail);
        }
        // Tạo đơn hàng
        Orders orders = new Orders();
        orders.setCreated_at(LocalDateTime.now());
        orders.setNote(rq.getNote());
        orders.setAddress(rq.getAddress());
        orders.setRecipient_name(rq.getRecipient_name());
        orders.setRecipient_phone(rq.getRecipient_phone());
        orders.setUpdated_at(LocalDateTime.now());
        orders.setCustomer_id(customer.getCustomer_id());
        orders.setIs_payment(true);
        orders.setIs_cancel(false);
        orders.setIs_delivery(false);
        orders.setStatus_id(status.getStatus_id()); // Trạng thái đơn hàng
        orders.setTotal_price(rq.getTotal_price());
        orders.setTotal_quantity(totalQuantity);

        // Lưu Orders và cập nhật Order_id trong Order_detail
        Orders createdOrders = ordersRepo.save(orders);
        for (Order_detail item : list) {
            item.setOrder_id(createdOrders.getOrder_id());
            orderDetailRepo.save(item);
        }

        // Xóa giỏ hàng sau khi đặt hàng thành công
        cartDetailService.deleteCart(jwt);
        return createdOrders;
    }


    //    @Override
//    public Orders orderBuyCart(OrderRequest rq, String jwt) throws Exception {
//        User user = userService.findUserByJwt(jwt);
//        Customer customer = customerService.findByUserId(user.getUser_id());
//        Cart cart = cartService.findCartByJwtCustomer(jwt);
//        List<OrderDetail> list = new ArrayList<>();
//        int totalQuantity = 0;
//        for (CartDetail detail : cart.getCartDetails()) {
//            OrderDetail orderDetail = new OrderDetail();
//            // orderDetail.setOder_id(createdOrders.getOrder_id());
//            orderDetail.setPrice(detail.getPrice());
//            orderDetail.setProduct_id(detail.getProduct_id());
//            Optional<Product> find = productRepo.findById(detail.getProduct_id());
//            Product get = find.get();
//            get.setQuantity(get.getQuantity()-detail.getQuantity());
//            productRepo.save(get);
//            orderDetail.setQuantity(detail.getQuantity());
//            OrderDetail createDetail = orderDetailRepo.save(orderDetail);
//            totalQuantity += createDetail.getQuantity();
//            list.add(createDetail);
//        }
//        Orders orders = new Orders();
//        orders.setCreated_at(LocalDateTime.now());
//        orders.setNote(rq.getNote());
//        orders.setAddress(rq.getAddress());
//        orders.setRecipient_name(rq.getRecipient_name());
//        orders.setRecipient_phone(rq.getRecipient_phone());
//        orders.setUpdated_at(LocalDateTime.now());
//        orders.setCreated_by(customer.getCustomer_id());
//        orders.setStatus("0");
//        orders.setTotal_price(rq.getTotal_price());
//        orders.setTotal_quantity(totalQuantity);
//        Orders createdOrders = ordersRepo.save(orders);
//        for (OrderDetail item : list) {
//            item.setOrder_id(createdOrders.getOrder_id());
//            orderDetailRepo.save(item);
//        }
//        cartDetailService.deleteCartDetail(cart.getCart_id());
//        cartService.autoUpdateCart(cart.getCart_id());
//        return createdOrders;
//    }
    @Override
    public List<StatisticRequest> getTotalAmountByMonth(int year) {
        List<Object[]> results = ordersRepo.getTotalAmountByMonth(year);
        return results.stream()
                .map(this::mapToStatisticRequest)
                .collect(Collectors.toList());
    }

    @Override
    public List<Orders> findAll() {
        return ordersRepo.findAll();
    }

    @Override
    public Page<Orders> findPageAll(Pageable pageable) {
        return ordersRepo.findAll(pageable);
    }

    @Override
    public List<ProductSaleRequest> getTotalAmountByDate(Date start, Date end) {
        List<Object[]> results = ordersRepo.getTotalAmountByDate(start, end);
        return results.stream()
                .map(this::mapToProductSaleRequest)
                .collect(Collectors.toList());

    }

    @Override
    public Orders updateStatusOrderByStaff(UpdateStatusRequest rq, Long id, String jwt) throws Exception {
        User user = userService.findUserByJwt(jwt);
        Staff staff = staffService.findByUserId(user.getUser_id());
        Orders findOrder = findById(id);
        List<Order_detail> orderDetails = orderDetailRepo.findOrderDetailByOrderId(findOrder.getOrder_id());

        if (rq.getIs_cancel()) {
            findOrder.setIs_cancel(true);
            for (Order_detail detail : orderDetails) {
                Optional<Product> productOpt = productRepo.findById(detail.getProduct_id());

                if (productOpt.isPresent()) {
                    Product product = productOpt.get();
                    product.setQuantity(product.getQuantity() + detail.getQuantity());
                    productRepo.save(product);
                }
            }
        } else {
            OrderStatus status = statusRepo.findStatusIndex(rq.getStatus_index());
            findOrder.setStatus_id(status.getStatus_id());
        }
        return ordersRepo.save(findOrder);
//        if(findOrder != null){
//            findOrder.setStatus(status);
//            findOrder.setStaff_id(staff.getStaff_id());
//            Orders saveOrder = ordersRepo.save(findOrder);
//            List<Order_detail> orderDetails = orderDetailRepo.findOrderDetailByOrderId(findOrder.getOrder_id());
//            if(saveOrder.getStatus().equals(OrderStatus.Delivered.getOrderStatus())){
//                Bill bill = new Bill();
//                bill.setOrder_id(findOrder.getOrder_id());
//                bill.setCreated_at(LocalDateTime.now());
//                bill.setStaff_id(staff.getStaff_id());
//                billRepo.save(bill);
//            } else if (findOrder.getStatus().equals(OrderStatus.Canceled.getOrderStatus())) {
//                for (Order_detail detail : orderDetails) {
//                    Optional<Product> productOpt = productRepo.findById(detail.getProduct_id());
//
//                    if (productOpt.isPresent()) {
//                        Product product = productOpt.get();
//                        product.setQuantity(product.getQuantity() + detail.getQuantity());
//                        productRepo.save(product);
//                    }
//                }
//            }
//            return saveOrder;
//        }
        //throw new Exception("Not found order by id " + id);
    }

    @Override
    public Orders orderPaymentBuyNow(OrderRequest rq, String jwt) throws Exception {
        User user = userService.findUserByJwt(jwt);
        Customer customer = customerService.findByUserId(user.getUser_id());
        OrderStatus status = statusRepo.findStatusIndex(1);
        Optional<Product> product = productRepo.findById(rq.getProduct_id());
        Product getProduct = product.get();
        Orders orders = new Orders();
        orders.setAddress(rq.getAddress());
        orders.setStatus_id(status.getStatus_id());
        orders.setCreated_at(LocalDateTime.now());
        orders.setRecipient_name(rq.getRecipient_name());
        orders.setUpdated_at(LocalDateTime.now());
        orders.setCustomer_id(customer.getCustomer_id());
        orders.setNote(rq.getNote());
        orders.setRecipient_phone(rq.getRecipient_phone());
        orders.setIs_payment(false);
        orders.setIs_cancel(false);
        orders.setIs_delivery(false);
        Orders createOrders = ordersRepo.save(orders);
        if (createOrders != null) {
            Order_detail orderDetail = new Order_detail();
            orderDetail.setOrder_id(createOrders.getOrder_id());
            orderDetail.setProduct_id(rq.getProduct_id());
            orderDetail.setPrice(rq.getPrice());
            orderDetail.setQuantity(rq.getQuantity());
            getProduct.setQuantity(getProduct.getQuantity() - rq.getQuantity());
            productRepo.save(getProduct);
            Order_detail createDetail = orderDetailRepo.save(orderDetail);
            if (createDetail != null) {
                int totalPrice = orderDetailRepo.totalPriceByOrderId(createOrders.getOrder_id());
                int totalQuantity = orderDetailRepo.totalQuantityByOrderId(createOrders.getOrder_id());
                createOrders.setTotal_quantity(totalQuantity);
                createOrders.setTotal_price(totalPrice);
                ordersRepo.save(createOrders);
            }

        }
        return createOrders;
    }

//    @Override
//    public Orders orderPaymentBuyCart(OrderRequest rq, String jwt) throws Exception {
//        User user = userService.findUserByJwt(jwt);
//        Customer customer = customerService.findByUserId(user.getUser_id());
//        Cart cart = cartService.findCartByJwtCustomer(jwt);
//        List<OrderDetail> list = new ArrayList<>();
//        int totalQuantity = 0;
//        for (CartDetail detail : cart.getCartDetails()) {
//            OrderDetail orderDetail = new OrderDetail();
//            // orderDetail.setOder_id(createdOrders.getOrder_id());
//            orderDetail.setPrice(detail.getPrice());
//            orderDetail.setProduct_id(detail.getProduct_id());
//            orderDetail.setQuantity(detail.getQuantity());
//            Optional<Product> find = productRepo.findById(detail.getProduct_id());
//            Product get = find.get();
//            get.setQuantity(get.getQuantity()-detail.getQuantity());
//            productRepo.save(get);
//            OrderDetail createDetail = orderDetailRepo.save(orderDetail);
//            totalQuantity += createDetail.getQuantity();
//            list.add(createDetail);
//        }
//        Orders orders = new Orders();
//        orders.setCreated_at(LocalDateTime.now());
//        orders.setNote(rq.getNote());
//        orders.setAddress(rq.getAddress());
//        orders.setRecipient_name(rq.getRecipient_name());
//        orders.setRecipient_phone(rq.getRecipient_phone());
//        orders.setUpdated_at(LocalDateTime.now());
//        orders.setCreated_by(customer.getCustomer_id());
//        orders.setStatus("3");
//        orders.setTotal_price(rq.getTotal_price());
//        orders.setTotal_quantity(totalQuantity);
//        Orders createdOrders = ordersRepo.save(orders);
//        for (OrderDetail item : list) {
//            item.setOrder_id(createdOrders.getOrder_id());
//            orderDetailRepo.save(item);
//        }
//        cartDetailService.deleteCartDetail(cart.getCart_id());
//        cartService.autoUpdateCart(cart.getCart_id());
//        return createdOrders;
//    }

    @Override
    public List<Orders> allOrderReceiveByStaff(String jwt) throws Exception {
        User user = userService.findUserByJwt(jwt);
        Staff staff = staffService.findByUserId(user.getUser_id());
        List<Orders> all = findAll();
        List<Orders> allReceive = new ArrayList<>();
        for (Orders od : all) {
            if (Objects.equals(od.getStaff_id(), staff.getStaff_id())) {
                allReceive.add(od);
            }
        }
        return allReceive;
    }

    @Override
    public List<StatisticRequest> getTotalPriceByStatus() {
        List<Object[]> results = ordersRepo.findTotalPriceByStatus();

        if (results == null || results.isEmpty()) {
            // Handle the case when no results are found
            return Collections.emptyList();  // Return an empty list instead of null
        }

        return results.stream()
                .filter(Objects::nonNull)  // Filter out null results
                .map(this::mapToStatisticRequestSale)
                .collect(Collectors.toList());
    }

    @Override
    public String isTransactionCreated(Long orderId) {
        // Gọi phương thức trong repository để kiểm tra
        return ordersRepo.isTransactionCreated(orderId);
    }

    @Override
    public Orders paymentSuccess(Long id, Boolean isPayment) throws Exception {
        Orders orders = findById(id);
        if (isPayment == null) {
            orders.setIs_payment(false);

        }
        orders.setIs_payment(isPayment);

        return ordersRepo.save(orders);
    }

    @Override
    public Page<Orders> getAllOrderDelivery(Pageable pageable) {
        return ordersRepo.getAllOrderDelivery(pageable);
    }

    @Override
    public Page<Orders> getAllOrderDeliveryByStaff(String jwt, Pageable pageable) throws Exception {
        User user = userService.findUserByJwt(jwt);
        Staff staff = staffService.findByUserId(user.getUser_id());
        return ordersRepo.getAllOrderDeliveryByStaff(staff.getStaff_id(), pageable);
    }

    @Override
    public Orders updateOrderShipper(Long id, String jwt, UpdateStatusRequest od) throws Exception {
        User user = userService.findUserByJwt(jwt);
        Staff staff = staffService.findByUserId(user.getUser_id());
        Orders orders = findById(id);
        orders.setIs_delivery(od.getIs_delivery());
        orders.setUpdated_by(staff.getStaff_id());
        return ordersRepo.save(orders);
    }

    @Override
    public Orders shipperAcceptOrder(Long id, String jwt) throws Exception {
        Orders orders = findById(id);
        User user = userService.findUserByJwt(jwt);
        Staff staff = staffService.findByUserId(user.getUser_id());
        orders.setStaff_id(staff.getStaff_id());
        return ordersRepo.save(orders);
    }

    @Override
    public Page<Orders> findOrdersByStatus(Long status_id, Pageable pageable) {
        return ordersRepo.searchOrderByStatusId(status_id, pageable);
    }

    @Override
    public Page<Orders> getOrdersByDateRange(LocalDate startDate, LocalDate endDate, Pageable pageable) {
        return ordersRepo.findOrdersByDateRange(startDate, endDate, pageable);
    }

    @Override
    public Page<Orders> getOrderByDateAndStatus(LocalDate startDate, LocalDate endDate, Long status_id, Pageable pageable) {
        return ordersRepo.findOrdersByDateAndStatus(startDate, endDate, status_id, pageable);
    }

    @Override
    public Page<Orders> getOrderByInfoCustomer(LocalDate startDate, LocalDate endDate, Long status_id, String receipt_name, String receipt_phone, Pageable pageable) {
        return ordersRepo.searchOrdersCustomer(startDate, endDate, status_id, receipt_name, receipt_phone, pageable);
    }

    @Override
    public Page<Orders> findByCustomerIdPage(String jwt, int page, int limit, String sortField, String sortDirection) throws Exception {
        User user = userService.findUserByJwt(jwt);
        Customer customer = customerService.findByUserId(user.getUser_id());
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortField);
        Pageable pageable = PageRequest.of(page - 1, limit, sort);
        return ordersRepo.findByCustomerIdPage(customer.getCustomer_id(), pageable);
    }

    @Override
    public Page<Orders> findOrderByCustomerAndStatus(String jwt, Orders orders, int page, int limit, String sortField, String sortDirection) throws Exception {
        User user = userService.findUserByJwt(jwt);
        Customer customer = customerService.findByUserId(user.getUser_id());
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortField);
        Pageable pageable = PageRequest.of(page - 1, limit, sort);
        String is_cancel = orders.getIs_cancel() ? "t" : "f";
        Long status_id;
        if (orders.getStatus_id() == -1) {
            status_id = null;
        } else {
            status_id = orders.getStatus_id();
        }
        System.err.println(is_cancel + "" + orders.getStatus_id() + customer.getCustomer_id());

        return ordersRepo.findOrderByCustomerAndStatusOrCancel(customer.getCustomer_id(), status_id, is_cancel, pageable);
    }

    @Override
    public Page<Orders> searchOrderByDatePage(String jwt, LocalDate startDate, LocalDate endDate, int page, int limit, String sortField, String sortDirection) throws Exception {
        User user = userService.findUserByJwt(jwt);
        Customer customer = customerService.findByUserId(user.getUser_id());
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortField);
        Pageable pageable = PageRequest.of(page - 1, limit, sort);
        return ordersRepo.searchOrderByDatePage(customer.getCustomer_id(), startDate, endDate, pageable);
    }

    private ProductSaleRequest mapToProductSaleRequest(Object[] result) {
        // Safely check if result is null
        if (result == null || result.length < 5) {
            throw new IllegalArgumentException("Invalid data in result array");
        }

        String productId = (String) result[0];
        String productName = (String) result[1];
        BigDecimal totalSoldQuantity = (BigDecimal) result[2];
        BigDecimal totalQuantity = (BigDecimal) result[3];
        Date datePay = (Date) result[4];

        return new ProductSaleRequest(productId, productName, totalSoldQuantity, totalQuantity, datePay);
    }

    private StatisticRequest mapToStatisticRequest(Object[] result) {
        int month = (int) result[0];
        long price = (long) result[1];
        return new StatisticRequest(month, price);
    }

    private StatisticRequest mapToStatisticRequestSale(Object[] result) {
        long price = (long) result[0];
        return new StatisticRequest(price);
    }
}
