package ptithcm.tttn.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ptithcm.tttn.entity.*;
import ptithcm.tttn.function.Status;
import ptithcm.tttn.repository.ProductRepo;
import ptithcm.tttn.repository.UpdatePriceRepo;
import ptithcm.tttn.request.ProductRequest;
import ptithcm.tttn.request.ProductSaleRequest;
import ptithcm.tttn.response.GetAllProductCouponRsp;
import ptithcm.tttn.response.QuantityInventoryRsp;
import ptithcm.tttn.response.RevenueProductReportRsp;
import ptithcm.tttn.service.*;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    private final UserService userService;
    private final StaffService staffService;
    private final ProductRepo productRepo;
    private final BrandService brandService;
    private final CategoryService categoryService;
    private final UpdatePriceRepo updatePriceRepo;

    public ProductServiceImpl(UserService userService, StaffService staffService, ProductRepo productRepo, BrandService brandService, CategoryService categoryService, UpdatePriceRepo updatePriceRepo) {

        this.userService = userService;
        this.staffService = staffService;
        this.productRepo = productRepo;
        this.brandService = brandService;
        this.categoryService = categoryService;
        this.updatePriceRepo = updatePriceRepo;
    }

    @Override
    public List<Product> findAll() {
        return productRepo.findAll();
    }

    @Override
    public Page<Product> findAllPageable(Pageable pageable) {
        return productRepo.findAll(pageable);
    }


    @Override
    public Product findById(String id) throws Exception {
        Optional<Product> find = productRepo.findById(id);
        if (find.isPresent()) {
            return find.get();
        }
        throw new Exception("Not found product by id " + id);
    }

    @Override
    public Page<Product> searchProductById(String product_id,Pageable pageable) {
        return productRepo.searchProductById(product_id, pageable);
    }

    @Override
    public List<GetAllProductCouponRsp> findByDetail(String desc) {
    List<Object[]> results = productRepo.searchProducts(desc);
        return results.stream()
                .map(this::mapToGetAllProductCoupon)
                .collect(Collectors.toList());
    }

    @Override
    public Product findByName(String name) throws Exception {
        Product find = productRepo.findByName(name);
        if (find != null) {
            return find;
        }
        throw new Exception("not found product by name " + name);
    }

    @Override
    public List<Product> findByCategoryId(Long id) throws Exception {
        List<Product> find = productRepo.findByCategoryId(id);
        if (find != null) {
            return find;
        }
        throw new Exception("Not found product by category id " + id);

    }

    @Override
    public List<Product> findByBrandId(Long id) throws Exception {
        List<Product> find = productRepo.findByBrandId(id);
        if (find != null) {
            return find;
        }
        throw new Exception("Not found product by brand id " + id);
    }

    @Override
    @Transactional
    public Product createProduct(ProductRequest product, String jwt) throws Exception {
        Product create = new Product();
        User user = userService.findUserByJwt(jwt);
        Staff staff = staffService.findByUserId(user.getUser_id());
        Brand brand = brandService.findByName(product.getBrand_name());
        Category category = categoryService.findByName(product.getCategory_name());
        String id = generateNewProductId();
        Product save = new Product();
        if (!checkExistProductName(product.getProduct_name())) {
            create.setProduct_name(product.getProduct_name());
            create.setProduct_id(id);
            create.setCreated_at(LocalDateTime.now());
            create.setCreated_by(staff.getStaff_id());
            create.setBrand_id(brand.getBrand_id());
            create.setCategory_id(category.getCategory_id());
            create.setQuantity(product.getQuantity());
            create.setUpdated_at(LocalDateTime.now());
            create.setUpdated_by(staff.getStaff_id());
            create.setStatus(Status.ACTIVE.getUserStatus());
            create.setBand_material(product.getBand_material());
            create.setBand_width(product.getBand_width());
            create.setCase_diameter(product.getCase_diameter());
            create.setCase_material(product.getCase_material());
            create.setCase_thickness(product.getCase_thickness());
            create.setColor(product.getColor());
            create.setDetail(product.getDetail());
            create.setDial_type(product.getDial_type());
            create.setFunc(product.getFunc());
            create.setGender(product.getGender());
            create.setMachine_movement(product.getMachine_movement());
            create.setModel(product.getModel());
            create.setQuantity(product.getQuantity());
            create.setSeries(product.getSeries());
            create.setWater_resistance(product.getWater_resistance());
            create.setImage(product.getImage());
            save = productRepo.save(create);
            if (save != null) {
                Update_price updatePrice = new Update_price();
                updatePrice.setCreated_at(LocalDateTime.now());
                updatePrice.setProduct_id(save.getProduct_id());
                updatePrice.setUpdated_at(LocalDateTime.now());
                updatePrice.setCreated_by(staff.getStaff_id());
                updatePrice.setUpdated_by(staff.getStaff_id());
                updatePrice.setPrice_new(product.getPrice());
                updatePrice.setPrice_old(product.getPrice());
                updatePriceRepo.save(updatePrice);
            }
        } else {
            throw new Exception("exist product by name " + product.getProduct_name());
        }
        return save;
    }

    @Override
    public Product updateProduct(String id, ProductRequest product, String jwt) throws Exception {
        Product find = findById(id);
        User user = userService.findUserByJwt(jwt);
        Staff staff = staffService.findByUserId(user.getUser_id());
        Brand brand = brandService.findByName(product.getBrand_name());
        Category category = categoryService.findByName(product.getCategory_name());
        Product update = new Product();

        if (find != null) {
            if (checkExistProductName(product.getProduct_name())) {
                find.setProduct_name(product.getProduct_name());
                find.setCreated_at(LocalDateTime.now());
                find.setCreated_by(staff.getStaff_id());
                find.setBrand_id(brand.getBrand_id());
                find.setCategory_id(category.getCategory_id());
                find.setQuantity(product.getQuantity());
                find.setUpdated_at(LocalDateTime.now());
                find.setUpdated_by(staff.getStaff_id());
                find.setStatus(Status.ACTIVE.getUserStatus());
                find.setBand_material(product.getBand_material());
                find.setBand_width(product.getBand_width());
                find.setCase_diameter(product.getCase_diameter());
                find.setCase_material(product.getCase_material());
                find.setCase_thickness(product.getCase_thickness());
                find.setColor(product.getColor());
                find.setDetail(product.getDetail());
                find.setDial_type(product.getDial_type());
                find.setFunc(product.getFunc());
                find.setGender(product.getGender());
                find.setMachine_movement(product.getMachine_movement());
                find.setModel(product.getModel());
                find.setQuantity(product.getQuantity());
                find.setSeries(product.getSeries());
                find.setWater_resistance(product.getWater_resistance());
                find.setImage(product.getImage());
                Update_price updatePrice = updatePriceRepo.findByProductId(id);
                if (product.getPrice() != updatePrice.getPrice_new()) {
                    updatePrice.setPrice_old(updatePrice.getPrice_new());
                    updatePrice.setPrice_new(product.getPrice());
                    updatePrice.setUpdated_by(staff.getStaff_id());
                    updatePrice.setUpdated_at(LocalDateTime.now());
                    updatePriceRepo.save(updatePrice);
                }
            } else {
                find.setProduct_name(product.getProduct_name());
            }
        }
        return update = productRepo.save(find);
    }

    @Override
    public boolean checkExistProductName(String name) throws Exception {
        Product find = productRepo.findByName(name);
        if (find != null) {
            return true;
        }
        return false;
    }


    @Override
    @Transactional
    public Product deleteProduct(String product_id, String jwt) throws Exception {
        User user = userService.findUserByJwt(jwt);
        Staff staff = staffService.findByUserId(user.getUser_id());
        Product product = findById(product_id);
        product.setStatus(Status.INACTIVE.getUserStatus());
        product.setUpdated_by(staff.getStaff_id());
        return productRepo.save(product);
    }


    @Override
    public List<ProductSaleRequest> getProductSales() {
        List<Object[]> results = productRepo.getProductSalesTop5();
        return results.stream()
                .map(this::mapToProductSaleRequest)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductSaleRequest> getProductSalesReport() {
        List<Object[]> results = productRepo.getProductSalesReport();
        return results.stream()
                .map(this::mapToProductSaleRequest)
                .collect(Collectors.toList());
    }

    @Override
    public Page<QuantityInventoryRsp> getQuantityProductReport(
            String filter, Date startDate, Date endDate, int page, int limit, String sortField, String sortDirection) {

        // Xử lý sắp xếp theo trường và thứ tự
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortField);
        Pageable pageable = PageRequest.of(page - 1, limit, sort);

        // Lấy dữ liệu từ repository
        Page<Object[]> results = productRepo.getQuantityInventoryByFilter(filter, startDate, endDate, pageable);

        // Ánh xạ dữ liệu từ Object[] sang QuantityInventoryRsp
        return results.map(this::mapToQuantityInventory);
    }

    @Override
    public List<RevenueProductReportRsp> getRevenueProductReport(String filter, Date startDate, Date endDate) {
        List<Object[]> results = productRepo.getRevenueProduct(filter, startDate, endDate);
        return results.stream()
                .map(this::mapToRevenueProduct)
                .collect(Collectors.toList());
    }

    @Override
    public List<GetAllProductCouponRsp> getAllProductCoupon() {
        List<Object[]> results = productRepo.getAllProductOrProductByCoupon();
        return results.stream()
                .map(this::mapToGetAllProductCoupon)
                .collect(Collectors.toList());
    }

    private String generateNewProductId() {

        List<Product> products = productRepo.findAll();
        int maxId = 0;
        for (Product p : products) {
            String idStr = p.getProduct_id().substring(2);  // Remove "DH" prefix
            int id = Integer.parseInt(idStr);
            if (id > maxId) {
                maxId = id;
            }
        }
        return String.format("DH%08d", maxId + 1);
    }

    private ProductSaleRequest mapToProductSaleRequest(Object[] result) {
        String productId = (String) result[0];
        String productName = (String) result[1];
        BigDecimal totalSoldQuantity = (BigDecimal) result[2];
        BigDecimal totalQuanity = (BigDecimal) result[3];
        return new ProductSaleRequest(productId, productName, totalSoldQuantity, totalQuanity, null);
    }

    private QuantityInventoryRsp mapToQuantityInventory(Object[] result) {
        String product_id = (String) result[0];
        String product_name = (String) result[1];
        String image = (String) result[2];
        Integer quantity = (Integer) result[3];
        Long total_import = (Long) result[4];
        Long total_export = (Long) result[5];
        Long current_stock = (Long) result[6];
        String info = (String) result[7];
        return new QuantityInventoryRsp(product_id, product_name, image, quantity, total_import, total_export, current_stock, info);
    }

    private RevenueProductReportRsp mapToRevenueProduct(Object[] result) {
        String product_id = (String) result[0];
        String product_name = (String) result[1];
        String image = (String) result[2];
        BigDecimal total_sold = (BigDecimal) result[3];
        BigDecimal total_quantity = (BigDecimal) result[4];
        String period_value = (String) result[5];
        Date date_range = (Date) result[6];
        return new RevenueProductReportRsp(product_id, product_name, image, total_sold, total_quantity, period_value, date_range);
    }

    private GetAllProductCouponRsp mapToGetAllProductCoupon(Object[] result) {
       
        String product_id = (String) result[0];
        String band_material = (String) result[1];
        String band_width = (String) result[2];
        BigInteger brand_id = (BigInteger) result[3];
        String case_diameter = (String) result[4];
        String case_material = (String) result[5];
        String case_thickness = (String) result[6];
        BigInteger category_id = (BigInteger) result[7];
        String color = (String) result[8];
        String detail = (String) result[9];
        String dial_type = (String) result[10];
        String func = (String) result[11];
        String gender = (String) result[12];
        String machine_movement = (String) result[13];
        String model = (String) result[14];
        String product = (String) result[15];
        Integer quantity = (Integer) result[16];
        String series = (String) result[17];
        String water_resistance = (String) result[18];
        String image = (String) result[19];
        BigInteger created_by = (BigInteger) result[20];
        BigInteger updated_by = (BigInteger) result[21];
        String status = (String) result[22];
        Timestamp created_at = (Timestamp) result[23];
        Timestamp updated_at = (Timestamp) result[24];
        String brand_name = (String) result[25];
        String category_name = (String) result[26];
        String created_by_name = (String) result[27];
        String updated_by_name = (String) result[28];
        String currentPrice =(String) result[29];// Or whatever index the discounted price is
        Double current_price = parseToDouble(currentPrice);

        Double discounted_price = (Double) result[30];
        return new GetAllProductCouponRsp(product_id, band_material, band_width, brand_id, case_diameter, case_material,
                case_thickness, category_id, color, detail, dial_type, func, gender, machine_movement, model, product, quantity,null,
                series, water_resistance, image, created_by, updated_by, status, created_at, updated_at, brand_name, category_name,
                created_by_name, updated_by_name, current_price, discounted_price);
    }
    private Double parseToDouble(String value) {
        try {
            return value != null ? Double.parseDouble(value) : 0.0;
        } catch (NumberFormatException e) {
            return 0.0; // Fallback in case of invalid format
        }
    }
}
