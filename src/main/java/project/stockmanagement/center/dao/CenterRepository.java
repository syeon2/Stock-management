package project.stockmanagement.center.dao;

import java.util.List;

import project.stockmanagement.center.dao.domain.Center;

public interface CenterRepository {

	Center save(Center center);

	List<Center> findAll();

	void delete(Integer id);
}
