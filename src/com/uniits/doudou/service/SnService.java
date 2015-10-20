/**
 * @description doudou com.uniits.doudou.service
 */
package com.uniits.doudou.service;

import java.util.List;

import org.nutz.dao.QueryResult;
import org.nutz.lang.util.NutMap;

import com.uniits.doudou.model.Sn;

/**
 * @author liuchengwei
 * @since 2015-3-17 下午3:29:58
 * @description 
 */
public interface SnService {

    public void addSn(Sn sn);
    
    public List<Sn> addSnBatch(List<Sn> list);
    
    public List<Sn> findAll();
    
    public List<Sn> find(NutMap nm);
    
    public void deleteSn(long id);
    
    public Sn fetchSn(long id);
    
    public Sn fecthSn(String sn);

	public QueryResult findPage(int pageNo, int pageSize, NutMap map);
    
}
