package org.gr8conf

class FieldToListASTTransformationTest extends GroovyTestCase {

    void testShouldTransformFieldToList() {
        assertScript '''import org.gr8conf.FieldToList
            import groovy.transform.ASTTest
            import static org.codehaus.groovy.control.CompilePhase.INSTRUCTION_SELECTION

            @ASTTest(phase=INSTRUCTION_SELECTION,value={
                // insert code checking that field called "name" is now of type List<String>
            })
            class Foo {
                @FieldToList String name
            }
            def foo = new Foo()
            foo.name = ['a','b','c']
            def clazz = foo.name.class
            assert List.isAssignableFrom(clazz)
        '''
    }
}
