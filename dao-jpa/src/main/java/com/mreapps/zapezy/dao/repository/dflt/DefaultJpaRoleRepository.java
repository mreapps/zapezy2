package com.mreapps.zapezy.dao.repository.dflt;

import com.mreapps.zapezy.dao.entity.user.JpaRole;
import com.mreapps.zapezy.dao.entity.user.JpaRole_;
import com.mreapps.zapezy.dao.repository.JpaRoleRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Repository for crud operations on the {@link JpaRole} entity
 */
@Repository
public class DefaultJpaRoleRepository extends DefaultCrudRepository<JpaRole> implements JpaRoleRepository
{
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public JpaRole findByCode(String code)
    {
        final CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        final CriteriaQuery<JpaRole> cq = cb.createQuery(JpaRole.class);
        final Root<JpaRole> root = cq.from(JpaRole.class);
        cq.select(root);
        cq.where(
                cb.equal(root.get(JpaRole_.code), code)
        );

        return findSingle(cq);
    }

    @Override
    public List<JpaRole> findAll()
    {
        final CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        final CriteriaQuery<JpaRole> cq = cb.createQuery(JpaRole.class);
        final Root<JpaRole> root = cq.from(JpaRole.class);
        cq.select(root);

        return findMany(cq, 0, Integer.MAX_VALUE);
    }
}
