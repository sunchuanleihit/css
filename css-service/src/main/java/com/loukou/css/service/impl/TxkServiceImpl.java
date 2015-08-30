package com.loukou.css.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.loukou.css.service.TxkService;
import com.loukou.pos.client.txk.processor.AccountTxkProcessor;
import com.loukou.pos.client.txk.req.TxkCardRowRespVO;
import com.loukou.pos.client.txk.req.TxkMemberCardsRespVO;

@Service("txkService")
public class TxkServiceImpl implements TxkService{

//	@Override
//	public List<TxkCardRowRespVO> findTxkListByUserId(Integer userId) {
//		TxkMemberCardsRespVO resp = AccountTxkProcessor.getProcessor().getTxkListByUserId(userId);
//		List<TxkCardRowRespVO> txkList = resp.getRows();
//		return txkList;
//	}
}
