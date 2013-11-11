package com.mreapps.zapezy.dao.repository.dflt;

import com.mreapps.zapezy.dao.entity.user.JpaUser;
import com.mreapps.zapezy.dao.entity.user.JpaUser_;
import com.mreapps.zapezy.dao.repository.JpaUserRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 * Repository for crud operations on the {@link JpaUser} entity
 */
@Repository
public class DefaultJpaUserRepository extends DefaultCrudRepository<JpaUser> implements JpaUserRepository
{
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public JpaUser findByEmailAddress(String emailAddress)
    {
        final CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        final CriteriaQuery<JpaUser> cq = cb.createQuery(JpaUser.class);
        final Root<JpaUser> root = cq.from(JpaUser.class);
        cq.select(root);
        cq.where(
                cb.equal(root.get(JpaUser_.emailAddress), emailAddress)
        );

        return findSingle(cq);
    }
}
