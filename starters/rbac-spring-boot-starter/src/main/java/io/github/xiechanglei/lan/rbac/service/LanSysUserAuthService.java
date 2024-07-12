package io.github.xiechanglei.lan.rbac.service;


import io.github.xiechanglei.lan.rbac.entity.base.SysUserAuth;
import io.github.xiechanglei.lan.rbac.init.LanJpaEntityManager;
import io.github.xiechanglei.lan.lang.string.StringOptional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
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
     *                    TODO service 中不应该有sqk查询逻辑，
     */
    public Page<SysUserAuth> searchUser(String word, PageRequest pageRequest) {
        StringBuilder hql = new StringBuilder("select u from " + lanJpaEntityManager.getUserEntityClass().getSimpleName() + " u ");
        StringBuilder countHql = new StringBuilder("select count(u) from " + lanJpaEntityManager.getUserEntityClass().getSimpleName() + " u ");
        StringOptional.of(word).ifPresent(w -> {
            hql.append("where u.userName like :word or u.nickName like :word ");
            countHql.append("where u.userName like :word or u.nickName like :word ");
        });
        List Users = entityManager.createQuery(hql.toString())
                .setParameter("word", "%" + word + "%")
                .setFirstResult(pageRequest.getPageNumber() * pageRequest.getPageSize())
                .setMaxResults(pageRequest.getPageSize())
                .getResultList();
        Long total = (Long) entityManager.createQuery(countHql.toString()).getSingleResult();
        return new PageImpl<>(Users, pageRequest, total);
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