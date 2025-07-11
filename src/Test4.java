
/**
 * Teste les méthodes size, add, get et remove
 */

class Test4 {

    public static void main( String[] args ) {

        ListNode node = new ListNode();
        if ( node.size() != 0 )
            System.out.println( "List not empty " );

        for ( int i=1; i<=10; i++ ) { // add
            node.add( new Integer( i ) );

            if ( node.size() != i )
                System.out.println( "size # " + i );
        }


        for ( int i=0; i<10; i++ ) {
            int m = ( (Integer) node.get( i ) ).intValue();
            if ( m != i ) {
                System.out.println( "test 1 failed, i = " + i );
            }
        }


        for ( int i=0; i<10 && node.size() > 0; i++ ) {
            int m = ( (Integer) node.get( 0 ) ).intValue();
            if ( m != i ) {
                System.out.println("test 2 failed, i = " + i);
            }
            node.remove( 0 );
        }

        System.out.println( "tested!" );
    }
}
