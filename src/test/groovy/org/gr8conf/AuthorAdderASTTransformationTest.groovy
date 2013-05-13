package org.gr8conf

class AuthorAdderASTTransformationTest extends GroovyTestCase {

    void testASTTransformationShouldBeDebuggableFromIDE() {
        assertScript '''import org.gr8conf.Author
            @Author('John Doe')
            class Foo {
            }
            assert Foo.$AUTHOR == 'John Doe'
        '''
    }
}
