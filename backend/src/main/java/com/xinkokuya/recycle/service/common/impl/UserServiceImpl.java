package com.xinkokuya.recycle.service.common.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.xinkokuya.recycle.core.AbstractService;
import com.xinkokuya.recycle.core.MessageService;
import com.xinkokuya.recycle.core.ServiceException;
import com.xinkokuya.recycle.core.utils.JwtTokenUtil;
import com.xinkokuya.recycle.core.utils.Utils;
import com.xinkokuya.recycle.dao.UserMapper;
import com.xinkokuya.recycle.dto.UserDto;
import com.xinkokuya.recycle.model.User;
import com.xinkokuya.recycle.service.common.UserService;
import tk.mybatis.mapper.entity.Condition;

@Service
@Transactional
public class UserServiceImpl extends AbstractService<User> implements UserService {

	@Resource
	private UserMapper userMapper;

	@Resource
	private JwtTokenUtil jwtTokenUtil;

	@Resource
	private PasswordEncoder passwordEncoder;

	@Override
	public String login(User user) throws ServiceException {
		// 用户名
		String username = user.getUsername();
		if (!StringUtils.isEmpty(user.getUsername())) {
			username = user.getUsername();
		}
		if (!StringUtils.isEmpty(user.getEmail())) {
			username = user.getEmail();
		}
		// Db中的用户
		User userInDB = this.findBy("username", username);
		if (userInDB == null || userInDB.getDeleteFlag() == 1)
			throw new ServiceException(MessageService.getMessage("error.user.username_not_found"));
		// 用户是否被冻结
		if (userInDB.getStatus() == 0)
			throw new ServiceException(MessageService.getMessage("error.user.username_be_freezed"));
		// 校验密码是否正确
		if (!this.verifyPassword(user.getPassword(), userInDB.getPassword()))
			throw new ServiceException(MessageService.getMessage("error.user.incorrect_password"));
		// 获取Token
		String token = this.jwtTokenUtil.sign(username);
		return token;
	}

	public void logout(String token) {
		// SecurityContextHolder.clearContext();
	}

	@Override
	public UserDto getUserInfoByToken() throws ServiceException {
		UserDto userDto = new UserDto();
		String userId = Utils.getCurrentUserId();
		String userName = Utils.getCurrentUsername();
		userDto.setUserId(userId);
		userDto.setUsername(userName);
		// 用户权限
//		List<String> permissions = this.getUserPermissions(userDto.getSeqId());
//		userDto.setPermissions(permissions);
		List<String> permissions = new ArrayList<String>();
		permissions.add("PERMISSION_ADMIN");
		permissions.add("PERMISSION_USER");
		userDto.setPermissions(permissions);
		return userDto;
	}

	@Override
	public UserDto getUserInfoByUsername(String username) {
		User user = this.findBy("username", username);
		if (user == null || user.getDeleteFlag() == 1)
			throw new ServiceException(MessageService.getMessage("error.user.username_not_found"));
		UserDto userDto = new UserDto();
		userDto.setUserId(user.getUserId());
		userDto.setUsername(user.getUsername());
		userDto.setPassword(user.getPassword());
		userDto.setStatus(user.getStatus());
		// 用户角色
//		List<Role> roleList = roleMapper.getUserRoles(user.getSeqId());
		List<String> roles = new ArrayList<String>();
		roles.add("ROLE_ADMIN");
		roles.add("ROLE_USER");
		userDto.setRoles(roles);
		return userDto;
	}

	/**
	 * 校验密码
	 * 
	 * @param rawPassword     明文密码
	 * @param encodedPassword 加密后密码
	 * @return boolean
	 */
	private boolean verifyPassword(final String rawPassword, final String encodedPassword) {
		return this.passwordEncoder.matches(rawPassword, encodedPassword);
	}

	/**
	 * 检查用户是否已存在
	 * 
	 * @param username
	 * @return
	 */
	private boolean checkUsernameExist(String username) {
		if (StringUtils.isEmpty(username)) {
			return false;
		}
		Condition condition = new Condition(User.class);
		condition.createCriteria().andEqualTo("username", username);
		List<User> usersInDB = this.findByCondition(condition);
		return usersInDB.size() > 0;
	}
}
