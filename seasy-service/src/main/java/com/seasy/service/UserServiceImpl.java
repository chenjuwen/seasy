package com.seasy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.github.pagehelper.PageInfo;
import com.seasy.core.encryptionpolicy.EncryptionPolicy;
import com.seasy.core.encryptionpolicy.EncryptionResult;
import com.seasy.dao.Paging;
import com.seasy.dao.mybatis.mapper.UsersMapper;
import com.seasy.interfaces.dto.ResourcesDTO;
import com.seasy.interfaces.dto.ResultDTO;
import com.seasy.interfaces.dto.RolesDTO;
import com.seasy.interfaces.dto.UsersDTO;
import com.seasy.interfaces.service.UserService;

@Service("userService")
public class UserServiceImpl extends AbstractBaseService implements UserService {
	@Autowired
	private UsersMapper usersMapper;
	@Autowired
	private EncryptionPolicy encryptionPolicy;
	
	@Override
	public UsersDTO selectByPrimaryKey(Long userId) {
		return usersMapper.selectByPrimaryKey(userId);
	}

	@Override
	public UsersDTO selectByLoginName(String loginName) {
		List<UsersDTO> userList = usersMapper.selectByLoginName(loginName);
		if(!CollectionUtils.isEmpty(userList)){
			return userList.get(0);
		}
		return null;
	}

	@Override
	public PageInfo<UsersDTO> selectByPage(int pageNum, int pageSize, Object params) {
		String key = "com.seasy.dao.mybatis.mapper.UsersMapper.selectByPage";
		Paging paging = new Paging(pageNum, pageSize);
		return getMybatisDao().selectPage(key, params, paging);
	}

	@Override
	public ResultDTO insert(UsersDTO user) {
		ResultDTO dto = new ResultDTO();
		if(selectByLoginName(user.getLoginName()) != null){
			dto.setCode(ResultDTO.CODE_ERROR);
			dto.setMessage("用户登录账号 " + user.getLoginName() + " 已存在！");
		}else{
			EncryptionResult encryptionResult = encryptionPolicy.encrypt(user.getPassword());
			user.setSalt(encryptionResult.getSalt());
			user.setPassword(encryptionResult.getPassword());
			
			usersMapper.insert(user);
			dto.setCode(ResultDTO.CODE_SUCCESS);
		}
		return dto;
	}

	@Override
	public ResultDTO update(UsersDTO user) {
		ResultDTO dto = new ResultDTO();
		usersMapper.update(user);
		dto.setCode(ResultDTO.CODE_SUCCESS);
		return dto;
	}

	@Override
	public ResultDTO updatePassword(Long userId, String oldPassword, String newPassword) {
		ResultDTO resultDTO = new ResultDTO();
		
		UsersDTO user = usersMapper.selectByPrimaryKey(userId);
		EncryptionResult oldEncryptionResult = encryptionPolicy.encrypt(oldPassword, user.getSalt());
		
		if(!oldEncryptionResult.getPassword().equals(user.getPassword())){
			resultDTO.setCode(ResultDTO.CODE_ERROR);
			resultDTO.setMessage("输入的旧密码有误！");
		}else{
			EncryptionResult newEncryptionResult = encryptionPolicy.encrypt(newPassword);
			user.setPassword(newEncryptionResult.getPassword());
			user.setSalt(newEncryptionResult.getSalt());
			usersMapper.updatePassword(user);
			resultDTO.setCode(ResultDTO.CODE_SUCCESS);
		}
		
		return resultDTO;
	}
	
	@Override
	public boolean resetPassword(Long userId) {
		UsersDTO user = usersMapper.selectByPrimaryKey(userId);
		
		EncryptionResult encryptionResult = encryptionPolicy.encrypt("");
		user.setSalt(encryptionResult.getSalt());
		user.setPassword(encryptionResult.getPassword());
		
		return 1 == usersMapper.updatePassword(user);
	}

	@Override
	public boolean updateState(UsersDTO user) {
		return 1 == usersMapper.updateState(user);
	}

	@Override
	public boolean updateLoginInfo(Long userId) {
		return 1 == usersMapper.updateLoginInfo(userId);
	}
	
	@Override
	public List<ResourcesDTO> getAllResourceByUserId(Long userId) {
		return usersMapper.getAllResourceByUserId(userId);
	}
	
	@Override
	public List<ResourcesDTO> getTopResourceByUserId(Long userId) {
		return usersMapper.getTopResourceByUserId(userId);
	}
	
	@Override
	public List<RolesDTO> getAllRoleByUserId(Long userId) {
		return usersMapper.getAllRoleByUserId(userId);
	}
	
}