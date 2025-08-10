package tv.db;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface MovieRepository extends CrudRepository<MovieEntity, Integer> {

  void deleteByCallDate(String callDate);

  List<MovieEntity> findByCallDate(String callDate);

  List<MovieEntity> findByCallDateAndSuccess(String callDate, boolean success);

}
