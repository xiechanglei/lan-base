package io.github.xiechanglei.lan.rbac.service;

import io.github.xiechanglei.lan.rbac.repo.SysMenuFcDsl;
import io.github.xiechanglei.lan.rbac.entity.base.SysMenuFc;
import io.github.xiechanglei.lan.rbac.repo.LanSysMenuFcRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LanSysMenuFcService {

    private final LanSysMenuFcRepository lanSysMenuFcRepository;
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
        return lanSysMenuFcRepository.findAll();
    }

    /**
     * 修改功能状态
     *
     * @param funcCode   功能编码
     * @param funcStatus 功能状态
     */
    public void changeFuncStatus(String funcCode, SysMenuFc.FuncStatus funcStatus) {
        lanSysMenuFcRepository.findById(funcCode).ifPresent(sysMenuFc -> {
            sysMenuFc.setFcStatus(funcStatus);
            lanSysMenuFcRepository.save(sysMenuFc);
        });
    }
}