package org.gr8conf

class AuthorAdderASTTransformationTest extends GroovyTestCase {

    void testASTTransformationShouldBeDebuggableFromIDE() {
        assertScript '''
            class Foo {
            }
            assert Foo.$AUTHOR == 'Cédric Champeau'
        '''
    }
}
