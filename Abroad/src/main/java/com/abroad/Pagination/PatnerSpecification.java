package com.abroad.Pagination;

import com.abroad.Entity.AbroadBecomePartner;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class PatnerSpecification {
    public static Specification<AbroadBecomePartner> build(AbroadBecomePartner partner){
        return (root, query, cb) ->{
            List<Predicate> predicates=new ArrayList<>();

            if(partner!=null){
                if(partner.getConductedBy() != null){
                    predicates.add(cb.like(cb.lower(root.get("conductedBy")),"%"+partner.getConductedBy().toLowerCase()+"%"));
                }
            }
            return cb.and(predicates.toArray(predicates.toArray(new Predicate[0])));
        };
    }
}
