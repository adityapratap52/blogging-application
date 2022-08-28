package com.blogging.commons;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
@Transactional
public class CommonDaoImpl implements CommonDao{
	
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public String saveOrUpdate(Object obj){
		String status = "success";
		try{
			Session session = sessionFactory.openSession();
			Transaction tx = session.beginTransaction();
			session.saveOrUpdate(obj);
			tx.commit();
			session.close();
		}
		catch(ConstraintViolationException e){
			e.printStackTrace();
			status = "ConstraintViolationException error";
		}
		catch (Exception e) {
			e.printStackTrace();
			status = "Exception error";
		}
		
		return status;
	}

	@Override
	public boolean isExists(final String beanName,final String whereCondition){
		boolean flag = false;
		try{
			@SuppressWarnings("rawtypes")
			List l = sessionFactory.openSession().createQuery("from " + beanName + " where " + whereCondition).list();
			if(l != null && l.size() > 0){
				flag = true;
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return flag;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> getAllData(Class<T> T){
		return sessionFactory.openSession().createCriteria(T).list();
	}

	@Override
	public <T> T get(Class<T> t, Integer id){

		@SuppressWarnings("unchecked")
		T returnData = (T)sessionFactory.openSession().get(t, id);
		
		return returnData;
	}

	@Override
	public <T> List<T> getDataPagination(Class<T> T, int startIndex, int maxResult){
		
		Query qry = sessionFactory.getCurrentSession().createQuery("from "+T.getName());
		qry.setFirstResult(startIndex);
		qry.setMaxResults(maxResult);

		@SuppressWarnings("unchecked")
		List<T> list = qry.list();
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> getDataByWhereCondition(Class<T> T, String whereCond) {
		return sessionFactory.getCurrentSession().createQuery(" from "+T.getName() + " where " + whereCond).list();
	}

	@Override
	public <T> void deleteData(Class<T> T, String whereCondition) {
		
		try{
			Session session = sessionFactory.openSession();
			Transaction tx = session.beginTransaction();
			session.createQuery("delete from " + T.getName() + " where " + whereCondition).executeUpdate();
			tx.commit();
			session.close();
		}
		catch(ConstraintViolationException e){
			e.printStackTrace();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public <T> void updateColumns(Class<T> T, List<String[]> columns, String whereCondition){
		
		String query = "update "+T.getName() + " set ";
		String columnsQry = "";
		if(columns != null && columns.size() > 0){
			Iterator<String[]> itr = columns.iterator();
			while(itr.hasNext()){
				String arr[] = itr.next();
				columnsQry = columnsQry + ", "+arr[0]+"=";
				if(arr[2].equalsIgnoreCase("number") || arr[2].equalsIgnoreCase("date")){
					columnsQry = columnsQry+arr[1];
				}
				else{
					columnsQry = columnsQry + "'"+arr[1]+"'";
				}
			}
		}
		if(columnsQry.length() > 0){
			columnsQry = columnsQry.substring(1);
		}
		if(!"".equalsIgnoreCase(whereCondition)){
			columnsQry = columnsQry + " where " + whereCondition;
		}
		query = query + columnsQry;
		
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.createQuery(query).executeUpdate();
		tx.commit();
		session.close();
		
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> List<Object> getSingleColumnList(Class<T> T,String columnName, String whereCondition){
		String query = "select "+columnName+" from "+T.getName() ;
		if(whereCondition != null && !whereCondition.equals("")){
			query = query + " where " + whereCondition;
		}
		return sessionFactory.getCurrentSession().createQuery(query).list();
	}

	@Override
	public List<HashMap<String, String>> getSpecificColumn(String beanName, List<String> columns, String whereCondition){

		List<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
		String queryColumns = "";
		Iterator<String> itr = columns.iterator();
		while (itr.hasNext()) {
			String column = itr.next();
			queryColumns = queryColumns +"," + column;
		}

		if(queryColumns.startsWith(",")){
			queryColumns = queryColumns.substring(1);
		}
		String query = "select "+ queryColumns + " from "+beanName;
		if(whereCondition != null && whereCondition.length() > 0){
			query = query + " where " + whereCondition;
		}
		@SuppressWarnings("unchecked")
		List<Object[]> queryData = sessionFactory.getCurrentSession().createQuery(query).list();
		if(queryData != null && queryData.size() > 0){
			Iterator<Object[]> itrQueryData = queryData.iterator();
			while(itrQueryData.hasNext()){
				Object[] arr = itrQueryData.next();
				HashMap<String, String> rowData = new HashMap<>();
				for(int i = 0; i< arr.length; i++){
					rowData.put(columns.get(i), ""+arr[i]);
				}
				data.add(rowData);
			}

		}

		return data;
	}
}
