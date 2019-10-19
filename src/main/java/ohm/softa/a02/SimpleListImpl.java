package ohm.softa.a02;

import java.util.Iterator;

/**
 * @author Peter Kurfer
 * Created on 10/6/17.
 */
public class SimpleListImpl implements SimpleList, Iterable {

    private Element head = null;

    @Override
    public Iterator iterator() {
        return new SimpleIteratorImpl(this);
    }

    static private class Element
    {
        Object item;
        Element next;

        public Element(Object item, Element next)
        {
            this.item = item;
            this.next = next;
        }
    }

    @Override
    public void add(Object item)
    {
        head = new Element(item, head);
    }

    @Override
    public int size()
    {
        Element current = head;
        int counter = 0;

        while(current != null)
        {
            current = current.next;
            counter++;
        }

        return counter;
    }

    // how should i implement this
    @Override
    public SimpleList filter(SimpleFilter filter)
    {
        SimpleListImpl filteredList = new SimpleListImpl();

        Element current = head;
        while(current != null)
        {
            if(filter.include(current.item))
                filteredList.add(current.item);

            current = current.next;
        }
        return filteredList;
    }

    private class SimpleIteratorImpl implements Iterator
    {
        Element current;
        SimpleListImpl list;

        private SimpleIteratorImpl(SimpleListImpl list)
        {
            this.list = list;
        }

        @Override
        public boolean hasNext() {
            if(current == null)
                current = list.head;
            else
                current = current.next;

            return current != null;
        }

        @Override
        public Object next() {
            return current.item;
        }
    }

    public static void main(String[] args) {
        SimpleListImpl list = new SimpleListImpl();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);

        for (Object i : list){
            System.out.println(i);
        }

        SimpleListImpl result = (SimpleListImpl) list.filter(new SimpleFilter() {
            @Override
            public boolean include(Object item) {
                int current = (int)item;
                return current > 2;
            }
        });

        System.out.println("=========");
        for (Object i : result){
            System.out.println(i);
        }
    }
}
