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
import com.uniits.doudou.service.SnService;

/**
 * @author liuchengwei
 * @since 2015-3-17 下午3:40:19
 * @description 
 */
@IocBean(name="snService" ,args = {"refer:dao"})
public class SnServiceImpl extends IdEntityService<Sn> implements SnService {
    
    public SnServiceImpl() {
        super();
    }

    public SnServiceImpl(Dao dao, Class<Sn> entityType) {
        super(dao, entityType);
    }

    public SnServiceImpl(Dao dao) {
        super(dao);
    }
    
    //以上必须

    @Override
    public void addSn(Sn sn) {
        dao().insert(sn);
    }

    @Override
    public List<Sn> findAll() {
        return dao().query(Sn.class,null);
    }

    @Override
    public List<Sn> find(NutMap nm) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void deleteSn(long id) {
        delete(id);
    }

    @Override
    public Sn fetchSn(long id) {
        return fetch(id);
    }

    @Override
    public Sn fecthSn(String sn) {
        return dao().fetch(Sn.class,Cnd.where("value","=",sn));
    }

    @Override
    public List<Sn> addSnBatch(List<Sn> list) {
    	List<Sn> successList = new ArrayList<Sn>();
		for(Sn sn : list){
			this.addSn(sn);
			successList.add(sn);
		}
		return successList;
    }

	@Override
	public QueryResult findPage(int pageNo, int pageSize, NutMap map) {
		
		Cnd cnd = Cnd.where("1","=","1");
		
		if(!Lang.isEmpty(map)) {
			if(!Lang.isEmpty(map.getString("sn"))) {
				cnd.and("value","LIKE","%" + map.getString("sn") + "%");
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
        List<Sn> list = dao().query(Sn.class, cnd, pager);
        
        if(!Lang.isEmpty(list)){
        	int k=(pageNo-1)*pageSize;
        	for(int i=0; i<list.size(); i++){
        		k++;
        		list.get(i).setSerialNumber((long) (k));
        	}
        }else{
        	pager.setPageNumber(0);
        }
        
        pager.setRecordCount(dao().count(Sn.class,cnd));
        
        return new QueryResult(list, pager);
	}

}
