package tv;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface MovieRepository extends CrudRepository<MovieEntity, Integer> {

  void deleteByCallDate(String callDate);

  List<MovieEntity> findByCallDate(String callDate);

}
