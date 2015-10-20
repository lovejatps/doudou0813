/**
 * @description doudou com.uniits.doudou.service
 */
package com.uniits.doudou.service;

import java.util.List;

import org.nutz.dao.QueryResult;
import org.nutz.lang.util.NutMap;

import com.uniits.doudou.model.Vin;

/**
 * @author liuchengwei
 * @since 2015-3-17 下午2:47:37
 * @description 
 */
public interface VinService {

    public void addVin(Vin v);
    
    public Vin fetchVin(long id);
    
    public Vin fetchVin(String vin);
    
    public List<Vin> findAll();
    
    public List<Vin> find(NutMap map);
    
    public void deleteVin(long id);

	public List<Vin> addVinBatch(List<Vin> list);

	public QueryResult findPage(int pageNo, int pageSize, NutMap map);
    
}
