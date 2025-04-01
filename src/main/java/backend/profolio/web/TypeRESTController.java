package backend.profolio.web;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import backend.profolio.domain.Project;
import backend.profolio.domain.ProjectRepository;
import backend.profolio.domain.Type;
import backend.profolio.domain.TypeRepository;

@RestController
public class TypeRESTController {

    private static final Logger log = LoggerFactory.getLogger(TypeRESTController.class);

    private final TypeRepository trepository;
    private ProjectRepository prepository;

    public TypeRESTController(ProjectRepository prepository, TypeRepository trepository) {
        this.prepository = prepository;
        this.trepository = trepository;
    }

    // list of types
    @GetMapping("/types")
    public Iterable<Type> getTypes() {
        log.info("fetch & return types");
        return trepository.findAll();
    }

    // add a new type
    @PostMapping("/types")
    Type newType(@RequestBody Type newType) {
        log.info("save a new type " + newType);
        return trepository.save(newType);
    }

    // find specific type by typeId
    @GetMapping("/types/{typeId}")
    Optional<Type> getTypeById(@PathVariable Long typeId) {
        log.info("Fetching type with ID: " + typeId);
        return trepository.findById(typeId);
    }

    // edit type
    @PutMapping("/types/{typeId}")
    Type editTypeById(@RequestBody Type editedType, @PathVariable Long typeId) {
        log.info("edit type " + editedType);
        editedType.setTypeId(typeId);
        return trepository.save(editedType);
    }

    // delete type
    @DeleteMapping("/types/{typeId}")
    public Iterable<Type> deleteType(@PathVariable Long typeId) {
        log.info("delete type, id = " + typeId);
        trepository.deleteById(typeId);
        return trepository.findAll();
    }

    // find type by typeName
    @GetMapping("/types/name/{typeName}")
    List<Type> getTypeByName(@PathVariable String typeName) {
        log.info("find types, typeName = " + typeName);
        return trepository.findByTypeNameIgnoreCase(typeName);
    }
}
