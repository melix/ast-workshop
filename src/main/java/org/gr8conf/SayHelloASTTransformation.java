/*
 * Copyright 2003-2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.gr8conf;

import org.codehaus.groovy.ast.*;
import org.codehaus.groovy.ast.expr.*;
import org.codehaus.groovy.ast.stmt.ExpressionStatement;
import org.codehaus.groovy.classgen.VariableScopeVisitor;
import org.codehaus.groovy.control.CompilePhase;
import org.codehaus.groovy.control.SourceUnit;
import org.codehaus.groovy.transform.AbstractASTTransformation;
import org.codehaus.groovy.transform.GroovyASTTransformation;

import java.util.List;

@GroovyASTTransformation(phase= CompilePhase.SEMANTIC_ANALYSIS)
public class SayHelloASTTransformation extends AbstractASTTransformation {
    @Override
    public void visit(final ASTNode[] nodes, final SourceUnit source) {
        List<ClassNode> classes = source.getAST().getClasses();
        for (ClassNode classNode : classes) {
            addHelloMethod(classNode, source);
        }
    }

    /**
     * For a given class node, adds the following method:
     * <code>void sayHello(String message, boolean shout=false) { println(shout?message.toUpperCase():message)}</code>
     * @param classNode the class node where to add the method
     * @param source the source unit
     */
    private void addHelloMethod(final ClassNode classNode, final SourceUnit source) {
        MethodCallExpression toUpperCaseCall = new MethodCallExpression(new VariableExpression("message"), "toUpperCase", ArgumentListExpression.EMPTY_ARGUMENTS);
        toUpperCaseCall.setLineNumber(666);
        toUpperCaseCall.setColumnNumber(666);
        toUpperCaseCall.setImplicitThis(false);
        TernaryExpression conditional = new TernaryExpression(
                new BooleanExpression(new VariableExpression("shout")),
                toUpperCaseCall,
                new VariableExpression("message")
        );
        ExpressionStatement stmt = new ExpressionStatement(
                new MethodCallExpression(new VariableExpression("this"), "println", conditional)
        );
        MethodNode method = classNode.addMethod("sayHello",
                ACC_PUBLIC,
                ClassHelper.VOID_TYPE,
                new Parameter[]{
                        new Parameter(ClassHelper.STRING_TYPE, "message"),
                        new Parameter(ClassHelper.boolean_TYPE, "shout", new ConstantExpression(false))},
                ClassNode.EMPTY_ARRAY,
                stmt);
        VariableScopeVisitor visitor = new VariableScopeVisitor(source);
        visitor.prepareVisit(classNode);
        visitor.visitMethod(method);
    }
}
