package org.gr8conf

class AuthorAdderASTTransformationTest extends GroovyTestCase {
    void testThatAuthorExists() {
        assert $AUTHOR == 'Cédric Champeau'
    }

    void testASTTransformationShouldBeDebuggableFromIDE() {
        assertScript '''
            class Foo {
            }
            assert Foo.$AUTHOR == 'Cédric Champeau'
        '''
    }
}
