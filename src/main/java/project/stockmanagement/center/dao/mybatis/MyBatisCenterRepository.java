package project.stockmanagement.center.dao.mybatis;

import java.util.List;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import project.stockmanagement.center.dao.CenterRepository;
import project.stockmanagement.center.dao.domain.Center;
import project.stockmanagement.common.basewrapper.CombinedEntity;

@Repository
@RequiredArgsConstructor
public class MyBatisCenterRepository implements CenterRepository {

	private final CenterMapper centerMapper;

	@Override
	public Center save(Center center) {
		CombinedEntity<Center> createData = CombinedEntity.toCreateData(center);
		centerMapper.save(createData);

		return createData.toEntity();
	}

	@Override
	public List<Center> findAll() {
		return centerMapper.findAll();
	}

	@Override
	public void delete(Integer id) {
		centerMapper.delete(id);
	}
}
