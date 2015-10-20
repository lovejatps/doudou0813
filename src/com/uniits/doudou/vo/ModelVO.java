package com.uniits.doudou.vo;

import java.util.List;

import com.uniits.doudou.model.Producecar;
import com.uniits.doudou.model.SystemModel;

public class ModelVO {
	
	private List<SystemModel> modelList ;
	private List<Producecar> prods;
	
	public List<SystemModel> getModelList() {
		return modelList;
	}
	public void setModelList(List<SystemModel> modelList) {
		this.modelList = modelList;
	}
	public List<Producecar> getProds() {
		return prods;
	}
	public void setProds(List<Producecar> prods) {
		this.prods = prods;
	}

	
}
