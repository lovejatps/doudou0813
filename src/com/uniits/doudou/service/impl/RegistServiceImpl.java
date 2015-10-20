/**
 * @description doudou com.uniits.doudou.service.impl
 */
package com.uniits.doudou.service.impl;

import java.util.Date;
import java.util.List;

import org.nutz.dao.Cnd;
import org.nutz.dao.Condition;
import org.nutz.dao.Dao;
import org.nutz.dao.QueryResult;
import org.nutz.dao.Sqls;
import org.nutz.dao.pager.Pager;
import org.nutz.dao.sql.Sql;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Lang;
import org.nutz.lang.Times;
import org.nutz.lang.util.NutMap;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.service.IdEntityService;

import com.uniits.doudou.model.CarMachine;
import com.uniits.doudou.model.Phone;
import com.uniits.doudou.model.Register;
import com.uniits.doudou.model.Vin;
import com.uniits.doudou.service.CarMachineService;
import com.uniits.doudou.service.RegistService;
import com.uniits.doudou.service.VinService;

/**
 * @author liuchengwei
 * @since 2015-3-16 下午3:21:00
 * @description 
 */
@IocBean(name="registService" ,args = {"refer:dao"})
public class RegistServiceImpl extends IdEntityService<Register> implements RegistService {
    
    public RegistServiceImpl() {
        super();
    }

    public RegistServiceImpl(Dao dao, Class<Register> entityType) {
        super(dao, entityType);
    }

    public RegistServiceImpl(Dao dao) {
        super(dao);
    }
    
    //以上必须
    @Inject
    private CarMachineService carMachineService;
    
    @Inject
    private VinService vinService;

    public void addRegist(Register register) {
        dao().insert(register);
    }

    public List<Register> findAll() {
        Sql sql = Sqls.create("select * from t_regist_info ");
        sql.setCallback(Sqls.callback.entities());
        sql.setEntity(dao().getEntity(Register.class));
        dao().execute(sql);
        List<Register> list = sql.getList(Register.class);
        return list;
    }

    public List<Register> find(NutMap map) {
        
        StringBuilder sq = new StringBuilder("select * from t_regist_info r where 1=1 ");
        
        if(map != null && map.size() > 0) {
            if(map.getString("username") != null) {
                sq.append(" and r.username like '%$username%'");
            }
            
            if(map.getString("pwd") != null) {
                sq.append(" and r.pwd like '%$pwd%'");
            }
        }
        
        Sql sql = Sqls.create(sq.toString());
        
        if(map != null && map.size() > 0) {
            if(map.getString("username") != null) {
                sql.vars().set("username", map.getString("username"));
            }
            
            if(map.getString("pwd") != null) {
                sql.vars().set("pwd", map.getString("pwd"));
            }
        }
        
        sql.setCallback(Sqls.callback.entities());
        sql.setEntity(dao().getEntity(Register.class));
        dao().execute(sql);
        List<Register> list = sql.getList(Register.class);
        return list;
    }

    public void updateRegister(Register register) {
        dao().update(register);
    }

    public void deleteRegister(long id) {
        delete(id);
    }

    @Override
    public NutMap addRegist(String vin, String id) {
        Register r = null;
        NutMap map = new NutMap();
        map.put("errorMsg", "");
        map.put("status", 0);
        if(!Lang.isEmpty(vin) && !Lang.isEmpty(id)) {
            
            Condition cnd = Cnd.where("username","=",vin).and("pwd", "=", id);
            r = fetch(cnd);
            if(Lang.isEmpty(r)) {//未注册过
                
                Vin v = vinService.fetchVin(vin);
                if(Lang.isEmpty(v)) {//vin 号不存在
                    map.put("errorMsg", "VIN号非法");
                    map.put("status", 1);
                    return map;
                }
                
                CarMachine car = carMachineService.fetchCarMachine(id);
                if(Lang.isEmpty(car)) {//id 号不存在
                    map.put("errorMsg", "ID号非法");
                    map.put("status", 1);
                    return map;
                }
                
                r = new Register();
//                r.setVIN(vin);
//                r.setCarMachineId(id);
//                r.setUsername(vin);
//                r.setPwd(id);
//                r.setRoleId(ConstantRole.CAR_USER);
//                addRegist(r);
            }
        }
        return map;
    }
    
    public Phone findOneCall(){
    	Sql sql = Sqls.create("select * from t_phone");
        sql.setCallback(Sqls.callback.entities());
        sql.setEntity(dao().getEntity(Phone.class));
        dao().execute(sql);
        List<Phone> list = sql.getList(Phone.class);
        if(list!=null && list.size()>0){
        	return list.get(0);
        }
        return null;
    }
    
    public void updateOneCall(Phone phone){
    	String createDate = Times.sD(new Date());
    	phone.setCreateDate(createDate);
    	if(phone.getId() == 0){
    		phone.setId(1);
    		dao().insert(phone);
    	}else{
    		dao().update(phone);
    	}
    }
    
    @Override
    public Register fetchRegist(long id) {
        return fetch(id);
    }

	@Override
	public QueryResult findPage(int pageNo, int pageSize, NutMap map) {
		Cnd cnd = Cnd.where("1","=","1");
		
		if(!Lang.isEmpty(map)) {
			if(!Lang.isEmpty(map.getString("vin"))) {
				cnd.and("VIN","LIKE","%" + map.getString("vin") + "%");
			}
			
			if(!Lang.isEmpty(map.getString("carMachineId"))) {
				cnd.and("carMachineId","LIKE","%" + map.getString("carMachineId") + "%");
			}
			
			if(!Lang.isEmpty(map.getString("username"))) {
				cnd.and("username","LIKE","%" + map.getString("username") + "%");
			}
			
		}
        cnd.desc("createDate").desc("id");
        Pager pager = dao().createPager(pageNo, pageSize);
        List<Register> list = dao().query(Register.class, cnd, pager);
        if(!Lang.isEmpty(list)){
        	int k=(pageNo-1)*pageSize;
        	for(int i=0; i<list.size(); i++){
        		k++;
        		list.get(i).setSerialNumber((long) (k));
        	}
        }else{
        	pager.setPageNumber(0);
        }
        
        pager.setRecordCount(dao().count(Register.class,cnd));
        
        return new QueryResult(list, pager);
	}

}
