package project.stockmanagement.center.dao.mybatis;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import project.stockmanagement.center.dao.domain.Center;
import project.stockmanagement.common.basewrapper.CombinedEntity;

@Mapper
public interface CenterMapper {

	void save(CombinedEntity<Center> combinedEntity);

	List<Center> findAll();

	void delete(Integer id);
}
