package pl.kielce.tu.kwiaciarnia.service.item;

import pl.kielce.tu.kwiaciarnia.model.item.Item;

import java.util.List;

public interface AbstractItemService<T extends Item> {
    List<T> findAll();
    String add(T t);
    String update(long id, T t);
    String delete(long id);
}
