package me.macao.controller;

public class HelpCommandChain
    extends AbstractCommandChain {
    public HelpCommandChain(CommandChain nextChain) {
        super(nextChain);
    }
}
