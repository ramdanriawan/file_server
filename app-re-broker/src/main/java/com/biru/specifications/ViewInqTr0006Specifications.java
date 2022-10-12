package com.biru.specifications;

import java.util.Date;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.biru.entity.TR0012Entity;
import com.biru.view.ViewInqTr0006Entity;
import com.biru.view.ViewInqTr0006Entity_;

@Component
public class ViewInqTr0006Specifications  {

	public Specification<ViewInqTr0006Entity> trxDateGreaterThanOrEqualTo(Date trxDate) {
		return new Specification<ViewInqTr0006Entity>() {
			private static final long serialVersionUID = 1L;
			@Override
			public Predicate toPredicate(Root<ViewInqTr0006Entity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				// TODO Auto-generated method stub
				return criteriaBuilder.greaterThanOrEqualTo(root.get(ViewInqTr0006Entity_.trxDate), trxDate);
			}
		};
	}

	public Specification<ViewInqTr0006Entity> trxDatelessThanOrEqualTo(Date trxDate) {
		return new Specification<ViewInqTr0006Entity>() {
			private static final long serialVersionUID = 1L;
			@Override
			public Predicate toPredicate(Root<ViewInqTr0006Entity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				// TODO Auto-generated method stub
				return criteriaBuilder.lessThanOrEqualTo(root.get(ViewInqTr0006Entity_.trxDate), trxDate);
			}
		};
	}
	
	public Specification<ViewInqTr0006Entity> trxCoverCodeEqual(String typeOfCover) {
		return new Specification<ViewInqTr0006Entity>() {
			private static final long serialVersionUID = 1L;
			@Override
			public Predicate toPredicate(Root<ViewInqTr0006Entity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				// TODO Auto-generated method stub
				return criteriaBuilder.equal(root.get(ViewInqTr0006Entity_.trxCoverCode), typeOfCover);
			}
		};
	}

	public Specification<ViewInqTr0006Entity> cliNameEqual(String cliName) {
		return new Specification<ViewInqTr0006Entity>() {
			private static final long serialVersionUID = 1L;
			@Override
			public Predicate toPredicate(Root<ViewInqTr0006Entity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				// TODO Auto-generated method stub
				return criteriaBuilder.lessThanOrEqualTo(root.get(ViewInqTr0006Entity_.cliName), cliName);
			}
		};
	}
	
	public Specification<ViewInqTr0006Entity> groupByTrxVoucherId() {
		return new Specification<ViewInqTr0006Entity>() {
			private static final long serialVersionUID = 1L;
			@Override
			public Predicate toPredicate(Root<ViewInqTr0006Entity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				return criteriaBuilder.createQuery(ViewInqTr0006Entity.class).groupBy(root.get(ViewInqTr0006Entity_.trxVoucherId)).getRestriction();
			}
		};
	}
}