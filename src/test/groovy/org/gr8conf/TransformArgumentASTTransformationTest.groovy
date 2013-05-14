package org.gr8conf

class TransformArgumentASTTransformationTest extends GroovyTestCase {

    void testTransformArgument() {
        assertScript '''import org.gr8conf.TransformArgument
            class Foo {
                def factor
                @TransformArgument({ it*factor })
                def sum(x,y) { x + y}
            }
            def foo = new Foo(factor:2)
            assert foo.sum(1,2) == 6
            assert foo.sum("a","b") == "aabb"
        '''
    }

}
