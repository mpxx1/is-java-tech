package me.macao.controller;

import lombok.AllArgsConstructor;
import me.macao.service.UserService;

@AllArgsConstructor
public abstract class AbstractCommandChain
    implements CommandChain {

    protected CommandChain nextChain = null;

    @Override
    public void setNext(CommandChain next) {
        nextChain = next;
    }

    @Override
    public void exec(UserService service, String[] line) {
        System.out.println("""
                command list:
                    usradd <ops>
                    lgn <ops>
                    lgt <ops>
                    exit
                
                    // for logged in user
                    allcats
                    addcat
                    dropcat <cat>
                    reqfrnd <ops>
                    cnslreq <ops>
                    getreqs 
                    acptreq <ops>
                    dropfr <ops>
                """);
    }
}
