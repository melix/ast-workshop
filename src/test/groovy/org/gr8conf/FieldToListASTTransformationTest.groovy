package org.gr8conf

class FieldToListASTTransformationTest extends GroovyTestCase {

    void testShouldTransformFieldToList() {
        assertScript '''import org.gr8conf.FieldToList
            import groovy.transform.ASTTest
            import static org.codehaus.groovy.control.CompilePhase.INSTRUCTION_SELECTION
            import static org.codehaus.groovy.ast.ClassHelper.*

            @ASTTest(phase=INSTRUCTION_SELECTION,value={
                def field = node.getDeclaredField('name')
                assert field.type == LIST_TYPE
                assert field.type.isUsingGenerics()
                def generics = field.type.genericsTypes
                assert generics.length==1
                assert generics[0].type == STRING_TYPE
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
