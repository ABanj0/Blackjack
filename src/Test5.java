/**
 * Teste toutes les méthodes de la classe ListNode y compris merge
 */

class Test5 {

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

        /*remove*/
        for ( int i=0; i<10 && node.size() > 0; i++ ) {
            int m = ( (Integer) node.get( 0 ) ).intValue();
            if ( m != i ) {
                System.out.println("test 2 failed, i = " + i);
            }
            node.remove( 0 );
        }

        /*add */
        for ( int i=10; i>=1; i--  ) { // add
            node.add( new Integer( i ) );

            if ( node.size() != 10 - i + 1 )
                System.out.println( "size # " + i );
        }

        /**merge**/
        ListNode node2 = new ListNode();

        for ( int i=1; i<10; i=i+2 ) {
            node2.add( new Integer( i ) );
        }

        node.merge( node2 );

        System.out.println("===========================================");
        for ( int i=0; i<15; i++ ) {
            int m = ( (Integer) node.get( i ) ).intValue();
            if (m != i) {
                System.out.println("test 3 failed, node.get( i ) = " + node.get( i ));
            }
        }
        System.out.println( "tested!" );
    }
}
