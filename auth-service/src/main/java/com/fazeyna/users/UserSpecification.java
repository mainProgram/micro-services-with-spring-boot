package com.fazeyna.users;

import com.fazeyna.dtos.filter.FilterDTO;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class UserSpecification {
    public static Specification<UserEntity> columnEqual(List<FilterDTO> filterDTOList)
    {
        return new Specification<UserEntity>()
        {
            private static final long serialVersionUID = 1L;

            @Override
            public Predicate toPredicate(Root<UserEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder)
            {
                List<Predicate> predicates = new ArrayList<>();
                filterDTOList.forEach(filter ->
                {
                    Predicate predicate = criteriaBuilder.like(
                            criteriaBuilder.lower(root.get(filter.getColumnName())),
                            "%" + filter.getColumnValue().toString().toLowerCase() + "%"
                    );
                    predicates.add(predicate);
                });

                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }
}
