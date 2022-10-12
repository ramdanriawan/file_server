package com.biru.specifications;

import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.biru.entity.TR0012Entity;
import com.biru.entity.TR0012Entity_;
import com.biru.view.ViewInqTr0006Entity;
import com.biru.view.ViewInqTr0006Entity_;

@Component
public class TR0012Specifications {
	public Specification<TR0012Entity> trxOldVoucherIdIsNotNull() {
		return new Specification<TR0012Entity>() {
			private static final long serialVersionUID = 1L;
			@Override
			public Predicate toPredicate(Root<TR0012Entity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				// TODO Auto-generated method stub
				return root.get(TR0012Entity_.trxOldVoucherId).isNotNull();
			}
		};
	}
	
	public Specification<TR0012Entity> trxTypeIn(List<String> trxType) {
		return new Specification<TR0012Entity>() {
			private static final long serialVersionUID = 1L;
			@Override
			public Predicate toPredicate(Root<TR0012Entity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				// TODO Auto-generated method stub
				return root.get(TR0012Entity_.trxType).in(trxType);
			}
		};
	}
	
	public Specification<TR0012Entity> trxTypeEquals(String trxType) {
		return new Specification<TR0012Entity>() {
			private static final long serialVersionUID = 1L;
			@Override
			public Predicate toPredicate(Root<TR0012Entity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				// TODO Auto-generated method stub
				return criteriaBuilder.equal(root.get(TR0012Entity_.trxType),trxType);
			}
		};
	}
	
	public Specification<TR0012Entity> trxTrxClassEquals(String trxTrxClass) {
		return new Specification<TR0012Entity>() {
			private static final long serialVersionUID = 1L;
			@Override
			public Predicate toPredicate(Root<TR0012Entity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				// TODO Auto-generated method stub
				return criteriaBuilder.equal(root.get(TR0012Entity_.trxTrxClass),trxTrxClass);
			}
		};
	}
	
	public Specification<TR0012Entity> trxOldVoucherIdEquals(String trxOldVoucherId) {
		return new Specification<TR0012Entity>() {
			private static final long serialVersionUID = 1L;
			@Override
			public Predicate toPredicate(Root<TR0012Entity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				// TODO Auto-generated method stub
				return criteriaBuilder.equal(root.get(TR0012Entity_.trxOldVoucherId),trxOldVoucherId);
			}
		};
	}
	
	public Specification<TR0012Entity> trxDataStatusIn(List<String> trxDataStatus) {
		return new Specification<TR0012Entity>() {
			private static final long serialVersionUID = 1L;
			@Override
			public Predicate toPredicate(Root<TR0012Entity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				// TODO Auto-generated method stub
				return root.get(TR0012Entity_.trxDataStatus).in(trxDataStatus);
			}
		};
	}
	
	public Specification<TR0012Entity> trxDateBetween(Date from, Date to) {
		return new Specification<TR0012Entity>() {
			private static final long serialVersionUID = 1L;
			@Override
			public Predicate toPredicate(Root<TR0012Entity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				// TODO Auto-generated method stub
				return criteriaBuilder.between(root.get(TR0012Entity_.trxDate), from, to);
			}
		};
	}
	
	public Specification<TR0012Entity> trxDateEquals(Date trxDate) {
		return new Specification<TR0012Entity>() {
			private static final long serialVersionUID = 1L;
			@Override
			public Predicate toPredicate(Root<TR0012Entity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				// TODO Auto-generated method stub
				return criteriaBuilder.equal(root.get(TR0012Entity_.trxDate), trxDate);
			}
		};
	}
	
	public Specification<TR0012Entity> trxDateGreaterThanOrEqualTo(Date trxDate) {
		return new Specification<TR0012Entity>() {
			private static final long serialVersionUID = 1L;
			@Override
			public Predicate toPredicate(Root<TR0012Entity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				// TODO Auto-generated method stub
				return criteriaBuilder.greaterThanOrEqualTo(root.get(TR0012Entity_.trxDate), trxDate);
			}
		};
	}

	public Specification<TR0012Entity> trxDatelessThanOrEqualTo(Date trxDate) {
		return new Specification<TR0012Entity>() {
			private static final long serialVersionUID = 1L;
			@Override
			public Predicate toPredicate(Root<TR0012Entity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				// TODO Auto-generated method stub
				return criteriaBuilder.lessThanOrEqualTo(root.get(TR0012Entity_.trxDate), trxDate);
			}
		};
	}
	
	public Specification<TR0012Entity> trxCoverCoderEquals(String trxCoverCode) {
		return new Specification<TR0012Entity>() {
			private static final long serialVersionUID = 1L;
			@Override
			public Predicate toPredicate(Root<TR0012Entity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				// TODO Auto-generated method stub
				return criteriaBuilder.equal(root.get(TR0012Entity_.trxCoverCode), trxCoverCode);
			}
		};
	}
	
	public Specification<TR0012Entity> trxClientEquals(String trxClient) {
		return new Specification<TR0012Entity>() {
			private static final long serialVersionUID = 1L;
			@Override
			public Predicate toPredicate(Root<TR0012Entity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				// TODO Auto-generated method stub
				return criteriaBuilder.equal(root.get(TR0012Entity_.trxClient), trxClient);
			}
		};
	}
	
	public Specification<TR0012Entity> trxSourceIn(List<String> trxSource) {
		return new Specification<TR0012Entity>() {
			private static final long serialVersionUID = 1L;
			@Override
			public Predicate toPredicate(Root<TR0012Entity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				// TODO Auto-generated method stub
				return root.get(TR0012Entity_.trxSource).in(trxSource);
			}
		};
	}
	
	public Specification<TR0012Entity> trxSetAmountNotEqualsTrxOrgAmount() {
		return new Specification<TR0012Entity>() {
			private static final long serialVersionUID = 1L;
			@Override
			public Predicate toPredicate(Root<TR0012Entity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				// TODO Auto-generated method stub
				return criteriaBuilder.notEqual(root.get(TR0012Entity_.trxSetAmount), root.get(TR0012Entity_.trxOrgAmount));
			}
		};
	}
}
