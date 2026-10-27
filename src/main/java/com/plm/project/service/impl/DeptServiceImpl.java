package com.plm.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.plm.project.common.UserContext;
import com.plm.project.dao.UserDao;
import com.plm.project.dto.DeleteCheckResultDto;
import com.plm.project.entity.Dept;
import com.plm.project.dao.DeptDao;
import com.plm.project.entity.User;
import com.plm.project.service.DeptService;
import com.plm.project.vo.DeptVo;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 部门表(Dept)表服务实现类
 *
 * @author makejava
 * @since 2026-10-25 15:40:42
 */
@Service("deptService")
public class DeptServiceImpl extends ServiceImpl<DeptDao, Dept> implements DeptService {
    public static final String DEPT_USER_EXIST_FAIL="该部门下存在用户，无法删除";
    public static final String DEPT_SUB_DEPT_EXIST_FAIL="该部门下存在子目录，无法删除";

    @Autowired
    UserDao userDao;

    @Override
    public Dept info(Long id) {
        return this.getById(id);
    }

    @Override
    public List<DeptVo> getTreeList(Dept dept) {
        QueryWrapper<Dept> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("del_flag", 0);
        if (StringUtils.isNotEmpty(dept.getDeptName())) {
            queryWrapper.like("dept_name", dept.getDeptName());
        }
        if (dept.getStatus() != null) {
            queryWrapper.eq("status", dept.getStatus());
        }
        //查询所有父目录，即parent-id为0的目录
        queryWrapper.eq("parent_id", 0);
        List<Dept> parentList = this.list(queryWrapper);
        //TODO 遍历判断是否存在子部门，递归构建DeptVo
        List<DeptVo> deptVoList = parentList.stream().map(p -> {
            DeptVo vo = new DeptVo();
            List<DeptVo> children = getChildren(p);
            vo.setDept(p);
            vo.setChildren(children);
            return vo;
        }).toList();
        return deptVoList;
    }

    private List<DeptVo> getChildren(Dept p) {
        QueryWrapper<Dept> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id", p.getDeptId());
        queryWrapper.eq("del_flag", 0);
        List<Dept> list = this.list(queryWrapper);
        if (list != null && list.size() > 0) {
            List<DeptVo> deptVoList = list.stream().map(l -> {
                DeptVo vo = new DeptVo();
                vo.setDept(l);
                List<DeptVo> children = getChildren(l);
                vo.setChildren(children);
                return vo;
            }).toList();
            return deptVoList;
        }
        return null;
    }


    @Override
    public boolean add(Dept dept) {
        //某些字段设置默认值
        User user = UserContext.get();
        dept.setCreateBy(user.getLoginName());
        dept.setCreateTime(new Date());
        //求ancestors
        String ancestors = computeAncestors(dept);
        dept.setAncestors(ancestors);
        return this.save(dept);
    }

    private String computeAncestors(Dept dept) {
        StringBuffer ancestors = new StringBuffer();
        //找到父对象的ancestors
        if (dept.getParentId() == 0) {
            return ancestors.append(dept.getParentId()).toString();
        }
        Dept parent = this.getById(dept.getParentId());
        ancestors.append(parent.getAncestors());
        //拼接
        ancestors.append(",");
        ancestors.append(dept.getParentId());
        return ancestors.toString();
    }

    @Override
    public boolean edit(Dept dept) {
        dept.setAncestors(computeAncestors(dept));
        dept.setUpdateTime(new Date());
        dept.setUpdateBy(UserContext.get().getLoginName());
        return this.updateById(dept);
    }

    @Override
    public DeleteCheckResultDto deleteByIds(List<Long> ids) {
        List<Dept> deptList = this.listByIds(ids);
        //批量获取该部门下的用户和子目录
        List<User> users = userDao.selectList(new QueryWrapper<User>().select("DISTINCT dept_id").eq("del_flag", 0).in("dept_id", ids));
        Set<Long> hasUser = users.stream().map(User::getDeptId).collect(Collectors.toSet());
        List<Dept> depts = this.list(new QueryWrapper<Dept>().select("parent_id").eq("del_flag", 0).in("parent_id", ids));
        Set<Long> hasSubDept = depts.stream().map(Dept::getParentId).collect(Collectors.toSet());
        //遍历ids，判断哪些id存在子目录或用户，并存入failList中
        DeleteCheckResultDto resultDto = new DeleteCheckResultDto();
        List<DeleteCheckResultDto.FailDetail> failList = new ArrayList<>();
        List<Long> successList = new ArrayList<>();
        for (Dept dept : deptList) {
            boolean isFail = false; // 标记是否校验失败
            if(hasUser.contains(dept.getDeptId())){
                DeleteCheckResultDto.FailDetail failDetail = new DeleteCheckResultDto.FailDetail();
                failDetail.setId(dept.getDeptId());
                failDetail.setName(dept.getDeptName());
                failDetail.setMsg(DEPT_USER_EXIST_FAIL);
                failList.add(failDetail);
                isFail = true;
            }
            if(hasSubDept.contains(dept.getDeptId())){
                DeleteCheckResultDto.FailDetail failDetail = new DeleteCheckResultDto.FailDetail();
                failDetail.setId(dept.getDeptId());
                failDetail.setName(dept.getDeptName());
                failDetail.setMsg(DEPT_SUB_DEPT_EXIST_FAIL);
                failList.add(failDetail);
                isFail = true;
            }
            if(!isFail){
                successList.add(dept.getDeptId());
            }
        }
        if (failList.size() > 0) {
            resultDto.setSuccess(false);
        }
        //封装为DTO返回
        return resultDto;
    }

    @Override
    public List<DeptVo> queryByExcludeNoed(Long deptId) {
        QueryWrapper<Dept> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("del_flag", 0);
        queryWrapper.ne("dept_id", deptId);
        List<Dept> list = this.list(queryWrapper);
        List<DeptVo> deptVoList = list.stream().map(l -> {
            DeptVo vo = new DeptVo();
            vo.setDept(l);
            vo.setChildren(null);
            return vo;
        }).toList();
        return deptVoList;
    }

    @Override
    public Boolean saveSort(List<Long> deptIds, List<Integer> sorts) {
        List<Dept> deptList = new ArrayList();
        for (int i = 0; i < deptIds.size(); i++) {
            Dept dept = getById(deptIds.get(i));
            dept.setOrderNum(sorts.get(i));
            deptList.add(dept);
        }
        return this.saveBatch(deptList);
    }

    @Override
    public List<Dept> getList(Dept dept) {
        QueryWrapper<Dept> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("del_flag", 0);
        if (StringUtils.isNotEmpty(dept.getDeptName())) {
            queryWrapper.like("dept_name", dept.getDeptName());
        }
        if (dept.getStatus() != null) {
            queryWrapper.eq("status", dept.getStatus());
        }
        List<Dept> list = this.list(queryWrapper);
        return list;
    }
}
