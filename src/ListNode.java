import java.util.Comparator;
import java.util.NoSuchElementException;
public class ListNode implements InterfaceNode {


    private static class Node {

        private Comparable value;

        private Node previous;
        private Node next;

        private Node( Comparable value, Node previous, Node next ) {
            this.value = value;
            this.previous = previous;
            this.next = next;
        }
    }

    // Instance variables

    private Node head;
    private Node tail;

    // Representation of the empty list.

    public ListNode() {
        head = null;
        tail = null;
    }

    // Calculates the size of the list

    public int size() {
        Node newnode = head;
        int count = 0;
        while ( newnode!=null ) {
            newnode = newnode.next;
            count++;
        }
        return count;
    }



    public boolean add( Comparable obj ) {

        if ( obj == null ) {
            throw new IllegalArgumentException( "null" );
        }

        if ( head == null ) {
            head = tail = new Node(obj, null, null);

        } else if ( head.value.compareTo(obj) >= 0 ) {
            head = new Node(obj , null, head );
            head.next.previous = head;

        } else {

            Node newnode = head;

            while ( newnode.next != null && newnode.next.value.compareTo(obj) < 0 ) {
                newnode = newnode.next;
            }

            if ( newnode.next == null ) { // adding at the end of the list
                tail.next = new Node( obj, tail, null );
                tail = tail.next;

            } else {
                Node q = newnode.next; // the node that follows
                newnode.next = new Node(obj , newnode, q );
                q.previous = newnode.next;
            }
        }
        return true;
    }



    public Object get( int pos ) {
        if (pos < 0) {
            throw new IndexOutOfBoundsException( Integer.toString( pos ) );
        }
        Node newnode = head;
        for ( int i=0; i<pos; i++ ) {
            if ( newnode == null ) {
                throw new IndexOutOfBoundsException( Integer.toString( pos ) );
            } else {
                newnode = newnode.next;
            }
        }
        return newnode.value;
    }

    public void remove( int pos ) {
        if ( pos < 0 ) {
            throw new IndexOutOfBoundsException( Integer.toString( pos ) );
        }
        Node newnode = head;

        if ( pos == 0 ) {
            if ( head == null ) {
                throw new IndexOutOfBoundsException( Integer.toString( pos ) );
            }
            head = head.next;

            if ( head == null ) {
                tail = null;
            } else {
                head.previous = null;
            }
            newnode.value = null;
            newnode.next = null;

        } else {
            for ( int i=0; i<pos; i++ )
                if ( newnode == null ) {
                    throw new IndexOutOfBoundsException(Integer.toString(pos));
                } else {
                    newnode = newnode.next;
                }

            Node del = newnode;
            newnode = newnode.previous;
            Node nodeNext = del.next;

            newnode.next = nodeNext;

            if ( del == tail ) {
                tail = newnode;
            } else {
                nodeNext.previous = newnode;
            }

            del.value = null;
            del.next = null;
            del.previous = null;

        }
    }


    public void merge( ListNode other ) {
        Node node = head.next;
        Node nodeNext = other.head.next;

        while ( nodeNext != head ) {
            if ( node == null ) {
                head = tail = new Node( nodeNext.value, null, null );
                node = head;
                nodeNext = nodeNext.next;
            } else if ( nodeNext.value.compareTo( node.value ) < 0) {
                if ( node == head ) {
                    head = new Node( nodeNext.value, null, head );
                    node.previous = head;
                } else {
                    node.previous.next = new Node( nodeNext.value, node.previous, node );
                    node.previous = node.previous.next;
                }
                nodeNext = nodeNext.next;
            } else if ( node.next == head ) {
                node.next = new Node( nodeNext.value, node, null );
                tail = node.next;
                node = node.next;
                nodeNext = nodeNext.next;
            } else {
                node = node.next;
            }
        }
    }
}

