package cl.bermann.tc.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import cl.bermann.tc.model.Mensaje;
@Repository
public interface MsgRepositoryCustom extends MongoRepository<Mensaje, String>{

	@Query("{id:'?0'}")
	public Mensaje findItemById(String id);
    
    @Query(value="{'mc_estado':?0}", fields="{'id_servicio' : 1, 'mc_ip' : 1}", sort = "{id : -1}" )
    public List<Mensaje> findAll(Integer mc_estado);
    
    public long count();
    
    
}
