package org.gr8conf

class AuthorAdderASTTransformationTest extends GroovyTestCase {

    void testUsingASTTest() {
        assertScript '''import org.gr8conf.Author
            import groovy.transform.ASTTest
            import org.codehaus.groovy.ast.*
            import static org.codehaus.groovy.control.CompilePhase.*

            @Author('John Doe')
            @ASTTest(phase=SEMANTIC_ANALYSIS, value={
                assert classNode instanceof ClassNode
            })
            class Foo {
            }
            assert Foo.$AUTHOR == 'John Doe'
        '''
    }
}
