package org.gr8conf

class AuthorAdderASTTransformationTest extends GroovyTestCase {

    void testASTTransformationShouldBeDebuggableFromIDE() {
        assertScript '''import org.gr8conf.Author
            @Author
            class Foo {
            }
            assert Foo.$AUTHOR == 'Cédric Champeau'
        '''
    }
}
