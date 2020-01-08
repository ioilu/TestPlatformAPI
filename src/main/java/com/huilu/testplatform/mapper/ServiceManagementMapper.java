package com.huilu.testplatform.mapper;

import com.huilu.testplatform.entity.dao.ServiceManagement;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceManagementMapper {
    List<ServiceManagement> queryAll();

    @Insert("insert into servcie_management (code,name,host,comment,create_time,update_time) values (#{code},#{name},#{host},#{comment},#{createtime},#{updatetime})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertService(ServiceManagement serviceManagement);

    int updateServiceById(ServiceManagement serviceManagement);

    ServiceManagement getOneService(int id);

}
