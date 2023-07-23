package project.stockmanagement.attendance.dao.mybatis;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import project.stockmanagement.attendance.dao.domain.Attendance;

@Mapper
public interface AttendanceMapper {

	void save(Attendance attendance);

	Optional<Attendance> findById(Long id);

	List<Attendance> findByEmployeeId(@Param("employeeId") Long employeeId, @Param("limit") Integer limit);

	List<Attendance> findByCenterId(Integer id);

	void update(@Param("id") Long id, @Param("updateParam") Attendance combinedEntity);

	void delete(Long id);
}
