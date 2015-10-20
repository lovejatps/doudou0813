package com.uniits.doudou.service;

import java.util.List;

import org.nutz.dao.QueryResult;
import org.nutz.lang.util.NutMap;

import com.uniits.doudou.model.Producecar;
import com.uniits.doudou.model.Sn;

public interface RightsService {
	public void addRightscar(Producecar p);
	public QueryResult findPage(int pageNo, int pageSize, NutMap map);
	public int isProdName(String prodName);
	
	
	public void deleteProduce(long id);
	
    public List<Producecar> findAll();

    public List<Producecar> find(NutMap nm);
    
    public int isSelectRights(long id);
    
  
    
}
