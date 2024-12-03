package ptithcm.tttn.service;

import ptithcm.tttn.entity.Product;
import ptithcm.tttn.request.ProductRequest;
import ptithcm.tttn.request.ProductSaleRequest;
import ptithcm.tttn.response.GetAllProductCouponRsp;
import ptithcm.tttn.response.QuantityInventoryRsp;
import ptithcm.tttn.response.RevenueProductReportRsp;

import java.util.Date;
import java.util.List;

public interface ProductService {
    List<Product> findAll();

    Product findById(String id) throws Exception;

    List<GetAllProductCouponRsp> findByDetail(String desc);

    Product findByName(String name) throws Exception;

    List<Product> findByCategoryId(Long id) throws Exception;

    List<Product> findByBrandId(Long id) throws Exception;

    Product createProduct(ProductRequest product, String jwt) throws Exception;

    Product updateProduct(String id, ProductRequest product, String jwt) throws Exception;

    boolean checkExistProductName(String name) throws Exception;

    Product deleteProduct(String product_id, String jwt) throws Exception;

    List<ProductSaleRequest> getProductSales();

    List<ProductSaleRequest> getProductSalesReport();

    List<QuantityInventoryRsp> getQuantityProductReport(String filter, Date startDate, Date endDate);

    List<RevenueProductReportRsp> getRevenueProductReport(String filter, Date startDate, Date endDate);

     List<GetAllProductCouponRsp> getAllProductCoupon();


}
