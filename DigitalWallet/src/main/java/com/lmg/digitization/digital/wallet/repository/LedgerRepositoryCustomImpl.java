package com.lmg.digitization.digital.wallet.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.util.CollectionUtils;

import com.lmg.digitization.digital.wallet.entity.DigitizationLedger;
import com.lmg.digitization.digital.wallet.enums.Source;

public class LedgerRepositoryCustomImpl implements LedgerRepositoryCustom {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Page<DigitizationLedger> search(String shukranId, String currency, Integer noofdays, Integer pagenumber, Integer size,
			List<String> status, List<String> concept, List<String> source) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<DigitizationLedger> cq = cb.createQuery(DigitizationLedger.class);
		Root<DigitizationLedger> iRoot = cq.from(DigitizationLedger.class);

		LocalDateTime upTodate = LocalDate.now().minusDays(noofdays).atStartOfDay();

		List<Predicate> predicates = new ArrayList<>();
		predicates.add(cb.equal(iRoot.<String>get("shukranId"), shukranId));

		if (StringUtils.isNotEmpty(currency)) {
			predicates.add(cb.equal(iRoot.<String>get("currency"), currency));
		}
		if (!CollectionUtils.isEmpty(status)) {
			predicates.add(iRoot.<String>get("status").in(status));
		}
		if (!CollectionUtils.isEmpty(concept)) {
			predicates.add(iRoot.<String>get("concept").in(concept));
		}
		if (!CollectionUtils.isEmpty(source)) {
			predicates.add(iRoot.<String>get("source")
					.in(source.stream().map(Source::valueOf).collect(Collectors.toSet())));
		}
		predicates.add(cb.greaterThanOrEqualTo(iRoot.<LocalDateTime>get("createdDate"), upTodate));
		Predicate[] predArray = predicates.toArray(new Predicate[predicates.size()]);
		cq.where(predArray);

		cq.orderBy(cb.desc(iRoot.get("createdDate")));
		TypedQuery<DigitizationLedger> query = entityManager.createQuery(cq);
		Pageable pageble = PageRequest.of(pagenumber - 1, size);
		query.setFirstResult((pagenumber - 1) * size);
		query.setMaxResults(size);

		CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
		countQuery.where(predArray);
		countQuery.select(cb.count(countQuery.from(DigitizationLedger.class)));
		Long count = entityManager.createQuery(countQuery).getSingleResult();

		return new PageImpl<>(query.getResultList(), pageble, count);
	}

}
