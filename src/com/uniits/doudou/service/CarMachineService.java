/**
 * @description doudou com.uniits.doudou.service
 */
package com.uniits.doudou.service;

import java.util.List;

import org.nutz.dao.QueryResult;
import org.nutz.lang.util.NutMap;

import com.uniits.doudou.model.CarMachine;

/**
 * @author liuchengwei
 * @since 2015-3-17 下午2:48:09
 * @description 
 */
public interface CarMachineService {

    public void addCarMachine(CarMachine carMachine);
    
    public CarMachine fetchCarMachine(long id);
    
    public void deleteCarMachine(Long id);

    public CarMachine fetchCarMachine(String carID);
    
    public List<CarMachine> findAll();
    
    public List<CarMachine> find(NutMap map);

	public List<CarMachine> addCarMachineBatch(List<CarMachine> list);

	public QueryResult findPage(int pageNo, int pageSize, NutMap map);
    
    
}
