package cn.org.citycloud.zwhs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import cn.org.citycloud.zwhs.entity.ServiceProvider;

public interface ServiceProviderDao extends JpaRepository<ServiceProvider, Integer>,
    JpaSpecificationExecutor<ServiceProvider>
{
    
    public ServiceProvider findByProviderId(int id);
}
