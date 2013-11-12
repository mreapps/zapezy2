package com.mreapps.zapezy.dao.repository.dflt;

import com.mreapps.zapezy.dao.entity.common.JpaMailMessage;
import com.mreapps.zapezy.dao.repository.JpaMailMessageRepository;
import org.springframework.stereotype.Repository;

/**
 * Default implementation of {@link JpaMailMessageRepository}
 */
@Repository
public class DefaultJpaMailMessageRepository extends DefaultCrudRepository<JpaMailMessage> implements JpaMailMessageRepository
{
}
