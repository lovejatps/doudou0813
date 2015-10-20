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

import com.uniits.doudou.model.CarMachine;
import com.uniits.doudou.model.Sn;
import com.uniits.doudou.service.CarMachineService;

/**
 * @author liuchengwei
 * @since 2015-3-17 下午3:34:29
 * @description 
 */
@IocBean(name="carMachineService" ,args = {"refer:dao"})
public class CarMachineServiceImpl extends IdEntityService<CarMachine> implements CarMachineService {
    
    public CarMachineServiceImpl() {
        super();
    }

    public CarMachineServiceImpl(Dao dao, Class<CarMachine> entityType) {
        super(dao, entityType);
    }

    public CarMachineServiceImpl(Dao dao) {
        super(dao);
    }
    
    //以上必须

    @Override
    public void addCarMachine(CarMachine carMachine) {
    	dao().insert(carMachine);
    }

    @Override
    public CarMachine fetchCarMachine(long id) {
        // TODO Auto-generated method stub
        return null;
    }
    
    public void deleteCarMachine(Long id){
    	delete(id);
    }

    @Override
    public CarMachine fetchCarMachine(String carID) {
        Cnd cnd = Cnd.where("value", "=", carID);
        return dao().fetch(CarMachine.class, cnd);
    }

    @Override
    public List<CarMachine> findAll() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<CarMachine> find(NutMap map) {
        // TODO Auto-generated method stub
        return null;
    }

	@Override
	public List<CarMachine> addCarMachineBatch(List<CarMachine> list) {
		
		List<CarMachine> successList = new ArrayList<CarMachine>();
		for(CarMachine carMachine : list){
			this.addCarMachine(carMachine);
			successList.add(carMachine);
		}
		return successList;
	}

	@Override
	public QueryResult findPage(int pageNo, int pageSize, NutMap map) {
		Cnd cnd = Cnd.where("1","=","1");
		
		if(!Lang.isEmpty(map)) {
			if(!Lang.isEmpty(map.getString("car"))) {
				cnd.and("value","LIKE","%" + map.getString("car") + "%");
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
        List<CarMachine> list = dao().query(CarMachine.class, cnd, pager);
        
        if(!Lang.isEmpty(list)){
        	int k=(pageNo-1)*pageSize;
        	for(int i=0; i<list.size(); i++){
        		k++;
        		list.get(i).setSerialNumber((long) (k));
        	}
        }else{
        	pager.setPageNumber(0);
        }
        
        pager.setRecordCount(dao().count(CarMachine.class,cnd));
        
        return new QueryResult(list, pager);
	}

}
