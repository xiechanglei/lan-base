package io.github.xiechanglei.lan.rbac.entity;

import io.github.xiechanglei.lan.rbac.util.ComparedEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

/**
 * 权限字符字典表，存储系统中定义的所有权限字符，权限字符是描述接口权限的最小单位：
 * 比如对于某一个模块的20个接口中，可以整理出来5个权限字符，分表表示数据的查询、新增、修改、删除、导出，
 * 权限字符的划分逻辑应该根据实际的业务与功能模块来划分，比如对于工单模块中工单状态的更改拆分成了多个接口（审批，提交，），而这多个接口对应的权限字符都是不一样的
 */
@Data
@Entity
@Table(name = "sys_auth_code")
@AllArgsConstructor
@NoArgsConstructor
@SuppressWarnings("all")
public class SysAuthCode implements ComparedEntity {

    /**
     * 权限字符，用于唯一表示权限的字符串的编码，常规格式为：模块:对象:动作，
     * 为区别于其他系统的权限字符，框架内置的模块的权限字符都以rbac: 开头
     */
    @Id
    @Column(name = "auth_code", length = 100, nullable = false)
    private String authCode;

    /**
     * 权限模块名称，没有实际的业务意义，只是用于描述权限字符所属的模块，比如用户模块、角色模块、菜单模块等，
     * 用于开发者在开发时更好的理解权限字符的作用
     */
    @Column(name = "auth_module", length = 100, nullable = false)
    private String authModule;

    /**
     * 权限字符的名称，没有实际的业务意义,用于描述权限字符的作用，比如“用户查询”、“用户编辑”等，
     * 与权限模块名称一样，用于开发者在开发时更好的理解权限字符的作用
     */
    @Column(name = "auth_title", length = 100, nullable = false)
    private String authTitle;

    @Override
    public Object buildUniqueId() {
        return getAuthCode();
    }

    /**
     * 对比是否相同，如果不相同，则将对应的字段更新
     *
     * @param comparedEntity 对比的实体
     * @return 是否变化
     */
    @Override
    public boolean changeIfNotSame(ComparedEntity comparedEntity) {
        SysAuthCode sysAuthCode = (SysAuthCode) comparedEntity;
        boolean isChange = false;
        if (!Objects.equals(this.authModule, sysAuthCode.getAuthModule()) && (isChange = true)) {
            this.authModule = sysAuthCode.getAuthCode();
        }
        if (!Objects.equals(this.authTitle, sysAuthCode.getAuthTitle()) && (isChange = true)) {
            this.authTitle = sysAuthCode.getAuthTitle();
        }
        return isChange;
    }
}
