package ptithcm.tttn.service;

import ptithcm.tttn.entity.Supplier;

import java.util.List;

public interface SupplierService {

    List<Supplier> findAll();

    Supplier findById(Long id) throws Exception;

    Supplier createSupplier(Supplier supplier,String jwt) throws Exception;

    Supplier updateSupplier(Long id,Supplier supplier, String jwt) throws Exception;

    Supplier deleteSupplier(Long id, String jwt) throws Exception;

    Supplier findByName(String name) throws Exception;

    boolean checkExitsSupplier(String name);
}
