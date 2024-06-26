package io.github.xiechanglei.lan.base.rbac.service;

import io.github.xiechanglei.lan.base.rbac.dsl.SysMenuFcDsl;
import io.github.xiechanglei.lan.base.rbac.entity.SysMenuFc;
import io.github.xiechanglei.lan.base.rbac.repo.SysMenuFcRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SysMenuFcService {

    private final SysMenuFcRepository sysMenuFcRepository;
    private final SysMenuFcDsl sysMenuFcDsl;

    /**
     * 根据用户id，查询个人的功能
     *
     * @param id 用户id
     */
    public List<SysMenuFc> findByUserId(String id) {
        return sysMenuFcDsl.findByUserId(id);
    }

    /**
     * 获取全部菜单功能
     * 、
     */
    public List<SysMenuFc> getMenuFcAll() {
        return sysMenuFcRepository.findAll();
    }

    /**
     * 修改功能状态
     *
     * @param funcCode   功能编码
     * @param funcStatus 功能状态
     */
    public void changeFuncStatus(String funcCode, SysMenuFc.FuncStatus funcStatus) {
        sysMenuFcRepository.findById(funcCode).ifPresent(sysMenuFc -> {
            sysMenuFc.setFcStatus(funcStatus);
            sysMenuFcRepository.save(sysMenuFc);
        });
    }
}
