/**
 * Created by Dim on 03.02.2015.
 */
public class Subset {

    public static void main(String[] args)
    {
        if (args.length != 1) return;
        int k = Integer.parseInt(args[0]);
        String str;
        RandomizedQueue<String> queue = new RandomizedQueue<String>() ;

        while((str = StdIn.readLine()) != null)
        {
            queue.enqueue(str);
        }
        for (int i = 0; i < k; i++)
        {
            System.out.println(queue.dequeue());
        }
    }
}
