

class BinarySearch {
    public static int binarySearch(int[] a, int key) {
        int lo = 0, hi = a.length-1;
        while (lo <= hi) {
            int mid = lo + (hi - lo)/2;
            if (key < a[mid]) hi = mid -1;
            else if (key > a[mid]) lo = mid + 1;
            else return mid;
        }
        return -1;
    }

    public static void main(String[] args) {
        int[] a = { 1,2,3,4,5,6,7,8,9,10 };
        int searchElem = 7;
        int index = 0;
        index = binarySearch(a, searchElem);
        if (index  == -1) {
            System.out.println("The element was not found in the array");
        }
        else {
            System.out.println("The element was found at position " + index);
        }
    }
}
