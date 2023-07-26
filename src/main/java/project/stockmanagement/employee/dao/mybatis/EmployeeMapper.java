package project.stockmanagement.employee.dao.mybatis;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import project.stockmanagement.common.basewrapper.CombinedEntity;
import project.stockmanagement.employee.dao.domain.Employee;

@Mapper
public interface EmployeeMapper {

	void save(CombinedEntity<Employee> employee);

	Optional<Employee> findById(Long id);

	List<Employee> findByCenterId(Integer centerId);

	void update(@Param("id") Long id, @Param("updateParam") CombinedEntity<Employee> updateEmployee);

	void delete(Long id);
}
