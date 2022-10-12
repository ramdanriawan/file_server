package com.biru.specifications;

import java.util.Date;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.biru.entity.TR0012Entity;
import com.biru.entity.ViewLaporanVatEntity;
import com.biru.entity.ViewLaporanVatEntity_;
import com.biru.view.ViewInqTr0006Entity;
import com.biru.view.ViewInqTr0006Entity_;

@Component
public class ViewLaporanVatSpecifications  {

	public Specification<ViewLaporanVatEntity> trxDateGreaterThanOrEqualTo(Date trxDate) {
		return new Specification<ViewLaporanVatEntity>() {
			private static final long serialVersionUID = 1L;
			@Override
			public Predicate toPredicate(Root<ViewLaporanVatEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				// TODO Auto-generated method stub
				return criteriaBuilder.greaterThanOrEqualTo(root.get(ViewLaporanVatEntity_.trxDate), trxDate);
			}
		};
	}

	public Specification<ViewLaporanVatEntity> trxDatelessThanOrEqualTo(Date trxDate) {
		return new Specification<ViewLaporanVatEntity>() {
			private static final long serialVersionUID = 1L;
			@Override
			public Predicate toPredicate(Root<ViewLaporanVatEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				// TODO Auto-generated method stub
				return criteriaBuilder.lessThanOrEqualTo(root.get(ViewLaporanVatEntity_.trxDate), trxDate);
			}
		};
	}
}