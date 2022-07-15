package DS;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class MyArrayList<E> implements Iterable<E> {
    /**
     * 真正存储数据的底层数组
     */
    private E[] data;

    /**
     * 记录当前元素个数
     */
    private int size;

    /**
     * 默认初始容量
     */
    private static final int INIT_CAP = 1;

    public MyArrayList() {
        this(INIT_CAP);
    }

    @SuppressWarnings("unchecked")
    public MyArrayList(int initCapacity) {
        data = (E[]) new Object[initCapacity];
        this.size = 0;
    }

    /**
     * 增
     * @param index
     * @param e
     */
    public void add(int index, E e) {
        // 检查索引越界
        checkPositionIndex(index);

        int cap = data.length;
        // 是否需要扩容
        if(size == cap) {
            resize(2 * cap);
        }

        // 移动数据 data[index..] -> data[index+1..]
        System.arraycopy(data, index, data, index + 1, size - index);

        // 插入数据
        data[index] = e;
        size++;
    }

    public void addLast(E e) {
        int cap = data.length;

        // 是否需要扩容
        if(size == cap) {
            resize(2 * cap);
        }

        // 在尾部插入元素
        data[size] = e;
        size++;
    }

    public void addFirst(E e) {
        add(0, e);
    }

    /**
     * 删
     * @param index
     * @return
     */
    public E remove(int index) {
        // 检查索引越界
        checkElementIndex(index);

        int cap = data.length;
        // 是否需要缩容
        if(size == cap / 4) {
            resize(cap / 2);
        }
        E deteleVal = data[index];
        // 移动数据 data[index+1..] -> data[index..]
        System.arraycopy(data, index, data, index - 1, size - index - 1);

        data[size - 1] = null;
        size--;

        return deteleVal;
    }

    public E removeLast() {
        if (size == 0) {
            throw new NoSuchElementException();
        }

        int cap = data.length;
        // 是否需要缩容
        if(size == cap / 4) {
            resize(cap / 2);
        }
        E deletedVal = data[size - 1];
        // 删除最后一个元素
        data[size - 1] = null;
        size--;

        return deletedVal;
    }

    public E removeFirst() {
        return remove(0);
    }

    /**
     * 改
     * @param index
     * @return
     */
    public E set(int index, E e) {
        // 检查索引越界
        checkElementIndex(index);
        // 修改数据
        E oldVal = data[index];
        data[index] = e;
        return oldVal;
    }

    /**
     * 查
     * @param index
     * @return
     */
    public E get(int index) {
        // 检查索引越界
        checkElementIndex(index);

        return data[index];
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    private boolean isPositionIndex(int index) {
        return index >= 0 && index <= size;
    }

    private boolean isElementIndex(int index) {
        return index >= 0 && index < size;
    }

    /**
     * 检查 index 索引位置是否可以添加元素
     * @param index
     */
    private void checkPositionIndex(int index) {
        if(!isPositionIndex(index)) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }

    /**
     * 检查 index 索引位置是否可以存在元素
     * @param index
     */
    private void checkElementIndex(int index) {
        if(!isElementIndex(index)) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }

    /**
     * 扩容缩容
     * @param newCap
     */
    @SuppressWarnings("unchecked")
    private void resize(int newCap) {
        if(size > newCap) {
            return;
        }
        
        E[] temp = (E[]) new Object[newCap];

        for (int i = 0; i < size; i++) {
            temp[i] = data[i];
        }

        data = temp;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private int p = 0;

            @Override
            public boolean hasNext() {
                return p != size;
            }

            @Override
            public E next() {
                return data[p++];
            }
        };
    }

    public static void main(String[] args) {
        MyArrayList<Integer> list = new MyArrayList<>(3);
        for (int i = 0; i < 5; i++) {
            list.addLast(i);
        }

        list.remove(3);
        list.add(1, 9);
        list.addFirst(100);
        int val = list.removeLast();

        Iterator<Integer> iterator = list.iterator();
        while(iterator.hasNext()) {
            System.out.println(iterator.next());
        }

        for (Integer integer : list) {
            System.out.println(integer);
        }
    }
}
