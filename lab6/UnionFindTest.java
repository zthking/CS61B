import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Test UnionFind.
 */
public class UnionFindTest {

    @Test
    public void testUnionFind() {
        UnionFind t1 = new UnionFind(8);
        t1.union(1,0);
        t1.union(2,1);

        /** Test size.*/
        assertEquals(3, t1.sizeOf(2));

        /**Test parent.*/
        assertEquals(0, t1.parent(1));
        assertEquals(0, t1.parent(2));

        /**Test connected.*/
        assertTrue(t1.connected(1, 2));

        /**Test parent.*/
        t1.union(4,3);
        t1.union(3,2);
        assertEquals(0, t1.find(4));
    }
}
