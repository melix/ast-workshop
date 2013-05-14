package org.gr8conf

class SayHelloASTTransformationTest extends GroovyTestCase {
    void testInsertionOfSayHello() {
        assertScript '''import groovy.transform.ASTTest
            import static org.codehaus.groovy.control.CompilePhase.*

            @ASTTest(phase=SEMANTIC_ANALYSIS,value={
                assert node.getDeclaredMethods('sayHello').size() == 1
            })
            //@groovy.transform.CompileStatic
            class Greeter {
            }

            def greeter = new Greeter()
            greeter.sayHello("Hello, GR8Conf!")
        '''
    }
}
