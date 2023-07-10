package project.stockmanagement.region.dao;

import java.util.List;

import project.stockmanagement.region.dao.domain.Region;

public interface RegionRepository {

	Region save(Region region);

	List<Region> findAll();

	void delete(Integer id);
}
