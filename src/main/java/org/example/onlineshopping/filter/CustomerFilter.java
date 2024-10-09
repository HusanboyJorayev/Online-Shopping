package org.example.onlineshopping.filter;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.example.onlineshopping.entity.Customer;
import org.springframework.data.jpa.domain.Specification;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerFilter implements Specification<Customer> {
    private Integer id;
    private String username;
    private Double amount;

    public void setId(Integer id) {
        if (id != null)
            this.id = id;
    }

    public void setUsername(String username) {
        if (username != null)
            this.username = username;
    }

    public void setAmount(Double amount) {
        if (amount != null)
            this.amount = amount;
    }

    @Override
    public Predicate toPredicate(@NonNull Root<Customer> root,
                                 CriteriaQuery<?> query,
                                 @NonNull CriteriaBuilder criteriaBuilder) {
        Predicate predicate = criteriaBuilder.conjunction();
        if (id != null) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("id"), id));
        }
        if (username != null) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(criteriaBuilder.lower(root.get("username")), "%" + username.toLowerCase() + "%"));
        }
        if (amount != null) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("amount"), amount));
        }
        return predicate;
    }
}
