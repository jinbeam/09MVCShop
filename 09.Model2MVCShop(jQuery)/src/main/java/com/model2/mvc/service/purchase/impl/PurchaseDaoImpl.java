package com.model2.mvc.service.purchase.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.purchase.PurchaseDao;

@Repository("purchaseDaoImpl")
public class PurchaseDaoImpl implements PurchaseDao{
	@Autowired
	@Qualifier("sqlSessionTemplate")
	private SqlSession sqlSession;
	
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	public Purchase findPurchase(int tranNo) throws Exception {
		return (Purchase)sqlSession.selectOne("PurchaseMapper.getPurchase01", tranNo);
	}

	public Purchase findPurchase2(int prodNo) throws Exception {
		return (Purchase)sqlSession.selectOne("PurchaseMapper.getPurchase02", prodNo);
	}

	public List<Purchase> getPurchaseList(Search search, String buyerId) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("search", search);
		map.put("buyerId", buyerId);
		return sqlSession.selectList("PurchaseMapper.getPurchaseList", map);
	}

	public Map<String, Object> getSaleList(Search search) {
		// TODO Auto-generated method stub
		return null;
	}

	public void insertPurchase(Purchase purchase) throws Exception {
		sqlSession.insert("PurchaseMapper.addPurchase", purchase);
		
	}

	public void updatePurchase(Purchase purchase) throws Exception {
		sqlSession.update("PurchaseMapper.updatePurchase", purchase);
		
	}

	public void updateTranCode(Purchase purchase) throws Exception {
		sqlSession.update("PurchaseMapper.updateTranCode", purchase);		
	}

	public void deletePurchase(int tranNo) throws Exception {
		sqlSession.delete("PurchaseMapper.deletePurchase", tranNo);
		
	}

	public void updateEval(Purchase purchase) throws Exception {
		sqlSession.update("PurchaseMapper.updateEvaluation", purchase);
		
	}

	public int getTotalCount(Search search, String buyerId) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("search", search);
		map.put("buyerId", buyerId);
		return sqlSession.selectOne("PurchaseMapper.getTotalCount", map);
	}
}
