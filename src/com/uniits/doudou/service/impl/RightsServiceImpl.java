package com.uniits.doudou.service.impl;

import java.util.List;

import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.QueryResult;
import org.nutz.dao.pager.Pager;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Lang;
import org.nutz.lang.util.NutMap;
import org.nutz.service.IdEntityService;

import com.uniits.doudou.model.FirmModel;
import com.uniits.doudou.model.Producecar;
import com.uniits.doudou.model.Sn;
import com.uniits.doudou.model.SystemModel;
import com.uniits.doudou.service.RightsService;

/**
 * 
 * @author hxn
 *
 */
@IocBean(name="rigistService" ,args = {"refer:dao"})
public class RightsServiceImpl extends IdEntityService<Sn> implements RightsService{

	public RightsServiceImpl() {
		super();
	}

	public RightsServiceImpl(Dao dao, Class<Sn> entityType) {
		super(dao, entityType);
	}

	public RightsServiceImpl(Dao dao) {
		super(dao);
	}

	@Override
	public void addRightscar(Producecar p) {
		// TODO Auto-generated method stub
		dao().insert(p);
		
	}
	
	@Override
	public int isProdName(String prodName){
		Cnd cnd = Cnd.where("1","=","1");
		List<Producecar> cars = dao().query(Producecar.class, cnd.and("name", "=", prodName));
		if(cars.size()>0){
			return 1 ;
		}else{
			return 0;
		}
		
	}
	

	@Override
	public QueryResult findPage(int pageNo, int pageSize, NutMap map) {

		
		Cnd cnd = Cnd.where("1","=","1");
		
		if(!Lang.isEmpty(map)) {
			if(!Lang.isEmpty(map.getString("name"))) {
				cnd.and("value","LIKE","%" + map.getString("name") + "%");
			}
			
		}
        cnd.desc("id");
        Pager pager = dao().createPager(pageNo, pageSize);
        List<Producecar> list = dao().query(Producecar.class, cnd, pager);
        
        if(!Lang.isEmpty(list)){
        	int k=(pageNo-1)*pageSize;
        	for(int i=0; i<list.size(); i++){
        		k++;
        		list.get(i).setSerialNumber((long) (k));
        	}
        }else{
        	pager.setPageNumber(0);
        }
        
        pager.setRecordCount(dao().count(Producecar.class,cnd));
        
        return new QueryResult(list, pager);
	
		
	}

	@Override
	public void deleteProduce(long id){
		dao().delete(Producecar.class, id);
//		 delete(id);
	}
	
	
    @Override
    public List<Producecar> findAll() {
        return dao().query(Producecar.class,null);
    }

    @Override
    public List<Producecar> find(NutMap nm) {
        // TODO Auto-generated method stub
        return null;
    }

	@Override
	public int isSelectRights(long id) {
		Cnd cnd = Cnd.where("1","=","1");
		
		if(!Lang.isEmpty(id)) {			
				cnd.and("produce_id","=",id);
			
		}
        cnd.desc("id");
        
        List<SystemModel> list = dao().query(SystemModel.class, cnd, null);
        if(list.size()>0){
        	return 1;
        }else{
        	List<FirmModel> list1 = dao().query(FirmModel.class, cnd, null);
        	if(list1.size()>0){
        		return 1;
        	}
        	
        }
		return 0;
	}
	
	

}
