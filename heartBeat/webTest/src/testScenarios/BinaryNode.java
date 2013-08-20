package testScenarios;

/**
 * Description
 * <p/>
 * User: Hunea Lucian | Email : hunealucian@gmail.com
 * Date: 8/20/13
 */
final class BinaryNode<AnyType>
{
    public BinaryNode( )
    {
        this( null, null, null );
    }

    public BinaryNode( AnyType theElement, BinaryNode<AnyType> lt, BinaryNode<AnyType> rt )
    {
        element = theElement;
        left    = lt;
        right   = rt;
    }

    /**
     * Return the size of the binary tree rooted at t.
     */
    public static <AnyType> int size( BinaryNode<AnyType> t )
    {
        if( t == null )
            return 0;
        else
            return 1 + size( t.left ) + size( t.right );
    }

    /**
     * Return the height of the binary tree rooted at t.
     */
    public static <AnyType> int height( BinaryNode<AnyType> t )
    {
        if( t == null )
            return -1;
        else
            return 1 + Math.max( height( t.left ), height( t.right ) );
    }

    // Print tree rooted at current node using preorder traversal.
    public void printPreOrder( )
    {
        System.out.println( element );       // Node
        if( left != null )
            left.printPreOrder( );           // Left
        if( right != null )
            right.printPreOrder( );          // Right
    }


    // Print tree rooted at current node using postorder traversal.
    public void printPostOrder( )
    {
        if( left != null )
            left.printPostOrder( );          // Left
        if( right != null )
            right.printPostOrder( );         // Right
        System.out.println( element );       // Node
    }

    // Print tree rooted at current node using inorder traversal.
    public void printInOrder( )
    {
        if( left != null )
            left.printInOrder( );            // Left
        System.out.println( element );       // Node
        if( right != null )
            right.printInOrder( );           // Right
    }


    /**
     * Return a reference to a node that is the root of a
     * duplicate of the binary tree rooted at the current node.
     */
    public BinaryNode<AnyType> duplicate( )
    {
        BinaryNode<AnyType> root = new BinaryNode<AnyType>( element, null, null );

        if( left != null )            // If there's a left subtree
            root.left = left.duplicate( );    // Duplicate; attach
        if( right != null )          // If there's a right subtree
            root.right = right.duplicate( );  // Duplicate; attach
        return root;                      // Return resulting tree
    }

    public AnyType getElement( )
    {
        return element;
    }

    public BinaryNode<AnyType> getLeft( )
    {
        return left;
    }

    public BinaryNode<AnyType> getRight( )
    {
        return right;
    }

    public void setElement( AnyType x )
    {
        element = x;
    }

    public void setLeft( BinaryNode<AnyType> t )
    {
        left = t;
    }

    public void setRight( BinaryNode<AnyType> t )
    {
        right = t;
    }

    private AnyType             element;
    private BinaryNode<AnyType> left;
    private BinaryNode<AnyType> right;
}
