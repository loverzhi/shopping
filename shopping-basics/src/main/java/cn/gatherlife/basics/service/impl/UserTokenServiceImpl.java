package cn.gatherlife.basics.service.impl;

import cn.gatherlife.basics.mapper.UserTokenMapper;
import cn.gatherlife.basics.model.UserToken;
import cn.gatherlife.basics.service.UserTokenService;
import cn.gatherlife.basics.util.TokenGenerator;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserTokenServiceImpl extends ServiceImpl<UserTokenMapper, UserToken> implements UserTokenService {
	
	// 12小时后过期
	private final static int EXPIRE = 3600 * 12;

	@Override
	public UserToken findByUserId(Long userId) {
		UserToken userToken = new UserToken();
		userToken.setUserId(userId);
		return this.baseMapper.selectOne(userToken);
	}

	@Override
	public UserToken findByToken(String token) {
		UserToken userToken = new UserToken();
		userToken.setToken(token);
		return this.baseMapper.selectOne(userToken);
	}

	/*@Override
	public int insert(UserToken record) {
		if(record.getId() == null || record.getId() == 0) {
			return this.baseMapper.insert(record);
		}
		return this.baseMapper.update(record,
				new EntityWrapper<UserToken>().eq("id", record.getId()));
	}

	@Override
	public int delete(UserToken record) {
		return this.baseMapper.deleteById(record.getId());
	}

	@Override
	public int delete(List<UserToken> records) {
		for(UserToken record:records) {
			delete(record);
		}
		return 1;
	}*/

/*
	@Override
	public UserToken selectById(Long id) {
		return this.baseMapper.selectById(id);
	}
*/

	@Override
	public UserToken createToken(long userId) {
		// 生成一个token
		String token = TokenGenerator.generateToken();
		// 当前时间
		Date now = new Date();
		// 过期时间
		Date expireTime = new Date(now.getTime() + EXPIRE * 1000);
		// 判断是否生成过token
		UserToken userToken = findByUserId(userId);
		if(userToken == null){
			userToken = new UserToken();
			userToken.setUserId(userId);
			userToken.setToken(token);
			userToken.setLastUpdateTime(now);
			userToken.setExpireTime(expireTime);
			// 保存token，这里选择保存到数据库，也可以放到Redis或Session之类可存储的地方
			this.insert(userToken);
		} else{
			userToken.setToken(token);
			userToken.setLastUpdateTime(now);
			userToken.setExpireTime(expireTime);
			// 如果token已经生成，则更新token的过期时间
			this.updateById(userToken);
		}
		return userToken;
	}
}
