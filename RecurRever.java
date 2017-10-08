import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.lang.reflect.Array;

public class RecurRever {

	private static<T> T[] concatArrays(T[] a, T[] b) {

		List<T> lst = new ArrayList<T>();
		
		Collections.addAll(lst, a);
		Collections.addAll(lst, b);
 
		return (T[])lst.toArray(a); // revisit this, why casting like that? 

	}

	private static<T> T[] headArray(T[] a) {

		T[] h = (T[])Array.newInstance(a[0].getClass(), 1);   // Only one element with head
		h[0] = a[0];

		return (T[])h; 
	}

	private static<T> T[] tailArray(T[] a) {
		
		int i = 1;      //start index
		int j = a.length - 1; // end index 

		T[] subArray = (T[])Array.newInstance(a[0].getClass(), j);
		for (int x=0; x<j; x++) {
			subArray[x] = a[x+1];
		} 
		return (T[])subArray;
	
	}

	public static <T> T[] reverse(T[] a) {


        if (a.length==1) {
            return a;
        } else {
			
			T[] head = headArray(a);
			T[] tail = tailArray(a);

			return (T[])concatArrays(reverse(tail), head);
        }
	} 
    public static <T> void display(T[] i) {
        for (T x: i) {
            System.out.println(x);
        }
    }
	public static void main(String[] args) {


		String[] a = {"hello", "world"};
		String[] b = {"ab", "xy"};

		Integer[] i = {100, 200, 300};

		String[] x = RecurRever.reverse(a);
		display(x);

		Integer[] y = RecurRever.reverse(i);
		display(y);

	}
}
