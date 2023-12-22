package com.dmdev.spring.database.repository;

import com.dmdev.spring.database.entity.QUser;
import com.dmdev.spring.database.entity.User;
import com.dmdev.spring.database.querydsl.QPredicates;
import com.dmdev.spring.dto.UserFilter;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class FilterUserRepositoryImpl implements FilterUserRepository{

    private final EntityManager entityManager;

    @Override
    public List<User> findAllByFilter(UserFilter userFilter){

        Predicate predicate = QPredicates.builder()
                .add(userFilter.firstname(), QUser.user.firstname::containsIgnoreCase)
                .add(userFilter.lastname(), QUser.user.lastname::containsIgnoreCase)
                .add(userFilter.birthDate(), QUser.user.birthDate::before)
                .build();

        return new JPAQuery<User>(entityManager)
                .select(QUser.user)
                .from(QUser.user)
                .where(predicate)
                .fetch();
    }

    /*@Override
    public List<User> findAllByFilter(UserFilter userFilter) {
        var cb = entityManager.getCriteriaBuilder();
        var criteria = cb.createQuery(User.class);

        var user = criteria.from(User.class);

        List<Predicate> predicates = new ArrayList<>();

        if(userFilter.firstname() != null){
            predicates.add(cb.like(user.get("firstname"), userFilter.firstname()));
        }
        if(userFilter.lastname()!= null){
            predicates.add(cb.like(user.get("lastname"), userFilter.lastname()));
        }
        if(userFilter.birthDate() != null){
            predicates.add(cb.lessThan(user.get("birthDate"), userFilter.birthDate()));
        }

        criteria.where(predicates.toArray(Predicate[]::new));

        return entityManager.createQuery(criteria).getResultList();
    }*/
}
