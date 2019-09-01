package com.scd.mapper;

import java.util.List;
import java.util.Map;

import com.scd.model.po.UserRole;

public interface UserRoleMapper {

	UserRole get(Long id);

	List<UserRole> list(Map<String, Object> map);

	int count(Map<String, Object> map);

	int save(UserRole userRole);

	int update(UserRole userRole);

	int remove(Long id);

	int batchRemove(Long[] ids);

	List<Long> listRoleId(Long userId);

	int removeByUserId(Long userId);

	int removeByRoleId(Long roleId);

	int batchSave(List<UserRole> list);

	int batchRemoveByUserId(Long[] ids);
}
