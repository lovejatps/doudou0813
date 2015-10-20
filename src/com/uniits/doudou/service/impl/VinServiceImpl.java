/**
 * @description doudou com.uniits.doudou.service.impl
 */
package com.uniits.doudou.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.QueryResult;
import org.nutz.dao.pager.Pager;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Lang;
import org.nutz.lang.util.NutMap;
import org.nutz.service.IdEntityService;

import com.uniits.doudou.model.Sn;
import com.uniits.doudou.model.Vin;
import com.uniits.doudou.service.VinService;

/**
 * @author liuchengwei
 * @since 2015-3-17 下午3:42:30
 * @description 
 */
@IocBean(name="vinService" ,args = {"refer:dao"})
public class VinServiceImpl extends IdEntityService<Vin> implements VinService {
    
    public VinServiceImpl() {
        super();
    }

    public VinServiceImpl(Dao dao, Class<Vin> entityType) {
        super(dao, entityType);
    }

    public VinServiceImpl(Dao dao) {
        super(dao);
    }

    @Override
    public void addVin(Vin v) {
        dao().insert(v);
    }

    @Override
    public Vin fetchVin(long id) {
        return fetch(id);
    }

    @Override
    public List<Vin> findAll() {
        return null;
    }

    @Override
    public List<Vin> find(NutMap map) {
        return null;
    }

    @Override
    public void deleteVin(long id) {
        delete(id);
    }

    @Override
    public Vin fetchVin(String vin) {
        return dao().fetch(Vin.class, Cnd.where("value","=",vin));
    }

	@Override
	public List<Vin> addVinBatch(List<Vin> list) {
		List<Vin> successList = new ArrayList<Vin>();
		for(Vin vin : list){
			addVin(vin);
			successList.add(vin);
		}
		return successList;
	}
	
	@Override
	public QueryResult findPage(int pageNo, int pageSize, NutMap map) {
		
		Cnd cnd = Cnd.where("1","=","1");
		
		if(!Lang.isEmpty(map)) {
			if(!Lang.isEmpty(map.getString("vin"))) {
				cnd.and("value","LIKE","%" + map.getString("vin") + "%");
			}
			
			if(!Lang.isEmpty(map.getString("startDate"))) {
				cnd.and("left(create_date,10)",">=", map.getString("startDate"));
			}
			
			if(!Lang.isEmpty(map.getString("endDate"))) {
				cnd.and("left(create_date,10)","<=", map.getString("endDate"));
			}
			
		}
        cnd.desc("createDate").desc("id");
        Pager pager = dao().createPager(pageNo, pageSize);
        List<Vin> list = dao().query(Vin.class, cnd, pager);
        
        if(!Lang.isEmpty(list)){
        	int k=(pageNo-1)*pageSize;
        	for(int i=0; i<list.size(); i++){
        		k++;
        		list.get(i).setSerialNumber((long) (k));
        	}
        }else{
        	pager.setPageNumber(0);
        }
        
        pager.setRecordCount(dao().count(Vin.class,cnd));
        
        return new QueryResult(list, pager);
	}
}
