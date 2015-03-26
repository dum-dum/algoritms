import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Created by Dim on 03.02.2015.
 */
public class RandomizedQueue<Item> implements Iterable<Item> {

    private List<Item> list;

    public RandomizedQueue()                 // construct an empty randomized queue
    {
       list = new LinkedList<Item>();
    }

    public boolean isEmpty()                 // is the queue empty?
    {
        return list.isEmpty();
    }

    public int size()                        // return the number of items on the queue
    {
         return list.size();
    }

    public void enqueue(Item item)           // add the item
    {
        if (item == null) throw new NullPointerException();
        list.add(item);
    }

    public Item dequeue()                    // delete and return a random item
    {
        if (isEmpty()) throw new NoSuchElementException();
        int i = StdRandom.uniform(list.size());
        Item item = list.get(i);
        list.remove(i);
        return item;
    }

    public Item sample()                     // return (but do not delete) a random item
    {
        if (isEmpty()) throw new NoSuchElementException();
        return list.get(StdRandom.uniform(list.size()));
    }

    public Iterator<Item> iterator()         // return an independent iterator over items in random order
    {
        Iterator<Item> it = new Iterator<Item>() {

            private LinkedList<Item> currentList = new LinkedList<Item>(list);   //copy list

            @Override
            public boolean hasNext() {
                return !currentList.isEmpty();
            }

            @Override
            public Item next()
            {
                if (!hasNext()) throw new NoSuchElementException();
                int i = StdRandom.uniform(currentList.size());
                Item item = currentList.get(i);
                currentList.remove(i);
                return item;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
        return it;
    }

    public static void main(String[] args)   // unit testing
    {
        RandomizedQueue<Integer> queue = new RandomizedQueue<Integer>();
        for (int i = 0; i < 10 ; i++) {
            queue.enqueue(i);
        }

        for (Integer i : queue){
            System.out.print(i + " ");
        }
    }
}