package io.github.xiechanglei.lan.base.rbac.init.data;

import io.github.xiechanglei.lan.base.beans.func.ThreeConsumer;
import io.github.xiechanglei.lan.base.rbac.repo.LanBaseSysMenuRepository;
import io.github.xiechanglei.lan.base.utils.collections.ArrayHelper;
import io.github.xiechanglei.lan.base.rbac.annotation.Function;
import io.github.xiechanglei.lan.base.rbac.annotation.Menu;
import io.github.xiechanglei.lan.base.rbac.entity.SysMenu;
import io.github.xiechanglei.lan.base.rbac.entity.SysMenuFc;
import io.github.xiechanglei.lan.base.rbac.entity.SysResourceCode;
import io.github.xiechanglei.lan.base.rbac.repo.LanBaseSysMenuFcRepository;
import io.github.xiechanglei.lan.base.rbac.repo.LanBaseSysResourceCodeRepository;
import io.github.xiechanglei.lan.base.rbac.util.DataUpdaterUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static io.github.xiechanglei.lan.base.rbac.entity.SysMenu.MenuType.PAGE;


/**
 * * 自动更新权限数据
 * * <p>
 * * 5.获取所有的菜单
 * * 6.从数据库中获取所有的菜单，做比较
 * * 7.将新增的菜单插入到数据库中
 * * 8.将删除的菜单从数据库中删除
 * * 9.只修改parent id,update de hql,update xxxx set parent_id = ? where code = ?
 * * <p>
 * * 10.获取所有的操作
 * * 11.从数据库中获取所有的操作，做比较
 * * 12.将新增的操作插入到数据库中
 * * 13.将删除的操作从数据库中删除
 * * <p>
 * * 11.获取资源于权限字符的管理关系
 * * 12.从数据库中获取所有的关系，做比较
 * * 13.将新增的关系插入到数据库中
 * * 14.将删除的关系从数据库中删除
 */
@Component
@Log4j2
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "lan.base.rbac", name = "enable", havingValue = "true", matchIfMissing = true)
public class LanBaseRbacMenuAndFuncInitiation {
    private final LanBaseSysMenuRepository lanBaseSysMenuRepository;
    private final LanBaseSysMenuFcRepository lanBaseSysMenuFcRepository;
    private final LanBaseSysResourceCodeRepository sysResourceCodeRepository;
    private final Environment env; //获取配置文件中的值

    public List<String> initData(ApplicationContext applicationContext) {
        log.info("更新菜单以及资源数据...");
        List<SysMenu> allMenus = new ArrayList<>();
        List<SysMenuFc> allMenuFc = new ArrayList<>();
        List<SysResourceCode> allResourceCode = new ArrayList<>();
        Map<String, Object> beansWithAnnotation = applicationContext.getBeansWithAnnotation(Menu.class);
        beansWithAnnotation.values().forEach(bean -> parseMenuClass(bean.getClass(), (menu, menuFcs, resCodes) -> {
            allMenus.add(menu);
            allMenuFc.addAll(menuFcs);
            allResourceCode.addAll(resCodes);
        }));
        //菜单
        DataUpdaterUtil.update(lanBaseSysMenuRepository, allMenus);
        //功能、
        DataUpdaterUtil.update(lanBaseSysMenuFcRepository, allMenuFc);
        // 资源权限字符字典
        DataUpdaterUtil.update(sysResourceCodeRepository, allResourceCode);
        List<String> allResourceIds = new ArrayList<>();
        allMenus.forEach(m -> allResourceIds.add(m.getMenuCode()));
        allMenuFc.forEach(f -> allResourceIds.add(f.getFcCode()));
        return allResourceIds;
    }

    /**
     * 解析权限菜单标注的类
     *
     * @param clazz 标注了@Menu的类
     */
    public void parseMenuClass(Class<?> clazz, ThreeConsumer<SysMenu, List<SysMenuFc>, List<SysResourceCode>> consumer) {
        Menu menu = clazz.getAnnotation(Menu.class);
        SysMenu sysMenu = new SysMenu(menu.title(), menu.code(), menu.menuType(), formatMenuIcon(menu.icon()), menu.order(), getParentCode(clazz));
        List<SysMenuFc> menuFcs = new ArrayList<>();
        List<SysResourceCode> resCodes = new ArrayList<>();
        if (menu.menuType() == PAGE) {
            Function[] authFunctions = clazz.getDeclaredAnnotationsByType(Function.class);
            Arrays.asList(authFunctions).forEach(authFunction -> {
                menuFcs.add(new SysMenuFc(authFunction.title(), authFunction.code(), menu.code()));
                String[] authCode = authFunction.authCode();
                authCode = ArrayHelper.distinct(authCode);
                for (String code : authCode) {
                    resCodes.add(new SysResourceCode(authFunction.code(), SysResourceCode.ResourceType.ACTION, code));
                }
            });
        }

        consumer.accept(sysMenu, menuFcs, resCodes);

    }

    /**
     * 获取父级code
     */
    public String getParentCode(Class<?> clazz) {
        if (clazz.getDeclaringClass() != null && clazz.getDeclaringClass().isAnnotationPresent(Menu.class)) {
            Menu outerMenu = clazz.getDeclaringClass().getAnnotation(Menu.class);
            return outerMenu.code();
        }
        return clazz.getAnnotation(Menu.class).parentCode();
    }

    /**
     * 格式化菜单图标
     */
    public String formatMenuIcon(String icon) {
        if (icon.startsWith("pro://")) {
            return env.getProperty(icon.substring(icon.indexOf("//") + 2));
        }
        return icon;
    }
}
