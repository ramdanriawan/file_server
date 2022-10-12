package com.biru.specifications;

import java.util.Date;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.biru.entity.TR0001Entity;
import com.biru.entity.TR0001Entity_;

@Component
public class TR0001Specifications  {
	
	public Specification<TR0001Entity> glDataStatusEqual(String glDataStatus) {
		return new Specification<TR0001Entity>() {
			private static final long serialVersionUID = 1L;
			@Override
			public Predicate toPredicate(Root<TR0001Entity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				// TODO Auto-generated method stub
				return criteriaBuilder.equal(root.get(TR0001Entity_.glDataStatus), glDataStatus);
			}
		};
	}

	public Specification<TR0001Entity> glTrxDateGreaterThanOrEqualTo(Date trxDate) {
		return new Specification<TR0001Entity>() {
			private static final long serialVersionUID = 1L;
			@Override
			public Predicate toPredicate(Root<TR0001Entity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				// TODO Auto-generated method stub
				return criteriaBuilder.greaterThanOrEqualTo(root.get(TR0001Entity_.glTrxDate), trxDate);
			}
		};
	}

	public Specification<TR0001Entity> glTrxDatelessThanOrEqualTo(Date trxDate) {
		return new Specification<TR0001Entity>() {
			private static final long serialVersionUID = 1L;
			@Override
			public Predicate toPredicate(Root<TR0001Entity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				// TODO Auto-generated method stub
				return criteriaBuilder.lessThanOrEqualTo(root.get(TR0001Entity_.glTrxDate), trxDate);
			}
		};
	}

	public Specification<TR0001Entity> glTrxClassEqual(String glTrxClass) {
		return new Specification<TR0001Entity>() {
			private static final long serialVersionUID = 1L;
			@Override
			public Predicate toPredicate(Root<TR0001Entity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				// TODO Auto-generated method stub
				return criteriaBuilder.equal(root.get(TR0001Entity_.glTrxClass), glTrxClass);
			}
		};
	}

	public Specification<TR0001Entity> glTypeEqual(String glType) {
		return new Specification<TR0001Entity>() {
			private static final long serialVersionUID = 1L;
			@Override
			public Predicate toPredicate(Root<TR0001Entity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				// TODO Auto-generated method stub
				return criteriaBuilder.equal(root.get(TR0001Entity_.glType), glType);
			}
		};
	}
	public Specification<TR0001Entity> glTrxProjectEqual(String glTrxProject) {
		return new Specification<TR0001Entity>() {
			private static final long serialVersionUID = 1L;
			@Override
			public Predicate toPredicate(Root<TR0001Entity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				// TODO Auto-generated method stub
				return criteriaBuilder.equal(root.get(TR0001Entity_.glTrxProject), glTrxProject);
			}
		};
	}
	public Specification<TR0001Entity> glTrxOfficeIdEqual(String glTrxOfficeId) {
		return new Specification<TR0001Entity>() {
			private static final long serialVersionUID = 1L;
			@Override
			public Predicate toPredicate(Root<TR0001Entity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				// TODO Auto-generated method stub
				return criteriaBuilder.equal(root.get(TR0001Entity_.glTrxOfficeId), glTrxOfficeId);
			}
		};
	}
}