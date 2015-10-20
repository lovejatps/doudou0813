/**
 * @description doudou com.uniits.doudou.service
 */
package com.uniits.doudou.service;

import java.util.List;

import org.nutz.dao.QueryResult;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.util.NutMap;

import com.uniits.doudou.model.Phone;
import com.uniits.doudou.model.Register;

/**
 * @author liuchengwei
 * @since 2015-3-16 下午3:13:04
 * @description
 */
public interface RegistService {

    public void addRegist(Register register);

    public NutMap addRegist(String vin,String id);
    
    public List<Register> findAll();

    public List<Register> find(NutMap map);
    
    public void updateRegister(Register register);
    
    public void deleteRegister(long id);
    
    
    public Register fetchRegist(long id);

	public QueryResult findPage(int pageNo, int pageSize, NutMap map);
	
	public Phone findOneCall();
	
	public void updateOneCall(Phone phone);
    
}
