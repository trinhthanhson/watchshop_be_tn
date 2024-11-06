package ptithcm.tttn.service.impl;

import org.springframework.stereotype.Service;
import ptithcm.tttn.entity.Category;
import ptithcm.tttn.entity.Staff;
import ptithcm.tttn.entity.Supplier;
import ptithcm.tttn.entity.User;
import ptithcm.tttn.function.Status;
import ptithcm.tttn.repository.StaffRepo;
import ptithcm.tttn.repository.SupplierRepo;
import ptithcm.tttn.service.SupplierService;
import ptithcm.tttn.service.UserService;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepo supplierRepo;
    private final UserService userService;
    private final StaffRepo staffRepo;

    public SupplierServiceImpl(SupplierRepo supplierRepo, UserService userService, StaffRepo staffRepo) {
        this.supplierRepo = supplierRepo;
        this.userService = userService;
        this.staffRepo = staffRepo;
    }

    @Override
    public List<Supplier> findAll() {
        return supplierRepo.findAll();
    }

    @Override
    public Supplier findById(Long id) throws Exception {
        Optional<Supplier> find = supplierRepo.findById(id);
        if(find.isPresent()){
            return find.get();
        }
        throw new Exception("Not found supplier by id " +id);
    }

    @Override
    public Supplier createSupplier(Supplier supplier, String jwt) throws Exception {
        Supplier create = new Supplier();
        User user = userService.findUserByJwt(jwt);
        Staff staff = staffRepo.findByUserId(user.getUser_id());
        Supplier saveSupplier = new Supplier();
        Supplier checkExist = supplierRepo.findSupplierByName(supplier.getSupplier_name());
        if(checkExist == null){
            try {
                create.setCreated_at(LocalDateTime.now());
                create.setCreated_by(staff.getStaff_id());
                create.setSupplier_name(supplier.getSupplier_name());
                create.setFax(supplier.getFax());
                create.setEmail(supplier.getEmail());
                create.setAddress(supplier.getAddress());
                create.setTax_id(supplier.getTax_id());
                create.setPhone(supplier.getPhone());
                create.setUpdated_at(LocalDateTime.now());
                create.setUpdated_by(staff.getStaff_id());
                create.setStatus(Status.ACTIVE.getUserStatus());
                saveSupplier = supplierRepo.save(create);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }else if(checkExist != null && checkExist.getStatus().equals(Status.INACTIVE.getUserStatus())) {
            checkExist.setStatus(Status.ACTIVE.getUserStatus());
            checkExist.setUpdated_by(staff.getStaff_id());
            return supplierRepo.save(checkExist);
        }
        else{
            throw new Exception("exist category name " + supplier.getSupplier_name());
        }
        return saveSupplier;
    }

    @Override
    public Supplier updateSupplier(Long id, Supplier supplier, String jwt) throws Exception {
        Supplier find = findById(id);
        boolean checkExist = checkExitsCategory(supplier.getSupplier_name());
        Supplier save = new Supplier();
        if(!checkExist || (find.getSupplier_name().equals(supplier.getSupplier_name()))){
            try{
                User user = userService.findUserByJwt(jwt);
                Staff staff = staffRepo.findByUserId(user.getUser_id());
                find.setSupplier_name(supplier.getSupplier_name());
                find.setAddress(supplier.getAddress());
                find.setTax_id(supplier.getTax_id());
                find.setEmail(supplier.getEmail());
                find.setFax(supplier.getFax());
                find.setPhone(supplier.getPhone());
                find.setUpdated_by(staff.getStaff_id());
                find.setUpdated_at(LocalDateTime.now());
                save = supplierRepo.save(find);
            }catch (Exception e){
                throw new Exception("error " + e.getMessage());
            }
        }else{
            throw new Exception("exist category by name " + supplier.getSupplier_name());
        }
        return save;
    }

    @Transactional
    @Override
    public Supplier deleteSupplier(Long id, String jwt) throws Exception {
        User user = userService.findUserByJwt(jwt);
        Staff staff = staffRepo.findByUserId(user.getUser_id());
        Supplier find = findById(id);
        if(find.getStatus().equals(Status.ACTIVE.getUserStatus())){
            find.setStatus(Status.INACTIVE.getUserStatus());
            find.setUpdated_by(staff.getStaff_id());
            return supplierRepo.save(find);
        } else if (find.getStatus().equals(Status.INACTIVE.getUserStatus())) {
            find.setStatus(Status.ACTIVE.getUserStatus());
            find.setUpdated_by(staff.getStaff_id());
            return supplierRepo.save(find);
        }

        throw new Exception("Not found category by id " + id);
    }

    @Override
    public Supplier findByName(String name) throws Exception {
        Supplier supplier = supplierRepo.findSupplierByName(name);
        if (supplier != null) {
            return supplier;
        }
        throw new Exception("Not found category by name " + name);
    }

    @Override
    public boolean checkExitsCategory(String name) {
        Supplier supplier = supplierRepo.findSupplierByName(name);
        if(supplier != null){
            return true;
        }else{
            return false;
        }
    }
}
