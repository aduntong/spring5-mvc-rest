package guru.springfamework.repositories;

import guru.springfamework.domain.Customer;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by jt on 9/27/17.
 */
public interface CustomerRepository extends JpaRepository<Customer, Long>{
	List<Customer> findAll( Specification<Customer> spec);
}
