/**
 * 
 */
package com.icfcc.SRRPDao.s.jpa.repository.portal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.icfcc.SRRPDao.s.jpa.entity.portal.InvestorQuery;

/**
 * @author lijj
 *
 */
@Repository
public interface InvestorQueryDao
		extends JpaRepository<InvestorQuery, String>, JpaSpecificationExecutor<InvestorQuery> {

}
