package com.ck.unit

/**
 * Created by ٩ on 2015/6/23.
 */
class BoundedBufferTest extends GroovyTestCase {
    void testIsEmpty() {
        BoundedBuffer<Integer> bb = new BoundedBuffer<Integer>(10);
        assertTrue(bb.isEmpty());
        assertFalse(bb.isFull());
    }

    void testIsFull() {
        BoundedBuffer<Integer>  bb = new BoundedBuffer<Integer>(10);
        for (int i = 0; i < 10; i++) {
             bb.put(i);
        }
        assertTrue(bb.isFull());
        assertFalse(bb.isEmpty());
    }


}
