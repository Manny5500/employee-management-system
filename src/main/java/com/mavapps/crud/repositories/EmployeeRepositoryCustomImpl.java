package com.mavapps.crud.repositories;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.mavapps.crud.entities.Employee;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

@Repository
public class EmployeeRepositoryCustomImpl implements EmployeeRepositoryCustom {
    
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Employee> searchWithCriteria (
        String firstName,
        String middleName,
        String lastName,
        Long departmentId,
        int pageSize,
        int pageNumber,
        String sortBy,
        Boolean isAsc
    ){

        Session session = entityManager.unwrap(Session.class);
        CriteriaBuilder cb = session.getCriteriaBuilder();

        //For Max Count
        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        countQuery.select(cb.count(countQuery.from(Employee.class)));
        Long count = session.createQuery(countQuery).getSingleResult();

        /* 
        CriteriaQuery<Employee> hqlCriteria = session.getCriteriaBuilder()
            .createQuery("FROM Employee e", Employee.class);
            */
        CriteriaQuery<Employee> hqlCriteria = cb.createQuery(Employee.class);
        Root<Employee> root = hqlCriteria.from(Employee.class);
        if(sortBy.equalsIgnoreCase("name")){
           hqlCriteria.orderBy(
            (isAsc) ? cb.asc(root.get("lastName")) 
                        : cb.desc(root.get("lastName"))
            ); 
        }else if(sortBy.equalsIgnoreCase("salary")){
            hqlCriteria.orderBy(
            (isAsc) ? cb.asc(root.get("salary")) 
                        : cb.desc(root.get("salary"))
            );
        }
        
        //List<Employee> list = session.createQuery(hqlCriteria).getResultList();


        int pageStart = (pageNumber-1) * pageSize;
 
        if(pageNumber < count){
            TypedQuery<Employee> typedQuery = session.createQuery(hqlCriteria);
            typedQuery.setFirstResult(pageStart);
            typedQuery.setMaxResults(pageSize);
            return typedQuery.getResultList();
        }else{
            return new ArrayList<Employee>();
        }
    };
}
