package moe.dazecake.moremid.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import moe.dazecake.moremid.entity.BookEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EconomicBookMapper extends BaseMapper<BookEntity> {
}
