package cn.org.citycloud.zwhs.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import cn.org.citycloud.zwhs.entity.CashRecoder;

public interface CashRecoderDao extends JpaRepository<CashRecoder, Integer>, JpaSpecificationExecutor<CashRecoder>
{
    
    public List<CashRecoder> findByStoreId(int storeId);
    
    public Page<CashRecoder> findByStoreIdAndAccType(int storeId, int accType, Pageable pageable);
    
    public CashRecoder findByCashNo(int cashNo);
}
