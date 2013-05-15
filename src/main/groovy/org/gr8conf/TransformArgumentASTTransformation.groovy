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
package org.gr8conf

import org.codehaus.groovy.ast.ASTNode
import org.codehaus.groovy.ast.AnnotationNode
import org.codehaus.groovy.ast.ClassHelper
import org.codehaus.groovy.ast.ClassNode
import org.codehaus.groovy.ast.FieldNode
import org.codehaus.groovy.ast.MethodNode
import org.codehaus.groovy.ast.Parameter
import org.codehaus.groovy.ast.builder.AstBuilder
import org.codehaus.groovy.ast.expr.BinaryExpression
import org.codehaus.groovy.ast.expr.ClosureExpression
import org.codehaus.groovy.ast.expr.ConstantExpression
import org.codehaus.groovy.ast.expr.MethodCallExpression
import org.codehaus.groovy.ast.expr.VariableExpression
import org.codehaus.groovy.ast.stmt.BlockStatement
import org.codehaus.groovy.ast.stmt.ExpressionStatement
import org.codehaus.groovy.ast.stmt.Statement
import org.codehaus.groovy.control.CompilePhase
import org.codehaus.groovy.control.SourceUnit
import org.codehaus.groovy.syntax.SyntaxException
import org.codehaus.groovy.syntax.Token
import org.codehaus.groovy.transform.AbstractASTTransformation
import org.codehaus.groovy.transform.GroovyASTTransformation

@GroovyASTTransformation(phase = CompilePhase.SEMANTIC_ANALYSIS)
class TransformArgumentASTTransformation extends AbstractASTTransformation {
    private static final ClassNode ARGTRANSFORMER_TYPE = ClassHelper.make(ArgTransformer)

    @Override
    public void visit(final ASTNode[] nodes, final SourceUnit source) {
        if (nodes.length != 2) return
        if (nodes[0] instanceof AnnotationNode && nodes[1] instanceof MethodNode) {
            transformMethod(nodes[1])
        }
    }

    void transformMethod(MethodNode node) {
        def code = createBlock(node.code)
        node.parameters.each { param ->
            def anns = param.getAnnotations(ARGTRANSFORMER_TYPE)
            if (anns) {
                def firstArgValue = anns[0].getMember('value')
                if (firstArgValue instanceof ClosureExpression) {
                    mapArgument(code, param, firstArgValue)
                }
            }
        }
    }

    void mapArgument(BlockStatement code, Parameter parameter, ClosureExpression argumentMapper) {
        def assignment = new BinaryExpression(
                new VariableExpression(parameter),
                Token.newSymbol("=", -1, -1),
                createMappingMethodCall(argumentMapper, parameter)
        )
        code.statements.add(0, new ExpressionStatement(assignment))
    }

    private MethodCallExpression createMappingMethodCall(ClosureExpression argumentMapper, Parameter parameter) {
        new MethodCallExpression(argumentMapper, "call", new VariableExpression(parameter))
    }

    private static BlockStatement createBlock(Statement code) {
        if (code instanceof BlockStatement) return code;
        BlockStatement block = new BlockStatement()
        block.addStatement(code)

        block
    }
}
