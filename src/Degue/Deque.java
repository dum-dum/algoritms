import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by Dim on 03.02.2015.
 */
public class Deque<Item> implements Iterable<Item> {

    private Item[] array;
    private int indexFirst = 5;         //указывает на индекс первого обьекта еще пуст
    private int indexLast = 5;          // указывает на индекс последнего обьекта уже заполнен

    public Deque()                           // construct an empty deque
    {
        array = (Item[])new Object[10];
    }

    public boolean isEmpty()                 // is the deque empty?
    {
        if (array == null || indexFirst == indexLast) return true;
        return false;
    }

    public int size()                        // return the number of items on the deque
    {
        return (indexLast - indexFirst);
    }

    public void addFirst(Item item)          // insert the item at the front
    {
        if (item == null) throw new NullPointerException();

        array[indexFirst--] = item;

        if (indexFirst < 0) resize();
    }

    public void addLast(Item item)           // insert the item at the end
    {
        if (item == null) throw new NullPointerException();
        array[++indexLast] = item;
        if (indexLast == array.length - 1) resize();
    }

    public Item removeFirst()                // delete and return the item at the front
    {
        if (isEmpty()) throw new NoSuchElementException();
        Item item = array[++indexFirst];
        array[indexFirst] = null;
        if (size() <= array.length / 4 && array.length > 10) resize();
        return item;
    }

    public Item removeLast()                 // delete and return the item at the end
    {
        if (isEmpty()) throw new NoSuchElementException();
        Item item = array[indexLast];
        array[indexLast--] = null;
        if (size() <= array.length / 4 && array.length > 10) resize();
        return item;
    }

    @Override
    public Iterator<Item> iterator()         // return an iterator over items in order from front to end
    {
        Iterator<Item> it = new Iterator<Item>() {

            private int currentIndex = indexFirst + 1;

            @Override
            public boolean hasNext() {
                return currentIndex <= indexLast;
            }

            @Override
            public Item next() {
                if (!hasNext()) throw new NoSuchElementException();
                return array[currentIndex++];
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
        Deque<Integer> deck = new Deque<Integer>();
        for (int i = 0; i < 100 ; i++) {
            if (i % 2 == 0) deck.addFirst(i);
            else deck.addLast(i);
        }

        int k = deck.size();
        for (int i = 0; i < k; i++) {
            System.out.print(deck.removeLast()+ " ");
        }
        System.out.println();
        for (Integer i : deck)
        {
            System.out.print(i + " ");
        }
    }

    private void resize()
    {
        int sz;

        if (size() > array.length / 2) sz = array.length * 2;
        else if (size() < array.length / 4) sz = array.length / 2;
        else sz = array.length;
        if (sz < 10) sz = 10;

        Item[] newArray = (Item[]) new Object[sz];
        int newFirst = (sz - size()) / 2;
        int count = newFirst;

        for (int i = indexFirst + 1; i <= indexLast; i++)
        {
            newArray[count++] = array[i];
        }

        indexFirst = newFirst - 1;
        indexLast = count - 1;
        array = newArray;
    }
}
