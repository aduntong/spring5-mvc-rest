package guru.springfamework.services;

import guru.springfamework.api.v1.mapper.CustomerMapper;
import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.domain.Customer;
import guru.springfamework.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by jt on 9/27/17.
 */
@Service
public class CustomerServiceImpl implements CustomerService {

    private CustomerMapper customerMapper;
    private CustomerRepository customerRepository;
    @Autowired
    public void setCustomerMapper(CustomerMapper customerMapper) {
        this.customerMapper = customerMapper;
    }
    @Autowired
    public void setCustomerRepository(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        return customerRepository
                .findAll()
                .stream()
                .map(customer -> {
                   CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customer);
                   customerDTO.setCustomerUrl("/api/v1/customers/" + customer.getId());
                   return customerDTO;
                })
                .collect(Collectors.toList());
    }
    public List<CustomerDTO> getListByCondition( CustomerDTO dto){
        Specification querySpecifi = (Specification< CustomerDTO >)( root, criteriaQuery, criteriaBuilder ) -> {

            List<Predicate> predicates = new ArrayList<>();
            if(null != dto.getFirstname()){
                predicates.add(criteriaBuilder.equal(root.get("firstname"), dto.getFirstname()));
            }
            if(null != dto.getLastname()){
                predicates.add(criteriaBuilder.equal(root.get("lastname"), dto.getLastname()));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
        return customerRepository.findAll( querySpecifi );
        /**
         *         Specification querySpecifi = new Specification<CustomerDTO>() {
         *             @Override
         *             public Predicate toPredicate( Root<CustomerDTO> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
         *
         *                 List<Predicate> predicates = new ArrayList<>();
         *                 if(null != dto.getFirstname()){
         *                     predicates.add(criteriaBuilder.equal(root.get("firstname"), minDate));
         *                 }
         *                 if(null != dto.getLastname()){
         *                     predicates.add(criteriaBuilder.equal(root.get("subscribeTime"), maxDate));
         *                 }
         *                 return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
         *             }
         *         };
         * */
    }
    @Override
    public CustomerDTO getCustomerById(Long id) {

        return customerRepository.findById(id)
                .map(customerMapper::customerToCustomerDTO)
                .orElseThrow(RuntimeException::new); //todo implement better exception handling
    }

    @Override
    public CustomerDTO createNewCustomer(CustomerDTO customerDTO) {

        Customer customer = customerMapper.customerDtoToCustomer(customerDTO);

        Customer savedCustomer = customerRepository.save(customer);

        CustomerDTO returnDto = customerMapper.customerToCustomerDTO(savedCustomer);

        returnDto.setCustomerUrl("/api/v1/customer/" + savedCustomer.getId());

        return returnDto;
    }
}
