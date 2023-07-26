package project.stockmanagement.region.dao.mybatis;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import project.stockmanagement.common.basewrapper.CombinedEntity;
import project.stockmanagement.region.dao.domain.Region;

@Mapper
public interface RegionMapper {

	void save(CombinedEntity<Region> region);

	List<Region> findAll();

	void delete(Integer id);
}
