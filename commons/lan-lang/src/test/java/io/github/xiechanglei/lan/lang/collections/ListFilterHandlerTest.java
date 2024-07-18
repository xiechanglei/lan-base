package io.github.xiechanglei.lan.lang.collections;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class ListFilterHandlerTest {
    @Test
    public void testFilter() {
        List<String> origin = Arrays.asList("a", "b", "c", "d", "e");
        List<String> filter = Arrays.asList("a", "c", "e", "f", "g");
        ListFilterHandler<String> res = ListFilterHandlerBuilder.from(origin).filter(filter);
        List<String> addList = res.getAddList();
        List<String> removeList = res.getDeleteList();
        List<String> retainList = res.getRetainList();
        List<String> updateList = res.getUpdateList();
        assert addList.size() == 2;
        assert addList.contains("f");
        assert addList.contains("g");
        assert removeList.size() == 2;
        assert removeList.contains("b");
        assert removeList.contains("d");
        assert retainList.size() == 3;
        assert retainList.contains("a");
        assert retainList.contains("c");
        assert retainList.contains("e");
        assert updateList.isEmpty();
    }

    @AllArgsConstructor
    @Getter
    public static class Animal{
        private String name;
        private int age;
        private String type;
    }

    @Test
    public void testFilter2() {
        Animal a1 = new Animal("a", 1, "dog");
        Animal a2 = new Animal("b", 2, "cat"); // delete
        Animal a3 = new Animal("c", 3, "dog");
        Animal a4 = new Animal("d", 4, "cat"); // delete
        Animal a5 = new Animal("e", 5, "dog");
        List<Animal> origin = Arrays.asList(a1, a2, a3, a4, a5);
        Animal a6 = new Animal("a", 1, "dog"); // old
        Animal a7 = new Animal("c", 3, "dog"); // old
        Animal a8 = new Animal("e", 5, "cat"); // old update
        Animal a9 = new Animal("f", 6, "dog"); // new
        Animal a10 = new Animal("g", 7, "dog"); // new
        List<Animal> filter = Arrays.asList(a6, a7, a8, a9, a10);
        ListFilterHandler<Animal> res = ListFilterHandlerBuilder.from(origin)
                .keyCompareFunction(Animal::getName)
                .updateCompareFunction((originData, nw) -> originData.getType().equals(nw.getType()) ? null : nw)
                .filter(filter);
        List<Animal> addList = res.getAddList();
        List<Animal> removeList = res.getDeleteList();
        List<Animal> retainList = res.getRetainList();
        List<Animal> updateList = res.getUpdateList();
        assert addList.size() == 2;
        assert addList.contains(a9);
        assert addList.contains(a10);
        assert removeList.size() == 2;
        assert removeList.contains(a2);
        assert removeList.contains(a4);
        assert retainList.size() == 3;
        assert retainList.contains(a6);
        assert retainList.contains(a7);
        assert retainList.contains(a8);
        assert updateList.size() == 1;
        assert updateList.contains(a8);
    }
}
