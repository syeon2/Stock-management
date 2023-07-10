package project.stockmanagement.region.dao.mybatis;

import java.util.List;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import project.stockmanagement.region.dao.RegionRepository;
import project.stockmanagement.region.dao.domain.Region;

@Repository
@RequiredArgsConstructor
public class MyBatisRegionRepository implements RegionRepository {

	private final RegionMapper regionMapper;

	@Override
	public Region save(Region region) {
		regionMapper.save(region);

		return region;
	}

	@Override
	public List<Region> findAll() {
		return regionMapper.findAll();
	}

	@Override
	public void delete(Integer id) {
		regionMapper.delete(id);
	}
}
