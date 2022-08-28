package com.blogging.commons;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CommonServiceImpl implements CommonService {

	@Autowired
	private CommonDao commonDao;
	
	@Autowired
	private HttpServletRequest request;
	
	@Override
	public String saveOrUpdate(Object obj) {
		return commonDao.saveOrUpdate(obj);
	}

	@Override
	public boolean isExists(String beanName, String whereCondition) {
		return commonDao.isExists(beanName, whereCondition);
	}

	@Override
	public <T> T get(Class<T> t, Integer id) {
		return commonDao.get(t, id);
	}

	@Override
	public List<HashMap<String, String>> getSpecificColumn(String beanName, List<String> columns, String whereCondition){
		return commonDao.getSpecificColumn(beanName, columns, whereCondition);
	}
	

	@Override
	public <T> List<T> getAllData(Class<T> T) {
		return commonDao.getAllData(T);
	}

	@Override
	public <T> List<T> getDataPagination(Class<T> T, int startIndex, int maxResult) {
		return commonDao.getDataPagination(T, startIndex, maxResult);
	}
	
	@Override
	public <T> List<T> getDataByWhereCondition(Class<T> T, String whereCond) {
		return commonDao.getDataByWhereCondition(T, whereCond);
	}
	
	@Override
	public <T> void deleteData(Class<T> T, String whereCondition) {
		commonDao.deleteData(T, whereCondition);
	}


	@Override
	public <T> void updateColumns(Class<T> T, List<String[]> columns, String whereCondition) {
		commonDao.updateColumns(T, columns, whereCondition);
	}
	
	@Override
	public <T> List<String> getSingleColumnList(Class<T> T, String columnName, String whereCondition) {
		List<String> list = new ArrayList<>();
		
		List<Object> objList = commonDao.getSingleColumnList(T, columnName, whereCondition);
		if (objList != null && objList.size() > 0) {
			Iterator<Object> itr = objList.iterator();
			while (itr.hasNext()) {
				list.add(String.valueOf(itr.next()));
			}
		}

		return list;
	}
}
