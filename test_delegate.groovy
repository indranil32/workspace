//class testDelegate {
    
//    def testDelegate () {
    
//    }
    
    def execute(Map options = [:], String stepName,  Closure scriptClosure) {
        println stepName
        println options
        scriptClosure.call()
        println "Done - ExecuteStep - testDelegate"
    }
//}

println  "delegate loaded " + this.class

def testFlow = {

    execute("name1", var1:"world1") {
             println "hello1"
    }
    
    execute("name2", var1:"world2") {
     println "hello2"
    }
}

println  "closure loaded " + testFlow


def emcTest = {
    emc ->
        emc.testFlow = { Closure c ->
            c.delegate = this
            c.resolveStrategy = Closure.DELEGATE_FIRST
            c()
        }
}

ExpandoMetaClass emc = new ExpandoMetaClass(testFlow.class, false)
emcTest(emc)
emc.initialize()
testFlow.metaClass = emc
testFlow.call()