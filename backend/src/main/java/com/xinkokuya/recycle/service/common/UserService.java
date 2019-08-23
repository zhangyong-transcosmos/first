package com.xinkokuya.recycle.service.common;
import com.xinkokuya.recycle.model.User;
import com.xinkokuya.recycle.core.Service;
import com.xinkokuya.recycle.dto.UserDto;


public interface UserService extends Service<User> {

	/**
	 * 登录
	 * @param user
	 * @return
	 */
	public String login(User user);

    /**
     * 注销
     * @param token
     * @return
     */
    public void logout(String token);
	
	/**
	 * 通过Token获取用户信息
	 * @param request
	 * @return
	 */
	public UserDto getUserInfoByToken();
	
	/**
	 * 根据用户名检索
	 * @param username
	 * @return
	 */
	public UserDto getUserInfoByUsername(String username);

}
