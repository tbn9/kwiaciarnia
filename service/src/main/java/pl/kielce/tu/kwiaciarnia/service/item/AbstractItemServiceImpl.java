package pl.kielce.tu.kwiaciarnia.service.item;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import pl.kielce.tu.kwiaciarnia.exception.FailedToDeleteException;
import pl.kielce.tu.kwiaciarnia.exception.FailedToEditException;
import pl.kielce.tu.kwiaciarnia.model.item.Item;

import java.util.List;

@Getter
public abstract class AbstractItemServiceImpl<T extends Item, S extends CrudRepository<T, Long>> implements AbstractItemService<T> {
    private final S repository;

    @Autowired
    public AbstractItemServiceImpl(S repository) {
        this.repository = repository;
    }

    @Override
    public List<T> findAll() {
        return (List<T>) repository.findAll();
    }

    @Override
    public String add(T t) {
        repository.save(t);

        return "Item o podanym id: " + t.getId() + " został pomyślnie dodany";
    }

    @Override
    public String update(long id, T t) {
        return repository.findById(id)
                .map(e -> {
                    t.setId(id);
                    repository.save(t);
                    return "Item o podanym id: " + id + " został pomyślnie zedytowany";
                }).orElseThrow(FailedToEditException::new);
    }

    @Override
    public String delete(long id) {
        return repository.findById(id)
                .map(e -> {
                    repository.deleteById(id);
                    return "Item o podanym id: " + id + " został pomyślnie usunięty";
                }).orElseThrow(FailedToDeleteException::new);
    }

    protected void reduceAmount(T t) {
        repository.findById(t.getId())
                .map(e -> {
                    t.setAmount(e.getAmount() - t.getAmount());
                    return repository.save(t);
                }).orElseThrow(FailedToEditException::new);
    }
}
