package org.gr8conf

class MirrorModeASTTransformationTest extends GroovyTestCase {

    void testShouldMirrorMethodCall() {
        assertScript '''import org.gr8conf.MirrorMode
            class Foo {
                @MirrorMode
                void test() {
                    nltnirp 'Mirror mode!'
                }
            }
            new Foo().test()
        '''
    }
}
