/**
 * Teste les méthodes size, add et get
 */

class Test3 {

    public static void main( String[] args ) {

        ListNode node = new ListNode();
        if ( node.size() != 0 )
            System.out.println( "List not empty " );

        for ( int i=1; i<=10; i++ ) { // add
            node.add( new Integer( i ) );

            if ( node.size() == i )
                System.out.println( "size = " + i );
        }
        node.add( new Integer( 5 ) );// add


        for ( int i=0; i<11; i++ ) {
            int m = ( (Integer) node.get( i ) ).intValue();
            if ( m != i ) {
                System.out.println( "test failed, i = " + i );
            }
            System.out.println( " Pour i  =" +i  +" ; get( " + i + " ) = " + node.get( i ) );
        }


        System.out.println( "tested!" );
    }
}
