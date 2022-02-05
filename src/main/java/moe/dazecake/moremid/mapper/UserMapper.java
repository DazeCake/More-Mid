package moe.dazecake.moremid.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import moe.dazecake.moremid.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<UserEntity> {
}
