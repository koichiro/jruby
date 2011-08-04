package org.jruby.test;

import org.jruby.Ruby;
import org.jruby.RubyNKF;

public class TestRubyNKF extends TestRubyBase {
    public TestRubyNKF(final String name) {
        super(name);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        if (runtime == null) {
            runtime = Ruby.newInstance();
        }
    }

    public void testOptParse() throws Exception {
        RubyNKF.CmdCommand cmd = RubyNKF.parseOption("-j");
        assertEquals("[[opt: j longOpt: jis hasArg: false pattern:  value: null]]", cmd.toString());
        cmd = RubyNKF.parseOption("--hiragana");
        assertEquals("[[opt: h1 longOpt: hiragana hasArg: false pattern:  value: null]]", cmd.toString());
        cmd = RubyNKF.parseOption("-j --hiragana");
        assertEquals("[[opt: j longOpt: jis hasArg: false pattern:  value: null], [opt: h1 longOpt: hiragana hasArg: false pattern:  value: null]]", cmd.toString());
        cmd = RubyNKF.parseOption("-Z");
        assertEquals("[[opt: Z longOpt: null hasArg: true pattern: [0-3] value: null]]", cmd.toString());
        cmd = RubyNKF.parseOption("-Z0");
        assertEquals("[[opt: Z longOpt: null hasArg: true pattern: [0-3] value: 0]]", cmd.toString());
        cmd = RubyNKF.parseOption("-Z1");
        assertEquals("[[opt: Z longOpt: null hasArg: true pattern: [0-3] value: 1]]", cmd.toString());
        cmd = RubyNKF.parseOption("--unix");
        assertEquals("[[opt: null longOpt: unix hasArg: false pattern:  value: null]]", cmd.toString());
    }

    public void testMultiShortOptParse() throws Exception {
        RubyNKF.CmdCommand cmd = RubyNKF.parseOption("-jSbw32");
        assertEquals("[[opt: j longOpt: jis hasArg: false pattern:  value: null], [opt: S longOpt: sjis-input hasArg: false pattern:  value: null], [opt: b longOpt: null hasArg: false pattern:  value: null], [opt: w longOpt: null hasArg: true pattern: [0-9][0-9] value: 32]]", cmd.toString());
    }

    public void testLongOptArg() throws Exception {
        RubyNKF.CmdCommand cmd = RubyNKF.parseOption("--ic=ISO-2022-JP");
        assertEquals("[[opt: null longOpt: ic hasArg: true pattern: ic=(.*) value: ISO-2022-JP]]", cmd.toString());
    }
}
