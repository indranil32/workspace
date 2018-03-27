enum test {
    test1("Hello"),
    test2("World")
    
    def str;
    
    private test (str) {
        this.str = str
    }
}

testClosure = {

    subClosure = {
        def str = test.test1;
        println str
        str
    } 

}

def result1 = testClosure
def result2 = result1.call().subClosure
def result3 = result2.call()
println result3


assert result3 == test.test1
