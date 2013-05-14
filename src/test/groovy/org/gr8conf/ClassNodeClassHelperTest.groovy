package org.gr8conf

import groovyjarjarasm.asm.Opcodes
import org.codehaus.groovy.ast.ClassHelper
import org.codehaus.groovy.ast.ClassNode

class ClassNodeClassHelperTest extends GroovyTestCase implements Opcodes {

    void testCreateStringClassNodeFromScratch() {
        ClassNode stringClassNode = new ClassNode("java.lang.String", ACC_PUBLIC, ClassHelper.OBJECT_TYPE)
        assert stringClassNode.getDeclaredMethods("toUpperCase").isEmpty()
    }

    void testCreateStringClassNodeUsingClass() {
        ClassNode stringClassNode = new ClassNode(String)
        assert stringClassNode.getDeclaredMethods("toUpperCase").size() == 2
    }

    void testCreateClassNodeUsingClassHelper() {
        ClassNode stringClassNode = ClassHelper.make(String)
        assert stringClassNode.getDeclaredMethods("toUpperCase").size() == 2
    }

    void testCreateClassNodeUsingClassHelperWithoutCaching() {
        ClassNode stringClassNode = ClassHelper.makeWithoutCaching(String)
        assert stringClassNode.getDeclaredMethods("toUpperCase").size() == 2
    }

    void testCompareInstancesOfClassHelper() {
        ClassNode stringClassNode = new ClassNode(String)
        ClassNode cachedClassNode = ClassHelper.make(String)
        ClassNode uncachedClassNode = ClassHelper.makeWithoutCaching(String)
        assert !stringClassNode.is(cachedClassNode)
        assert !stringClassNode.is(uncachedClassNode)
        assert cachedClassNode.is(uncachedClassNode)
        assert cachedClassNode.is(ClassHelper.make(String))
        assert uncachedClassNode.is(ClassHelper.makeWithoutCaching(String))
    }

    void testMakeWithoutCachingBehaviour() {
        ClassNode notCached = ClassHelper.makeWithoutCaching("foo.Bar")
        ClassNode cached = ClassHelper.make("foo.Bar")
        assert !notCached.is(cached)
        assert ClassHelper.makeWithoutCaching("foo.Bar") == cached
    }
}
