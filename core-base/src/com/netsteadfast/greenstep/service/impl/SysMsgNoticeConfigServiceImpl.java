/* 
 * Copyright 2012-2016 bambooCORE, greenstep of copyright Chen Xin Nien
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * -----------------------------------------------------------------------
 * 
 * author: 	Chen Xin Nien
 * contact: chen.xin.nien@gmail.com
 * 
 */
package com.netsteadfast.greenstep.service.impl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.netsteadfast.greenstep.base.Constants;
import com.netsteadfast.greenstep.base.SysMessageUtil;
import com.netsteadfast.greenstep.base.dao.IBaseDAO;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.GreenStepSysMsgConstants;
import com.netsteadfast.greenstep.base.model.PageOf;
import com.netsteadfast.greenstep.base.model.QueryResult;
import com.netsteadfast.greenstep.base.model.SearchValue;
import com.netsteadfast.greenstep.base.service.BaseService;
import com.netsteadfast.greenstep.dao.ISysMsgNoticeConfigDAO;
import com.netsteadfast.greenstep.po.hbm.TbSysMsgNoticeConfig;
import com.netsteadfast.greenstep.service.ISysMsgNoticeConfigService;
import com.netsteadfast.greenstep.vo.SysMsgNoticeConfigVO;

@Service("core.service.SysMsgNoticeConfigService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=true)
public class SysMsgNoticeConfigServiceImpl extends BaseService<SysMsgNoticeConfigVO, TbSysMsgNoticeConfig, String> implements ISysMsgNoticeConfigService<SysMsgNoticeConfigVO, TbSysMsgNoticeConfig, String> {
	protected Logger logger=Logger.getLogger(SysMsgNoticeConfigServiceImpl.class);
	private ISysMsgNoticeConfigDAO<TbSysMsgNoticeConfig, String> sysMsgNoticeConfigDAO;
	
	public SysMsgNoticeConfigServiceImpl() {
		super();
	}

	public ISysMsgNoticeConfigDAO<TbSysMsgNoticeConfig, String> getSysMsgNoticeConfigDAO() {
		return sysMsgNoticeConfigDAO;
	}

	@Autowired
	@Resource(name="core.dao.SysMsgNoticeConfigDAO")
	@Required		
	public void setSysMsgNoticeConfigDAO(
			ISysMsgNoticeConfigDAO<TbSysMsgNoticeConfig, String> sysMsgNoticeConfigDAO) {
		this.sysMsgNoticeConfigDAO = sysMsgNoticeConfigDAO;
	}

	@Override
	protected IBaseDAO<TbSysMsgNoticeConfig, String> getBaseDataAccessObject() {
		return sysMsgNoticeConfigDAO;
	}

	@Override
	public String getMapperIdPo2Vo() {		
		return MAPPER_ID_PO2VO;
	}

	@Override
	public String getMapperIdVo2Po() {
		return MAPPER_ID_VO2PO;
	}
	
	private Map<String, Object> getQueryGridParameter(SearchValue searchValue) throws Exception {
		Map<String, Object> params=new LinkedHashMap<String, Object>();
		String msgId = searchValue.getParameter().get("msgId");
		String systemOid = searchValue.getParameter().get("systemOid");
		if (!StringUtils.isBlank(msgId)) {
			params.put("msgId", msgId);
		}
		if (!this.isNoSelectId(systemOid) ) {
			params.put("systemOid", systemOid);
		}		
		return params;
	}
	
	private String getQueryGridHql(String type, Map<String, Object> params) throws Exception {
		StringBuilder hqlSb=new StringBuilder();
		hqlSb.append("SELECT ");
		if (Constants.QUERY_TYPE_OF_COUNT.equals(type)) {
			hqlSb.append("  count(*) ");
		} else {
			hqlSb.append("	new com.netsteadfast.greenstep.vo.SysMsgNoticeConfigVO(m.oid, m.msgId, m.system, m.className) ");
		}
		hqlSb.append("FROM TbSysMsgNoticeConfig m WHERE 1=1 ");
		if (params.get("systemOid")!=null) {
			hqlSb.append("  and m.system IN ( SELECT s.sysId FROM TbSys s WHERE s.oid = :systemOid ) ");
		}		
		if (params.get("msgId")!=null) {
			hqlSb.append("  and m.msgId = :msgId ");
		}
		if (Constants.QUERY_TYPE_OF_SELECT.equals(type)) {
			hqlSb.append("ORDER BY m.system, m.msgId ASC ");
		}		
		return hqlSb.toString();
	}	

	@Override
	public QueryResult<List<SysMsgNoticeConfigVO>> findGridResult(
			SearchValue searchValue, PageOf pageOf) throws ServiceException, Exception {
		if (searchValue==null || pageOf==null) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.SEARCH_NO_DATA));
		}
		Map<String, Object> params=this.getQueryGridParameter(searchValue);	
		int limit=Integer.parseInt(pageOf.getShowRow());
		int offset=(Integer.parseInt(pageOf.getSelect())-1)*limit;
		QueryResult<List<SysMsgNoticeConfigVO>> result=this.sysMsgNoticeConfigDAO.findResult2(
				this.getQueryGridHql(Constants.QUERY_TYPE_OF_SELECT, params), 
				this.getQueryGridHql(Constants.QUERY_TYPE_OF_COUNT, params), 
				params, 
				offset, 
				limit);
		pageOf.setCountSize(String.valueOf(result.getRowCount()));
		pageOf.toCalculateSize();
		return result;
	}

	/**
	 * Select 下拉資料
	 * 
	 * @param pleaseSelect
	 * @return
	 * @throws ServiceException
	 * @throws Exception
	 */	
	@Override
	public Map<String, String> findForDataMap(boolean pleaseSelect) throws ServiceException, Exception {
		Map<String, String> dataMap = this.providedSelectZeroDataMap(pleaseSelect);
		List<TbSysMsgNoticeConfig> searchList = this.findListByParams(null);
		for (int i=0; searchList!=null && i<searchList.size(); i++) {
			dataMap.put(searchList.get(i).getOid(), searchList.get(i).getMsgId());
		}
		return dataMap;
	}

}
