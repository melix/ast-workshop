package org.gr8conf

class TransformArgumentASTTransformationTest extends GroovyTestCase {

    void testTransformArgument() {
        assertScript '''import org.gr8conf.TransformArgument
            import org.gr8conf.ArgTransformer

            class Foo {
                def factor
                @TransformArgument
                def sum(
                    @ArgTransformer({ it*factor }) x,
                    @ArgTransformer({ it*factor })y) {
                        x + y
                }
            }
            def foo = new Foo(factor:2)
            assert foo.sum(1,2) == 6
            assert foo.sum("a","b") == "aabb"
        '''
    }

}
