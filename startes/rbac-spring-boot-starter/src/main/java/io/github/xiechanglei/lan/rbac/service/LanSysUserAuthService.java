package io.github.xiechanglei.lan.rbac.service;


import io.github.xiechanglei.lan.rbac.entity.base.SysUserAuth;
import io.github.xiechanglei.lan.rbac.init.LanJpaEntityManager;
import io.github.xiechanglei.lan.utils.string.StringOptional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LanSysUserAuthService {
    private final LanJpaEntityManager lanJpaEntityManager;
    private final EntityManager entityManager;


    /**
     * 判断用户名是否存在
     *
     * @param userName 用户名
     * @return true:存在;false:不存在
     * 注：创建的query里参数名(u.userName)必须与实体类属性名一致，否则查询语句无法执行
     */
    public boolean existsByUsername(String userName) {
        return !entityManager.createQuery("select u.id from " + lanJpaEntityManager.getUserEntityClass().getSimpleName() + " u where u.userName=:username").setParameter("username", userName).getResultList().isEmpty();
    }

    /**
     * 添加用户
     */
    @Transactional
    public void add(SysUserAuth user) {
        entityManager.persist(user);
    }

    /**
     * 根据用户名和密码查询用户
     *
     * @param userName     用户名
     * @param userPassword 密码
     * @return 用户
     */
    @SuppressWarnings("unchecked")
    public SysUserAuth findByUserNameAndUserPassword(String userName, String userPassword) {
        String query = "select u from " + lanJpaEntityManager.getUserEntityClass().getSimpleName() + " u where u.userName=:username and u.userPassword=:userPassword";
        List<SysUserAuth> resultList = entityManager.createQuery(query).setParameter("username", userName).setParameter("userPassword", userPassword).getResultList();
        if (resultList.isEmpty()) {
            return null;
        }
        return resultList.get(0);
    }

    /**
     * 查询用户
     *
     * @param word        用户名
     * @param pageRequest 分页信息
     *                    TODO 优化查询条件
     */
    public Page<SysUserAuth> searchUser(String word, PageRequest pageRequest) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<SysUserAuth> criteriaQuery = criteriaBuilder.createQuery(SysUserAuth.class);
        Root<? extends SysUserAuth> root = criteriaQuery.from(lanJpaEntityManager.getUserEntityClass());
        criteriaQuery.select(root);
        List<Predicate> predicates = new ArrayList<>();
        StringOptional.of(word).ifPresent(w -> {
            predicates.add(criteriaBuilder.or(criteriaBuilder.like(root.get("userName"), "%" + word + "%"), criteriaBuilder.like(root.get("nickName"), "%" + word + "%")));
        });
        if (!predicates.isEmpty()) {
            criteriaQuery.where(predicates.toArray(new Predicate[0]));
        }

        criteriaQuery.orderBy(criteriaBuilder.desc(root.get("createTime")));//按创建时间倒序排列

        TypedQuery<SysUserAuth> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult(pageRequest.getPageNumber() * pageRequest.getPageSize());
        typedQuery.setMaxResults(pageRequest.getPageSize());

        List<SysUserAuth> resultList = typedQuery.getResultList();

        // 查询总数
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<? extends SysUserAuth> countRoot = countQuery.from(lanJpaEntityManager.getUserEntityClass());
        countQuery.select(criteriaBuilder.count(countRoot));
        countQuery.where(predicates.toArray(new Predicate[0]));

        Long total = entityManager.createQuery(countQuery).getSingleResult();

        return new PageImpl<>(resultList, pageRequest, total);
    }

    /**
     * 获取用户信息
     *
     * @param userId 用户id
     */
    public Optional<SysUserAuth> findById(String userId) {
        return Optional.ofNullable(entityManager.find(lanJpaEntityManager.getUserEntityClass(), userId));
    }

    /**
     * 修改用户信息
     *
     * @param user 用户信息
     */
    @Transactional
    public void update(SysUserAuth user) {
        entityManager.merge(user);
    }

    @Transactional
    public void updatePassword(SysUserAuth user, String password) {
        user.updateSerial();
        String hql = "update " + lanJpaEntityManager.getUserEntityClass().getSimpleName() + " u set u.userPassword=:password,u.userSerial=:userSerial where u.id=:id";
        entityManager.createQuery(hql)
                .setParameter("userSerial", user.getUserSerial())
                .setParameter("id", user.getId())
                .setParameter("password", password)
                .executeUpdate();
    }

    @Transactional
    public void disableUser(String userId) {
        String hql = "update " + lanJpaEntityManager.getUserEntityClass().getSimpleName() + " u set u.userStatus=:userStatus where u.id=:id";
        entityManager.createQuery(hql)
                .setParameter("userStatus", SysUserAuth.UserStatus.DISABLE).setParameter("id", userId)
                .executeUpdate();
    }

    @Transactional
    public void enableUser(String userId) {
        String hql = "update " + lanJpaEntityManager.getUserEntityClass().getSimpleName() + " u set u.userStatus=:userStatus where u.id=:id";
        entityManager.createQuery(hql)
                .setParameter("userStatus", SysUserAuth.UserStatus.ENABLE).setParameter("id", userId)
                .executeUpdate();
    }
}